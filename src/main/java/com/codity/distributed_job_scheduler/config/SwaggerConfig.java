package com.codity.distributed_job_scheduler.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI distributedJobSchedulerOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Distributed Job Scheduler API")

                        .description("""
                                REST API for a Distributed Job Scheduler System.

                                Features:
                                - Authentication
                                - Organizations
                                - Projects
                                - Queues
                                - Jobs
                                - Workers
                                - Retry Engine
                                - Monitoring
                                """)

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Mohammad Hussain Shaik")
                                .email("your-email@example.com"))

                        .license(new License()
                                .name("MIT License")))

                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }

}