package ksd.sto.ndm.infs.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserVO {
	private String userId;
	private String username;
	private String password;
	private String roles;
	private Date regDate;
	private Date modDate;
}
