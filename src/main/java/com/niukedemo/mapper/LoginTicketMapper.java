package com.niukedemo.mapper;

import com.niukedemo.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    /**
     * @Description:登录成功时，向login_ticket添加一条数据，用来保存登录凭证
     * @Param: [loginTicket]
     * @return: int
     * @Author: 刘欢
     */
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * @Description: 根据登录凭证，找到这是哪个用户的登陆凭证
     * @Param: [ticket]
     * @return: com.niukedemo.entity.LoginTicket
     * @Author: 刘欢
     */
    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    /**
     * @Description: 退出时，不能将登录凭证删除，而是把用户的状态设置成禁用
     * @Param: [ticket, status]
     * @return: int
     * @Author: 刘欢
     */
    @Update({
            "update login_ticket ",
            "set status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
