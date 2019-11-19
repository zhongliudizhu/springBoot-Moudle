package com.winstar.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {


    public void start() {
        ObjectOutputStream outputStream = null;
        try {
            Socket socket = new Socket("localhost", 9987);
            outputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
