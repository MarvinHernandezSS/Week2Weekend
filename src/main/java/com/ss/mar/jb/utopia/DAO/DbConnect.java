package com.ss.mar.jb.utopia.DAO;

import com.ss.mar.jb.utopia.FrontEnd.MenuSystem;

import java.sql.*;
import java.util.ArrayList;

public class DbConnect  {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/utopia";
    private static final String username = "root";
    private static final String password = "MySQL3131#!#!";

    //Selects
    public ArrayList<ArrayList<String>> connSelect(String sqlStatement) throws SQLException {
        ArrayList<ArrayList<String>> results = new ArrayList<>();

        Connection conn = DriverManager.getConnection(url,username,password);

        //Register th driver
        try {
            Class.forName(driver);
            //Create Connection
//            Connection conn = DriverManager.getConnection(url,username,password);
            //Statement
            Statement stmt = conn.createStatement();
            PreparedStatement pstmt =
                    conn.prepareStatement((sqlStatement));

            //dynamically get the columncount
            ResultSet rs = pstmt.executeQuery();
            int size= rs.getMetaData().getColumnCount();

            while (rs.next())
            {
                ArrayList<String> A = new ArrayList<>();

                for (int i = 1; i <= size; i++) {
                    A.add(rs.getString(i));
                }
//                A.add(rs.getString(1));
//                A.add(rs.getString(2));
                results.add(A);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
        return results;
    }

    public Integer insUpconn(String sqlInsertorUpdate, boolean isGenKeyExpected) throws SQLException {

        Connection conn = DriverManager.getConnection(url,username,password);
        Integer genKey=0;

        if (!isGenKeyExpected){
            try {
                Class.forName(driver);

                PreparedStatement pstmt =
                        conn.prepareStatement((sqlInsertorUpdate));
                genKey = pstmt.executeUpdate();

            } catch (ClassNotFoundException | SQLException e) {
                MenuSystem M = new MenuSystem();
                System.out.println("There was an issue with your request." +
                        " Please check your input. Returning to the Main Menu\n\n\n");
//                System.out.println(sqlInsertorUpdate + "\n");
                M.mainMenu();
//            System.out.println("block it");
            }
            finally {
                conn.close();
            }
        }
        else {
            PreparedStatement pstmt = conn.prepareStatement(sqlInsertorUpdate,
                    new String[]{ "ID"});
            if (pstmt.executeUpdate() > 0){
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (null != generatedKeys && generatedKeys.next()) {

                    genKey = generatedKeys.getInt(1);
                }
            }

        }

//        try {
//            Class.forName(driver);
//
//            PreparedStatement pstmt =
//                    conn.prepareStatement((sqlInsertorUpdate));
//            genKey = pstmt.executeUpdate();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            MenuSystem M = new MenuSystem();
//            System.out.println("There was an issue with your request." +
//                    " Please check your input. Returning to the Main Menu\n\n\n");
//            M.mainMenu();
////            System.out.println("block it");
//        }
//        finally {
//            conn.close();
//        }

        return genKey;

    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        //Register th driver
//        Class.forName(driver);
//        //Create Connection
//        Connection conn = DriverManager.getConnection(url,username,password);
//        //Statement
//        Statement stmt = conn.createStatement();
//        PreparedStatement pstmt =
//                conn.prepareStatement(("SELECT iata_id, city FROM UTOPIA.AIRPORT WHERE iata_id = ? or iata_id = ?;"));
//        String airportCode = "LON";
//        pstmt.setString(1,airportCode);
//        pstmt.setObject(2,"MAA");
////        pstmt.setObject(2,null);
//
//        String sql = "SELECT * FROM UTOPIA.AIRPORT;";
//        String sql1 = "SELECT iata_id, city FROM UTOPIA.AIRPORT;";
//        String sql2 = "SELECT city, iata_id FROM UTOPIA.AIRPORT;";
//
//        ResultSet rs = stmt.executeQuery(sql1);
//        System.out.println("Query1 results");
//
//        while (rs.next())
//        {
//            System.out.print(rs.getString("iata_id"));
//            System.out.print("\t");
//            System.out.println(rs.getString("city"));
//
////            //get results via column#, Result Set return is not Zero-based!!!
////            System.out.print( rs.getString(1).toString());
////            System.out.print("\t");
////            System.out.println(rs.getString(2));
//
//        }
//
//        ResultSet rs2 = stmt.executeQuery(sql2);
//        System.out.println("Query2 results");
//        while (rs2.next())
//        {
//            System.out.print(rs2.getString("iata_id"));
//            System.out.print("\t");
//            System.out.println(rs2.getString("city"));
//
////            //get results via column#, Result Set return is not Zero-based!!!
////            System.out.print( rs2.getString(1).toString());
////            System.out.print("\t");
////            System.out.println(rs2.getString(2));
//
//        }
//
//        //Read is done with executeQuery because it returns a result Set
//        ResultSet rs3 = pstmt.executeQuery();
//        //only CUD operations are done in batch if there are multiple of them, since the db returns 0 or 1
//        //the batch will put them into an int[], if its just one CUD, use execute will return a boolean
//        //or executeUpdate
//
//        pstmt.addBatch();
//
//
//
//        System.out.println("Query3 results");
//        System.out.println(pstmt.toString());
////        pstmt.executeBatch();
//        while (rs3.next())
//        {
//            System.out.print(rs3.getString("iata_id"));
//            System.out.print("\t");
//            System.out.println(rs3.getString("city"));
//
//
//        }




    }

}
