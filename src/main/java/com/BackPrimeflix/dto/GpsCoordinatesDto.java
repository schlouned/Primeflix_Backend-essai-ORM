package com.BackPrimeflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties ({ "place_id", "licence", "osm_type", "osm_id", "boundingbox", "display_name", "class", "type", "importance", "icon"}) //ignore usused properties
public class GpsCoordinatesDto implements Serializable {
    //members
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("lat")
    private String lat;

    //getters and setters
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    //to string

    @Override
    public String toString() {
        return "GpsCoordinates{" +
                "lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
