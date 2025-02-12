package ksd.sto.ndm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrafanaCreateOutDTO {

    private String folderUid;
    private int id;
    private String slug;
    private String status;
    private String uid;
    private String url;
    private int version;
}
