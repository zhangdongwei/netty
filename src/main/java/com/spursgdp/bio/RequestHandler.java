package com.spursgdp.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author zhangdongwei
 * @create 2020-02-06-19:32
 */
public class RequestHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void handleRequest() {
        try {
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    //输出客户端发送过来的数据
                    String str = new String(bytes, 0, read);
                    System.out.println("线程" + Thread.currentThread().getName() + " 接收数据：" + str);
                } else {
                    //客户端断开连接，跳出循环，终止线程，在finally中会关闭socket
                    System.out.println("线程" + Thread.currentThread().getName() + " 终止，客户端断开连接.");
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("发生异常：" + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("关闭socket发生异常：" + e);
                throw new RuntimeException(e);
            }
        }
    }

}
