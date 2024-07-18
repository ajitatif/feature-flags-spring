package org.turkisi.featureflags.spring.example.domain;

import java.util.Date;

public record Auction(Integer id, Integer auctionTypeId, Date startDateTime, Date endDateTime) {
}
