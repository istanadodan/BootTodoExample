package ksd.sto.ndm.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PrometheusServiceImpl implements PrometheusService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    @Value("${monitoring.prometheusUrl}")
    private String prometheusUrl;
    @Value("${monitoring.apiKey}")
    private String apiKey;
    
    public PrometheusServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
    }

    /**
     * GET request
     * 
     * @param query
     * @param url
     * @return
     */
    @Override
    public String sendQuery(String endpoint, String query) {
        String url = this.prometheusUrl + endpoint + query;
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate
            .exchange(url, HttpMethod.GET, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj;
        try {
            jsonObj = mapper.readTree(response.getBody());
//            JsonNode result = jsonObj.get("data").get("result").get(0);
//            ObjectNode nodes = jsonObj.ch
            // return result.get(0).asText();
//            return mapper.writeValueAsString(jsonObj);
            return jsonObj.toPrettyString();

        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * POST request
     * 
     * @param settingJson
     * @return
     */
    public String createDashboard(String settingJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bear " + apiKey);

        HttpEntity<String> request = new HttpEntity<>(settingJson, headers);
        ResponseEntity<String> response = restTemplate
            .postForEntity(prometheusUrl + "/dashboard/db", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) { return response.getBody(); }
        return null;
    }

}
