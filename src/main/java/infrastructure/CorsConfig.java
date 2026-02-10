package infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
//добавляем для спринга потому что опшен ломает корс
@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins}")
    private String frontend;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Для дебага можно временно:
        System.out.println("CORS frontend: " + frontend);

        // Разрешаем фронт
        config.setAllowedOrigins(List.of(frontend));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        // Обрабатываем preflight
        config.addExposedHeader("Authorization"); // если используешь JWT

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
