package com.lsy.rmi;

import com.lsy.rmi.api.FirstInterface;
import com.lsy.rmi.impl.FirstRMIImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  创建一个服务实现对象 提供服务 并注册到Registry
 *  rmi的registry在创建时会自动启动一个子线程并升级为守护线程，提供持久服务
 *  守护线程 - 为其他线程提供服务 如果只剩下守护线程虚拟机会退出
 *
 */
public class MainClass {
    public static void main(String[] args) throws  MalformedURLException {

        try {
            System.out.println("服务器开始启动...");

            System.out.println(LocateRegistry.getRegistry(9999));
            //创建服务对象
            FirstInterface first = new FirstRMIImpl();
            //注册到registry
//            System.out.println(LocateRegistry.getRegistry(9999));
            LocateRegistry.createRegistry(9999);
//多次创建会报错端口异常

//            System.out.println(LocateRegistry.getRegistry(9999));
            //绑定一个服务到注册中心。提供命名，格式为 rmi://ip:port/别名
            //如果服务重复 抛出异常 - AlreadyBoundException
            //-重复： 命名相同
            String name = "rmi://localhost:9999/first";
//            Naming.bind(name,first);

            //重新绑定一个服务到注册中心 和bind的区别是 命名冲突会直接覆盖

            Naming.rebind(name,first);
            System.out.println("服务器启动完毕...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
