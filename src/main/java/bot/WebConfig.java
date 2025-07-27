package bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешаем доступ только с фронта на порту 8080
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // Указываем фронтовый сервер
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешаем методы
                .allowedHeaders("*");  // Разрешаем любые заголовки
    }
}