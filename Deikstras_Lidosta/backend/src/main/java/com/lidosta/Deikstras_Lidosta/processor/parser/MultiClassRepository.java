package com.lidosta.Deikstras_Lidosta.processor.parser;

import com.lidosta.Deikstras_Lidosta.model.Airplane;
import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MultiClassRepository {
    private List<Airplane> airplanes;
    private List<Flight> flights;
    private List<Airport> airports;


}
