package com.spursgdp.bio;

/**
 * @author zhangdongwei
 * @create 2020-02-06-19:39
 */
public class RequestThread extends Thread{

    private RequestHandler handler;

    public RequestThread(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.handleRequest();
    }
}
