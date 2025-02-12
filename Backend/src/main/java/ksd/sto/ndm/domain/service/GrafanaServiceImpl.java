package ksd.sto.ndm.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ksd.sto.ndm.domain.dto.GrafanaCreateInDTO;
import ksd.sto.ndm.domain.dto.GrafanaCreateOutDTO;

@Service
public class GrafanaServiceImpl implements GrafanaService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    
    @Value("${monitoring.grafanaUrl}")
    private String grafanaUrl;
    @Value("${monitoring.apiKey}")
    private String apiKey;

    public GrafanaServiceImpl() {
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
        String url = this.grafanaUrl + endpoint + query;
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
//    public String createDashboard(String settingJson) {        
//
//        HttpEntity<String> request = new HttpEntity<>(settingJson, headers);
//        ResponseEntity<String> response = restTemplate
//            .postForEntity(grafanaUrl + "/dashboard/db", request, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) { return response.getBody(); }
//        return null;
//    }

    /**
     * 대시보드 생성
     */
    @Override
    public GrafanaCreateOutDTO createDashboard(GrafanaCreateInDTO inDTO) {
        ObjectMapper mapper = new ObjectMapper();
        HttpEntity<GrafanaCreateInDTO> request = new HttpEntity<>(inDTO, headers);
        
        ResponseEntity<String> response = restTemplate
            .postForEntity(grafanaUrl + "/dashboards/db", request, String.class);

           
        String a =response.getBody();
        try {
            return mapper.readValue(a, GrafanaCreateOutDTO.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
