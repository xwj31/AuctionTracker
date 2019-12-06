package com.byhiras.AuctionTracker.repository;

import com.byhiras.AuctionTracker.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(
            value = "SELECT * FROM BIDS b WHERE FK_ITEM = ?1",
            nativeQuery = true
    )
    public List<Bid> findAllBidsByItemId(Long itemId);

    @Query( //TODO: add date time to this
           value = "SELECT * FROM BIDS b WHERE FK_ITEM = ?1 ORDER BY AMOUNT DESC LIMIT 1",
            nativeQuery = true
    )
    public Optional<Bid> findWinningBidByItemId(Long itemId);
}
