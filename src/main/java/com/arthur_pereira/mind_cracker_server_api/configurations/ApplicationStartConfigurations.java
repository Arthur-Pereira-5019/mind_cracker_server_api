package com.arthur_pereira.mind_cracker_server_api.configurations;

import com.arthur_pereira.mind_cracker_server_api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationStartConfigurations {
    @Autowired
    private GameService gameService;

    @Bean
    public CommandLineRunner runOnStartup() {
        return args -> {
            gameService.shutdownEveryGame();
        };
    }
}
