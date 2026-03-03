package com.accenture.core.osgi;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface WeatherConfig {

    @AttributeDefinition(
            name = "Weather API Endpoint",
            description = "This calls the Weather API endpoint"
    )
    String api_endpoint() default "http://api.openweathermap.org/data/2.5/weather";
}
