package br.com.salute.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class AppProperty {

    private String originPermitida = "http://localhost:8080";
    private Seguranca seguranca = new Seguranca();
    private Swagger swagger = new Swagger();

    @Data
    public static class Seguranca {
        private boolean enableHttps;
    }

    @Data
    public static class Swagger {
        private String pacoteProjeto;
        private String nomeProjeto;
    }
}
