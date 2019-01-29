/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author UNGERW
 */
public class TelegramBot extends TelegramLongPollingBot {
    
//    Done! Congratulations on your new bot. You will find it at t.me/crtr_bot. You can now add a description, about section and profile picture for your bot, see /help for a list of commands. By the way, when you've finished creating your cool bot, ping our Bot Support if you want a better username for it. Just make sure the bot is fully operational before you do this.
//
//Use this token to access the HTTP API:
//674587410:AAFj94WXHkD_XTf8c0FBLYaNMZBS8PofYlQ
//Keep your token secure and store it safely, it can be used by anyone to control your bot.
//
//For a description of the Bot API, see this page: https://core.telegram.org/bots/api


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            System.out.println(update.getMessage().getChatId());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void sendMessage(String msg, Long chatId){
            SendMessage message = new SendMessage() 
                    .setChatId(chatId)
                    .setText(msg);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }
        public void sendMessage(String msg){
            Long chatId = 99L;
//            SendMessage message = new SendMessage() 
//                    .setChatId(chatId)
//                    .setText(msg);
                  SendMessage message = new SendMessage() 
                    .setText(msg);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String getBotUsername() {
        return "crtr_bot";
    }

    @Override
    public String getBotToken() {
        return "674587410:AAFj94WXHkD_XTf8c0FBLYaNMZBS8PofYlQ";
    }
}
