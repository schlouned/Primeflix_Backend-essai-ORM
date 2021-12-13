package com.BackPrimeflix.util.service;

import com.BackPrimeflix.dto.GpsCoordinatesDto;
import com.BackPrimeflix.dto.RoutesDto;

public interface ParsingService {
    //Service to parse Json in the desired model Class
    Object parse(String url);
    GpsCoordinatesDto gpsPars(String url);
    RoutesDto distancePars(String url);

}
