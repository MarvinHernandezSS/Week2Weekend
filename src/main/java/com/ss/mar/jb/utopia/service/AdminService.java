package com.ss.mar.jb.utopia.service;

import com.ss.mar.jb.utopia.DAO.AirportDAO;
import com.ss.mar.jb.utopia.DAO.RouteDAO;

import java.sql.Connection;
import java.sql.SQLException;



public class AdminService {

    Util util = new Util();

    public String addFlight() throws SQLException {
        Connection conn = null;
        try {
            conn = util.getConnection();
            AirportDAO adao = new AirportDAO(conn);
            RouteDAO rdao = new RouteDAO(conn);
            //flightdao pass same conn
            //airplanedao pass same conn

            conn.commit();
            return "Flight added successfully";

        }catch(Exception e) {
            e.printStackTrace();
            conn.rollback();
            return "Flight could not be added";
        }finally{
            if(conn!=null) {
                conn.close();
            }
        }
    }

}
