package com.byhiras.AuctionTracker.api;

import com.byhiras.AuctionTracker.model.Bid;
import com.byhiras.AuctionTracker.model.Item;
import com.byhiras.AuctionTracker.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    private AuctionService auctionService;

    @PutMapping(value = "items/{itemId}/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bid> placeBidOnItem(@PathVariable Long itemId, @PathVariable Long accountId, @Valid @RequestBody Bid bid) {
        return ResponseEntity.ok(auctionService.placeBid(bid, itemId, accountId));
    }

    @GetMapping(value = "items/{itemId}/bids/current-winning-bid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bid> getWinningBidForItem(@PathVariable Long itemId) {
            return ResponseEntity.ok(auctionService.getWinningBid(itemId));
    }

    @GetMapping(value = "items/{itemId}/bids", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Bid>> getAllBidsOnItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(auctionService.getAllBidsOnItem(itemId));
    }

    @GetMapping(value = "items/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getAllItemsForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(auctionService.getAllItemsForAccount(accountId));
    }
}
