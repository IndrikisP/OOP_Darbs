package com.lidosta.Deikstras_Lidosta.dao;

import com.lidosta.Deikstras_Lidosta.model.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres_airplane")
public class AirplaneAccessService implements Dao<Airplane> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AirplaneAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Airplane insert(Airplane airplane) {
        final String sql ="INSERT INTO airplanes (airplane_id," +
                          "  type," +
                          "  model," +
                          "  passenger_count)" +
                          " VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,
                airplane.getAirplaneId(),
                airplane.getType(),
                airplane.getModel(),
                airplane.getPassengerCount());
        return airplane;
    }

    @Override
    public List<Airplane> selectAll() {
        return null;
    }

    @Override
    public Airplane selectById(UUID id) {
        return null;
    }

    @Override
    public Airplane checkIfExist(Airplane airplane) {
        final String sql = "SELECT airplane_id, type, model, passenger_count " +
                            "  FROM airplanes " +
                            " WHERE type=? " +
                            "  AND passenger_count=? " +
                            "  AND model=? ";
        List<Airplane> results = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Airplane(UUID.fromString(resultSet.getString("airplane_id")),
                    resultSet.getString("type"),
                    resultSet.getString("model"),
                    resultSet.getInt("passenger_count"));
        }, airplane.getType(), airplane.getPassengerCount(), airplane.getModel());

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<Airplane> selectByIds(List<UUID> t) {
        return null;
    }

}

