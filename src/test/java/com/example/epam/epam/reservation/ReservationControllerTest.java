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
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRespository carRespository;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private Car car1;
    private Car car2;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters){
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
    }

    @Before
    public void prepare(){
        car1 = new Car();
        car1.setType("Ferrari");
        car1.setPrice(1000);
        car1.setLocation(new Location("Debrecen"));
        car1.setUser(new User("John", "Doe"));

        car2 = new Car();
        car2.setType("Bugatti");
        car2.setPrice(2000);
        car2.setLocation(new Location("Los Angeles"));
        car2.setUser(new User("",""));
    }

    @Test
    public void getReservationsTest() throws Exception{
        List<Car> cars = new ArrayList<>();
        List<Car> cars2 = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        for(Car car : cars){
            if( !car.getUser().getFirstName().isEmpty() && !car.getUser().getLastName().isEmpty()){
                cars2.add(car);
            }
        }

        given(carRespository.findAll()).willReturn(cars2);
        mockMvc.perform(get("/api/v1/reservation").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("Ferrari")))
                .andExpect(jsonPath("[0].price", is(1000)));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
