package com.byhiras.AuctionTracker.api;

import com.byhiras.AuctionTracker.model.Account;
import com.byhiras.AuctionTracker.model.Bid;
import com.byhiras.AuctionTracker.model.Item;
import com.byhiras.AuctionTracker.repository.AccountRepository;
import com.byhiras.AuctionTracker.repository.BidRepository;
import com.byhiras.AuctionTracker.repository.ItemRepository;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;

import static io.restassured.RestAssured.with;
import static java.net.HttpURLConnection.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApiControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Gson gson;
    private Item item;
    private Account account;
    private Bid bid;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.gson = new Gson();

        account = new Account();
        account.setAccountId(1L);

        item = new Item();
        item.setName("test item");
        item.setItemId(1L);

        bid = new Bid();
        bid.setBidId(1L);
        bid.setAmount(new BigDecimal(12));
        bid.setAccount(account);
        bid.setItem(item);
    }

    private String uri;

    @PostConstruct
    public void init() { uri = "http://localhost:" + port;}

    @Test
    public void placeBidOnItem() {

        String payload = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        assertThat(bidRepository.findById(1L).isPresent());
        assertThat(bid.equals(bidRepository.findById(1L).get()));
    }

    @Test
    public void replaceBidOnItem() {

        String payload = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        String updatedPayload = "{\n" +
                "  \"amount\": \"13.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .body(updatedPayload)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        assertThat(bidRepository.findById(1L).isPresent()); //TODO: ergh DRY
        assertThat(bidRepository.findById(1L)
                .get().getAmount()
                .compareTo(new BigDecimal(13)));
    }

    @Test
    public void placeMalformedRequestForBidOnItem() {

        String payload = "{\n" +
                "  \"cost\": \"12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .log();
    }

    @Test
    public void placeNegativeBidOnItem() {

        String payload = "{\n" +
                "  \"amount\": \"-12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .log();
    }

    @Test //TODO: should fail constraints missing
    public void placeBidOnMissingItem() {

        String payload = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(payload)
                .put("/items/99/accounts/"+account.getAccountId())
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .log();
    }

    @Test
    public void getWinningBidForItem() {

        String bid = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        String winningBid = "{\n" +
                "  \"amount\": \"13.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(bid)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .body(winningBid)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .get("/items/ {} /bids/current-winning-bid",
                        item.getItemId()
                )
                .then()
                .assertThat()
                .body("amount", equalTo(13.0f))
                .statusCode(HTTP_OK)
                .log();
    }

    @Test
    public void getAllBidsOnItem() {

        String bid = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        String winningBid = "{\n" +
                "  \"amount\": \"13.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(bid)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .body(winningBid)
                .put("/items/"+item.getItemId()+"/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .get("/items/ {} /bids",
                        item.getItemId()
                )
                .then()
                .assertThat()
                .body("size()", is(2))
                .statusCode(HTTP_OK)
                .log();
    }

    @Test
    public void getAllItemsForAccount() {

        String bid1 = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(bid1)
                .put("/items/1/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        String bid2 = "{\n" +
                "  \"amount\": \"12.00\"\n" +
                "}";

        with().given()
                .contentType("application/json\r\n")
                .body(bid2)
                .put("/items/2/accounts/"+account.getAccountId())
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .log();

        with().given()
                .contentType("application/json\r\n")
                .get("/items/accounts/ {} ",
                        account.getAccountId()
                )
                .then()
                .assertThat()
                .body("size()", is(2))
                .statusCode(HTTP_OK)
                .log();
    }
}
