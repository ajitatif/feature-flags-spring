package org.turkisi.featureflags.spring.domain;

import java.io.Serializable;

public record CarLead(Integer id, String stockNumber, String country, Integer merchantId, Integer rerunCount) implements Serializable {

}
