package ksd.sto.ndm.domain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ksd.sto.ndm.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mailing")
@RequiredArgsConstructor
public class MailSendController {

    private final EmailService emailService;


    @PostMapping("/send")
    public String sendMail(@RequestBody String content) {
        emailService.sendEmail("c4now@naver.com", "test mail sending", "hello!! How are you?");
        return "OK";
    }

}
