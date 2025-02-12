package ksd.sto.ndm.domain.service;

import ksd.sto.ndm.domain.dto.GrafanaCreateInDTO;
import ksd.sto.ndm.domain.dto.GrafanaCreateOutDTO;

public interface GrafanaService {
    public String sendQuery(String query, String url);

    public GrafanaCreateOutDTO createDashboard(GrafanaCreateInDTO inDTO);
}
