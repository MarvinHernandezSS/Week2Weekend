package com.ss.mar.jb.utopia.entities;

import java.util.List;

public class Airport {

    private String airportCode;
    private String city;
    //Relation
    private List<Route> routes;

    public String getAirportCode() {
        return airportCode;
    }
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public List<Route> getRoutes() {
        return routes;
    }
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((airportCode == null) ? 0 : airportCode.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Airport other = (Airport) obj;
        if (airportCode == null) {
            if (other.airportCode != null)
                return false;
        } else if (!airportCode.equals(other.airportCode))
            return false;
        return true;
    }
}

