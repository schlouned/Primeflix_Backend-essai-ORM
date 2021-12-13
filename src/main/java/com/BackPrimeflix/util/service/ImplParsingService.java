package com.BackPrimeflix.util.service;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.DeliveryDistanceDto;
import com.BackPrimeflix.dto.GpsCoordinatesDto;
import com.BackPrimeflix.dto.RoutesDto;
import com.BackPrimeflix.exception.AddressDontExistCustomException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ImplParsingService implements ParsingService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object parse(String url){

        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public GpsCoordinatesDto gpsPars(String url){
        //create a mapper object
        ObjectMapper mapper = new ObjectMapper();
        //recover the list of result (it's a list even if there is only one result in
        List<GpsCoordinatesDto> gpsCoordinatesList = mapper.convertValue(this.parse(url), new TypeReference<List<GpsCoordinatesDto>>() { });
        //if no address list throw exception
        if(gpsCoordinatesList.size()==0)
            throw new AddressDontExistCustomException(ResponseCode.ADDRESS_DONT_EXIST);
        //extract the result (get(0)
        GpsCoordinatesDto gpsCoordinates = gpsCoordinatesList.get(0);
        //return the result
        return gpsCoordinates;
    }

    @Override
    public RoutesDto distancePars(String url){
        //create a mapper object
        ObjectMapper mapper = new ObjectMapper();
        //recover the list of result (it's a list even if there is only one result in
        DeliveryDistanceDto deliveryDistance = mapper.convertValue(this.parse(url), new TypeReference<DeliveryDistanceDto>() { });
        //extract the route
        List<RoutesDto> routes = deliveryDistance.getRoutes();
        RoutesDto route = routes.get(0);

        return route;
    }

}
