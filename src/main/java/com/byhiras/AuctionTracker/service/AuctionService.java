package com.byhiras.AuctionTracker.service;

import com.byhiras.AuctionTracker.error.AccountNotFoundException;
import com.byhiras.AuctionTracker.error.BidNotFoundException;
import com.byhiras.AuctionTracker.error.ItemNotFoundException;
import com.byhiras.AuctionTracker.model.Bid;
import com.byhiras.AuctionTracker.model.Item;
import com.byhiras.AuctionTracker.repository.AccountRepository;
import com.byhiras.AuctionTracker.repository.BidRepository;
import com.byhiras.AuctionTracker.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Bid placeBid(Bid bid, Long itemId, Long accountId) {
        bid.setAccount(accountRepository.findById(accountId)
                .orElseThrow( () -> new AccountNotFoundException(accountId)));
        bid.setItem(itemRepository.findById(itemId)
                .orElseThrow( () -> new ItemNotFoundException(itemId)));
        return bidRepository.save(bid);
    }

    public Bid getWinningBid(Long itemId) {
        return bidRepository.findWinningBidByItemId(itemId)
                .orElseThrow(() -> new BidNotFoundException(itemId));
    }

    public List<Bid> getAllBidsOnItem(Long itemId) {
        return bidRepository.findAllBidsByItemId(itemId);
    }

    public List<Item> getAllItemsForAccount(Long accountId) {
        return  itemRepository.findAllItemsByAccountId(accountId);
    }
}
