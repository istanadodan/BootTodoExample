package ksd.sto.ndm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.JsonObject;

import ksd.sto.ndm.domain.service.PrometheusService;

@SpringBootTest
class PromethuesTest {

    @Autowired
    private PrometheusService service;
    
    @Test
    void test() {
        String metricName = "process_resident_memory_bytes"; // 가져올 메트릭 이름
        String query = metricName + "{instance=\"localhost:9090\", job=\"prometheus\"}"; // PromQL 쿼리
        
        JsonObject result = service.simpleQuery(query);
        System.out.println(result);
    }

}
