package com.lidosta.Deikstras_Lidosta.processor.calculator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PriceDistanceInfo {
    UUID flightId;
    float price;
    int distance;
}
