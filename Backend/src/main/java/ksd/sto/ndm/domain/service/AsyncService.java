package ksd.sto.ndm.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

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
    public CompletableFuture<Object> processTask(String task) throws InterruptedException {
        Thread.sleep(1000); // 2초 대기 (예: 오래 걸리는 작업)
        List<String> lst = new ArrayList<>();
        lst.add(task);
        return CompletableFuture.completedFuture(lst);        
    }
}