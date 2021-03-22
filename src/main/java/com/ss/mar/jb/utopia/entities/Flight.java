package com.ss.mar.jb.utopia.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Flight {

private Integer id;
private Integer route_id;
private Integer airplane_id;
private LocalDateTime departureTime;
private Integer reserved_seats;
private Float seat_price;
private Integer travelTimeEstimate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Integer route_id) {
        this.route_id = route_id;
    }

    public Integer getAirplane_id() {
        return airplane_id;
    }

    public void setAirplane_id(Integer airplane_id) {
        this.airplane_id = airplane_id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        String[] x = departureTime.split(" ");
        ArrayList<Integer> nums2Dates = new ArrayList<Integer>();

        for (String s : x) {
            Integer temp = 0;
            try {
                temp = Integer.parseInt(s);
                nums2Dates.add(temp);
            } catch (NumberFormatException e) {

            }
        }
        
        this.departureTime = LocalDateTime.of(nums2Dates.get(0),
                nums2Dates.get(1), nums2Dates.get(2),
        nums2Dates.get(3), nums2Dates.get(4), nums2Dates.get(5));
    }

    public Integer getReserved_seats() {
        return reserved_seats;
    }

    public void setReserved_seats(Integer reserved_seats) {
        this.reserved_seats = reserved_seats;
    }

    public Float getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(Float seat_price) {
        this.seat_price = seat_price;
    }

    public Integer getTravelTimeEstimate() {
        return travelTimeEstimate;
    }

    public void setTravelTimeEstimate(Integer travelTimeEstimate) {
        this.travelTimeEstimate = travelTimeEstimate;
    }
}
