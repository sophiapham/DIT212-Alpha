package cse.dit012.lost;

import android.location.Location;

public class Broadcast {
    private Course course;
    private String description;
    private double longitude;
    private double latitude;

    public Broadcast(Course course, String description, double longitude, double latitude) {
        this.course = course;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}