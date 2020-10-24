package aviationModelling;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class AviationModellingApplication {
	public static void main(String[] args) throws InterruptedException {
//		Thread.sleep(90000);
		SpringApplication.run(AviationModellingApplication.class, args);
	}

//
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer()
//	{
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedOrigins("http://localhost:4200");
//			}
//		};
//	}
}

