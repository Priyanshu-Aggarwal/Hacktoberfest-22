package Socket_Programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class server {

    public ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    ArrayList<Socket> al = new ArrayList<>();

    public server() {
        try {
            server = new ServerSocket(9999);
            System.out.println("server is ready to accept connectiion... waiting!!");
            
            socket = server.accept();
            al.add(socket);
            
            // for reading data from socket ki inputstream se we use
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // create two threads for achieving writing and reading simultioneously
    public void startWriting() {
        // thread 1
        Runnable r1 = () -> {
            System.out.println("writer started");
            while (true) {
                String content;
                try {
                    System.out.println("Enter your message");
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    content = br1.readLine();

                    out.println(content);
                    out.flush();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        };
        new Thread(r1).start();
    }

    public void startReading() {
        // thread 2

        Runnable r2 = () -> {
            System.out.println("Reader started");
            while (true) {
                try {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client 1 terminated the chat");
                        break;
                    }
                    System.out.println("Client 1 : " + msg);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {

      
        new server();
    }
}
