package ksd.sto.ndm.cmns;

import lombok.AllArgsConstructor;  // 추가
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;   // 추가

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private Error error;

    @Data    
    public static class Error {
        private String type;
        private String code;
        private String message;
    }
}