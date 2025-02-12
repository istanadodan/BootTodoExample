package ksd.sto.ndm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrafanaCreateInDTO {
    private String UpdatedAt;
    private Object dashboard;
    private int folderId;
    private String folderUid;
    private boolean isFolder;
    private String message;
    private boolean overwrite;
    private int userId;
}