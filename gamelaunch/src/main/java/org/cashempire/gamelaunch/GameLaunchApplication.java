package org.cashempire.gamelaunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.cashempire.repository"})
public class GameLaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameLaunchApplication.class, args);
    }

}
