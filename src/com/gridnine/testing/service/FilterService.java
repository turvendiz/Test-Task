package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FilterService {

    List<Flight> removeItemUpToTheCurrentTime(List<Flight> flights);

    List<Flight> removeItemTravelingBackInTime(List<Flight> flights);

    List<Flight> removeItemIdleMoreThanTwoHours(List<Flight> flights);

}
