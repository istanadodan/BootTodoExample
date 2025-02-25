package ksd.sto.ndm.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;

import ksd.sto.ndm.cmns.exceptions.BizException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class HttpRequestTemplate {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson mapper = new Gson();

    @Value("${monitor.apiKey}")
    private String apiKey;

    @SuppressWarnings("unchecked")
    public <R> R get(String url, Class<R> targetClass) throws BizException {

        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer " + apiKey)
            .url(url)
            .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() == false) { return (R) handleError(response.code()); }

            return handleSuccess(response.body().string(), targetClass);
        } catch (IOException e) {
            return (R) handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <R> R post(String url, Object data, Class<R> targetClass)
            throws BizException, IOException {
        // json
        String jsonString = mapper.toJson(data);
        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json"));

        Request request = new Request.Builder()
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer " + apiKey)
            .url(url)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return handleSuccess(response.body().string(), targetClass);
            } else {
                return (R) handleError(response.code());
            }
        }
    }

    protected abstract <R> R handleSuccess(String responseBody, Class<?> targetClass);

    protected Object handleError(int statusCode) throws BizException {
        if (statusCode == 404) { return null; }
        System.out.println("Error: " + statusCode);
        throw new BizException(null);
    }
    protected Object handleException(Exception e) throws BizException {
        e.printStackTrace();
        throw new BizException(null);
    }
}
