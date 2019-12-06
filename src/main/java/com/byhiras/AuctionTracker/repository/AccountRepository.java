package com.byhiras.AuctionTracker.repository;

import com.byhiras.AuctionTracker.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
