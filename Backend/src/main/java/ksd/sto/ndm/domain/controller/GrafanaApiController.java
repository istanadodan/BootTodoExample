package ksd.sto.ndm.domain.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import ksd.sto.ndm.domain.dto.GrafanaCreateInDTO;
import ksd.sto.ndm.domain.dto.GrafanaCreateOutDTO;
import ksd.sto.ndm.domain.service.GrafanaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/grafana/api")
public class GrafanaApiController {

    private final GrafanaService grafanaService;

    @GetMapping("/grafana")
    public String queryGrafana(@RequestParam("endpoing") String endpoint,
            @RequestParam("query") String qry) {
        // String url = "?query=" + qry;
        return grafanaService.sendQuery(endpoint, qry);
    }

    @PostMapping("/dashboards/create")
    @Operation(summary = "대시보드생성", description = "")
    public GrafanaCreateOutDTO createDashboard() {
        Map<String, Object> dashboardJson = readJsonToMap(getResourceFilePath("files/1860_rev37.json"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = (List<Map<String, Object>>) dashboardJson.get("__inputs");
        dashboardJson.remove("__inputs");
        
        for(Map<String, Object> element : lst) {
              String target = (String) element.get("name");
              String replace = (String) element.get("pluginId");
              
              replaceValue(dashboardJson, target, replace);
        }
        
        // pass
        return grafanaService
            .createDashboard(GrafanaCreateInDTO
                .builder()
                .UpdatedAt("2025-02-11T13:01:36.564Z")
                .dashboard(dashboardJson)
                .folderId(3)
                .folderUid("cecfe86sd6lmoa")
                .isFolder(false)
                .message("message")
                .overwrite(true)
                .userId(1)
                .build());
    }

    private void replaceValue(Map<String, Object> map, Object target, Object replacement) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // 값이 Map인 경우 재귀적으로 탐색
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                replaceValue(nestedMap, target, replacement);
            } else if (value != null && value.equals(target)) {
                // 값이 target과 일치하는 경우 replacement로 치환
                map.put(key, replacement);
            }
        }
    }
    /**
     * JSON파일을 Map으로 일괄 변환
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> readJsonToMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 파일 절대경로 계산
     * 
     * @param resourcePath
     * @return
     */
    private String getResourceFilePath(String resourcePath) {
        Path path = Paths.get("src/main/resources", resourcePath);
        return path.toAbsolutePath().toString();
    }

}
