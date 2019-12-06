package com.byhiras.AuctionTracker.repository;

import com.byhiras.AuctionTracker.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT DISTINCT ITEM_ID, DESCRIPTION, NAME FROM BIDS " +
            "INNER JOIN ITEMS ON BIDS.FK_ITEM = ITEMS.ITEM_ID " +
            "WHERE FK_ACCOUNT = ?1"
            ,nativeQuery = true)
   public List<Item> findAllItemsByAccountId(Long accountId);
}
