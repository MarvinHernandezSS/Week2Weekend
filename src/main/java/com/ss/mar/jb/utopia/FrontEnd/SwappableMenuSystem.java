package com.ss.mar.jb.utopia.FrontEnd;

import com.ss.mar.jb.utopia.DAO.DbConnect;
import com.ss.mar.jb.utopia.entities.Airport;
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

        if (handle.handletravelerMemberShipMenuInput(userInput)) {
            travelerMenuOne();
        } else {
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

        //removing the first row/Column names from returned query
        one.remove(0);

        for (ArrayList<String> strings : one) {
            sql2.append(strings.get(0) + ",");
        }
        sql2.replace(sql2.length() - 1, sql2.length(), ")");


        two = dbc.connSelect(sql2.toString());
        //removing the first row/Column names from returned query
        two.remove(0);

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
//        Select action for $choice
        System.out.println("Please select the action to take for "
                + choice + "\n");
        System.out.println("1 Add\n" +
                "2 Update\n" +
                "3 Delete\n" +
                "4 Read");

        handle.AddUpdateDeleteReadChoiceResponse(userInput, choice);

    }

    public void read(ArrayList<ArrayList<String>> readResults, String choice) {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        //have a select statement for each of
        // the 7 options and pass that to
        // DbConnect.connSelect and then loop
        // over the View and display results
        Integer topRow = 0;

        System.out.println("\n" + choice + " results:\n");

        for (ArrayList<String> strings : readResults) {
            for (String string : strings) {
                if (topRow == 0) {
                    System.out.print(string + "  ");
                    topRow++;
                } else {
                    System.out.print(string + " \t\t");
                }
            }
            System.out.println();
        }
        System.out.println("\nPress 1) to return to the main menu");

        //after results handle input to return to previous or main menu
        handle.handleReadResponse(userInput);
    }

    public void delete(String choice) {
        //have a delete statement for each of
        // the 7 options and pass that to
        // DbConnect.insUpconn and then
        // confirm record removal for the id# provided

        //after results handle input to return to previous or main menu
    }

    public void add(String choice) {
        Scanner userInput = new Scanner(System.in);
        HandleFrontEndResponse handle = new HandleFrontEndResponse();
        DbConnect dbc = new DbConnect();
        //make a switch statement based off of choice 7 options
        //and make a separate menu for the user to interact with
        //based off values to enter

        switch (choice) {
            case "Flights": {
                String inputCommands;
                String id = "";
                String route_id = "";
                String airplane_id = "";
                String seat_price = "";
                StringBuilder departTime = new StringBuilder();
                String reserved_seats = "";
                String travel_time_estimate = "";

                System.out.println("Please enter a new numerical value for flight id:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        id = inputCommands;
                    }
                }
                System.out.println("Please enter an existing value for route id:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        route_id = inputCommands;
                    }
                }
                System.out.println("Please enter an existing airplane id:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        airplane_id = inputCommands;
                    }
                }
                System.out.println("Please enter a the seat price for this filght:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        seat_price = inputCommands;
                    }
                }
                System.out.println("Please enter new Departure Date (YYYY-MM-DD):");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        departTime.append(inputCommands);
                    }
                }
                System.out.println("Please enter new Departure Time (HH:MM:SS):");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        departTime.append(" " + inputCommands);
                    }
                }
                System.out.println("Please enter the number of seats that you wish to reserve:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        reserved_seats = inputCommands;
                    }
                }
                System.out.println("Please enter the Estimated Travel Time (HH:MM:SS):");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        travel_time_estimate = inputCommands;
                    }
                }

                String sql = "INSERT INTO `utopia`.`flight` (`id`, `route_id`, `airplane_id`, " +
                        "`departure_time`, `reserved_seats`, `seat_price`, `travel_time_estimate`) " +
                        "VALUES ('" + id + "', '" + route_id + "', '" + airplane_id +
                        "', '" + departTime.toString() + "', '" + reserved_seats + "', '" + seat_price +
                        "', '" + travel_time_estimate + "');";

//                System.out.println("debug : " + sql);
                Integer success = 0;

                try {
                    success = dbc.insUpconn(sql, false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (success > 0) {
                    System.out.println("The record was successfully inserted.");
                }


                break;
            }
            case "Seats": {
                System.out.println("Not yet implemented");
                break;
            }
            case "Tickets and Passengers": {
                String inputCommands;
                String id = "";
                String is_active = "";
                String confirmation_code = "";
                String given_name = "";
                String family_name = "";
                String dob = "";
                String gender = "";
                String address = "";


                System.out.println("Please enter a new numerical value for id:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        id = inputCommands;
                    }
                }
                System.out.println("Please enter a zero or one value for is_active:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands.equals("0") || inputCommands.equals("1")) {
                        is_active = inputCommands;
                    }
                }
                System.out.println("Please enter an confirmation_code:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        confirmation_code = inputCommands;
                    }
                }
                System.out.println("Please enter the first name of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        given_name = inputCommands;
                    }
                }
                System.out.println("Please enter the last name of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        family_name = inputCommands;
                    }
                }
                System.out.println("Please enter the D.O.B (YYYY-MM-DD) of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        dob = inputCommands;
                    }
                }
                System.out.println("Please enter the gender of the passenger");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        gender = inputCommands;
                    }
                }
                System.out.println("Please enter the address of the passenger");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        address = inputCommands;
                    }
                }

                String insertBooking = "INSERT INTO `utopia`.`booking` " +
                        "(`id`, `is_active`, `confirmation_code`) " +
                        "VALUES ('" + id + "', '" + is_active + "', '" + confirmation_code + "');";


                String insertPassenger = "INSERT INTO `utopia`.`passenger` " +
                        "(`booking_id`, `given_name`, `family_name`, " +
                        "`dob`, `gender`, `address`) VALUES " +
                        "('" + id + "', '" + given_name + "', '" + family_name + "', " +
                        "'" + dob + "', '" + gender + "', " +
                        "'" + address + "');";

//                System.out.println("debug : " + insertBooking);
//                System.out.println("debug : " + insertPassenger);

                Integer bookingSuccess = 0;

                try {
                    bookingSuccess = dbc.insUpconn(insertBooking, false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Integer passengerSuccess = 0;

                if (bookingSuccess > 0) {
                    try {
                        passengerSuccess = dbc.insUpconn(insertPassenger, false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                if (bookingSuccess > 0 && passengerSuccess > 0) {
                    System.out.println("The record was successfully inserted.");
                }


//
                break;
            }
            case "Airports": {
                String inputCommands;
                Airport origin = new Airport();

                System.out.println("Please enter new Airport code:");
                if (userInput.hasNextLine()){
                    inputCommands=userInput.nextLine();
                    if (inputCommands != "N/A"){origin.setAirportCode(inputCommands); }
                }
                System.out.println("Please enter new City :");
                if (userInput.hasNextLine()){
                    inputCommands=userInput.nextLine();
                    if (inputCommands != "N/A"){ origin.setCity(inputCommands); }
                }

                String sql = "INSERT INTO `utopia`.`airport` " +
                        "(`iata_id`, `city`) VALUES (" +
                        "'"+origin.getAirportCode() +"', '"+ origin.getCity() +"');";

//                System.out.println("debug : " + sql);
                Integer success = 0;
                if (origin.getAirportCode() !=null && origin.getCity() != null) {
                    try {
                        success = dbc.insUpconn(sql, false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else{
                    System.out.println("Please check your input and try again.\n");
                }

                if (success > 0) {
                    System.out.println("The record was successfully inserted.");
                }

                break;
            }
            case "Travelers": {
                String inputCommands;
                String id = "";
                String given_name = "";
                String family_name = "";
                String dob = "";
                String gender = "";
                String address = "";

                System.out.println("Please enter an existing booking id number:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        id = inputCommands;
                    }
                }
                System.out.println("Please enter the first name of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        given_name = inputCommands;
                    }
                }
                System.out.println("Please enter the last name of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        family_name = inputCommands;
                    }
                }
                System.out.println("Please enter the D.O.B (YYYY-MM-DD) of the passenger:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        dob = inputCommands;
                    }
                }
                System.out.println("Please enter the gender of the passenger");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        gender = inputCommands;
                    }
                }
                System.out.println("Please enter the address of the passenger");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        address = inputCommands;
                    }
                }

                String insertPassenger = "INSERT INTO `utopia`.`passenger` " +
                        "(`booking_id`, `given_name`, `family_name`, " +
                        "`dob`, `gender`, `address`) VALUES " +
                        "('" + id + "', '" + given_name + "', '" + family_name + "', " +
                        "'" + dob + "', '" + gender + "', " +
                        "'" + address + "');";

//                System.out.println("debug : " + insertPassenger);

                Integer passengerSuccess = 0;

                    try {
                        passengerSuccess = dbc.insUpconn(insertPassenger, false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                if (passengerSuccess > 0) {
                    System.out.println("The record was successfully inserted.");
                }

                break;
            }
            case "Employees": {
                String inputCommands;
                String given_name = "";
                String family_name = "";
                String username = "";
                String email = "";
                String password = "";
                String phone = "";

//                INSERT INTO `utopia`.`user`
//                (`role_id`, `given_name`, `family_name`, `username`, `email`, `password`, `phone`)
//                VALUES ('3', 'Sue', 'Cash', 'Scash', 'SueCash@dollar.com', '123abc', '123456789');


                System.out.println("Please enter the first name of the employee:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        given_name = inputCommands;
                    }
                }
                System.out.println("Please enter the last name of the employee:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        family_name = inputCommands;
                    }
                }
                System.out.println("Please enter the username of the employee:");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        username = inputCommands;
                    }
                }
                System.out.println("Please enter the email of the employee");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        email = inputCommands;
                    }
                }
                System.out.println("Please enter the password of the employee");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        password = inputCommands;
                    }
                }
                System.out.println("Please enter the phone of the employee");
                if (userInput.hasNextLine()) {
                    inputCommands = userInput.nextLine();
                    if (inputCommands != "N/A") {
                        phone = inputCommands;
                    }
                }

                String sql = "INSERT INTO `utopia`.`user` " +
                        "(`role_id`, `given_name`, `family_name`," +
                        " `username`, `email`, `password`, `phone`) " +
                        "VALUES ('3', '"+ given_name +"', '"+ family_name +"', '"+ username +"', '" +
                        email + "', '"+ password +"', '"+ phone +"');";

//                System.out.println("debug : " + sql);
                Integer success = 0;

                if (given_name.equals("")||family_name.equals("")||
                        username.equals("")||email.equals("")||
                        password.equals("")||phone.equals(""))
                {
                    System.out.println("Please recheck your input for any missing paramerters.");
                }
                else {
                    try {
                        success = dbc.insUpconn(sql, false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if (success > 0) {
                    System.out.println("The record was successfully inserted.");
                }

                break;
            }
            default:

        }
        System.out.println("\nPress 1) to return to the main menu");

        //after results handle input to return to previous or main menu
        handle.handleReadResponse(userInput);

    }

    public void update(String choice) {
        //make a switch statement based off of choice 7 options
        //and make a separate menu for the user to interact with
        //based off values to enter

    }
}


