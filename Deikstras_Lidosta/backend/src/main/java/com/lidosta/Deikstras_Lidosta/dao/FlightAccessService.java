package com.lidosta.Deikstras_Lidosta.dao;

import com.lidosta.Deikstras_Lidosta.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Repository("postgres_flight")
public class FlightAccessService implements Dao<Flight> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Flight insert(Flight flight) {
        final String sql = "INSERT INTO flights (flight_id," +
                           "  from_id," +
                           "  to_id," +
                           "  distance," +
                           "  price," +
                           "  time_of_arrival," +
                           "  time_of_departure," +
                           "  timezone," +
                           "  airplane_id," +
                           "  company)" +
                           " VALUES (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                flight.getFlightId(),
                flight.getFromId(),
                flight.getToId(),
                flight.getDistance(),
                flight.getPrice(),
                flight.getTimeOfArrival(),
                flight.getTimeOfDeparture(),
                flight.getTimeZone(),
                flight.getAirplaneId(),
                flight.getCompany());
        return flight;
    }

    @Override
    public List<Flight> selectAll() {
        final String sql = "SELECT * FROM flights";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Flight(
                    UUID.fromString(resultSet.getString("flight_id")),
                    UUID.fromString(resultSet.getString("from_id")),
                    UUID.fromString(resultSet.getString("to_id")),
                    Integer.valueOf(resultSet.getString("distance")),
                    Float.valueOf(resultSet.getString("price")),
                    resultSet.getDate("time_of_arrival"),
                    resultSet.getDate("time_of_departure"),
                    resultSet.getString("timezone"),
                    UUID.fromString(resultSet.getString("airplane_id")),
                    resultSet.getString("company")
                    );
        });
    }

    @Override
    public Flight selectById(UUID id) {
        return null;
    }

    @Override
    public Flight checkIfExist(Flight object) {
        //TODO Implement method
        return null;
    }

    @Override
    public List<Flight> selectByIds(List<UUID> ids) {
        List<String> uuidStrings = new ArrayList<>();
        for (UUID id : ids) {
            uuidStrings.add("'"+id.toString()+"'");
        }
        String idList = String.join(",", uuidStrings);

        final String sql = "SELECT * FROM flights WHERE flight_id IN (" + idList + ")";

        Object[] params = uuidStrings.toArray();

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Flight(
                    UUID.fromString(resultSet.getString("flight_id")),
                    UUID.fromString(resultSet.getString("from_id")),
                    UUID.fromString(resultSet.getString("to_id")),
                    Integer.valueOf(resultSet.getString("distance")),
                    Float.valueOf(resultSet.getString("price")),
                    resultSet.getDate("time_of_arrival"),
                    resultSet.getDate("time_of_departure"),
                    resultSet.getString("timezone"),
                    UUID.fromString(resultSet.getString("airplane_id")),
                    resultSet.getString("company")
            );
        });
    }
}
