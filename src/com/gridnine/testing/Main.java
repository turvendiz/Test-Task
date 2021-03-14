package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {

    private static final FilterService filterService = new FlightFilterServiceImpl();

    public static void main(String[] args) {
        List<Flight> allFlights = FlightBuilder.createFlights();
        allFlights.forEach(System.out::println);
        System.out.println("****");
        filterService.removeItemIdleMoreThanTwoHours(allFlights).forEach(System.out::println);
    }
}
