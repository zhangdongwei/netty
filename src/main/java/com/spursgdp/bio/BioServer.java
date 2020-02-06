package com.spursgdp.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangdongwei
 * @create 2020-02-06-19:45
 */
public class BioServer {

    public static final Logger LOGGER = LoggerFactory.getLogger(BioServer.class);

    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);

    public static void main(String[] args) {
        serverBootstrap();
    }

    public static void serverBootstrap() {
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(true) {
                LOGGER.info("线程"+Thread.currentThread().getName()+" accept...");
                socket = serverSocket.accept();
                LOGGER.info("线程"+Thread.currentThread().getName()+"接收到新的连接请求，开始处理...");
                RequestHandler requestHandler = new RequestHandler(socket);
                threadPool.execute(new RequestThread(requestHandler));
            }
        } catch (IOException e) {
            LOGGER.error("发生异常："+e);
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.error("关闭socket发生异常："+e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
