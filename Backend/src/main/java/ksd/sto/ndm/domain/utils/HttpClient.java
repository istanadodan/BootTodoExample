package ksd.sto.ndm.domain.utils;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ksd.sto.ndm.cmns.exceptions.BizException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class HttpClient {

    private final OkHttpClient httpClient;
    private String apiKey;

    public HttpClient(@Value("${monitoring.apiKey}") String apiKey) {
        this.httpClient = new OkHttpClient();
        this.apiKey = apiKey;
    }

    public <R> R request(String url, Class<R> klazz) throws IOException, BizException {
        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer " + apiKey)
            .url(url)
            .get()
            .build();

        String jsonString = null;

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) { throw new BizException(url); }

            String responseBody = response.body().string();
            JsonElement jsonElement = JsonParser.parseString(responseBody);
            if (jsonElement.isJsonObject()) {
                jsonString = jsonElement.getAsString();

            } else if (jsonElement.isJsonArray()) {
                jsonString = jsonElement.getAsJsonArray().get(0).getAsString();
            }

        } 

        return DTOMapperUtils.json2ObjMapper(jsonString, klazz);
    }
}
