package ksd.sto.ndm.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ksd.sto.ndm.domain.dto.MailSendAddrDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService service;

    @Async
    public Future<String> performTask1() throws InterruptedException {
        Thread.sleep(2000); // 2초 대기 (예: 오래 걸리는 작업)
        return CompletableFuture.completedFuture("Task 1 Completed");
    }

    @Async
    public Future<String> performTask2() throws InterruptedException {
        Thread.sleep(3000); // 3초 대기 (예: 오래 걸리는 작업)
        return CompletableFuture.completedFuture("Task 2 Completed");
    }

    @Async
    public CompletableFuture<String> processTask(MailSendAddrDTO task) throws InterruptedException {

        MailSendAddrDTO dto = task;
        // String result = service.sendEmail(dto.getEmail(), dto.getTitle(),
        // dto.getContent());

        return CompletableFuture
            .completedFuture(service.sendEmail(dto.getEmail(), dto.getTitle(), dto.getContent()));
    }

    @Async
    public CompletableFuture<Object> processTask2(String task) throws InterruptedException {

        Thread.sleep(1000); // 2초 대기 (예: 오래 걸리는 작업)
        List<String> lst = new ArrayList<>();
        lst.add(task);
        return CompletableFuture.completedFuture(lst);

    }
}