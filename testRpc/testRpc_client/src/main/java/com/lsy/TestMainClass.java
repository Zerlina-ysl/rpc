package com.lsy;

import com.lsy.rpc.RpcFactory;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 * 测试基于自定义rpc框架的客户端开发
 */
public class TestMainClass {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException, NotBoundException {

        //通过自定义框架连接zk 获取接口的动态代理对象
        UserService proxy = RpcFactory.getServiceProxy(UserService.class);
        System.out.println(proxy.getClass().getName());

        String result = proxy.getUser("管理员");
        System.out.println("远程服务返回查询结果："+result);

    }


}
