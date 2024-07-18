package org.turkisi.featureflags.spring;

import org.turkisi.featureflags.spring.example.domain.CarLead;

public class TestDataHelper {
    public static CarLead createCar() {
        return new CarLead(1, "XX", "de", 1, 0);
    }

    public static CarLead createCar(int rerunCount) {
        return new CarLead(1, "XX", "de", 1, rerunCount);
    }
}
