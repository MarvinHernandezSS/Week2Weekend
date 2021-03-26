package com.ss.mar.jb.utopia.FrontEnd;

import com.ss.mar.jb.utopia.DAO.DbConnect;
import com.ss.mar.jb.utopia.entities.Airport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuSystem {

    private Integer x = 0;
    private String a;
    private Integer counter = 1;

    public void mainMenu() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to the Utopia Airlines Management System. " +
                "Which category of a user are you?");
        System.out.println("1) Employee");
        System.out.println("2) Administrator");
        System.out.println("3) Traveler");


//        handleMainMenuResponse();
        handleMainMenuResponse(userInput);
        userInput.close();
    }

    private void handleMainMenuResponse(Scanner scan) {
//    private void handleMainMenuResponse() {
//        Scanner userInput = new Scanner(System.in);
//        String inputCommands;
        verifyUserInput(scan);
//        verifyUserInput();
//        Integer x = verifyUserInput();

//        if (userInput.hasNextLine()) {
//            inputCommands = userInput.nextLine();
//            try {
//                x = Integer.parseInt(inputCommands);
//            } catch (NumberFormatException e) {
//                System.out.println("Please make a valid selection");
//                return 0;
//            }finally {
//                userInput.close();
//            }

        switch (this.x) {
            case 1: {
                employeeMenuOne();
                break;
            }
            case 2: {
                adminMenuOne();
                break;
            }
            case 3: {
                travelerMemberShipMenu();
                break;
            }
            default:
//                    System.out.println("Please make a valid selection");
//                    System.out.println();
                System.out.println(x);


                mainMenu();
        }

//            if (x > 0 && x <= 3) {
//                System.out.println("debug" + x);
//                return x;
//            } else {
//                System.out.println("Please make a valid selection");
//                return 0;
//            }
//        }
//        return x;
    }

    private void travelerMemberShipMenu() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter your Membership Number:");

        if(handletravelerMemberShipMenuInput(userInput)){
            travelerMenuOne();
        }
        else{
            System.out.println("That is not a valid membership number. Returning to the Main Menu");
            mainMenu();
        }
        
        userInput.close();

    }

    private void travelerMenuOne() {
        System.out.println("\n1)\tBook a Ticket\n" +
                "2)\tCancel an Upcoming Trip\n" +
                "3)\tQuit to Previous (should take you to menu MAIN)\n");
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

        for (ArrayList<String> membershipNum : membershipNums) {
            if (this.x == Integer.parseInt(membershipNum.get(0))){
                match = true;
                break;
            }

        }


        return match;
    }

    private void adminMenuOne() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("1) Add/Update/Delete/Read Flights");
        System.out.println("2) Add/Update/Delete/Read Seats");
        System.out.println("3) Add/Update/Delete/Read Tickets and Passengers");
        System.out.println("4) Add/Update/Delete/Read Airports");
        System.out.println("5) Add/Update/Delete/Read Travelers");
        System.out.println("6) Add/Update/Delete/Read Employees");
        System.out.println("7) Over-ride Trip Cancellation for a ticket.");
        userInput.close();
    }

    private void employeeMenuOne() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("1)Enter Flights You Mangage");
        System.out.println("2) Quit to previous");
        handleEmployeeMenuOneResponse(userInput);
        userInput.close();
    }

    private void handleEmployeeMenuOneResponse(Scanner userInput) {
        verifyUserInput(userInput);

        switch (this.x) {
            case 1: {
                this.x = 0;
                try {
                    employeeMenuTwo();
                } catch (SQLException throwables) {
                    System.out.println("Oops something went wrong... now returning to Main Menu");
                    this.x = 0;
                    mainMenu();
                }

                break;
            }
            case 2: {
                mainMenu();
                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Main Menu");
                this.x = 0;
                mainMenu();
        }
    }

    private void employeeMenuTwo() throws SQLException {
        Scanner userInput = new Scanner(System.in);
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

        handleEmployeeMenuTwoResponse(userInput, one);

//        SELECT *, TIMESTAMP(UTOPIA.FLIGHT.DEPARTURE_TIME + UTOPIA.FLIGHT.TRAVEL_TIME_ESTIMATE) AS ARRIVALTIME FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID=123

        userInput.close();
    }

//    private Integer debughandleEmployeeMenuTwoResponse = 0;

    private void handleEmployeeMenuTwoResponse(Scanner userInput, ArrayList<ArrayList<String>> one) {
        verifyUserInput(userInput);
//        debughandleEmployeeMenuTwoResponse++;

        if (this.x > this.counter || this.x == 0) {
            System.out.println("Invalid selection... now returning to previous menu");
            this.counter = 1;
            employeeMenuOne();
        }
        if (this.x != this.counter) {
            employeeMenuThree(one.get(x - 1));
            this.counter =1;
        } else {
            this.counter = 1;
            employeeMenuOne();
        }

    }

    private void employeeMenuThree(ArrayList<String> one) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("1) View more details about the flight");
        System.out.println("2) Update the details of the Flight");
        System.out.println("3) Add Seats to Flight");
        System.out.println("4) Quit to previous");

        handleEmployeeMenuThreeResponse(userInput, one);

        userInput.close();
    }

    private void handleEmployeeMenuThreeResponse(Scanner userInput, ArrayList<String> one) {
        verifyUserInput(userInput);


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
                        " WHERE UTOPIA.FLIGHT.ID=123";

                try {
                    threeOne = dbc.connSelect(sql);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    this.x = 0;
                }

                //rounding down to not to exceed remaining seating capacity
                Integer first = (int) Math.floor(Integer.parseInt(threeOne.get(0).get(2)) * .15);
                Integer business = (int) Math.floor(Integer.parseInt(threeOne.get(0).get(2)) * .25);
                Integer economy = (int) Math.floor(Integer.parseInt(threeOne.get(0).get(2)) * .60);

                System.out.println("Departure Airport: " + one.get(1) +
                        " | " + "Arrival Airport: " + one.get(3) + " |");

                String[] temp1 = threeOne.get(0).get(0).split(" ");
                System.out.println("Departure Date: " + temp1[0] + " | " + "Departure Time: " +
                        temp1[1].substring(0, temp1[1].length() - 2) + " |");
                String[] temp2 = threeOne.get(0).get(1).split(" ");
                System.out.println("Arrival Date: " + temp2[0] + " | " + "Arrival Time: " +
                        temp2[1].substring(0, temp2[1].length() - 2) + " |\n");

                System.out.println("Available Seats by Class:\n" +
                        "1) First → " + first + "\n" +
                        "2) Business → " + business + "\n" +
                        "3) Economy → " + economy + "\n" +
                        "4) Return to previous menu");

//                System.out.println("debug X = " + this.x);

                if (userInput.hasNextLine()){
                    employeeMenuThree(one);
                }

                break;
            }
            case 2: {
                System.out.println("You have chosen to update the Flight with Flight" +
                        " Id: " + one.get(0) + " and Departure Airport: " +
                        one.get(1) + " and Arrival Airport: " +
                        one.get(3) + ".");
                System.out.println("Enter ‘quit’ at any prompt to cancel operation.\n");
                System.out.println("Please enter new Origin Airport and City or enter N/A for no change:");
                System.out.println("\n");

                handleEmployeeMenuThreeResponseTwo(userInput,one);
                

                break;
            }
            case 3: {
                System.out.println("Pardon our dust, feature not implemented");
                this.x = 0;
                this.counter = 1;
                mainMenu();

                break;
            }
            case 4: {
                try {
                    this.x =0;
                    this.counter =1;
                    employeeMenuTwo();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Main Menu");
                this.x = 0;
                mainMenu();
        }


    }

    private void handleEmployeeMenuThreeResponseTwo(Scanner userInput, ArrayList<String> one) {
        String inputCommands;
        Integer x = 0;
        Airport origin = new Airport();
        Airport dest = new Airport();
        StringBuilder departTime = new StringBuilder();
        StringBuilder arriveTime = new StringBuilder();
        DbConnect dbc = new DbConnect();


        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){origin.setAirportCode(inputCommands); }
        }
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ origin.setCity(inputCommands); }
        }
        System.out.println("Please enter new Destination Airport and City or enter N/A for no change:");
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){dest.setAirportCode(inputCommands); }
        }
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ dest.setCity(inputCommands); }
        }
        System.out.println("Please enter new Departure Date (YYYY-MM-DD) or enter N/A for no change:");
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ departTime.append(inputCommands); }
        }
        System.out.println("Please enter new Departure Time (HH:MM:SS) or enter N/A for no change:");
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ departTime.append(" " + inputCommands); }
        }
        System.out.println("Please enter new Arrival Date (YYYY-MM-DD) or enter N/A for no change:");
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ arriveTime.append(inputCommands); }
        }
        System.out.println("Please enter new Arrival Time (HH:MM:SS) or enter N/A for no change:");
        if (userInput.hasNextLine()){
            inputCommands=userInput.nextLine();
            if (inputCommands != "N/A"){ arriveTime.append(" " + inputCommands); }
        }


        String de = departTime.toString();
        String ar = arriveTime.toString();



        System.out.println("debug " + origin.getAirportCode() + " " + origin.getCity());
        System.out.println("debug " + dest.getAirportCode() + " " + dest.getCity());
        System.out.println("debug " + departTime.toString());
        System.out.println("debug " + arriveTime.toString());

        String sqlInsertNewOriginIntoAirport1 = "INSERT INTO utopia.airport VALUES ('"
                + origin.getAirportCode() + "', '" + origin.getCity() + "');";

        String sqlInsertNewDestIntoAirport1 = "INSERT INTO utopia.airport VALUES ('"
                + dest.getAirportCode() + "', '" + dest.getCity() + "');";

//        System.out.println(sqlInsertNewOriginIntoAirport1);
//        System.out.println(sqlInsertNewDestIntoAirport1);
        Integer originSuccess = 0;
        Integer destSuccess = 0;

        //insert Origin into Airport
        if (origin.getCity() != null && origin.getAirportCode() != null){
            try {
                originSuccess =  dbc.insUpconn(sqlInsertNewOriginIntoAirport1, false,"employeeMenuTwo");
            } catch (SQLException throwables) { }
        }
        //Insert Destination into Airport
        if (dest.getAirportCode() !=null && dest.getCity() != null){
            try {
                destSuccess =  dbc.insUpconn(sqlInsertNewDestIntoAirport1, false,"employeeMenuTwo");
            } catch (SQLException throwables) { }
        }


        //insert a new route and capture the PK key generated
        Integer routeGenKey = 0;
        if ( originSuccess > 0 && destSuccess > 0){
            String sql = "INSERT INTO UTOPIA.ROUTE VALUES (NULL ,'"+ origin.getAirportCode()
            + "','"+dest.getAirportCode() +"');";
            try {
                routeGenKey = dbc.insUpconn(sql,true,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            System.out.println(sql);
        }
        System.out.println("debug routeGenKey: " + routeGenKey);


        //update the origin of a route
        if (originSuccess > 0 && destSuccess <= 0){
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
           String getRouteID="SELECT utopia.flight.route_id FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID = "+ one.get(0) +";";
            try {
                temp = dbc.connSelect(getRouteID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String updateOrginonRoute = "UPDATE UTOPIA.ROUTE SET UTOPIA.ROUTE.origin_id ='" +
                    origin.getAirportCode() +"' WHERE UTOPIA.ROUTE.ID = '" + temp.get(0).get(0) + "';";
//            System.out.println(updateOrginonRoute);
            try {
                dbc.insUpconn(updateOrginonRoute,false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //update the destination of a route
        if (originSuccess <= 0 && destSuccess > 0){
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            String getRouteID="SELECT utopia.flight.route_id FROM UTOPIA.FLIGHT WHERE UTOPIA.FLIGHT.ID = "+ one.get(0) +";";
            try {
                temp = dbc.connSelect(getRouteID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String updateDestinationRoute = "UPDATE UTOPIA.ROUTE SET UTOPIA.ROUTE.destination_id ='" +
                    dest.getAirportCode() +"' WHERE UTOPIA.ROUTE.ID = '" + temp.get(0).get(0) + "';";
            try {
                dbc.insUpconn(updateDestinationRoute,false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        //now I need to update flight with the new departure/arrival date times and if needed,
        // i need to also update//the route if routeGenKey is > 0
        //Flight Update based off if there is a new route to add or not
        if(routeGenKey > 0){
//            UPDATE `utopia`.`flight` SET `route_id`='77', `departure_time`='2021-06-21 18:32:14' WHERE `id`='971';
            String flightUpdateNewRoute = "UPDATE `utopia`.`flight` SET `route_id`='" + routeGenKey +"'," +
                    " `departure_time`='"+ departTime.toString() + "' WHERE `id`='" + one.get(0) + "';";
            try {
                dbc.insUpconn(flightUpdateNewRoute,false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else {
//            UPDATE `utopia`.`flight` SET `departure_time`='2021-06-21 18:32:14' WHERE `id`='971';
            String flightUpdateDepartureOnly = "UPDATE `utopia`.`flight` SET `" +
                    "departure_time`='" + departTime.toString() + "' WHERE `id`='"+ one.get(0) +"';";
            try {
                dbc.insUpconn(flightUpdateDepartureOnly,false,"employeeMenuTwo");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        employeeMenuThree(one);


    }


    private void verifyUserInput(Scanner userInput) {
//    private void verifyUserInput() {
//        Scanner userInput = new Scanner(System.in);
        String inputCommands;
//        Integer x = 0;
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


}


