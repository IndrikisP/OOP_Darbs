package com.lidosta.Deikstras_Lidosta.dao;

import com.lidosta.Deikstras_Lidosta.model.Airport;
import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres_airport")
public class AirportAccessService implements Dao<Airport>{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AirportAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Airport insert(Airport airport) {
        final String sql = "INSERT INTO airports (airport_id," +
                           "  name," +
                           "  code," +
                           "  city_name)" +
                           " VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,
                airport.getAirportId(),
                airport.getName(),
                airport.getCode(),
                airport.getCityName());
        return airport;
    }

    @Override
    public List<Airport> selectAll() {
        final String sql = "SELECT * FROM airports";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Airport(
                    UUID.fromString(resultSet.getString("airport_id")),
                    resultSet.getString("name"),
                    resultSet.getString("code"),
                    resultSet.getString("city_name")
            );
        });
    }

    @Override
    public Airport selectById(UUID id) {
        return null;
    }

    @Override
    public Airport checkIfExist(Airport airport) {
        final String sql = "SELECT airport_id, name, code, city_name " +
                "  FROM airports " +
                " WHERE name=? " +
                "  AND code=? " +
                "  AND city_name=? ";
        List<Airport> results = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Airport(UUID.fromString(resultSet.getString("airport_id")),
                    resultSet.getString("name"),
                    resultSet.getString("code"),
                    resultSet.getString("city_name"));
        },airport.getName(), airport.getCode(), airport.getCityName());
        if (results.isEmpty()) {
            return null; // or throw an exception, depending on your requirements
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<Airport> selectByIds(List<UUID> t) {
        return null;
    }

    @Override
    public List<List<Airport>> selectPaths(List<List<UUID>> t) {
        return null;
    }

    @Override
    public List<FlightsInfo> selectPaths2(List<FlightsInfo> t) {
        return null;
    }
}
