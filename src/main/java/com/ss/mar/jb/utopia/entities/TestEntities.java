package com.ss.mar.jb.utopia.entities;

import java.time.LocalDateTime;

public class TestEntities {
    public static void main(String[] args) {
        Airport A = new Airport();
        A.setAirportCode("123");
//        A.setCity("123City");
        System.out.println(A.getCity());

        A.setCity("Los Angelos");
        A.setAirportCode("abc");


        System.out.println(A.getCity());
        System.out.println(A.getAirportCode());

        Route B = new Route();
        B.setDestairportCode("123");
        System.out.println(B.getDestairportCode());


        B.setOriginairportCode("456");
        System.out.println(B.getOriginairportCode());



        B.setDestairportCode("xyz");
        System.out.println(B.getDestairportCode());


        B.setOriginairportCode("leE");
        System.out.println(B.getOriginairportCode());


//        2019-03-11 21:32:14

        LocalDateTime t =
                LocalDateTime.of(2019,03,11,21,32,14);

        System.out.println(t.toString());


    }


}
