package br.com.salute.config.cors;

/*
package br.com.salute.config.cors;

import br.com.salute.config.property.AppProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    private final AppProperty appProperty;

    @Autowired
    public CorsConfig(AppProperty appProperty) {
        this.appProperty = appProperty;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(appProperty.getOriginPermitida());
        corsConfiguration.addAllowedMethod("POST, GET, DELETE, PUT,	OPTIONS, HEAD");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Accept",
                "Content-Type",
                "Authorization",
                "X-REQUESTED-WITH")
        );
        corsConfiguration.setExposedHeaders(Arrays.asList(

        ));
        return corsConfiguration;
    }
}
*/