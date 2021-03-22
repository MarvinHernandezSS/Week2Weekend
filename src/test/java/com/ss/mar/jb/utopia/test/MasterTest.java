package com.ss.mar.jb.utopia.test;
import com.ss.mar.jb.utopia.DAO.DbConnect;
import com.ss.mar.jb.utopia.FrontEnd.MenuSystem;
import com.ss.mar.jb.utopia.FrontEnd.SwappableMenuSystem;
import com.ss.mar.jb.utopia.service.HandleFrontEndResponse;
import org.junit.Test;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;


public class MasterTest {

MenuSystem A = new MenuSystem();
Scanner testScan = new Scanner(System.in);
//HandleFrontEndResponse B = new HandleFrontEndResponse();
DbConnect dbc = new DbConnect();
    ArrayList<ArrayList<String>> aTest = new ArrayList<>();
    Integer x = 0;




//    @Test
//    public void handletravelerMemberShipMenuInputSwapTest()
//    {
//        assertEquals(true,B.handletravelerMemberShipMenuInput(testScan));
//    }

    @Test
    public void connSelectTest()
    {
        try {
            assertEquals(aTest , dbc.connSelect("SELECT * FROM utopia.user;"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void insUpconnTest(){
        try {
            assertEquals(x,dbc.insUpconn("",false));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Test
    public void handletravelerMemberShipMenuInputTest() {

        assertEquals(true,A.handletravelerMemberShipMenuInput(testScan));


    }



}
