package com.byhiras.AuctionTracker.error;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {super("Item not found: " + id);}
}

