package com.zl.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjie
 * @version 2018/11/9
 * tomcat启动类
 */
public class MyTomcat {
    private int port=8088;
    private Map<String,String> urlServletMap =new HashMap<String,String>();
    public MyTomcat(int port){
        this.port=port;
    }

    public void start(){
//        初始化URL与对应处理的servlet的关系
        initServletMapping();


        ServerSocket serverSocket=null;
        try{
            serverSocket = new ServerSocket(port);
            MyShutdownHook hook = new MyShutdownHook(serverSocket);
            Runtime.getRuntime().addShutdownHook(hook);
            System.out.println("MyTomcat is start...");

            while(true){
                Socket socket= serverSocket.accept();
                InputStream inputStream=socket.getInputStream();
                OutputStream outputStream=socket.getOutputStream();

                MyRequest myRequest= new MyRequest(inputStream);
                MyResponse myResponse =new MyResponse(outputStream);

//                请求分发
                dispatch(myRequest,myResponse);
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initServletMapping(){
        for(ServletMapping servletMapping:ServletMappingConfig.servletMappingList){
            urlServletMap.put(servletMapping.getUrl(),servletMapping.getClazz());
        }
    }

    public void dispatch(MyRequest myRequest,MyResponse myResponse){
        String clazz =urlServletMap.get(myRequest.getUrl());

        //反射
        try{
            Class<MyServlet> myServletClass =(Class<MyServlet>) Class.forName(clazz);
            MyServlet myServlet= myServletClass.newInstance();

            myServlet.service(myRequest,myResponse);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new MyTomcat(8088).start();
    }
}
