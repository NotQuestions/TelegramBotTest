package testbottelegrame.testbottelegrame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class TestbottelegrameApplication {

    public static void main(String[] args) throws TelegramApiRequestException {
        SpringApplication.run(TestbottelegrameApplication.class, args);
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new TestBotTelegrame());


    }

}
