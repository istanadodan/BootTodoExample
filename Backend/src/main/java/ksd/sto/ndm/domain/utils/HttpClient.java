package ksd.sto.ndm.domain.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ksd.sto.ndm.cmns.exceptions.BizException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpClient {

    private final OkHttpClient httpClient;
    private String grafanaApiKey;
    private ObjectMapper mapper;

    public HttpClient(@Value("${monitoring.apiKey}") String apiKey) {
        this.httpClient = new OkHttpClient();
        this.grafanaApiKey = apiKey;
        this.mapper = new ObjectMapper();
    }

    /**
     * 
     * 그라파나 POST 요청
     */
    public <R> List<R> getRequest(String url, Class<R> outClass) throws IOException, BizException {
        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer " + grafanaApiKey)
            .url(url)
            .get()
            .build();

        return extractRequstBody(request, outClass);
    }

    /**
     * 그라파나 GET 요청
     */
    public <T, R> List<R> postRequest(String url, T inDTO, Class<R> outClass)
            throws IOException, BizException {

        String jsonBody = mapper.writeValueAsString(inDTO);
        // body생성
        MediaType JSON_TYPE = MediaType.get("application/json");
        RequestBody body = RequestBody.create(jsonBody, JSON_TYPE);

        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer " + grafanaApiKey)
            .url(url)
            .post(body)
            .build();

        return extractRequstBody(request, outClass);
    }

    /**
     * prometheus api 호출
     */
    public <T, R> List<R> getMetrics(String url, Class<R> outClass)
            throws IOException, BizException {
        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .url(url)
            .get()
            .build();

        return extractRequstBody(request, outClass);
    }

    /**
     * 공통처리 json body를 파싱해 dto목록을 출력
     * 
     * @param <R>
     * @param request
     * @param outClass
     * @return
     * @throws IOException
     * @throws BizException
     */
    private <R> List<R> extractRequstBody(Request request, Class<R> outClass)
            throws IOException, BizException {

        List<R> output = new ArrayList<>();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) { throw new BizException(""); }

            String jsonString = null;
            JsonElement jsonElement = JsonParser.parseString(response.body().string());

            if (jsonElement.isJsonObject()) {
                jsonString = jsonElement.getAsString();

                output.add(DTOMapperUtils.json2ObjMapper(jsonString, outClass));

            } else if (jsonElement.isJsonArray()) {
                for (JsonElement element : jsonElement.getAsJsonArray()) {
                    output.add(DTOMapperUtils.json2ObjMapper(element.getAsString(), outClass));
                }
            }

        }

        return output;
    }

}
