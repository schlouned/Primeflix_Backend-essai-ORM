package com.BackPrimeflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"legs", "weight_name", "geometry", "weight", "duration"})
public class RoutesDto {
    long distance;
    public long getDistance() {
        return distance;
    }
    public void setDistance(long distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "distance=" + distance +
                '}';
    }
}
