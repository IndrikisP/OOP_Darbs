package com.lidosta.Deikstras_Lidosta.processor.calculator.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
//rename to path distance price class
public class PriceDistanceInfo {
    UUID flightId;
    float price;
    int distance;
    List<UUID> path;

    public PriceDistanceInfo(UUID flightId, float price, int distance) {
        this.flightId = flightId;
        this.price = price;
        this.distance = distance;
    }

    public PriceDistanceInfo(UUID flightId, float price, int distance, List<UUID> path) {
        this.flightId = flightId;
        this.price = price;
        this.distance = distance;
        this.path = path;
    }
}
