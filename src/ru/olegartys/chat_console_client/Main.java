package ru.olegartys.chat_console_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private static ServerUser usr;
    //private static Message msg;

    public static void main(String[] args) {
        try {
            Socket sock = new Socket(InetAddress.getByName("localhost"), 1488);

            usr = new ServerUser("olegartys");
            Message msg = new Message(usr, "Hello Server!");



            ObjectOutputStream is = new ObjectOutputStream(sock.getOutputStream());
            System.out.println (msg.getMessage());

        } catch (IOException e) {
            System.out.println ("HEY!!");
            e.printStackTrace();
        }

    }
}
