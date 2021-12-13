package com.BackPrimeflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonIgnoreProperties({ "code", "waypoints"})
public class DeliveryDistanceDto implements Serializable {
    List<RoutesDto> routes;

    public List<RoutesDto>  getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesDto>  routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "DeliveryDistance{" +
                "routes=" + routes +
                '}';
    }
}
