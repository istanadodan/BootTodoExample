package ksd.sto.ndm.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ksd.sto.ndm.domain.dto.GrafanaCreateInDTO;
import ksd.sto.ndm.domain.dto.GrafanaCreateOutDTO;
import ksd.sto.ndm.domain.service.GrafanaService;
import ksd.sto.ndm.domain.service.PrometheusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/prometheus/api")
public class PrometheusApiController {

    private final PrometheusService prometheusService;
    
    @GetMapping("/query")
    public String queryPrometheus(@RequestParam("query") String qry)  {        
        return prometheusService.simpleQuery(qry).toString();
    }   

}
