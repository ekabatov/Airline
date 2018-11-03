package com.foxminded.airline.web.controller;

import com.foxminded.airline.domain.service.impl.FlightServiceImpl;
import com.foxminded.airline.dto.FlightDTO;
import com.foxminded.airline.utils.FlightConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchFlightController {
    @Autowired
    FlightServiceImpl flightService;

    @Autowired
    FlightConverter flightConverter;

    private FlightDTO flightDTO;

    @GetMapping(value = "/searchflight",
            params = {"nameDepartureAirport", "nameArrivalAirport", "date"})
    public String showBuyTicket(@RequestParam("nameDepartureAirport") String nameDepartureAirport,
                                @RequestParam("nameArrivalAirport") String nameArrivalAirport,
                                @RequestParam("date") String date) {
        flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(nameDepartureAirport);
        flightDTO.setArrivalAirport(nameArrivalAirport);
        flightDTO.setDateString(date);
        return "searchFlight";
    }

    @PostMapping(value = {"/searchflight", "/user/searchflight"})
    public ResponseEntity<List<FlightDTO>> searchFlight() {
        return new ResponseEntity<>(flightConverter.createDTOsForFlights(flightService.findFlightsByFlightDTO(flightDTO)), HttpStatus.OK);
    }

    @GetMapping(value = "/user/searchflight",
            params = {"nameDepartureAirport", "nameArrivalAirport", "date"})
    public String showBuyTicketForUser(@RequestParam("nameDepartureAirport") String nameDepartureAirport,
                                       @RequestParam("nameArrivalAirport") String nameArrivalAirport,
                                       @RequestParam("date") String date) {
        flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(nameDepartureAirport);
        flightDTO.setArrivalAirport(nameArrivalAirport);
        flightDTO.setDateString(date);
        return "/user/searchFlight";
    }
}