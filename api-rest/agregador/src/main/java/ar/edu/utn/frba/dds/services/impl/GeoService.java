package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.services.IGeoService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeoService implements IGeoService {

    private final WebClient webClient;

    public GeoService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public String obtenerProvincia(Double latitud, Double longitud) {
        String url = "https://nominatim.openstreetmap.org/reverse" +
                "?lat=" + latitud +
                "&lon=" + longitud +
                "&format=json&addressdetails=1";

        Map response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
          if(!response.containsKey("address")) {
              return null;
          }
          Map address = (Map) response.get("address");
          return (String) address.get("state");
    }
}