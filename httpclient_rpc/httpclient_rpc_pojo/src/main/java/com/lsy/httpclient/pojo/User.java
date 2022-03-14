package com.lsy.httpclient.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/13
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    private String name;
    private String password;
    private Date birth;

    public int getAge(){

        if(birth==null){
            return -1;}
        return new Date().getYear()-birth.getYear();
        }

    public void setAge(int age){
    }

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"password\":\""+password+"\"}";
    }
}
