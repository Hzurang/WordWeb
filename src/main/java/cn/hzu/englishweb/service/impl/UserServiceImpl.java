package cn.hzu.englishweb.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hzu.englishweb.dao.UserDao;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.User;
import cn.hzu.englishweb.service.UserService;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @className UserServiceImpl
 * @description 用户业务处理实现类
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String registerByTel(User user, String smsCode) {
        //获取系统验证码
        String sysCode = (String) redisTemplateUtil.get("code" + user.getTel());
        logger.info("从Redis中取到的短信验证码为："+sysCode+"   用户收到的的短信验证码为："+smsCode);

        System.out.println("从Redis中取到的短信验证码为："+sysCode+"   用户收到的的短信验证码为："+smsCode);
        if (!sysCode.equals(smsCode)) {
            return "alert('验证码错误，请重新输入')";
        }

        if (sysCode == null) {
            return "alert('验证码已过期，请重新获取')";
        }

        String resStr;

        // 先去数据库找用户名是否存在
        User getUser = null;
        try {
            getUser = userDao.getByPhone(user.getTel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser != null) {
            resStr = "该手机号已注册！";
            return resStr;
        }
        // 加密储存用户的密码
        String str = SaSecureUtil.md5(user.getPassword());
        user.setPassword(str);
        // 设置默认的用户名
        user.setUserName(user.getTel());
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        user.setCreateTime(timeStamp);
        // 存入数据库
        userDao.addByPhone(user);
        // 返回用户数据，成功消息
        resStr = "注册成功";
        return resStr;
    }

    @Override
    public String registerByMail(User user, String smsCode) {
        String sysCode = (String) redisTemplateUtil.get("code" + user.getEmail());
        System.out.println("从Redis中取到的邮箱验证码为："+sysCode+"   用户收到的的邮箱验证码为："+smsCode);

        if (!sysCode.equals(smsCode)) {
            return "alert('验证码错误，请重新输入')";
        }
        if (sysCode == null) {
            return "alert('验证码已过期，请重新获取')";
        }

        String resStr;

        // 先去数据库找用户名是否存在
        User getUser = null;
        try {
            getUser = userDao.getByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser != null) {
            resStr = "该邮箱号已注册！";
            return resStr;
        }
        // 加密储存用户的密码
        String str = SaSecureUtil.md5(user.getPassword());
        user.setPassword(str);
        // 设置默认的用户名
        user.setUserName(user.getEmail());
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        user.setCreateTime(timeStamp);
        userDao.addByMail(user);
        // 返回用户数据，成功消息
        resStr = "注册成功";
        System.out.println("registerByMail"+resStr);
        return resStr;
    }

    @Override
    public String findBackByTel(User user, String smsCode) {
        //获取系统验证码
        String sysCode = (String) redisTemplateUtil.get("code" + user.getTel());
        System.out.println("从Redis中取到的短信验证码为："+sysCode+"   用户收到的的短信验证码为："+smsCode);
        if (!sysCode.equals(smsCode)) {
            return "alert('验证码错误，请重新输入')";
        }

        if (sysCode == null) {
            return "alert('验证码已过期，请重新获取')";
        }

        String resStr;
        User getUser = null;

        try {
            getUser = userDao.getByPhone(user.getTel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            resStr = "用户不存在，请先注册！";
            return resStr;
        } else {
            userDao.updateByPhone(getUser.getTel(), user.getPassword());
            resStr = "用户密码重置成功";
            return resStr;
        }
    }

    @Override
    public String findBackByMail(User user, String smsCode) {
        String sysCode = (String) redisTemplateUtil.get("code" + user.getEmail());
        System.out.println("从Redis中取到的邮箱验证码为："+sysCode+"   用户收到的的邮箱验证码为："+smsCode);

        if (!sysCode.equals(smsCode)) {
            return "alert('验证码错误，请重新输入')";
        }
        if (sysCode == null) {
            return "alert('验证码已过期，请重新获取')";
        }

        String resStr;
        User getUser = null;
        try {
            getUser = userDao.getByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            resStr = "用户不存在，请先注册！";
            return resStr;
        } else {
            userDao.updateByEmail(getUser.getEmail(), user.getPassword());
            resStr = "用户密码重置成功";
            return resStr;
        }
    }

    @Override
    public Result<User> loginByTel(User user) {
        Result<User> result = new Result<>();

        User getUser = null;
        try {
            getUser = userDao.getByPhone(user.getTel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            result.setResultFailed("用户不存在！");
            return result;
        }
        String str = getUser.getPassword();
        String str2 = user.getPassword();
        //比对密码（数据库取出用户的密码是加密的，因此要把前端传来的用户密码加密再比对）
        if (!str.equals(str2)) {
            result.setResultFailed("手机号或者密码错误！");
            return result;
        }
        if (getUser.getRole() == 1) {
            result.setResultFailed("用户登录失败，该用户被锁定");
        }
        //设定登录成功消息以及用户信息
        result.setResultSuccess("登录成功！", getUser);
        return result;
    }

    @Override
    public Result<User> loginByMail(User user) {
        Result<User> result = new Result<>();

        User getUser = null;
        try {
            getUser = userDao.getByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            result.setResultFailed("用户不存在！");
            return result;
        }
        String str = getUser.getPassword();
        String str2 = user.getPassword();
        //比对密码（数据库取出用户的密码是加密的，因此要把前端传来的用户密码加密再比对）
        if (!str.equals(str2)) {
            result.setResultFailed("邮箱号或者密码错误！");
            return result;
        }
        if (getUser.getRole() == 1) {
            result.setResultFailed("用户登录失败，该用户被锁定");
        }
        result.setResultSuccess("登录成功！", getUser);
        return result;
    }

    @Override
    public Result<User> getUserById(Integer userId) {
        Result<User> result = new Result<>();

        User getUser = null;
        try {
            getUser = userDao.getById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            result.setResultFailed("用户不存在");
            return result;
        } else {
            result.setResultSuccess("查找成功", getUser);
            return result;
        }
    }

    @Override
    public int updateUserNameById(User user) {
        return userDao.updateUserName(user);
    }
}
