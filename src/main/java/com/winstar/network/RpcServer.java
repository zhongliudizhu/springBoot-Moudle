package com.winstar.network;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RpcServer {

    private static final int port = 9997;

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public void start() {
        log.info("服务端在port {} 启动", port);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                log.info("有新的客户端连接进来");
                pool.execute(new RpcExecutor(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
