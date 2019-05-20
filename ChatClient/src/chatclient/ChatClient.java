package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        int serverPort = 1234;
        PrintStream out = System.out;
        InputStream in = System.in;
        Scanner r = new Scanner(in);
        
        Socket connection;
        InputStream s_in;
        PrintStream s_out;
        String line;
        try {
            connection = new Socket("localhost", serverPort);
            out.println("Connected to server!");
            s_in = connection.getInputStream();
            s_out = new PrintStream(connection.getOutputStream());
            while (r.hasNext()){
                line = r.nextLine();
                s_out.println(line);
                s_out.flush();
                if (line.equals("bye")) break;
            }
            
        } catch (IOException e) {
            System.out.println(
                    "Attempt to create connection failed with error: " + e);
            return;
        }

    }

}
