package com.example.epam.epam;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;

    @OneToMany(mappedBy = "location")
    private Set<Car> cars;

    public Location() {
    }

    public Location(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", cars=" + cars +
                '}';
    }
}
