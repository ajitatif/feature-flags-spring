package org.turkisi.featureflags.spring.auction.rerun.limit;

import org.turkisi.featureflags.spring.auction.AuctionIgnitionFailException;
import org.turkisi.featureflags.spring.domain.CarLead;

import static java.text.MessageFormat.format;

public class CarAuctionRerunLimitExhaustedException extends AuctionIgnitionFailException {

    private final CarLead carLead;
    private final int auctionRerunLimit;

    public CarAuctionRerunLimitExhaustedException(CarLead carLead, int auctionRerunLimit) {
        super(format("CarLead[{0}] has exhausted its rerun limit of [{1}]", carLead.id(), auctionRerunLimit));
        this.carLead = carLead;
        this.auctionRerunLimit = auctionRerunLimit;
    }

    public CarLead getCarLead() {
        return carLead;
    }

    public int getAuctionRerunLimit() {
        return auctionRerunLimit;
    }
}
