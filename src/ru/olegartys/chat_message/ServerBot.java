package ru.olegartys.chat_message;

/**
 * Created by olegartys on 29.01.15.
 */
import ru.olegartys.chat_message.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerBot extends ServerUser implements Serializable {

    private final String BOT_NAME = "BOT";

    public ServerBot () {
        this.login = BOT_NAME;
    }

    public void sendMessage (ArrayList<ServerUser> onlineUsers, String msg)  {
        Message msg1 = new Message(this, msg);
        ServerUser usr = null;
        for (ServerUser usrIter : onlineUsers) {
            try {
                usrIter.getUserOutputStream().writeObject(msg1);
                usr = usrIter;
            } catch (IOException e) {
                System.err.println("[BOT]: error sending message to user " + usr.getLogin());
                e.printStackTrace();
            }
        }
        //Server.getChatHistory().addMessage(msg1);
    }

    public void sendMessage (ServerUser usr, String msg) {
        Message msg1 = new Message (this, msg);
        try {
            usr.getUserOutputStream().writeObject(msg1);
            //Server.getChatHistory().addMessage(msg1);
        } catch (IOException e) {
            System.err.println("[BOT]: error sending message to user " + usr.getLogin());
            e.printStackTrace();
        }
    }

    public String getBotName () {
        return BOT_NAME;
    }

}
