package com.ss.mar.jb.utopia.FrontEnd;

import com.ss.mar.jb.utopia.DAO.DbConnect;
import com.ss.mar.jb.utopia.service.HandleFrontEndResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SwappableMenuSystem {

//    HandleFrontEndResponse handle = new HandleFrontEndResponse();
//    private Integer x = 0;
//    private String a;
    private Integer counter = 1;

    public void mainMenu() {
        Scanner userInput = new Scanner(System.in);
            HandleFrontEndResponse handle = new HandleFrontEndResponse();

        System.out.println("Welcome to the Utopia Airlines Management System. " +
                "Which category of a user are you?");
        System.out.println("1) Employee");
        System.out.println("2) Administrator");
        System.out.println("3) Traveler");

        handle.handleMainMenuResponse(userInput);
        userInput.close();
    }


    public void travelerMemberShipMenu() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();

        System.out.println("Enter your Membership Number:");

        if(handle.handletravelerMemberShipMenuInput(userInput)){
            travelerMenuOne();
        }
        else{
            System.out.println("That is not a valid membership number. Returning to the Main Menu");
            mainMenu();
        }
        userInput.close();
    }

    private void travelerMenuOne() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        System.out.println("\n1) Book a Ticket\n" +
                "2) Cancel an Upcoming Trip\n" +
                "3) Quit to Previous");
        handle.handleTravelMenuOneResponse(userInput);

    }

    public void adminMenuOne() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        System.out.println("1) Add/Update/Delete/Read Flights");
        System.out.println("2) Add/Update/Delete/Read Seats");
        System.out.println("3) Add/Update/Delete/Read Tickets and Passengers");
        System.out.println("4) Add/Update/Delete/Read Airports");
        System.out.println("5) Add/Update/Delete/Read Travelers");
        System.out.println("6) Add/Update/Delete/Read Employees");
        System.out.println("7) Over-ride Trip Cancellation for a ticket.");

        handle.handleAdminMenuOne(userInput);

        userInput.close();
    }

    public void employeeMenuOne() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        System.out.println("1)Enter Flights You Mangage");
        System.out.println("2) Quit to previous");
        handle.handleEmployeeMenuOneResponse(userInput);
        userInput.close();
    }

//Need to create a new handler for this, menu should only be doing frontend diplaying
    public void employeeMenuTwo() throws SQLException {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        ArrayList<ArrayList<String>> one = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> two = new ArrayList<ArrayList<String>>();
        System.out.println("Please choose a flight:");

        DbConnect dbc = new DbConnect();
//        String sql1 = "SELECT UTOPIA.ROUTE.origin_id, UTOPIA.AIRPORT.city" +
//                " FROM UTOPIA.FLIGHT" +
//                " INNER JOIN  UTOPIA.ROUTE ON ROUTE.id=UTOPIA.FLIGHT.route_id" +
//                " INNER JOIN  UTOPIA.AIRPORT ON UTOPIA.AIRPORT.iata_id=UTOPIA.ROUTE.origin_id" +
//                " LIMIT 10;";

        String sql1 = "SELECT UTOPIA.FLIGHT.id, UTOPIA.ROUTE.origin_id, UTOPIA.AIRPORT.city" +
                " FROM UTOPIA.FLIGHT" +
                " INNER JOIN  UTOPIA.ROUTE ON ROUTE.id=UTOPIA.FLIGHT.route_id" +
                " INNER JOIN  UTOPIA.AIRPORT ON UTOPIA.AIRPORT.iata_id=UTOPIA.ROUTE.origin_id" +
                " LIMIT 10;";

        one = dbc.connSelect(sql1);

//        String sql2 = "SELECT UTOPIA.ROUTE.destination_id, UTOPIA.AIRPORT.city" +
//                " FROM UTOPIA.FLIGHT" +
//                " INNER JOIN  UTOPIA.ROUTE ON ROUTE.id=UTOPIA.FLIGHT.route_id" +
//                " INNER JOIN  UTOPIA.AIRPORT ON UTOPIA.AIRPORT.iata_id=UTOPIA.ROUTE.destination_id" +
//                " LIMIT 10;";
        StringBuilder sql2 = new StringBuilder("SELECT UTOPIA.ROUTE.destination_id, UTOPIA.AIRPORT.city" +
                " FROM UTOPIA.FLIGHT" +
                " INNER JOIN  UTOPIA.ROUTE ON ROUTE.id=UTOPIA.FLIGHT.route_id" +
                " INNER JOIN  UTOPIA.AIRPORT ON UTOPIA.AIRPORT.iata_id=UTOPIA.ROUTE.destination_id" +
                " WHERE UTOPIA.FLIGHT.ID IN ( ");

        for (ArrayList<String> strings : one) {
            sql2.append(strings.get(0) + ",");
        }
        sql2.replace(sql2.length() - 1, sql2.length(), ")");


        two = dbc.connSelect(sql2.toString());

        for (int i = 0; i < one.size(); i++) {
            one.get(i).add(two.get(i).get(0));
            one.get(i).add(two.get(i).get(1));
        }

        for (ArrayList<String> strings : one) {
            System.out.println(counter + ") " + strings.get(1) +
                    "," + strings.get(2) + " → " +
                    strings.get(3) + "," + strings.get(4));
            counter++;
        }

        System.out.println(counter + ") " + "Quit to previous");

        handle.handleEmployeeMenuTwoResponse(userInput, one, counter);

//        SELECT *, TIMESTAMP(UTOPIA.FLIGHT.DEPARTURE_TIME + UTOPIA.FLIGHT.TRAVEL_TIME_ESTIMATE) AS ARRIVALTIME FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID=123

        userInput.close();
    }

//    private Integer debughandleEmployeeMenuTwoResponse = 0;


    public void employeeMenuThree(ArrayList<String> one) {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();

        System.out.println("1) View more details about the flight");
        System.out.println("2) Update the details of the Flight");
        System.out.println("3) Add Seats to Flight");
        System.out.println("4) Quit to previous");

       handle.handleEmployeeMenuThreeResponse(userInput, one);

        userInput.close();
    }


    public void travelerMenuOneOptionOneBookTicket() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();

        //Present below options through select statement
/*        Pick the Flight you want to book a ticket for:
        1)	LAX, Los Angeles → JFK, New York
        2)	…...
        3)	…...
        4)	…...
        5)	Quit to previous (should take you menu TRAV1)*/


        handle.handleTravelMenuOneOptionOneResponseBook(userInput);

    }

    public void travelerMenuOneOptionTwoCancelTrip() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();

        //Present below options through select statement
/*        Pick the Flight you want to CANCEL a ticket for:
        1)	LAX, Los Angeles → JFK, New York
        2)	…...
        3)	…...
        4)	…...
        5)	Quit to previous (should take you menu TRAV1)*/

        handle.handleTravelMenuOneOptionTwoResponseCancel(userInput);
    }

    public void seatClass() {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();

        System.out.println("1)\tView Flight Details\n" +
                "2) First\n" +
                "3) Business\n" +
                "4) Economy\n" +
                "5) Quit to cancel operation (should take you menu TRAV1)\n");

        handle.seatClassResponse(userInput);

    }

    public void addUpdateDeleteReadChoice(String choice) {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
/*        Select action for $choice
          1 Add
          2 Update
          3 Delete
          4 Read
*/
        handle.AddUpdateDeleteReadChoiceResponse(userInput, choice);

    }

    public void read(String choice) {
        //have a select statement for each of
        // the 7 options and pass that to
        // DbConnect.connSelect and then loop
        // over the View and display results

        //after results handle input to return to previous or main menu
    }

    public void delete(String choice) {
        //have a delete statement for each of
        // the 7 options and pass that to
        // DbConnect.insUpconn and then
        // confirm record removal for the id# provided

        //after results handle input to return to previous or main menu
    }

    public void add(String choice) {
        //make a switch statement based off of choice 7 options
        //and make a separate menu for the user to interact with
        //based off values to enter

    }

    public void update(String choice) {
        //make a switch statement based off of choice 7 options
        //and make a separate menu for the user to interact with
        //based off values to enter

    }
}


