/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hust.set.carobyfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author binhm
 */
public class ConnectThread extends Thread {

    public final int PORT = 1234;

    private String hostName = "localhost";         // Name of the server computer to connect to.

    CONNECT_STATUS status;

    public CONNECT_STATUS getStatus() {
        return status;
    }

    private Socket connection;          // A socket for communicating with server.
    private BufferedReader incoming;    // For reading data from the connection.
    private OutputStreamWriter outgoing;// For writing date to connection  

    @Override
    public void run() {

        status = CONNECT_STATUS.OK;

        try {
            connection = new Socket(hostName, PORT);
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            outgoing = new OutputStreamWriter(connection.getOutputStream());
            outgoing.write("Hello");
            outgoing.flush();
            //outgoing.close();
            String lineFromServer = incoming.readLine();
            if (lineFromServer == null) {
                // A null from incoming.readLine() indicates that
                // end-of-stream was encountered.
                throw new IOException("Connection was opened, "
                        + "but server did not send any data.");
            }
            System.out.println();
            System.out.println(lineFromServer);
            System.out.println();
            incoming.close();
        } catch (IOException e) {
            status = CONNECT_STATUS.FAILED;
            System.out.println("Error:  " + e);
        }
    }
}
