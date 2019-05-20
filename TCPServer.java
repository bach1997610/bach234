package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author NguyenTuanVu
 */
public class TCPServer {

    public ServerSocket serverSocket = null;
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    public String data;
    public boolean isConnect = false;

    public TCPServer() {

    }

    public void sentData(String str) {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Kiểm tra kết nối", "Thông báo", 1);
        }
    }

    public void readData() throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        data = dataInputStream.readUTF();
    }

    public void connect() {
        try {
            serverSocket = new ServerSocket(2019);
            socket = serverSocket.accept();
            serverSocket.close();
            isConnect = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Thất bại", "Thông báo", 1);
        }
    }

    public void close() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi", "Thông báo", 1);
        }
    }

}
