package com.airguard.airguard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AirlyClientService {

    @Value("${airlyApiKey}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public String getDataByExternalSensorId(Integer externalSensorId) throws URISyntaxException, IOException, InterruptedException {
        var request = HttpRequest.newBuilder(
                new URI("https://airapi.airly.eu/v2/measurements/installation?indexType=AIRLY_CAQI&installationId=" + externalSensorId.toString()))
                .setHeader("apikey", apiKey).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getNearestSensorInfo(Double longitude, Double latitude) throws IOException, InterruptedException, URISyntaxException {
        var request = HttpRequest.newBuilder(
                new URI("https://airapi.airly.eu/v2/installations/nearest?lat=" + latitude + "&lng=" + longitude + "&maxDistanceKM=3"))
                .setHeader("apikey", apiKey).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getPointMeasurements(Double longitude, Double latitude) throws IOException, InterruptedException, URISyntaxException {
        var request = HttpRequest.newBuilder(
                new URI("https://airapi.airly.eu/v2/measurements/point?indexType=AIRLY_CAQI&lat=" + latitude + "&lng=" + longitude))
                .setHeader("apikey", apiKey).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
