package com.foxminded.airline.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.airline.domain.entity.Flight;
import com.foxminded.airline.domain.service.impl.FlightServiceImpl;
import com.foxminded.airline.domain.service.utils.FlightConverter;
import com.foxminded.airline.web.dto.FlightDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SearchFlightControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private SearchFlightController searchFlightController;

    @Mock
    private FlightServiceImpl flightService;

    @Mock
    private FlightConverter flightConverter;

    private ObjectMapper mapper;

    private FlightDTO flightDTO;
    private Flight flight;
    private List<FlightDTO> flightDTOS;
    private List<Flight> flights;

    @Before
    public void setUp() throws Exception {
        flightDTO = new FlightDTO();
        flightDTO.setDateString("2018-11-11");
        flightDTO.setDepartureAirport("London, airport Heathrow");
        flightDTO.setArrivalAirport("Berlin, airport Tegel");

        flightDTOS = new ArrayList<>();
        flightDTOS.add(flightDTO);

        flight = new Flight();

        flights = new ArrayList<>();
        flights.add(flight);

        mapper = new ObjectMapper();


        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(searchFlightController)
                .build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenSearchFlightsByDepartureAirportAndArrivalAirportAndDate_thenReturnFlightsIfExists() throws Exception {
        when(flightService.findFlightsByDepartureAirportAndArrivalAirportAndDate(flightDTO.getDateString(), flightDTO.getDepartureAirport(), flightDTO.getArrivalAirport())).thenReturn(flights);
        when(flightConverter.createDTOsForFlights(flights)).thenReturn(flightDTOS);

        MockHttpServletResponse searchFlightResponse = mvc.perform(
                get("/searchflight")
                        .param("nameDepartureAirport",flightDTO.getDepartureAirport())
                        .param("nameArrivalAirport",flightDTO.getArrivalAirport())
                        .param("date",flightDTO.getDateString())
                        .contentType(MediaType.TEXT_HTML_VALUE))
                .andReturn().getResponse();

        MockHttpServletResponse listFlightsresponse = mvc.perform(
                get("/searchflight/listflights")
                        .contentType(MediaType.TEXT_HTML_VALUE)
        )
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), listFlightsresponse.getStatus());
        assertEquals(mapper.writeValueAsString(flightDTOS), listFlightsresponse.getContentAsString());
    }
}