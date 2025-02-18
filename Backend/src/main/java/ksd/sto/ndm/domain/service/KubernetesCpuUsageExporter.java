//package ksd.sto.ndm.domain.service;
//
//import java.io.IOException;
//
//import io.prometheus.metrics.core.metrics.Gauge;
//
//
//public class KubernetesCpuUsageExporter {
//
//    public double getGaugeMetrics() throws IOException, InterruptedException {
//        // JVM 메트릭 등록
//        JvmMetrics.builder().register();
//
//        // Gauge 메트릭 생성
//        Gauge cpuUsageGauge = Gauge.builder()
//                .name("kubernetes_cpu_usage_percentage")
//                .help("CPU usage percentage in Kubernetes")
//                .register();
//
//        // HTTP 서버 시작 (메트릭 노출)
//        HTTPServer server = HTTPServer.builder()
//                .port(9400)
//                .buildAndStart();
//
//        // CPU 사용량을 주기적으로 업데이트
////        while (true) {
////            double cpuUsage = getCpuUsageFromKubernetes();
////            cpuUsageGauge.set(cpuUsage);
////            Thread.sleep(10000); // 10초마다 업데이트
////        }
////        
//        return getCpuUsageFromKubernetes();
//    }
//
//    private double getCpuUsageFromKubernetes() {
//        // Kubernetes API를 호출하여 CPU 사용량을 가져오는 로직
//        return Math.random() * 100; // 임의의 값 반환
//    }
//}