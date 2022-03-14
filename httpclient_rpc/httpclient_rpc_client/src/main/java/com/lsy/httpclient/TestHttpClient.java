package com.lsy.httpclient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lsy.httpclient.pojo.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 */
public class TestHttpClient {
    public static void main(String[] args) throws IOException, URISyntaxException {

//        testGetNoParams();
//        testGetParams();

        testPost();
    }

    /**
     * 无参数get请求
     * 浏览器访问网站的过程
     * 1 打开浏览器
     * 2 输入地址
     * 3 访问 并查看结果
     * 使用http访问web服务的过程
     * 1 创建客户端 相当于打开浏览器
     * 2 创建请求地址 相当于输入地址
     * 3 发起请求 相当于访问网站
     * 4 处理响应结果 相当于浏览器回显界面
     */
    public static void testGetNoParams() throws IOException {
        //按照开发规则，类型或接口名后加s通常都是工具类
        //创建客户端对象
        HttpClient client = HttpClients.createDefault();
        //创建请求地址
        HttpGet get = new HttpGet("http://localhost:80/test");
        //发起请求 返回响应对象
        HttpResponse response = client.execute(get);
        //获取响应体,响应数据是一个基于http协议标准字符串封装的对象
        //响应体和响应头都是封装的http协议下的字符串使用 直接使用可能会乱码或解析错误
        HttpEntity entity = response.getEntity();
        //通过http实体工具类转换相应数据
        String responseString = EntityUtils.toString(entity,"UTF-8");
        System.out.println("服务器响应数据是-["+responseString+"]");



    }


    //有参get请求
    public static void testGetParams() throws URISyntaxException, IOException {
        HttpClient client = HttpClients.createDefault();
        //基于URIBuilder构件请求地址
        URIBuilder builder = new URIBuilder("http://localhost:80/params");
        //基于单参数传递构建请求地址
        builder.addParameter("name","小李");
        builder.addParameter("password","88888888");
        URI build = builder.build();
        //基于多参数传递
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        list.add(new BasicNameValuePair("name","小李"));
//        list.add(new BasicNameValuePair("password","88888888"));
//        builder.addParameters(list);
        HttpEntity response = client.execute(new HttpGet(build)).getEntity();
        String result = EntityUtils.toString(response);
        System.out.println(result);
    }

    //post请求
    public static void testPost() throws IOException, URISyntaxException {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:80/test");
        HttpResponse response = client.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity()));

        //有参数的post请求
        //请求头传递参数
        URIBuilder builder = new URIBuilder("http://localhost:80/params");
        builder.addParameter("name","小李");
        builder.addParameter("password","88888888");
        HttpResponse execute = client.execute(new HttpPost(builder.build()));
        System.out.println(EntityUtils.toString(execute.getEntity()));

        //请求体传递参数
        HttpPost bodyParamsPost = new HttpPost("http://localhost:80/bodyParams");
        User user = new User();
        user.setName("xiaoli");
        user.setPassword("123");
        User user1 = new User();
        user1.setName("dali");
        user1.setPassword("123");
        //拼接json格式字符串 表示请求参数 List<User>
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user1);
        //把集合转换为json字符串
        //创建转换器对象 objectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        String paramsString = objectMapper.writeValueAsString(users);
        /**
         * [{"name":"xiaoli","password":"123","birth":null,"age":-1},
         * {"name":"dali","password":"123","birth":null,"age":-1}]
         */
        System.out.println(paramsString);
//        String paramsString = "["+user.toString()+","+user1.toString()+"]";
        //需要修改请求体类型
        HttpEntity entity = new StringEntity(paramsString,"application/json","UTF-8");
        //请求体传递参数默认是表单格式
        //使用URIBuilder构件的uri对象，是请求体传递参数
        bodyParamsPost.setEntity(entity);
        String responseString = EntityUtils.toString(client.execute(bodyParamsPost).getEntity());
        String userString = responseString.substring(1, responseString.indexOf("},")+1);
        User responseUser = objectMapper.readValue(userString, User.class);

        System.out.println(responseUser);
        //构件jackson识别的java类型映射
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class,User.class);
        List<User> responseList = objectMapper.readValue(responseString, valueType);
        System.out.println(responseList);

    }


}
