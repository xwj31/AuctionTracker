package com.byhiras.AuctionTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue
    private Long itemId;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private Set<Bid> bids;

    @NotNull
    private String name;

    private String description;
}
