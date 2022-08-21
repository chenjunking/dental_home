package com.dental.home.app.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config{

    private String domainUrl = "http://127.0.0.1:";
    @Value("${server.port}")
    private String port;
    @Value("${server.servlet.context-path}")
    private String contentPath;

    /****************************************************pc端接口*****************************************************************/
    /**
     * pc端接口
     * @return
     */
    private ApiInfo manageAppInfo() {
        return new ApiInfoBuilder()
                .title("pc端接口")
                .description("pc端接口")
                .version("1.0")
                .termsOfServiceUrl(domainUrl.concat(port).concat(contentPath).concat("/pc/manage"))
                .build();
    }
    @Bean
    public Docket createManageAppInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(manageAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dental.home.app.controller.pc.manage"))
                .paths(PathSelectors.any())
                .build()
                .groupName("pc端接口");
    }


    /****************************************************移动端公共接口*****************************************************************/
    /**
     * 移动端公共接口
     * @return
     */
    private ApiInfo commonAppInfo() {
        return new ApiInfoBuilder()
                .title("移动端公共接口")
                .description("移动端公共接口")
                .version("1.0")
                .termsOfServiceUrl(domainUrl.concat(port).concat(contentPath).concat("/phone/"))
                .build();
    }
    @Bean
    public Docket createCommonAppInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(commonAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dental.home.app.controller.phone.common"))
                .paths(PathSelectors.any())
                .build()
                .groupName("移动端公共接口");
    }

}
