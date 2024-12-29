package ksd.sto.ndm.cmns;

import lombok.Builder;
import lombok.Getter;  // 추가
import lombok.AllArgsConstructor;  // 추가
import lombok.NoArgsConstructor;   // 추가

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private Error error;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private String type;
        private String code;
        private String message;
    }
}