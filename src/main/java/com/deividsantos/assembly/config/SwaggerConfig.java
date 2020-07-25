package com.deividsantos.assembly.config;

import com.deividsantos.assembly.exception.Error;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {
        List<ResponseMessage> defaultResponses = Arrays.asList(buildResponseMessage(HttpStatus.BAD_REQUEST),
                buildResponseMessage(HttpStatus.FORBIDDEN));
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .additionalModels(typeResolver.resolve(Error.class))
                .globalResponseMessage(RequestMethod.POST, defaultResponses)
                .globalResponseMessage(RequestMethod.DELETE, defaultResponses)
                .globalResponseMessage(RequestMethod.GET, defaultResponses)
                .globalResponseMessage(RequestMethod.PUT, defaultResponses)
                .globalResponseMessage(RequestMethod.PATCH, defaultResponses)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deividsantos.assembly"))
                .paths(PathSelectors.any())
                .build();
    }

    private ResponseMessage buildResponseMessage(HttpStatus httpStatus) {
        return new ResponseMessageBuilder()
                .code(httpStatus.value())
                .responseModel(new ModelRef(Error.class.getSimpleName()))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> routingFunction() {
        return route(GET(""), req -> ServerResponse.temporaryRedirect(URI.create("swagger-ui.html")).build());
    }

}
