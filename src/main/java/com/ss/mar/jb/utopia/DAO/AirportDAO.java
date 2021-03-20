package com.ss.mar.jb.utopia.DAO;

import com.ss.mar.jb.utopia.entities.Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AirportDAO extends BaseDAO<Airport> {

    public AirportDAO(Connection conn) {
        super(conn);
    }

    public void addAirport(Airport airport) throws ClassNotFoundException, SQLException {
        save("insert into airport values (?, ?)", new Object[] {airport.getAirportCode(), airport.getCity()});
    }

    public void updateAirport(Airport airport) throws ClassNotFoundException, SQLException {
        save("update airport set city = ? where iata_id = ?", new Object[] {airport.getCity(), airport.getAirportCode()});
    }

    public void deleteAirport(Airport airport) throws ClassNotFoundException, SQLException {
        save("delete from aiport where iata_id = ?", new Object[] {airport.getAirportCode()});
    }

    public List<Airport> readAllAirports(Airport airport) throws ClassNotFoundException, SQLException {
        return read("select * from airport", null);
    }

    public List<Airport> readAirportsByCode(Airport airport) throws ClassNotFoundException, SQLException {
        return read("select * from airport where iata_id = ", new Object[] {airport.getAirportCode()});
    }

    public List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        List<Airport> airports = new ArrayList<>();
        while(rs.next()) {
            Airport a = new Airport();
            a.setAirportCode(rs.getString("iata_id"));
            a.setCity(rs.getString("city"));
            airports.add(a);
        }
        return airports;
    }

}
