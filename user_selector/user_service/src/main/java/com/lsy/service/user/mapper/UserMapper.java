package com.lsy.service.user.mapper;

import com.lsy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
@Mapper
public interface UserMapper {


    @Select("select id,name,gender,age from tb_user where name like concat('%', #{name},'%")
    List<User> getUser(String name);
}
