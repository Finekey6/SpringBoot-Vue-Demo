package springboot.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import springboot.springboot.common.Result;
import springboot.springboot.entity.User;
import springboot.springboot.mapper.UserMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

     /*这样引入userMapper并不规范，
     因为没有在项目中写入service层*/
    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody User user){
        //设置默认密码
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }

        userMapper.insert(user);//MybatisPlus实现
        return Result.success();//返回状态码等信息给前端
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){

        userMapper.deleteById(id);//MybatisPlus实现
        return Result.success();//返回状态码等信息给前端
    }

    @PutMapping
    public Result<?> update(@RequestBody User user){

        userMapper.updateById(user);//MybatisPlus实现
        return Result.success();//返回状态码等信息给前端
    }

    /**
     *
     * @param pageNum 前端页面的当前页码数
     * @param pageSize 前端页面显示的多少条/页
     * @param search 前端页面的关键字查询
     * @return
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //分页查询
        Page<User> objectPage = new Page<>(pageNum, pageSize);
        /*nickName为null时也可查询的出来*/
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){ //StrUtil为第三方依赖:hutool
            lambdaQuery.like(User::getNickName,search);
        }
        Page<User> userPage = userMapper.selectPage(objectPage, lambdaQuery);
        /*查询不出nickName为null的数据
        Page<User> userPage = userMapper.selectPage(objectPage, Wrappers.<User>lambdaQuery().like(User::getNickName, search));*/
        return Result.success(userPage);//返回状态码 获取到的数据库等信息给前端
    }

}
