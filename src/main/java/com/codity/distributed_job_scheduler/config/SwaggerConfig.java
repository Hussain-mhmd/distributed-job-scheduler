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
    public OpenAPI distributedJobSchedulerAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Distributed Job Scheduler API")

                        .description(
                                "REST API documentation for the Distributed Job Scheduler application."
                        )

                        .version("1.0")

                        .contact(new Contact()

                                .name("Shaik Mohammad Hussain")
                                .email("ms1937@srmist.edu.in")
                        )

                        .license(new License()

                                .name("MIT License")
                        ))

                .externalDocs(new ExternalDocumentation()

                        .description("Project Repository")
                        .url("https://github.com/Hussain-mhmd/distributed-job-scheduler"));
    }

}