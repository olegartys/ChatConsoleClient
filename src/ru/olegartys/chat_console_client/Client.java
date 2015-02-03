package ru.olegartys.chat_console_client;

import ru.olegartys.chat_message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class Listener extends Thread {

    private ObjectInputStream is;

    public Listener (ObjectInputStream is) {
        this.is = is;
        this.start();
    }

    @Override
    public void run () {
        while (true) {
            try {

                //System.out.println("HELLO");

                Message msg1 = (Message) is.readObject();

                if (msg1 != null)
                    System.out.println ("[" + msg1.getLogin() + "]: " + msg1.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Client {

    private static ServerUser usr;
    private static Socket sock;
    private static ObjectOutputStream os;
    private static ObjectInputStream is;
    private static BufferedReader br;

    public static void main(String[] args) {
        try {
            //opens stdin for a read
            br = new BufferedReader(new InputStreamReader(System.in));

            //creating user object
            System.out.print ("Enter your login: ");
            String login;
            login = br.readLine();
            usr = new ServerUser(login);
            Message helloMsg = new Message(usr, ServerConfig.USER_CONNECT_MESSAGE);

            //creating client socket
            sock = new Socket(InetAddress.getByName("localhost"), 1488);

            //open sttreams for speaking with server
            os = new ObjectOutputStream(sock.getOutputStream());
            is = new ObjectInputStream(sock.getInputStream());
            os.writeObject (helloMsg);
            //os.flush();

            //getting hello message from server
            try {
                Message srvHelloMessage = (Message)is.readObject();
                System.out.println ("[HELLO_MSG]: " + srvHelloMessage.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Listener msgListener = new Listener (is);

            //start chat!!!
            while (true) {
                String input = br.readLine();
                Message msg = new Message(usr, input);
                os.writeObject(msg);
             }
            //sock.close();
        } catch (IOException e) {
            System.out.println ("HEY!!");
            e.printStackTrace();
        }
    }

}
