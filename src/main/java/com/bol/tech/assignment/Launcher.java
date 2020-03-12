package com.bol.tech.assignment;

import com.bol.tech.assignment.core.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {

    @Bean
    Game createGame(@Value("${game.player1.id}") String player1Id,
                    @Value("${game.player2.id}") String player2Id) {
        return new Game(player1Id, player2Id);
    }

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
