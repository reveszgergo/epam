package com.example.epam.epam.reservation;

import com.example.epam.epam.Car;
import com.example.epam.epam.CarRespository;
import com.example.epam.epam.Location;
import com.example.epam.epam.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATE = "2000-01-01";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRespository carRespository;

    private Car car1;
    private Car car2;
    private List<Car> cars;

    @Before
    public void prepare(){
        car1 = new Car();
        car1.setType("Ferrari");
        car1.setPrice(2000);
        car1.setFromDate("2017-11-10");
        car1.setUntilDate("2017-11-15");
        car1.setLocation(new Location("Debrecen"));
        car1.setUser(new User("John", "Doe"));

        car2 = new Car();
        car2.setType("Bugatti");
        car2.setPrice(3000);
        car2.setFromDate(DEFAULT_DATE);
        car2.setUntilDate(DEFAULT_DATE);
        car2.setLocation(new Location("London"));
        car2.setUser(new User("", ""));

        cars = Arrays.asList(car1, car2);
    }

    @Test
    public void getAvailableCarsTest() throws Exception{
        List<Car> goodCars = new ArrayList<>();

        for(Car car : cars){
            if(car.getUser().getLastName().isEmpty() && car.getUser().getFirstName().isEmpty()){
                goodCars.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(goodCars);
        mockMvc.perform(get("/api/v1/search").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Bugatti")))
                .andExpect(jsonPath("$[0].price", is(3000)))
                .andExpect(jsonPath("$[0].fromDate", is(DEFAULT_DATE)))
                .andExpect(jsonPath("$[0].untilDate", is(DEFAULT_DATE)));
    }

    @Test
    public void filterByLocationTest() throws Exception{
        List<Car> goodCars = new ArrayList<>();

        for(Car car : cars){
            if(car.getLocation().getCity().equals("Debrecen")){
                goodCars.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(goodCars);
        mockMvc.perform(get("/api/v1/search/by_location?location=Debrecen").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Ferrari")))
                .andExpect(jsonPath("$[0].price", is(2000)));
    }

    @Test
    public void filterByPriceTest() throws Exception{
        List<Car> goodCars = new ArrayList<>();

        for(Car car : cars){
            if(car.getPrice() <= 2500 && car.getPrice() >= 1500){
                goodCars.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(goodCars);
        mockMvc.perform(get("/api/v1/search/by_price?min_price=1500&max_price=2500").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Ferrari")))
                .andExpect(jsonPath("$[0].price", is(2000)));
    }

    @Test
    public void filterByTypeTest() throws Exception{
        List<Car> goodCars = new ArrayList<>();

        for(Car car : cars){
            if(car.getType().equals("Bugatti")){
                goodCars.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(goodCars);
        mockMvc.perform(get("/api/v1/search/by_type?type=Bugatti").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Bugatti")))
                .andExpect(jsonPath("$[0].price", is(3000)));
    }

    @Test
    public void filterByTimeTest() throws Exception{
        List<Car> goodCars = new ArrayList<>();
        Date fromDate = new SimpleDateFormat(DATE_FORMAT).parse("2017-11-10");
        Date untilDate = new SimpleDateFormat(DATE_FORMAT).parse("2017-11-15");

        for(Car car : cars){
            if((fromDate.compareTo(new SimpleDateFormat(DATE_FORMAT).parse(car.getFromDate())) > 0 && untilDate.compareTo(new SimpleDateFormat(DATE_FORMAT).parse(car.getUntilDate())) > 0)
                    || (fromDate.compareTo(new SimpleDateFormat(DATE_FORMAT).parse(car.getFromDate())) < 0 && untilDate.compareTo(new SimpleDateFormat(DATE_FORMAT).parse(car.getUntilDate())) < 0)){
                goodCars.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(goodCars);
        mockMvc.perform(get("/api/v1/search/by_time?from=2017-11-10&until=2017-11-15").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Bugatti")))
                .andExpect(jsonPath("$[0].price", is(3000)));
    }

}
