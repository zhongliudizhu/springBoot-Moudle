package com.winstar.network;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class RpcExecutor implements Runnable {
    private Socket socket;

    public RpcExecutor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("Thread is {} 来进行处理", Thread.currentThread().getName());
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
