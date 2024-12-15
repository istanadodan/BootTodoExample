package ksd.sto.ndm.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    private String userId;
    private String username;
    private String password;
    private String roles;
    @JsonIgnore
    @Builder.Default
    private Date regDate = new Date();
    @JsonIgnore
    private Date modDate;
}