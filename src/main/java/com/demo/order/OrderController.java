package com.demo.order;

import brave.sampler.Sampler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    WebClient webClient;

    @GetMapping(value = "/v1/orders/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getCustomerOrder(@PathVariable String id){
        log.info("OrderController");

        //Mono<String> customerOrder = Mono.just("Broadband");

        Mono<String> billq = webClient.get().uri("/v1/billq/{id}",id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .map(bill -> " - Broadband -" + bill)
                .log("Customer billq: ");



        return billq;

    }


    @Bean
    WebClient webClient() {
        return WebClient.create("http://localhost:8083");
    }

}
