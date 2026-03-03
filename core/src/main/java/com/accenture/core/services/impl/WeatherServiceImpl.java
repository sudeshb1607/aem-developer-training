package com.accenture.core.services.impl;

import com.accenture.core.osgi.WeatherConfig;
import com.accenture.core.services.WeatherService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import java.util.Map;


@Component(service = WeatherService.class)
@Designate(ocd= WeatherConfig.class, factory=true)
public class WeatherServiceImpl implements WeatherService {

    private String api_endpoint;

    private static final Map<String, String> CITY_CODES = Map.of(
            "munich","48.13743,11.57549",
            "lisbon", "38.71667,-9.13333",
            "london", "51.50853,-0.12574"
    );

    @Activate
    @Modified
    public void activate(WeatherConfig weatherConfig) {
        this.api_endpoint = weatherConfig.api_endpoint();
    }

    @Override
    public String processWeatherBasedOnCity(String city) {

        String city_coord = CITY_CODES.get(city);
        String[] latLong = city_coord.split(",");

        return api_endpoint
                + "?latitude=" + latLong[0]
                + "&longitude=" + latLong[1]
                + "&current_weather=true";
    }
}
