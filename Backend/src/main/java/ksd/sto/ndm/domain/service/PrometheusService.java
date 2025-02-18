package ksd.sto.ndm.domain.service;

import java.io.IOException;

import com.google.gson.JsonObject;

public interface PrometheusService {
    public JsonObject simpleQuery(String query);
    public String rangeQuery(String query, long start, long end, int step) throws IOException;
}
