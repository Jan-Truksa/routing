package net.truksa.route.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import net.truksa.route.model.raw.RawCountry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Value("${routing.data.uri}")
    private String rawDataUri;

    private List<RawCountry> rawCountries;

    private Map<String, List<String>> mapOfCountriesBorders;

    @PostConstruct
    private void readData() throws URISyntaxException, IOException, InterruptedException {
        final var objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        rawCountries = objectMapper.readValue(getRawCountriesResponse(), objectMapper.getTypeFactory().constructCollectionType(List.class, RawCountry.class));
    }

    public Map<String, List<String>> getMapOfCountriesAndBorders() {
        if (mapOfCountriesBorders == null) {
            mapOfCountriesBorders = Collections.unmodifiableMap(rawCountries.stream().collect(Collectors.toMap(RawCountry::cca3, RawCountry::borders)));
        }
        return mapOfCountriesBorders;
    }

    private String getRawCountriesResponse() throws URISyntaxException, IOException, InterruptedException {
        final var client = HttpClient.newHttpClient();
        final var request = HttpRequest.newBuilder()
                .uri(new URI(rawDataUri))
                .GET()
                .build();
        final var response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
