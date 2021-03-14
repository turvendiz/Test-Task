package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FilterService;
import com.gridnine.testing.util.FlightBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gridnine.testing.util.FlightBuilder.createFlight;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterServiceImplTest {

    private List<Flight> testingFlights;
    private static final FilterService filterService = new FlightFilterServiceImpl();

    @BeforeEach
    void setUp() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        testingFlights = FlightBuilder.createFlights();
    }

    @Test
    void removeItemUpToTheCurrentTime() {

        List<Flight> result = filterService.removeItemUpToTheCurrentTime(testingFlights);

        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> expectedFlights = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));

        assertEquals(expectedFlights, result);
    }

    @Test
    void removeItemTravelingBackInTime() {
        List<Flight> result = filterService.removeItemTravelingBackInTime(testingFlights);

        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> expectedFlights = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));

        assertEquals(expectedFlights, result);
    }

    @Test
    void removeItemIdleMoreThanTwoHours() {
        List<Flight> result = filterService.removeItemIdleMoreThanTwoHours(testingFlights);

        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> expectedFlights = Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)));

        assertEquals(expectedFlights, result);
    }
}