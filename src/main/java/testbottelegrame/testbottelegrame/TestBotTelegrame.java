package testbottelegrame.testbottelegrame;

import org.springframework.ui.Model;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestBotTelegrame extends TelegramLongPollingBot {
    Map<String, Boolean> listUsers = new HashMap<>();
    Map<String, Date> activeUser = new HashMap<>();

    TestBotTelegrame() {
        listUsers.put("TOKEN12132323123", false);
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            //Отримє інформацію про повідомлення
            Message inMessage = update.getMessage();
            //Створюємо
            SendMessage outMessage = new SendMessage();
            long chatId = inMessage.getChatId();
            outMessage.setChatId(chatId);
            //Перевірка повідомлення
            String messageCommand = "Список команд:\n" +
                    "/start {idToken} - регістрація юзера \n" +
                    "/Hello - привітатися з користувачем \n" +
                    "/time - отримати сьогнішню дату та час";
//            outMessage.setText(messageCommand);
//            execute(outMessage);

            if (update.hasMessage() && update.getMessage().hasText()) {
                String userID = "" + inMessage.getFrom().getId();
                String command = inMessage.getText().split(" ", 2)[0];

                switch (command) {
                    case "/time": {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        outMessage.setText(dtf.format(now));
                        execute(outMessage);
                        break;
                    }
                    case "/Hello": {
                        //Перевіряєм чи має юзер доступ
                        if (activeUser.get(userID) != null) {
                            outMessage.setText("Hi. My name is TestBotTelegram");
                        } else {
                            outMessage.setText("Нажаль у вас немає підписки на цього бота. Придбрати підписку можна тут site.ua/"+getBotUsername());
                        }
                        execute(outMessage);

                        break;
                    }
                    case "/start": {
//                        Перевіряєм чи передав юзер параметри
                        if (inMessage.getText().trim().length()<7){
                            outMessage.setText("Команда /start повина мати парметри !");
                            execute(outMessage);
                            return;
                        }
                        String param = inMessage.getText().split(" ", 3)[1];
                        Boolean user = listUsers.get(param);
                        //Перевірка чи купив юзер підписку і чи внесаний вже userId в базу
                        if (user != null && user.equals(false)) {
                            //Вносим UserId в базу активних юзерів(тобто юзерів які купили підписку)
                            activeUser.put(userID, new Date());
                            listUsers.put(param, true);
                            outMessage.setText("Ви отримали підписку. Чим я можу вам допомогти ?");
                        } else {
                            outMessage.setText("Нажаль у вас немає підписки на цього бота. Придбрати підписку можна тут site.ua/botName");
                        }
                        execute(outMessage);
                        break;
                    }

                    default: {
                        outMessage.setText(messageCommand);
                    execute(outMessage);
                        break;
                    }

                }
            }
            System.out.println(update);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public String getBotUsername() {
        return "TestTelegrameBot";
    }

    @Override
    public String getBotToken() {
        return "1345643472:AAE7ZVs87X0W3aPp7wpk9QM0hw1mQTl4d8Y";
    }
}
