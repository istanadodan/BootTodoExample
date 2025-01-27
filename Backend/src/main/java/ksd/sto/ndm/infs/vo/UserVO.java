package ksd.sto.ndm.infs.vo;

import java.sql.Date;

import ksd.sto.ndm.domain.dto.UserDTO;
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

    public UserDTO toDTO() {
        return UserDTO
            .builder()
            .userId(userId)
            .username(username)
            .password(password)
            .roles(roles)
            .build();
    }
}
