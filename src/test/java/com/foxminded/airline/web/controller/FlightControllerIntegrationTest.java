package com.foxminded.airline.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.airline.domain.entity.Flight;
import com.foxminded.airline.web.dto.FlightDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FlightControllerIntegrationTest {
    @Autowired
    private FlightController flightController;

    private MockMvc mvc;

    private ObjectMapper mapper;

    private FlightDTO flightDTO;
    private List<FlightDTO> flightDTOS;
    private FlightDTO notExistFlightDTO;

    @Before
    public void setUp() throws Exception {
        flightDTO = new FlightDTO();
        flightDTO.setNumber("1574");
        flightDTO.setDateString("2018-10-01");
        flightDTO.setTimeString("08:05");
        flightDTO.setPlaneName("Boeing 747");
        flightDTO.setDepartureAirport("London, airport Heathrow");
        flightDTO.setArrivalAirport("Stockholm, airport Arlanda");

        flightDTOS = new ArrayList<>();
        flightDTOS.add(flightDTO);

        Flight flight = new Flight();
        flight.setId((long) 1);

        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        notExistFlightDTO = new FlightDTO();
        notExistFlightDTO.setDepartureAirport("Berlin");
        notExistFlightDTO.setArrivalAirport("London");
        notExistFlightDTO.setDateString("2018-11-15");

        mapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    public void whenSearchFlightsByDepartureAirportAndArrivalAirportAndDate_thenReturnFlightsIfExist() throws Exception {
        mvc.perform(get("/flights")
                .param("nameDepartureAirport", flightDTO.getDepartureAirport())
                .param("nameArrivalAirport", flightDTO.getArrivalAirport())
                .param("date", flightDTO.getDateString())
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        MockHttpServletResponse listFlightsResponse = mvc.perform(
                get("/api/v1/flights/listflights")
                        .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), listFlightsResponse.getStatus());
        assertEquals(mapper.writeValueAsString(flightDTOS), listFlightsResponse.getContentAsString());
    }

    @Test
    public void whenSearchFlightsByDepartureAirportAndArrivalAirportAndDate_thenReturnEmptyListFlightsIfNotExist() throws Exception {
        mvc.perform(get("/flights")
                .param("nameDepartureAirport", notExistFlightDTO.getDepartureAirport())
                .param("nameArrivalAirport", notExistFlightDTO.getArrivalAirport())
                .param("date", notExistFlightDTO.getDateString())
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        MockHttpServletResponse listFlightsResponse = mvc.perform(
                get("/api/v1/flights/listflights")
                        .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), listFlightsResponse.getStatus());
        assertEquals(mapper.writeValueAsString(Collections.emptyList()), listFlightsResponse.getContentAsString());
    }

    @Test
    public void whenSearchFlightsForAirportByDate_thenReturnFlightsIfExist() throws Exception {
        mvc.perform(get("/admin/flights")
                .param("nameAirport", flightDTO.getDepartureAirport())
                .param("date", flightDTO.getDateString())
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        MockHttpServletResponse listFlightsResponse = mvc.perform(
                get("/api/v1/admin/flights/listflights")
                        .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), listFlightsResponse.getStatus());
        assertEquals(mapper.writeValueAsString(flightDTOS), listFlightsResponse.getContentAsString());
    }

    @Test
    public void whenSearchFlightsForAirportByDate_thenReturnEmptyListFlightsIfNotExist() throws Exception {
        mvc.perform(get("/admin/flights")
                .param("nameAirport", notExistFlightDTO.getDepartureAirport())
                .param("date", notExistFlightDTO.getDateString())
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        MockHttpServletResponse listFlightsResponse = mvc.perform(
                get("/api/v1/admin/flights/listflights")
                        .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), listFlightsResponse.getStatus());
        assertEquals(mapper.writeValueAsString(Collections.emptyList()), listFlightsResponse.getContentAsString());
    }
}