package kr.or.ksd.sto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginReqDto {
    @Schema(description = "사용자 ID")
    private String userid;

    @Schema(description = "비밀번호")
    private String password;
}