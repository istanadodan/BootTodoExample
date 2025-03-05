package ksd.sto.ndm.domain.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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
import ksd.sto.ndm.domain.utils.BizUtils;
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
    
    @GetMapping("/jwt")
    public String getJwt(@RequestParam("args") String args) {
        String privateKeyPath = "src/main/resources/files/id_rsa";
        String issuer = "java-account";
        String subject = "test@ksd.com";
        int expirationMinutes = 5;
        String jwt = null;
        try {
            jwt = BizUtils.generateJwt(privateKeyPath, issuer, subject, expirationMinutes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Generated JWT: " + jwt);
        return jwt;
    }

    @PostMapping("/dashboards/create")
    @Operation(summary = "대시보드생성", description = "")
    public GrafanaCreateOutDTO createDashboard() {
        Map<Object, Object> dashboardJson = readJsonToMap(getResourceFilePath("files/1860_rev37.json"));
        
        @SuppressWarnings("unchecked")
        List<Map<Object, Object>> replLst = (List<Map<Object, Object>>) dashboardJson.get("__inputs");
        // 파일업로드 처리용 불요키 삭제 : __로 시작되는 hidden키
        Iterator<Entry<Object, Object>> iterator = dashboardJson.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<Object, Object> next = iterator.next();
            if(next.getKey().toString().startsWith("__") == true) {
                iterator.remove();
            }
        }
        
        for(Map<Object, Object> element : replLst) {
            Object target = element.get("name");
            Object replace = element.get("pluginId");
              
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

    @SuppressWarnings("unchecked")
    private void replaceValue(Map<Object, Object> map, Object target, Object replacement) {
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if(Objects.isNull(value)) {
                return;
            }

            if (value instanceof Map) {
                // 값이 Map인 경우 재귀적으로 탐색
                Map<Object, Object> nestedMap = (Map<Object, Object>) value;
                replaceValue(nestedMap, target, replacement);
            } if (value instanceof List) {
                for(Map<Object, Object> subMap : (List<Map<Object, Object>>)value) {
                    replaceValue(subMap, target, replacement);
                }
                
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
    private Map<Object, Object> readJsonToMap(String filePath) {
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
