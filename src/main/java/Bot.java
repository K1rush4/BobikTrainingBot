import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());


        sendMessage.setText(text);
        try {

            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public static String read(String filename) {
        String inputFileName = "src/main/resources/" + filename;
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText().toLowerCase()) {
                case "start":
                case "/start":
                    sendMsg(message, read("start.txt"));
                    break;
                case "help":
                case "/help":
                    sendMsg(message, read("help.txt"));
                    break;
                case "составить резюме":
                case "/resume":
                    sendMsg(message, read("ResumeTutorial.txt"));
                    break;
                case "залить свой проект на github":
                case "/github":
                    sendMsg(message, read("GitHubTutorial.txt"));
                    break;
                case "связаться с разработчиком":
                case "/contact":
                    sendMsg(message, "Мой телеграмм: https://t.me/K1rush444");
                    break;
                default:
                    sendMsg(message, "Неверная команда, введите /help для справки");
                    break;
            }
        }

    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirtRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Start"));
        keyboardFirstRow.add(new KeyboardButton("Help"));
        keyboardSecondRow.add(new KeyboardButton("Составить резюме"));
        keyboardSecondRow.add(new KeyboardButton("Залить свой проект на github"));
        keyboardThirtRow.add(new KeyboardButton("Связаться с разработчиком"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardThirtRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public String getBotUsername() {
        return "BobikTrainingBot";
    }

    public String getBotToken() {
        return "5747617059:AAEHRv-2B7RQ4IlK0Uk7k1-iCtviRAZQji4";
    }
}
