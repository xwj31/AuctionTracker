package com.byhiras.AuctionTracker.errorHandlers;

import com.byhiras.AuctionTracker.error.AccountNotFoundException;
import com.byhiras.AuctionTracker.error.BidNotFoundException;
import com.byhiras.AuctionTracker.error.ItemNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public void AccountNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(BidNotFoundException.class)
    public void BidNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public void ItemNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
