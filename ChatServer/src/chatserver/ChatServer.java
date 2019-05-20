package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        int port = 1234;
        PrintStream out = System.out;
        //InputStream s_in;
        BufferedReader s_in;
        OutputStream s_out;
        String line;
        try {
            ServerSocket server = new ServerSocket(port);
            out.println("Server is listening...");
            while (true) {
                Socket connection = server.accept();
                out.println(connection.getInetAddress() + " connected!");
                s_in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                s_out = connection.getOutputStream();
                boolean finished = false;
                while (!finished) {
                    if (s_in.ready()) {
                        line = s_in.readLine();
                        if (line.equals("bye")) {
                            finished=true;
                            //line ="bye!";
                        }
                        out.println(line);
                    }

                }

            }
        } catch (IOException e) {
            System.out.println("Server shut down with error: " + e);
        }
        // TODO code application logic here
    }

}
