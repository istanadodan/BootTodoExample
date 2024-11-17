package kr.or.ksd.sto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

//@Data
public class LoginReqDto {
    @Schema(description = "사용자 ID")
    private String userid;

    @Schema(description = "비밀번호")
    private String password;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}