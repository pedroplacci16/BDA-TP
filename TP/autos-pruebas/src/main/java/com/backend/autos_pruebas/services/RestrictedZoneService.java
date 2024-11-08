package com.backend.autos_pruebas.services;

import com.backend.autos_pruebas.dtos.RestrictedZone;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import com.backend.autos_pruebas.dtos.AgencyConfig;

@Service
public class RestrictedZoneService {
    private final String RESTRICTED_ZONES_API = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isPositionRestricted(float lat, float lon) {
        AgencyConfig config = getAgencyConfig();
        return isOutsideRadius(lat, lon, config) || isInRestrictedZone(lat, lon, config.getZonasRestringidas());
    }

    private AgencyConfig getAgencyConfig() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(RESTRICTED_ZONES_API, JsonNode.class);
        JsonNode root = response.getBody();

        AgencyConfig config = new AgencyConfig();

        RestrictedZone.Coordinate agencyCoord = new RestrictedZone.Coordinate();
        agencyCoord.setLat(root.get("coordenadasAgencia").get("lat").asDouble());
        agencyCoord.setLon(root.get("coordenadasAgencia").get("lon").asDouble());

        config.setCoordenadasAgencia(agencyCoord);
        config.setRadioAdmitidoKm(root.get("radioAdmitidoKm").asDouble());
        config.setZonasRestringidas(parseRestrictedZones(root.get("zonasRestringidas")));

        return config;
    }

    private boolean isOutsideRadius(float lat, float lon, AgencyConfig config) {
        double agencyLat = config.getCoordenadasAgencia().getLat();
        double agencyLon = config.getCoordenadasAgencia().getLon();

        double distance = Math.sqrt(
                Math.pow(lat - agencyLat, 2) +
                        Math.pow(lon - agencyLon, 2)
        );

        // Convert the euclidean distance to approximate kilometers
        double distanceKm = distance * 111.32;

        return distanceKm > config.getRadioAdmitidoKm();
    }

    private boolean isInRestrictedZone(float lat, float lon, List<RestrictedZone> zones) {
        return zones.stream()
                .anyMatch(zone -> isPointInZone(lat, lon, zone));
    }

    public List<RestrictedZone> getRestrictedZones() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(RESTRICTED_ZONES_API, JsonNode.class);
        return parseRestrictedZones(response.getBody().get("zonasRestringidas"));
    }

    private List<RestrictedZone> parseRestrictedZones(JsonNode zonasRestringidas) {
        List<RestrictedZone> zones = new ArrayList<>();
        for (JsonNode zona : zonasRestringidas) {
            zones.add(parseZone(zona));
        }
        return zones;
    }

    private RestrictedZone parseZone(JsonNode zona) {
        RestrictedZone zone = new RestrictedZone();

        RestrictedZone.Coordinate noroeste = new RestrictedZone.Coordinate();
        noroeste.setLat(zona.get("noroeste").get("lat").asDouble());
        noroeste.setLon(zona.get("noroeste").get("lon").asDouble());

        RestrictedZone.Coordinate sureste = new RestrictedZone.Coordinate();
        sureste.setLat(zona.get("sureste").get("lat").asDouble());
        sureste.setLon(zona.get("sureste").get("lon").asDouble());

        zone.setNoroeste(noroeste);
        zone.setSureste(sureste);
        return zone;
    }

    private boolean isPointInZone(float pointLat, float pointLon, RestrictedZone zone) {
        return pointLat <= zone.getNoroeste().getLat() &&
                pointLat >= zone.getSureste().getLat() &&
                pointLon >= zone.getNoroeste().getLon() &&
                pointLon <= zone.getSureste().getLon();
    }
}

