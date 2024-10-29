package com.example.eventadminapp1;

public class Event {
    private Long eventId;
    private String eventName;
    private String location;
    private double price;
    private int ticketsAvailable;
    private String imageUrl;

    public Event() {
    }

    public Event(Long eventId, String eventName, String location, double price, int ticketsAvailable, String imageUrl) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.location = location;
        this.price = price;
        this.ticketsAvailable = ticketsAvailable;
        this.imageUrl = imageUrl;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {

        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

