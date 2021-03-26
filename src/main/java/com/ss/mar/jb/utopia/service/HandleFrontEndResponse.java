package com.ss.mar.jb.utopia.service;

import com.ss.mar.jb.utopia.DAO.DbConnect;
import com.ss.mar.jb.utopia.FrontEnd.SwappableMenuSystem;
import com.ss.mar.jb.utopia.entities.Airport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandleFrontEndResponse {

    private Integer x = 0;
    private Integer counter = 1;
//    SwappableMenuSystem menu = new SwappableMenuSystem();

    public void handleMainMenuResponse(Scanner scan) {
        SwappableMenuSystem menu = new SwappableMenuSystem();

        verifyUserInput(scan);


        switch (this.x) {
            case 1: {
                menu.employeeMenuOne();
                break;
            }
            case 2: {
                menu.adminMenuOne();
                break;
            }
            case 3: {
                menu.travelerMemberShipMenu();
                break;
            }
            default:
//                    System.out.println("Please make a valid selection");
//                    System.out.println();
//                System.out.println(x);
                System.out.println("Not a valid selection returing to Main menu\n");

                menu.mainMenu();
        }

    }

    public void verifyUserInput(Scanner userInput) {
        String inputCommands;
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            try {
                x = Integer.parseInt(inputCommands);
            } catch (NumberFormatException e) {
                x = 0;
            } finally {
//                userInput.close();
            }

        }
    }

    public boolean handletravelerMemberShipMenuInput(Scanner userInput) {
        verifyUserInput(userInput);
        DbConnect dbc = new DbConnect();
        ArrayList<ArrayList<String>> membershipNums = new ArrayList<>();
        Boolean match = false;


//        String getValidMemberShipnums = "SELECT utopia.user.id FROM utopia.user;";
        String getValidMemberShipnums = "SELECT utopia.user.id ,utopia.user.given_name , utopia.user.family_name FROM utopia.user;";
        try {
            membershipNums = dbc.connSelect(getValidMemberShipnums);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Integer columncheck = 0;
        for (ArrayList<String> membershipNum : membershipNums) {
            if (columncheck > 0) {
                if (this.x == Integer.parseInt(membershipNum.get(0))) {
                    match = true;
                    break;
                }
            }
            columncheck++;
        }
        return match;
    }

    public void handleEmployeeMenuOneResponse(Scanner userInput) {
        verifyUserInput(userInput);
        SwappableMenuSystem menu = new SwappableMenuSystem();


        switch (this.x) {
            case 1: {
                this.x = 0;
                try {
                    menu.employeeMenuTwo();
                } catch (SQLException throwables) {
                    System.out.println("Oops something went wrong... now returning to Previous Menu");
                    this.x = 0;
                    menu.mainMenu();
                }

                break;
            }
            case 2: {
                menu.mainMenu();
                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Previous Menu");
                this.x = 0;
                menu.mainMenu();
        }
    }

    public void handleEmployeeMenuTwoResponse(Scanner userInput, ArrayList<ArrayList<String>> one, Integer counter) {
        verifyUserInput(userInput);
        SwappableMenuSystem menu = new SwappableMenuSystem();
        this.counter = counter;

//        debughandleEmployeeMenuTwoResponse++;

        if (this.x > this.counter || this.x == 0) {
            System.out.println("Invalid selection... now returning to previous menu");
            this.counter = 1;
            menu.employeeMenuOne();
        }
        if (this.x != this.counter) {
            menu.employeeMenuThree(one.get(x - 1));

        } else {
            this.counter = 1;
            menu.employeeMenuOne();
        }

    }

    //need to Refactor, its displaying Menu stuff
    public void handleEmployeeMenuThreeResponse(Scanner userInput, ArrayList<String> one) {
        verifyUserInput(userInput);
        SwappableMenuSystem menu = new SwappableMenuSystem();


        switch (this.x) {
            case 1: {

                System.out.println("You have chosen to view the Flight with Flight" +
                        " Id: " + one.get(0) + " and Departure Airport: " +
                        one.get(1) + " and Arrival Airport: " +
                        one.get(3) + ".\n");


                DbConnect dbc = new DbConnect();
                ArrayList<ArrayList<String>> threeOne = new ArrayList<ArrayList<String>>();

                String sql = "SELECT UTOPIA.FLIGHT.DEPARTURE_TIME, TIMESTAMP" +
                        "(UTOPIA.FLIGHT.DEPARTURE_TIME + UTOPIA.FLIGHT.TRAVEL_TIME_ESTIMATE)" +
                        " AS 'FORMATTED DATETIME', " +
                        "(UTOPIA.AIRPLANE_TYPE.MAX_CAPACITY - UTOPIA.FLIGHT.RESERVED_SEATS)" +
                        " AS AVAIL_SEATS" + "  FROM UTOPIA.FLIGHT " +
                        " INNER JOIN UTOPIA.AIRPLANE ON UTOPIA.AIRPLANE.ID=UTOPIA.FLIGHT.AIRPLANE_ID" +
                        " INNER JOIN UTOPIA.AIRPLANE_TYPE ON UTOPIA.AIRPLANE_TYPE.ID=UTOPIA.AIRPLANE.ID" +
                        " WHERE UTOPIA.FLIGHT.ID="+one.get(0)+"";

                try {
                    threeOne = dbc.connSelect(sql);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    this.x = 0;
                }



//                for (String s : one) {
//                    System.out.print("debug one : " +s );
//                }
//
//                for (ArrayList<String> strings : threeOne) {
//                    for (String string : strings) {
//                        System.out.print("Debug inner " + string);
//                    }
//                    System.out.println("Debug outter");
//                }

                //rounding down to not to exceed remaining seating capacity

                    Integer first = (int) Math.floor(Integer.parseInt(threeOne.get(1).get(2)) * .15);
                    Integer business = (int) Math.floor(Integer.parseInt(threeOne.get(1).get(2)) * .25);
                    Integer economy = (int) Math.floor(Integer.parseInt(threeOne.get(1).get(2)) * .60);


                System.out.println("Departure Airport: " + one.get(1) +
                        " | " + "Arrival Airport: " + one.get(3) + " |");

                String[] temp1 = threeOne.get(1).get(0).split(" ");
                System.out.println("Departure Date: " + temp1[0] + " | " + "Departure Time: " +
                        temp1[1].substring(0, temp1[1].length() - 2) + " |");
                String[] temp2 = threeOne.get(1).get(1).split(" ");
                System.out.println("Arrival Date: " + temp2[0] + " | " + "Arrival Time: " +
                        temp2[1].substring(0, temp2[1].length() - 2) + " |\n");

                System.out.println("Available Seats by Class:\n" +
                        "1) First → " + first + "\n" +
                        "2) Business → " + business + "\n" +
                        "3) Economy → " + economy + "\n" +
                        "4) Return to previous menu");

//                System.out.println("debug X = " + this.x);

                if (userInput.hasNextLine()) {
                    menu.employeeMenuThree(one);
                }

                break;
            }
            case 2: {
                System.out.println("You have chosen to update the Flight with Flight" +
                        " Id: " + one.get(0) + " and Departure Airport: " +
                        one.get(1) + " and Arrival Airport: " +
                        one.get(3) + ".");
//                System.out.println("Enter ‘quit’ at any prompt to cancel operation.\n");
                System.out.println("Please enter new Origin Airport and City or enter N/A for no change:");
                System.out.println("\n");

                handleEmployeeMenuThreeResponseTwo(userInput, one);


                break;
            }
            case 3: {
                System.out.println("Feature not yet implemented. " +
                        "Please check back soon. \nReturning to Main menu......\n\n");
                menu.mainMenu();

                break;
            }
            case 4: {
                try {
                    menu.employeeMenuTwo();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Main Menu");
                this.x = 0;
//                menu.mainMenu();
                try {
                    menu.employeeMenuTwo();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }


    }

    //need to Refactor, its displaying Menu stuff
    private void handleEmployeeMenuThreeResponseTwo(Scanner userInput, ArrayList<String> one) {
        String inputCommands;
        Integer x = 0;
        Airport origin = new Airport();
        Airport dest = new Airport();
        StringBuilder departTime = new StringBuilder();
        StringBuilder arriveTime = new StringBuilder();
        DbConnect dbc = new DbConnect();
        SwappableMenuSystem menu = new SwappableMenuSystem();


        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                origin.setAirportCode(inputCommands);
            }
        }
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                origin.setCity(inputCommands);
            }
        }
        System.out.println("Please enter new Destination Airport and City or enter N/A for no change:");
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                dest.setAirportCode(inputCommands);
            }
        }
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                dest.setCity(inputCommands);
            }
        }
        System.out.println("Please enter new Departure Date (YYYY-MM-DD) or enter N/A for no change:");
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                departTime.append(inputCommands);
            }
        }
        System.out.println("Please enter new Departure Time (HH:MM:SS) or enter N/A for no change:");
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                departTime.append(" " + inputCommands);
            }
        }
        System.out.println("Please enter new Arrival Date (YYYY-MM-DD) or enter N/A for no change:");
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                arriveTime.append(inputCommands);
            }
        }
        System.out.println("Please enter new Arrival Time (HH:MM:SS) or enter N/A for no change:");
        if (userInput.hasNextLine()) {
            inputCommands = userInput.nextLine();
            if (inputCommands != "N/A") {
                arriveTime.append(" " + inputCommands);
            }
        }


        String de = departTime.toString();
        String ar = arriveTime.toString();


//        System.out.println("debug " + origin.getAirportCode() + " " + origin.getCity());
//        System.out.println("debug " + dest.getAirportCode() + " " + dest.getCity());
//        System.out.println("debug " + departTime.toString());
//        System.out.println("debug " + arriveTime.toString());

        String sqlInsertNewOriginIntoAirport1 = "INSERT INTO utopia.airport VALUES ('"
                + origin.getAirportCode() + "', '" + origin.getCity() + "');";

        String sqlInsertNewDestIntoAirport1 = "INSERT INTO utopia.airport VALUES ('"
                + dest.getAirportCode() + "', '" + dest.getCity() + "');";

//        System.out.println(sqlInsertNewOriginIntoAirport1);
//        System.out.println(sqlInsertNewDestIntoAirport1);
        Integer originSuccess = 0;
        Integer destSuccess = 0;

        //insert Origin into Airport
        if (origin.getCity() != null && origin.getAirportCode() != null) {
            try {
                originSuccess = dbc.insUpconn(sqlInsertNewOriginIntoAirport1, false,"employeeMenuTwo");
            } catch (SQLException throwables) {
            }
        }
        //Insert Destination into Airport
        if (dest.getAirportCode() != null && dest.getCity() != null) {
            try {
                destSuccess = dbc.insUpconn(sqlInsertNewDestIntoAirport1, false,"employeeMenuTwo");
            } catch (SQLException throwables) {
            }
        }


        //insert a new route and capture the PK key generated
        Integer routeGenKey = 0;
        Integer resultReturn = 0;
        if (originSuccess > 0 && destSuccess > 0) {
            String sql = "INSERT INTO UTOPIA.ROUTE VALUES (NULL ,'" + origin.getAirportCode()
                    + "','" + dest.getAirportCode() + "');";
            try {
                routeGenKey = dbc.insUpconn(sql, true,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            System.out.println(sql);
        }
//        System.out.println("debug routeGenKey: " + routeGenKey);


        //update the origin of a route
        if (originSuccess > 0 && destSuccess <= 0) {
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            String getRouteID = "SELECT utopia.flight.route_id FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID = " + one.get(0) + ";";
            try {
                temp = dbc.connSelect(getRouteID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String updateOrginonRoute = "UPDATE UTOPIA.ROUTE SET UTOPIA.ROUTE.origin_id ='" +
                    origin.getAirportCode() + "' WHERE UTOPIA.ROUTE.ID = '" + temp.get(0).get(0) + "';";
//            System.out.println(updateOrginonRoute);
            try {
                dbc.insUpconn(updateOrginonRoute, false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
//                throwables.get

            }
        }

        //update the destination of a route
        if (originSuccess <= 0 && destSuccess > 0) {
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            String getRouteID = "SELECT utopia.flight.route_id FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID = " + one.get(0) + ";";
            try {
                temp = dbc.connSelect(getRouteID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String updateDestinationRoute = "UPDATE UTOPIA.ROUTE SET UTOPIA.ROUTE.destination_id ='" +
                    dest.getAirportCode() + "' WHERE UTOPIA.ROUTE.ID = '" + temp.get(0).get(0) + "';";
            try {
                dbc.insUpconn(updateDestinationRoute, false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        //now I need to update flight with the new departure/arrival date times and if needed,
        // i need to also update//the route if routeGenKey is > 0
        //Flight Update based off if there is a new route to add or not
        if (routeGenKey > 0) {
//            UPDATE `utopia`.`flight` SET `route_id`='77', `departure_time`='2021-06-21 18:32:14' WHERE `id`='971';
            String flightUpdateNewRoute = "UPDATE `utopia`.`flight` SET `route_id`='" + routeGenKey + "'," +
                    " `departure_time`='" + departTime.toString() + "' WHERE `id`='" + one.get(0) + "';";
            try {
                dbc.insUpconn(flightUpdateNewRoute, false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else {
//            UPDATE `utopia`.`flight` SET `departure_time`='2021-06-21 18:32:14' WHERE `id`='971';
            String flightUpdateDepartureOnly = "UPDATE `utopia`.`flight` SET `" +
                    "departure_time`='" + departTime.toString() + "' WHERE `id`='" + one.get(0) + "';";
            try {
                resultReturn = dbc.insUpconn(flightUpdateDepartureOnly, false,"employeeMenuTwo");
            } catch (SQLException throwables) {

                throwables.printStackTrace();
            }
        }

        if (routeGenKey > 0 || resultReturn > 0){
            System.out.println("Update Successful");
        }

        menu.employeeMenuThree(one);


    }

    public void handleTravelMenuOneResponse(Scanner userInput) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);


        switch (this.x) {
            case 1: {
                //Book Ticket
//                menu.travelerMenuOneOptionOneBookTicket();
                System.out.println("Feature not yet implemented. " +
                        "Please check back soon. \nReturning to Main menu......\n\n");
                menu.mainMenu();
                break;
            }
            case 2: {
                //Cancel Trip
//                menu.travelerMenuOneOptionTwoCancelTrip();
                System.out.println("Feature not yet implemented. " +
                        "Please check back soon. \nReturning to Main menu......\n\n");
                menu.mainMenu();
                break;
            }
            case 3: {
                System.out.println("\nReturning to Main menu......\n\n");
                menu.mainMenu();
                break;
            }
            default:
                System.out.println("Invalid Selection.\nReturning to Main menu......\n\n");
                menu.mainMenu();
        }
    }

    public void handleTravelMenuOneOptionOneResponseBook(Scanner userInput) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);

        //input logic to handle user flight choice probably similar to
        // employee menu2 and then send them to the menu
        //Seat Class Menu

        menu.seatClass();

    }

    public void seatClassResponse(Scanner userInput) {

//        Option 1 should retrieve flight details for the Flight the user selected.
//        Then add entry into your tickets table.
        //2,3,4 probably update/insert booking and related tables

        //include a flag for book/cancel operations so a delete

    }

    public void handleTravelMenuOneOptionTwoResponseCancel(Scanner userInput) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);

        //input logic to handle user flight choice probably similar to
        // employee menu2 and then send them to the menu
        //Seat Class Menu

        //Try and reuse the seatClass and add a flag for cancel vs book
        menu.seatClass();

    }

    public void handleAdminMenuOne(Scanner userInput) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);
        String Choice;

        switch (this.x) {
            case 1: {
                Choice = "Flights";
                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 2: {
                Choice = "Seats";
                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 3: {
                Choice = "Tickets and Passengers";
                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 4: {
                Choice = "Airports";
                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 5: {
                Choice = "Travelers";
                menu.addUpdateDeleteReadChoice(Choice);

                break;
            }
            case 6: {
                Choice = "Employees";
                menu.addUpdateDeleteReadChoice(Choice);

                break;
            }
            case 7: {
                System.out.println("Feature not yet implemented. " +
                        "Please check back soon. \nReturning to Main menu......\n\n");
                menu.mainMenu();

                break;
            }
            case 8: {
                menu.mainMenu();
            }
            default:
                System.out.println("Invalid Selection.\nReturning to Main menu......\n\n");
                menu.mainMenu();
        }


    }

    public void AddUpdateDeleteReadChoiceResponse(Scanner userInput, String choice) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);
        DbConnect dbc = new DbConnect();
        ArrayList<ArrayList<String>> readResults = new ArrayList<>();

        switch (this.x) {
            case 1: {
                menu.add(choice);

                break;
            }
            case 2: {
                menu.update(choice);
                break;
            }
            case 3: {
                menu.delete(choice);
                break;
            }
            case 4: {
                switch (choice) {
                    case "Flights": {
                        try {
                            readResults =
                                    dbc.connSelect(
                                            "SELECT * FROM utopia.flight LIMIT 10;");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    case "Seats": {
                        //readResults.add(new String[]{"Looks ","like there"," are no results......."});

                        break;
                    }
                    case "Tickets and Passengers": {
                        try {
                            readResults =
                                    dbc.connSelect(
                                            "SELECT utopia.booking.id,utopia.passenger.GIVEN_NAME, " +
                                                    "utopia.passenger.FAMILY_NAME, " +
                                                    "utopia.booking.confirmation_code FROM " +
                                                    "utopia.passenger INNER JOIN utopia.booking on " +
                                                    "utopia.booking.id=utopia.passenger.booking_id;");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    case "Airports": {
                        try {
                            readResults =
                                    dbc.connSelect(
                                            "SELECT * FROM utopia.airport LIMIT 10;");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    case "Travelers": {
                        try {
                            readResults =
                                    dbc.connSelect(
                                            "SELECT utopia.passenger.id, " +
                                                    "utopia.passenger.given_name, " +
                                                    "utopia.passenger.family_name, " +
                                                    "utopia.passenger.dob, " +
                                                    "utopia.passenger.gender FROM utopia.passenger;");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    case "Employees": {
                        try {
                            readResults =
                                    dbc.connSelect(
                                            "SELECT * FROM utopia.user where utopia.user.role_id = 3;");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    }
                    default:

                }


                menu.read(readResults, choice);
                break;
            }
            default:
                System.out.println("Invalid selection returning to Admin Menu One\n");
//                menu.mainMenu();
                menu.adminMenuOne();
        }


    }

    public void handleReadUpdateResponse(Scanner userInput) {

        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);

        switch (this.x) {
            case 1: {
                this.x = 0;
                menu.adminMenuOne();
                break;
            }
            default:
//                ?System.out.println("Invalid selection... now returning to Main Menu");
                this.x = 0;
                menu.mainMenu();
        }

    }

    public void handleAdminMenuOneReadResults(Scanner userInput) {
        SwappableMenuSystem menu = new SwappableMenuSystem();
        verifyUserInput(userInput);
        DbConnect dbc = new DbConnect();
        String Choice = "";
        ArrayList<ArrayList<String>> readResults = new ArrayList<>();
        Boolean notSeats = true;

        switch (this.x) {
            case 1: {
                Choice = "Flights";
//                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 2: {
                Choice = "Seats";
//                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 3: {
                Choice = "Tickets and Passengers";
//                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 4: {
                Choice = "Airports";
//                menu.addUpdateDeleteReadChoice(Choice);
                break;
            }
            case 5: {
                Choice = "Travelers";
//                menu.addUpdateDeleteReadChoice(Choice);

                break;
            }
            case 6: {
                Choice = "Employees";
//                menu.addUpdateDeleteReadChoice(Choice);

                break;
            }
            case 7: {
                System.out.println("Feature not yet implemented. " +
                        "Please check back soon. \nReturning to previous menu......\n\n");
                menu.mainMenu();

                break;
            }
            case 8: {
                menu.mainMenu();
            }
            default:
                System.out.println("Invalid Selection.\nReturning to Main menu......\n\n");
                menu.mainMenu();
        }

//Run Select Query off of selection

        switch (Choice) {
            case "Flights": {
                try {
                    readResults =
                            dbc.connSelect(
                                    "SELECT * FROM utopia.flight LIMIT 10;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            case "Seats": {
                notSeats = false;
                break;
            }
            case "Tickets and Passengers": {
                try {
                    readResults =
                            dbc.connSelect(
                                    "SELECT utopia.booking.id,utopia.passenger.GIVEN_NAME, " +
                                            "utopia.passenger.FAMILY_NAME, " +
                                            "utopia.booking.confirmation_code FROM " +
                                            "utopia.passenger INNER JOIN utopia.booking on " +
                                            "utopia.booking.id=utopia.passenger.booking_id;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            case "Airports": {
                try {
                    readResults =
                            dbc.connSelect(
                                    "SELECT * FROM utopia.airport LIMIT 10;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            case "Travelers": {
                try {
                    readResults =
                            dbc.connSelect(
                                    "SELECT utopia.passenger.id, " +
                                            "utopia.passenger.given_name, " +
                                            "utopia.passenger.family_name, " +
                                            "utopia.passenger.dob, " +
                                            "utopia.passenger.gender FROM utopia.passenger;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            case "Employees": {
                try {
                    readResults =
                            dbc.connSelect(
                                    "SELECT * FROM utopia.user where utopia.user.role_id = 3 limit 10;");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            default:

        }
        if (notSeats) {
            menu.addUpdateDeleteChoiceWithRead(readResults, Choice);
        } else {
            System.out.println("Not yet implemented\nReturning to Admin Menu One\n");
            menu.adminMenuOne();
        }

    }

    public void AddUpdateDeleteChoiceResponse(Scanner userInput,
                                              ArrayList<ArrayList<String>> readResults,
                                              Integer topRow, String choice) {

        verifyUserInput(userInput);
        SwappableMenuSystem menu = new SwappableMenuSystem();
        this.counter = topRow;

//        System.out.println("debug toprow = " + this.counter);
//        debughandleEmployeeMenuTwoResponse++;

        if (this.x > this.counter || this.x == 0) {
            if (this.x == this.counter + 1) {
                menu.adminMenuOne();
                this.counter = 1;
            } else {
                System.out.println("Invalid selection... now returning to previous admin menu");
                this.counter = 1;
                menu.adminMenuOne();
            }
        }
        if (this.x == this.counter) {
            menu.add(choice);
            this.counter = 1;
        }
        if (this.x != this.counter) {
            menu.updateDeleteChoicePicked(readResults.get(x), choice);
//            menu.employeeMenuThree(readResults.get(x - 1));

        } else {
            this.counter = 1;
            System.out.println("not sure when this will proc");
            menu.adminMenuOne();
        }

    }

    public void updateDeleteChoicePickedResponse(Scanner userInput, ArrayList<String> strings, String choice) {
        verifyUserInput(userInput);
        SwappableMenuSystem menu = new SwappableMenuSystem();

        switch (this.x) {
            case 1: {
                menu.updateWithChoice(choice, strings);

                break;
            }
            case 2: {
                menu.deleteWithChoice(choice, strings);
                break;
            }
            case 3: {
                menu.adminMenuOne();
                ;
                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Previous Menu\n");
                this.x = 0;
//                menu.mainMenu();
                menu.adminMenuOne();
        }


    }
}
