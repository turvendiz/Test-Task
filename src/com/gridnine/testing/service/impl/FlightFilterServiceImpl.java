package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FilterService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterServiceImpl implements FilterService {

    @Override
    public List<Flight> removeItemUpToTheCurrentTime(List<Flight> flights) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                if (!segment.getDepartureDate().isBefore(currentDateTime)) {
                    filteredFlights.add(flight);
                }
                break;
            }
        }
        return filteredFlights;
    }

    @Override
    public List<Flight> removeItemTravelingBackInTime(List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                if (!segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    filteredFlights.add(flight);
                }
                break;
            }
        }
        return filteredFlights;

    }

    @Override
    public List<Flight> removeItemIdleMoreThanTwoHours(List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            Duration idleTime = Duration.ZERO;
            Duration limit = Duration.ofHours(2);
            for (int i = 0; i < segments.size() - 1; i++) {
                idleTime = idleTime.plus(
                        Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate()));
            }
            if (idleTime.compareTo(limit) < 0) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }
}
