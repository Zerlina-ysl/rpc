package com.lsy.rmi;

import com.lsy.rmi.api.FirstInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  客户端主方法
 *
 */
public class ClientMainClass {
    public static void main(String[] args) {
        FirstInterface first = null;
        //创建代理对象

        //通过名称寻找服务，并自动创建代理服务
        //object类型，对象一定是proxy的子类型，且一定实现了服务接口
        try {
            //面向代理对象
            //此时没有守护线程，运行结束会正常关闭
            first = (FirstInterface) Naming.lookup("rmi://localhost:9999/first");
            //first是代理对象
            System.out.println("对象的类型："+first.getClass().getName());
            String result = first.first("rpc好难啊，现在在干什么");
            System.out.println(result);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
