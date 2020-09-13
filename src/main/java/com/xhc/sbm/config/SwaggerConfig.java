package com.xhc.sbm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo createApiInfo(){
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("spring boot+mybatis练习工程接口")
                .description("spring boot+mybatis练习工程接口")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
        return apiInfo;
    }

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false) //token非必填
                .build();
        pars.add(ticketPar.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(createApiInfo());
        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.xhc.sbm.controller"))
                .paths(PathSelectors.any()).build().globalOperationParameters(pars);
        return docket;
    }

  /*  private Docket createDocket(String title, String description, String path, String groupName) {
        ApiInfo apiInfo = new ApiInfo(title, description, "V1", SERVICE_URL, AUTHOR_NAME, "", "");

        Set<String> setProtocol = new HashSet<String>();
        setProtocol.add("http");
        setProtocol.add("https");

        Set<String> setProduce = new HashSet<String>();
        setProduce.add("application/json");

        Set<String> setConsume = new HashSet<String>();
        // setConsume.add("x-www-form-urlencoded");

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.zhengtoon.project.web"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.regex(path))
                .build()
                .groupName(groupName).pathMapping("/").enable(true)
                .apiInfo(apiInfo).useDefaultResponseMessages(false).protocols(setProtocol).produces(setProduce)
                .consumes(setConsume);
    }
*/



}
