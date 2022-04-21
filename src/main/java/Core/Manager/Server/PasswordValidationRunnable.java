package Core.Manager.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PasswordValidationRunnable implements Runnable {
    private static boolean result = false;

    @Override
    public void run() {
        while (true) {
            while (!result) {
                // check password and IP
                ServerSocket dataTransferServer = null;
                Socket dataTransferSocket = null;
                List<String> passwordAndIP = new ArrayList<>();

                try {
                    dataTransferServer = new ServerSocket(ManagedServer.getServers().get("dataTransferServer").getPort());
                } catch (IOException e) {
                    //TODO something
                    e.printStackTrace();
                }
                for (int i = 0; i < 2; i++) {
                    try {
                        dataTransferSocket = dataTransferServer.accept();
                    } catch (IOException e) {
                        //TODO something
                        e.printStackTrace();
                    }
                    DataInputStream dataInputStream = null;
                    try {
                        dataInputStream = new DataInputStream(dataTransferSocket.getInputStream());
                    } catch (IOException e) {
                        //TODO something
                        e.printStackTrace();
                    }

                    try {
                        passwordAndIP.add(dataInputStream.readUTF());
                    } catch (IOException e) {
                        //TODO something
                        e.printStackTrace();
                    }
                }
                result = ManagedServer.passwordValidate(passwordAndIP.get(0), passwordAndIP.get(1));
            }
        }
    }

    public static boolean getResult() {
        return result;
    }
}
