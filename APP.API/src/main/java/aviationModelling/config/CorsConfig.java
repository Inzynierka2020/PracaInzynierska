package aviationModelling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "https://localhost", "http://grcho.ds.pg.gda.pl:50001", "https://walltedo.com", "https://www.walltedo.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
