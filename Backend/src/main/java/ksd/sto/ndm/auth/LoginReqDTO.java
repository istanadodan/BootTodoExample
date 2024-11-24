package ksd.sto.ndm.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginReqDTO {
    @Schema(description = "사용자 ID")
    private String userId;

    @Schema(description = "비밀번호")
    private String password;
}