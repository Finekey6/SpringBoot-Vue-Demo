package springboot.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 对应数据库中相应的表
 * 以及属性
 */
@Data
@TableName("user")//对应数据库的user表
public class User {

    @TableId(type = IdType.AUTO)//设置id自增
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;

}
