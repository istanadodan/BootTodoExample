package ksd.sto.ndm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSendAddrDTO {
    
    private String email;
    private String title;
    private String content;
}
