package com.wfs.tlmarket.serviceImpl;

import com.wfs.tlmarket.mapper.UserInfoMapper;
import com.wfs.tlmarket.service.response.Response;
import com.wfs.tlmarket.models.UserInfo;
import com.wfs.tlmarket.service.UserService;
import com.wfs.tlmarket.utils.AESUtil;
import com.wfs.tlmarket.utils.SqeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 创建人：王福顺  创建时间：2019/10/23
 * 服务 实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;


    /**
     * 注册
     * @param userInfo
     * @return
     */
    @Override
    public Response register(UserInfo userInfo) {
        Response response = new Response();
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        String userNickName = userInfo.getUserNickName();

        UserInfo checkUser = userInfoMapper.selectByUserName(userName);
        if (null != checkUser){
            // 已存在
            response.setIsSuccess(false);
            response.setErrorMsg("用户已存在");
            return response;
        }
        userInfo.setUserNo(SqeUtil.createUserNo());
        userInfo.setPassword(AESUtil.Encrypt(password));
        // todo 如果没有，随机分配昵称
        userInfo.setUserNickName(userNickName);
        Date date = new Date();
        userInfo.setStatus((byte) 1);
        userInfo.setCreatedBy("王");
        userInfo.setCreatedAt(date);
        userInfo.setUpdatedBy("王");
        userInfo.setUpdatedAt(date);
        try{
            userInfoMapper.insertSelective(userInfo);
        }catch (Exception e) {
            response.setIsSuccess(false);
            response.setErrorMsg("网络繁忙，请稍后");
        }
        return response;
    }


    /**
     * 登录鉴权
     * @param userInfo
     * @return
     */
    @Override
    public Response<UserInfo> auth(UserInfo userInfo) {
        Response<UserInfo> response = new Response();
        String userName = userInfo.getUserName();
        String password = AESUtil.Encrypt(userInfo.getPassword());
        UserInfo checkUser = userInfoMapper.selectByUserName(userName);
        if (null == checkUser) {
            response.setIsSuccess(false);
            response.setErrorMsg("未找到用户名");
        }else {
            // 鉴权
            if (!password.equals(checkUser.getPassword())) {
                response.setIsSuccess(false);
                response.setErrorMsg("密码错误");
            }
        }
        response.setResult(checkUser);
        return response;
    }
}
