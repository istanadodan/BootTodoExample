package ksd.sto.ndm.domain.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ksd.sto.ndm.domain.service.AsyncService;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/run-tasks")
    public String runTasks() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // 병렬로 두 작업 실행
        Future<String> task1 = asyncService.performTask1();
        Future<String> task2 = asyncService.performTask2();

        // 두 작업이 완료될 때까지 대기
        String result1 = task1.get();
        String result2 = task2.get();

        long end = System.currentTimeMillis();

        return "Results: " + result1 + ", " + result2 + " | Time Taken: " + (end - start) + "ms";
    }
    

    @GetMapping("/parallel-tasks")
    public String executeParallelTasks() throws ExecutionException, InterruptedException {
        List<String> tasks = Arrays.asList("Task1", "Task2", "Task3", "Task4");

        // CompletableFuture를 사용하여 병렬 처리
        List<CompletableFuture<Object>> futures = tasks.stream()
                .map(task -> {
                    try {
                        return asyncService.processTask2(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toList();

        // 모든 작업이 완료될 때까지 기다림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 결과 출력
        futures.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return "All tasks completed!";
    }
}