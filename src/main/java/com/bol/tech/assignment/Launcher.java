package com.bol.tech.assignment;

import com.bol.tech.assignment.core.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class Launcher {

    @Bean
    Game createGame(@Value("${game.player1.id}") String player1Id,
                    @Value("${game.player2.id}") String player2Id) {
        return new Game(player1Id, player2Id);
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
