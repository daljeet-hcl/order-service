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

import com.demo.DemoProperties;
import com.demo.HeaderExchange;

@RestController
@Slf4j
public class OrderController {

	DemoProperties dp = new DemoProperties();
	HeaderExchange headerExchange = new HeaderExchange(dp);

    @Autowired
    WebClient webClient;

    @GetMapping(value = "/orders/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getCustomerOrder(@PathVariable String id){
        log.info("OrderController");

        //Mono<String> customerOrder = Mono.just("Broadband");
		String plan = System.getenv("plan-name");
	    if(plan == null || plan.isEmpty())
		plan = "Broadband";
		
		final String finalPlan = plan;

        Mono<String> billq = Mono.just(finalPlan +"49 USD");
		
		/*webClient.get().uri("/billq/{id}",id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .map(bill -> finalPlan + bill)
                .log("Customer billq: ");*/



        return billq;

    }

/*
    @Bean
    WebClient webClient() {
        return WebClient.create(System.getenv("billq-service"));
    }*/
	
	 @Bean
     WebClient webClient() {
		String upStreamUrl = System.getenv("billq-service");
	    if(upStreamUrl == null || upStreamUrl.isEmpty())
		upStreamUrl = "http://localhost:8083";
		
        return WebClient
                .builder()
                //.filter(headerExchange)
                .baseUrl(upStreamUrl)
                .build();
    }

}
