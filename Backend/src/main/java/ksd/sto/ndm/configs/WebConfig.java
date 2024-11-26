package ksd.sto.ndm.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
//				.allowedOriginPatterns("http://localhost:[*]", "http://backend:[*]", "http://10.111.36.17:[*]")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(false);
	}

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/api-docs/swagger-ui.html").setViewName("redirect:/swagger-ui/index.html");
//	}
}