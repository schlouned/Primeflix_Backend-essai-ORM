package com.BackPrimeflix.controller;


import com.BackPrimeflix.dto.GpsCoordinatesDto;
import com.BackPrimeflix.dto.RoutesDto;
import com.BackPrimeflix.util.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DistanceCalculationController {
    //members: URLs
    private static final String GPS_ISL_COORDINATES_URL = "https://nominatim.openstreetmap.org/search?street=Rue Saint-Laurent 33&city=Liège&postalcode=4000&countrycodes=BE&format=json&limit=1";
    private static final String DISTANCE_SHOP_DELIVERY = "https://router.project-osrm.org/route/v1/driving/";

    @Autowired
    private ParsingService parsingService;

    @GetMapping("https://nominatim.openstreetmap.org/*")
    public GpsCoordinatesDto GetGpsFromAddress(String url){
                return parsingService.gpsPars(url);
    }

    @GetMapping("https://router.project-osrm.org/*")
    public RoutesDto distanceCalculation(GpsCoordinatesDto shop, GpsCoordinatesDto deliveryAddress){
        //build the url
        String url = DISTANCE_SHOP_DELIVERY + shop.getLon() +","+shop.getLat()+";"+deliveryAddress.getLon()+","+deliveryAddress.getLat();
        // call the api
        return parsingService.distancePars(url);
    }

}
