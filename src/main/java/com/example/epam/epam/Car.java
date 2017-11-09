package com.example.epam.epam;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private Integer price;
    private String fromDate;
    private String untilDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    protected Car() {
    }

    public Car(String type, Integer price, String fromDate, String untilDate, Location location, User user) {
        this.type = type;
        this.price = price;
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.location = location;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(String untilDate) {
        this.untilDate = untilDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
