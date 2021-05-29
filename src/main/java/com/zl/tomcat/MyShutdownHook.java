package com.zl.tomcat;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author zhangliang
 * @date 2020/10/23.
 */
public class MyShutdownHook extends Thread {
    ServerSocket serverSocket;
    MyShutdownHook( ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
//        if (null != serverSocket){
//            try{
//                serverSocket.close();
//                System.out.println("server close");
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
    }
}
