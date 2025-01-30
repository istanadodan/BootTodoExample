package ksd.sto.ndm.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PrometheusQueryController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String prometheusUrl = "http://localhost:9090/api/v1/query";

    @GetMapping("/query")
    public String queryPrometheus(@RequestParam("query") String qry)  {
        String url = prometheusUrl + "?query=" + qry;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj;
        try {
            jsonObj = mapper.readTree(response.getBody());
            JsonNode result = jsonObj.get("data").get("result");
            return result.get(0).asText();
//            return mapper.writeValueAsString(jsonObj.get("data").get("result"));
            
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
