package br.com.salute.config.swagger;

import br.com.salute.config.property.AppProperty;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.nio.file.Files.exists;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class})
public class SwaggerConfig {

    private Model model;

    private final AppProperty appProperty;
    private final TypeResolver typeResolver;
    private final ApplicationEventPublisher publisher;

    public SwaggerConfig(AppProperty appProperty, TypeResolver typeResolver, ApplicationEventPublisher publisher) {
        this.appProperty = appProperty;
        this.typeResolver = typeResolver;
        this.publisher = publisher;
    }

    @Bean
    public Docket apiV1() {
        return criarDocket("v1");
    }

    @Bean
    public Docket apiV2() {
        return criarDocket("v2");
    }

    private Docket criarDocket(String apiVersion) {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(newHashSet(appProperty.getSeguranca().isEnableHttps() ? "https" : "http"))
                .forCodeGeneration(true)
                .groupName(apiVersion)
                .produces(newHashSet(APPLICATION_JSON_UTF8_VALUE))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/" + apiVersion + "/.*"))
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(true)
                .globalResponseMessage(RequestMethod.GET, globalResponses())
                .globalResponseMessage(RequestMethod.POST, globalResponses())
                .globalResponseMessage(RequestMethod.DELETE, globalResponses())
                .globalResponseMessage(RequestMethod.PUT, globalResponses())
                .globalResponseMessage(RequestMethod.PATCH, globalResponses());

        publisher.publishEvent(new DocketCriadoEvent(this, docket));

        return docket;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                //.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private ApiInfo apiInfo() {
        Organization organization = model.getOrganization();

        return new ApiInfoBuilder()
                .version(model.getVersion())
                .title(model.getName())
                .description(model.getDescription())
                .contact(new Contact(organization.getName(), organization.getUrl(), organization.getUrl()))
                .build();
    }

    private List<ResponseMessage> globalResponses() {
        return newArrayList(
                new ResponseMessageBuilder().code(500).message("Erro inesperado no servidor, não foi possível processar a solicitação").build()
                , new ResponseMessageBuilder().code(400).message("Dados inválidos fornecidos pelo cliente").build()
                , new ResponseMessageBuilder().code(401).message("Usuário não autenticado").build()
                , new ResponseMessageBuilder().code(403).message("Usuário sem permissão para esta operação").build()
//                , new ResponseMessageBuilder().code(404).message("Recurso não encontrado").responseModel(new ModelRef("Error")).build()
//                , new ResponseMessageBuilder().code(200).message("Successo").responseModel(new ModelRef("Error")).build()
        );
    }

    @PostConstruct
    public void init() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();

        String pomXml = "pom.xml";
        if (exists(Paths.get(pomXml))) {
            this.model = reader.read(new FileReader(pomXml));
            return;
        }

        this.model = reader.read(getClass().getClassLoader().getResourceAsStream(
                "META-INF/maven/" + appProperty.getSwagger().getPacoteProjeto() + "/" + appProperty.getSwagger().getNomeProjeto() + "/pom.xml"));
    }
}