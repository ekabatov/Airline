package com.foxminded.airline.web.controller;

import com.foxminded.airline.domain.service.impl.FlightServiceImpl;
import com.foxminded.airline.web.dto.FlightDTO;
import com.foxminded.airline.domain.service.utils.FlightConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchFlightController {
    @Autowired
    private FlightServiceImpl flightService;

    @Autowired
    private FlightConverter flightConverter;

    private FlightDTO flightDTO;

    @GetMapping(value = "/searchflight",
            params = {"nameDepartureAirport", "nameArrivalAirport", "date"})
    public String showListFlightsPage(@RequestParam("nameDepartureAirport") String nameDepartureAirport,
                                @RequestParam("nameArrivalAirport") String nameArrivalAirport,
                                @RequestParam("date") String date) {
        flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(nameDepartureAirport);
        flightDTO.setArrivalAirport(nameArrivalAirport);
        flightDTO.setDateString(date);
        return "searchFlight";
    }

    @GetMapping(value = "/searchflight/listflights")
    public ResponseEntity<List<FlightDTO>> searchFlights() {
        return new ResponseEntity<>(flightConverter.createDTOsForFlights(flightService.findFlightsByDepartureAirportAndArrivalAirportAndDate(flightDTO.getDateString(), flightDTO.getDepartureAirport(), flightDTO.getArrivalAirport())), HttpStatus.OK);
    }

    @GetMapping(value = "/user/searchflight",
            params = {"nameDepartureAirport", "nameArrivalAirport", "date"})
    public String showListFlightsForUserPage(@RequestParam("nameDepartureAirport") String nameDepartureAirport,
                                       @RequestParam("nameArrivalAirport") String nameArrivalAirport,
                                       @RequestParam("date") String date) {
        flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(nameDepartureAirport);
        flightDTO.setArrivalAirport(nameArrivalAirport);
        flightDTO.setDateString(date);
        return "user/searchFlight";
    }

    @GetMapping(value = "/admin/listflights",
            produces = MediaType.TEXT_HTML_VALUE,
            params = {"nameAirport", "date"})
    public String showListFlightsForAdminPage(@RequestParam("nameAirport") String nameAirport,
                                  @RequestParam("date") String date) {
        flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(nameAirport);
        flightDTO.setArrivalAirport("");
        flightDTO.setDateString(date);
        return "admin/listFlights";
    }

    @PostMapping(value = "/admin/listflights")
    public ResponseEntity<List<FlightDTO>> searchFlightsForAirport() {
        return new ResponseEntity<>(flightConverter.createDTOsForFlights(flightService.findFlightsForAirportByDate(flightDTO.getDateString(), flightDTO.getDepartureAirport())), HttpStatus.OK);
    }
}