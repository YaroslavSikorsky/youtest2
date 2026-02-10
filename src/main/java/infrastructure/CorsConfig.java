package infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins}")
    private String frontend;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Для дебага
        System.out.println("CORS frontend: [" + frontend + "]");

        // Разрешаем фронт (обязательно без лишних пробелов/кавычек)
        config.setAllowedOrigins(Arrays.asList(frontend.trim()));

        // Методы
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

        // Заголовки
        config.setAllowedHeaders(Arrays.asList("*"));

        // Если нужны куки/сессии
        config.setAllowCredentials(true);

        // Expose для JWT или других нужных заголовков
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}