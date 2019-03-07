package com.ratella.ReactiveRecommendationServive.handler.router;

import com.ratella.ReactiveRecommendationServive.handler.RecommendationHandler;
import com.ratella.ReactiveRecommendationServive.model.Recommendation;
import com.ratella.ReactiveRecommendationServive.model.RecommendationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RecommendationRouter {
    @Bean
    RouterFunction<ServerResponse> allRecommendations() {
        return route(GET("/recommendations"),
                req -> ok().body(new RecommendationRepository().getRecommendations(), Recommendation.class));
    }
/*    @Bean
    public RouterFunction<ServerResponse> route(RecommendationHandler rHandler) {

        return RouterFunctions
                .route(GET("/all").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), rHandler::getAllRecommendations);
    }*/

}
