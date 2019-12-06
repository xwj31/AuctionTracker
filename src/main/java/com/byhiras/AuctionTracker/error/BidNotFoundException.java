package com.byhiras.AuctionTracker.error;

public class BidNotFoundException extends RuntimeException {

    public BidNotFoundException(Long id) {super("No Bids found: " + id);}
}

