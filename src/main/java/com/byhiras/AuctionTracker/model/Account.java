package com.byhiras.AuctionTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<Bid> bids;
}
