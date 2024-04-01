package com.zettamine.gateway;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator zettaBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
	 return routeLocatorBuilder.routes()
				.route(p -> p
					     .path("/zettabank/accounts/**")
					     .filters( f -> f.rewritePath("/zettabank/accounts/(?<segment>.*)","/${segment}")
					     .addResponseHeader("Accounts-Response-Time", LocalDateTime.now().toString()))
					.uri("lb://ACCOUNTS"))
				.route(p -> p
					     .path("/zettabank/loans/**")
					     .filters( f -> f.rewritePath("/zettabank/loans/(?<segment>.*)","/${segment}")
					     .addResponseHeader("Loan permission", "loan granted"))
					     
					.uri("lb://LOANS")) 

				.route(p -> p
					     .path("/zettabank/cards/**")
					     .filters( f -> f.rewritePath("/zettabank/cards/(?<segment>.*)","/${segment}")
					     .addResponseHeader("cards-response-time", LocalDateTime.now().toString()))
					.uri("lb://CARDS")) 

		.build();			
	}

}
