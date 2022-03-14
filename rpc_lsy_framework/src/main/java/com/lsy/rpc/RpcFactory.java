package com.lsy.rpc;

import com.lsy.rpc.Connection.zkConnection;
import com.lsy.rpc.registry.RpcRegistry;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 *  框架入口
 */
public class RpcFactory {

    private static final Properties config = new Properties();
    private static zkConnection connection ;
    private static RpcRegistry registry ;

    //读取初始化的配置对象
    private static final Properties sources   = new Properties();

    /**
     * 初始化过程
     * classpath下提供配置文件 命名为lsy-rpc.properties
     * 配置文件结构：
     *  registry.ip
     *  registry.port 默认为9090
     *  zk.server
     *  zk.sessionTimeout 默认为10000
     */
    static{


        try {
        //获取classpath类路径下的配置文件输入流
        InputStream input = RpcFactory.class.getClassLoader().getResourceAsStream("lsy-rpc.properties");
            config.load(input);
       //按理说需要默认值 但是我连接阿里云的zookeeper
            String serverIp = config.getProperty("registry.ip")==null?"localhost":config.getProperty("registry.ip");
       int serverPort = config.getProperty("registry.port")==null?9090:Integer.parseInt(config.getProperty("registry.port"));
       String zkServer = config.getProperty("zk.server");

       int zkSessionTimeout = config.getProperty("zk.sessionTimeout")==null?10000:Integer.parseInt(config.getProperty("zk.sessionTimeout"));
       connection = new zkConnection(zkServer,zkSessionTimeout);
       registry = new RpcRegistry();
       registry.setIp(serverIp);
       registry.setPort(serverPort);
       registry.setConnection(connection);
       //创建rmi的注册器registry
            LocateRegistry.createRegistry(serverPort);

            //需初始化zk中的父节点，不然会报错  NoNode for /lsy/rpc/com.lsy.service.UserService

            ExistOrCreate("","lsy");
            ExistOrCreate("lsy","rpc");


            //使用配置文件注册服务  lsy-rpc-service.properties
            //自定义配置文件格式规则 接口全类名=实现类全类名
            InputStream inputSource = RpcFactory.class.getClassLoader().getResourceAsStream("lsy-rpc-service.properties");
            if(inputSource!=null){
                sources.load(inputSource);
                for(Object key:sources.keySet()){

        //key是接口全类名，value是实现类的全类名
                    Object value = sources.get(key);
                    //获取接口全类名和实现类的全类名
                    Class<Remote> serviceInterface = (Class<Remote>) Class.forName(key.toString());
                    Remote serviceObject = (Remote) Class.forName(value.toString()).newInstance();
                    //注册
                    RpcFactory.registerService(serviceInterface,serviceObject);


                }
            }

        } catch (IOException e) {
        //当初始化代码块发生异常时，抛出错误，中断虚拟机
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void ExistOrCreate(String oldPath,String newPath) throws IOException, KeeperException, InterruptedException {
        List<String> children = connection.getConnection().getChildren("/"+oldPath, false);
        if(!children.contains(newPath)&&!oldPath.equals("")){
            connection.getConnection().create("/"+oldPath+"/"+newPath,null,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }else if(!children.contains(newPath)){
            connection.getConnection().create("/"+newPath,null,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }else{
            return ;
        }


    }

    /**
     * 快速注册服务和创建客户端代理对象的静态工具方法 调用的是RpcRegistry的registryService()
     * @param serviceInterface
     * @param serviceObject
     * @throws InterruptedException
     * @throws IOException
     * @throws KeeperException
     */
    public static void registerService(Class<? extends Remote>serviceInterface,Remote serviceObject) throws InterruptedException, IOException, KeeperException {
        registry.registerService(serviceInterface,serviceObject);

    }


    public static <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws InterruptedException, NotBoundException, KeeperException, IOException {
        return registry.getServiceProxy(serviceInterface);
    }


}
