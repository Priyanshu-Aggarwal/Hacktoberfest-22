package Socket_Programming;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class client1 {
    BufferedReader br;
    PrintWriter out;
    Socket socket;
    public client1()
    {
        try {
            socket=new Socket("localhost",9999);
            System.out.println("connection done...");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
    
            startReading();
            startWriting();

        } 
        catch (IOException e) {
             e.printStackTrace();
        }

    }
    public void startWriting() {
        //thread 1
        Runnable r1=()->{
            System.out.println("writer started");
            while(true)
            {
                String content;
                try {
                    System.out.println("Enter your message");
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
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
        //thread 2
       
        Runnable r2=()->{
            System.out.println("Reader started");
            while(true){
                try {
                 
                    String msg=br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Server terminated the chat");
                        socket=null;
                        break;
                    }
                    System.out.println("Server : "+msg);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        
      new client1();
        
    }
}
