package com.niukedemo.util;

import com.niukedemo.entity.User;
import org.springframework.stereotype.Component;

/**持有用户信息，代替session对象，线程隔离
 * @author 刘欢
 * @date 2022年10月12日 19:23
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users=new ThreadLocal<>();
    public void setUsers(User user){
        users.set(user);
    }
    public User getUsers(){
        return users.get();
    }
    public void clear(){
        users.remove();
    }
}
