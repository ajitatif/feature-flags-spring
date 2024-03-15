package org.turkisi.featureflags.spring;

import org.turkisi.featureflags.spring.domain.CarLead;

public class TestDataHelper {
    static CarLead createCar() {
        return new CarLead(1, "XX", "de", 1, 0);
    }

    static CarLead createCar(int rerunCount) {
        return new CarLead(1, "XX", "de", 1, rerunCount);
    }
}
