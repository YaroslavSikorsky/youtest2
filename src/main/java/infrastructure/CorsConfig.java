//package infrastructure;
//
//import org.apache.catalina.filters.CorsFilter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
////@Configuration //временно убираю чтобы протесстить. в рейл вей этот вариант работал!!!
////public class CorsConfig implements WebMvcConfigurer {
////
////    @Value("${cors.allowed.origins}")
////    private String frontend;
////
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        System.out.println("CORS frontend: [" + frontend + "]");
////
////        registry.addMapping("/**")
////                .allowedOrigins(frontend.trim())
////                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
////                .allowedHeaders("*")
////                .allowCredentials(true);
////    }
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("🔓 CORS: Registering for https://youtest2.vercel.app");
//
//        registry.addMapping("/**")
//                .allowedOriginPatterns("https://youtest2.vercel.app") // ⬅️ Используем patterns!
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}

package infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter; // ← Spring'овский, не Tomcat!

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("https://youtest2.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}