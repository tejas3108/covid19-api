package com.tejas.covid19api.api.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@SpringBootApplication
@ComponentScan("com.tejas.covid19api")
@EnableSwagger2
class Covid19ApiApplication {

    static void main(String[] args) {
        SpringApplication.run(Covid19ApiApplication, args)

    }

    @Bean
    Docket covid19Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(MetaClass.class, MetaMethod.class)
                .groupName("COVID-19")
                .apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tejas.covid19api.api.web"))
                .paths(PathSelectors.any())
                .build()
    }

    private static ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "COVID-19 Summary Services API",
                "Set of services to get summary of confirmations, deaths and recoveries of cases related to COVID-19. The data is being obtained from the John Hopkins dataset publically available on GCP.",
                "1.0",
                " ",
                "Tejas Deshpande",
                " ",
                " ")
        return apiInfo
    }

}
