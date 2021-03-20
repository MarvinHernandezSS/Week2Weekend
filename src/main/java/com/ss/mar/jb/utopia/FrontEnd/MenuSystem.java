package com.ss.mar.jb.utopia.FrontEnd;

import com.ss.mar.jb.utopia.DAO.DbConnect;

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
                employeeMenuTwo();
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

    private void employeeMenuTwo() {
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

        one = dbc.conn(sql1);

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


        two = dbc.conn(sql2.toString());

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
            employeeMenuThree(one);

//            System.out.println("You have chosen to view the Flight with Flight" +
//                    " Id: " + one.get(x).get(0) + " and Departure Airport: " +
//                    one.get(x).get(1) + " and Arrival Airport: " +
//                    one.get(x).get(3) + ". ");
////            System.out.println(debughandleEmployeeMenuTwoResponse);
        } else {
            this.counter = 1;
            employeeMenuOne();
        }

    }

    private void employeeMenuThree(ArrayList<ArrayList<String>> one) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("1) View more details about the flight");
        System.out.println("2) Update the details of the Flight");
        System.out.println("3) Add Seats to Flight");
        System.out.println("4) Quit to previous");

        handleEmployeeMenuThreeResponse(userInput,one);

        userInput.close();
    }

    private void handleEmployeeMenuThreeResponse(Scanner userInput, ArrayList<ArrayList<String>> one) {
        verifyUserInput(userInput);

        switch (this.x) {
            case 1: {
                this.x = 0;
                employeeMenuTwo();
                break;
            }
            case 2: {
                mainMenu();
                break;
            }
            case 3:{

                break;
            }
            case 4:{

                break;
            }
            default:
                System.out.println("Invalid selection... now returning to Main Menu");
                this.x = 0;
                mainMenu();
        }


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


