package com.niukedemo.mapper;

import com.niukedemo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * @Description: 根据id出查询用户
     * @Param: [id]
     * @return: com.niukedemo.entity.User
     * @Author: 刘欢
     */
    User selectById(int id);

    /**
     * @Description:根据用户名出查询用户
     * @Param: [username]
     * @return: com.niukedemo.entity.User
     * @Author: 刘欢
     */
    User selectByName(String username);

    /**
     * @Description: 根据邮箱出查询用户
     * @Param: [email]
     * @return: com.niukedemo.entity.User
     * @Author: 刘欢
     */
    User selectByEmail(String email);

    /**
     * @Description: 保存用户
     * @Param: [user]
     * @return: int
     * @Author: 刘欢
     */
    int insertUser(User user);

    /**
     * @Description: 根据id出修改用户的状态
     * @Param: [id, status]
     * @return: int
     * @Author: 刘欢
     */
    int updateStatus(@Param("id") int id, @Param("status") int status);

    /**
     * @Description: 根据id修改头像地址
     * @Param: [id, headerUrl]
     * @return: int
     * @Author: 刘欢
     */
    int updateHeader(@Param("id") int id, @Param("headerUrl") String headerUrl);

    /**
     * @Description: 根据id修改用户密码
     * @Param: [id, password]
     * @return: int
     * @Author: 刘欢
     */
    int updatePassword(@Param("id") int id, @Param("password") String password);
}
