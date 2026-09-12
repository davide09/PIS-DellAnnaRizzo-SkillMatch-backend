
package com.skillmatch.matching_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().set("X-Internal-Key", "SKILLMATCH_INTERNAL_KEY");
            return execution.execute(request, body);
        };

        restTemplate.setInterceptors(List.of(interceptor));

        return restTemplate;
    }
}
