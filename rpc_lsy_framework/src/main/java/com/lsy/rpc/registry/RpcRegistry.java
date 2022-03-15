package com.lsy.rpc.registry;

import com.lsy.rpc.Connection.zkConnection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  注册器工具
 *  通过zk连接对象，和传入的Remote接口实现对象，完成RMI地址的拼接和保存
 */
@Data
@Getter
@Setter
public class RpcRegistry {

    private zkConnection connection;

    private String ip;
    private int port;
    /**
     * 注册服务
     * @param serviceInterface - 服务接口类对象 要求必须是Remote的子接口 com.lsy.service.UserService.class
     * @param serviceObject - 服务实现类型的对象 如： new com.lsy.service.impl.UserServiceImpl
     *                      必须实现serviceInterface 且是Remote的直接或间接实现类
     * @throws IOException 抛出异常代表注册失败
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, KeeperException, InterruptedException {
        //rmi://ip:port/com.lsy.service.UserService
        String rmi = "rmi://"+ip+":"+port+"/"+serviceInterface.getName();
       //拼接有规则的zk存储节点命名 相当于节点地址
        String path = "/lsy/rpc/"+serviceInterface.getName();
        //存储到zk

        //解决节点已存在问题
            //方案： 插入前查看节点是否存在 若存在则删除
        List<String> children = connection.getConnection().getChildren("/lsy/rpc", false, null);
        if(children.contains(serviceInterface.getName())){

            Stat stat = new Stat();
           connection.getConnection().getData(path, false, stat);
            connection.getConnection().delete(path,stat.getCversion());

        }

        connection.getConnection().create(path,rmi.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


//        LocateRegistry.getRegistry(port);
        //服务对象在rmi的registry中注册
        Naming.rebind(rmi,serviceObject);
        //反复注册抛出异常



    }

    /**
     * 根据服务接口类型访问zk，获取远程代理对象
     * 1. 拼接一个zk中的节点名称
     * 2. 访问zk 查询节点中存储的数据
     * 3。 根据查询的结果访问代理对象
     * @return
     */
    public <T extends Remote> T getServiceProxy(Class<? extends Remote> serviceInterface) throws IOException, KeeperException, InterruptedException, NotBoundException {
        String path="/lsy/rpc/"+serviceInterface.getName();

        //查询节点中存储的数据
        byte[] datas = connection.getConnection().getData(path, false, null);

        String rmi = new String(datas);
        //创建代理对象
        Remote                                                                                                                                                                                                                                                                                                                                                                                               obj = Naming.lookup(rmi);
        return (T)obj;

    }


    public zkConnection getConnection(){
        return connection;
    }



}
