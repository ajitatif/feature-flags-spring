package org.turkisi.featureflags.spring.example.external;

import org.springframework.stereotype.Component;
import org.turkisi.featureflags.spring.example.domain.Auction;
import org.turkisi.featureflags.spring.example.domain.CarLead;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Component
public class AuctionService {
    private static final Random random = new Random();

    public Auction checkoutCar(CarLead car, int auctionTypeId) {
        return new Auction(random.nextInt(100), auctionTypeId, // might also be an auction service
                new Date(), Date.from(Instant.now().plus(2, ChronoUnit.DAYS)));
    }

}
