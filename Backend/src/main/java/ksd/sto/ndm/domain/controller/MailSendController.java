package ksd.sto.ndm.domain.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ksd.sto.ndm.domain.dto.MailSendAddrDTO;
import ksd.sto.ndm.domain.service.AsyncService;
import ksd.sto.ndm.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mailing")
@RequiredArgsConstructor
public class MailSendController<R> {

    private final AsyncService asyncService;

    @PostMapping("/send")
    public String sendMail(@RequestBody String content) {

        List<MailSendAddrDTO> dtoLst = List
            .of(MailSendAddrDTO
                .builder()
                .title("title1")
                .email("c4now@naver.com")
                .content("content1")
                .build(),
                    MailSendAddrDTO
                        .builder()
                        .title("title2")
                        .email("c4now@naver.com")
                        .content("content2")
                        .build());

        List<CompletableFuture<String>> futures = dtoLst.stream().map(task -> {
            try {
                return asyncService.processTask(task);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }).toList();
        
        futures.forEach(future -> {
            try {
                log.info("mail reply message: {}", future.get());
            } catch (Exception e) {
                // TODO: handle exception
            }
        });

        return "All tasks completed";
    }

}
