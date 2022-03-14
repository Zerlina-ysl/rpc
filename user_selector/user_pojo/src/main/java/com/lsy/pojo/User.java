package com.lsy.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private Integer id;
    private String name;
    private int age;
    private String gender;


}
