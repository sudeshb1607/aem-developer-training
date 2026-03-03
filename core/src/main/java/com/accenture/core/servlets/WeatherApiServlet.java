package com.accenture.core.servlets;

import com.accenture.core.services.WeatherService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/weatherAPI",
            "sling.servlet.methods=POST"
        })

@ServiceDescription("Weather API Servlet")
public class WeatherApiServlet extends SlingAllMethodsServlet {

    @Reference
    private WeatherService weatherService;

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherApiServlet.class);

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        String cityName = request.getParameter("cityName");

        String weather_url = weatherService.processWeatherBasedOnCity(cityName);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpURLConnection connection = (HttpURLConnection)  new URL(weather_url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        InputStream inputStream = connection.getInputStream();
        String apiResponse = new String(inputStream.readAllBytes(),
                StandardCharsets.UTF_8);
        LOG.debug("API Response: {}", apiResponse);

        response.getWriter().write(apiResponse);

    }
}
