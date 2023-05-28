package com.lidosta.Deikstras_Lidosta.processor.calculator.response;

import com.lidosta.Deikstras_Lidosta.model.Flight;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FlightsInfo {
    private int distance;
    private float price;
    private List<UUID> path;
    private List<Flight> flight;
}
