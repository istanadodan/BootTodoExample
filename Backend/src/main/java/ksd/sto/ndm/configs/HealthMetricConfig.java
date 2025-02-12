//package ksd.sto.ndm.configs;
//
//import java.util.Collections;
//import java.util.stream.Collectors;
//
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
//import org.springframework.boot.actuate.health.HealthContributorRegistry;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.boot.actuate.health.SimpleStatusAggregator;
//import org.springframework.boot.actuate.health.Status;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.micrometer.core.instrument.MeterRegistry;
//
////@Configuration
//public class HealthMetricConfig {
//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> healthRegistryCustomizer(HealthContributorRegistry healthRegistry) {
//        return registry -> registry.gauge("health", Collections.emptyList(), healthRegistry, health -> {
//            var status = aggregatedStatus(health);
//            if ("UP".equals(status.getCode())) {
//                return 1;
//            }
//            return 0;
//        });
//    }
//
//    private static Status aggregatedStatus(HealthContributorRegistry health) {
//        var healthList = health.stream()
//            .map(r -> ((HealthIndicator) r.getContributor()).getHealth(false).getStatus())
//            .collect(Collectors.toSet());
//        var statusAggregator = new SimpleStatusAggregator();
//        return statusAggregator.getAggregateStatus(healthList);
//    }
//}
