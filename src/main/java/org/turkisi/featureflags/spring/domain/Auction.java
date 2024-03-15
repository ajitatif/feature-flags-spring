package org.turkisi.featureflags.spring.domain;

import java.util.Date;

public record Auction(Integer id, Integer auctionTypeId, Date startDateTime, Date endDateTime) {
}
