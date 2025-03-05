package ksd.sto.ndm.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
@Service
public class PrometheusServiceImpl implements PrometheusService {
    @Value("${monitoring.prometheusUrl}")
    private String prometheusUrl;
    @Value("${monitoring.apiKey}")
    private String apiKey;

    private final HttpHeaders headers;    
    private OkHttpClient httpClient;

    public PrometheusServiceImpl() {
        headers = new HttpHeaders();
        httpClient = new OkHttpClient();
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
    public JsonObject simpleQuery(String query) {
        String url = prometheusUrl + "/api/v1/query?query=" + query;

        Request request = new Request.Builder().url(url).get().build();
        
        try(Response response = httpClient.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                log.info("err");
                return null;
            }
            
            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            
            return jsonResponse.getAsJsonObject("data");
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode jsonObj;
//        try {
//            jsonObj = mapper.readTree(response.getBody());
//            // JsonNode result = jsonObj.get("data").get("result").get(0);
//            // ObjectNode nodes = jsonObj.ch
//            // return result.get(0).asText();
//            // return mapper.writeValueAsString(jsonObj);
//            return jsonObj.toPrettyString();
//
//        } catch (JsonProcessingException e) {
//            return "";
//        }
    }

    @Override
    public String rangeQuery(String query, long start, long end, int step) throws IOException {

        String url = prometheusUrl + "/api/v1/query_range?query=" + query + "&start=" + start
                + "&end=" + end + "&step=" + step;

        Request request = new Request.Builder().url(url).get().build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(
                        "Failed to fetch metric: " + response.code() + " " + response.message());
            }
            return response.body().string();
        }
    }

}
