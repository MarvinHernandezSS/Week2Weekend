package com.ss.mar.jb.utopia.DAO;

import com.ss.mar.jb.utopia.entities.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



public class RouteDAO extends BaseDAO<Route>{

    public RouteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Route> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
        return null;
    }

}
