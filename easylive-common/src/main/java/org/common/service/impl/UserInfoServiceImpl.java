package org.common.service.impl;

import org.common.component.RedisComponent;
import org.common.constant.Constants;
import org.common.entity.dto.TokenUserInfoDTO;
import org.common.entity.enums.PageSize;
import org.common.entity.po.UserInfo;
import org.common.entity.enums.UserSexEnum;
import org.common.entity.enums.UserStatusEnum;
import org.common.entity.query.SimplePage;
import org.common.entity.query.UserInfoQuery;
import org.common.entity.vo.PaginationResultVO;
import org.common.exception.BaseException;
import org.common.exception.RegisterFailedException;
import org.common.mappers.UserInfoMapper;
import org.common.service.UserInfoService;
import org.common.utils.CopyTools;
import org.common.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserInfo> findListByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(param);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserInfo bean, UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.deleteByParam(param);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据Email获取对象
     */
    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email修改
     */
    @Override
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    @Override
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    /**
     * 根据NickName获取对象
     */
    @Override
    public UserInfo getUserInfoByNickName(String nickName) {
        return this.userInfoMapper.selectByNickName(nickName);
    }

    /**
     * 根据NickName修改
     */
    @Override
    public Integer updateUserInfoByNickName(UserInfo bean, String nickName) {
        return this.userInfoMapper.updateByNickName(bean, nickName);
    }

    /**
     * 根据NickName删除
     */
    @Override
    public Integer deleteUserInfoByNickName(String nickName) {
        return this.userInfoMapper.deleteByNickName(nickName);
    }


    @Override
    public void register(String email, String nickName, String registerPassword) {

        if(userInfoMapper.selectByEmail(email)!=null)
        {
            throw new RegisterFailedException("邮箱已被注册");
        }
        if(userInfoMapper.selectByNickName(nickName)!=null)
        {
            throw new RegisterFailedException("昵称已被注册");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);

        userInfo.setPassword(DigestUtils.md5DigestAsHex(registerPassword.getBytes()));
//        userInfo.setPassword(registerPassword);

        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setSex(UserSexEnum.SECRECT.getSex());
        userInfo.setJoinTime(new java.sql.Timestamp(System.currentTimeMillis()));

        userInfo.setTheme(Constants.THEME_DEFAULT);
        userInfo.setTotalCoinCount(Constants.COIN_DEFAULT);
        userInfo.setCurrentCoinCount(Constants.COIN_DEFAULT);

        int retries = 0;
        while (retries < Constants.MAX_RETRIES)
        {
            try {
                userInfo.setUserId(StringTools.getRandomNumber(Constants.ID_LENGTH));
                userInfoMapper.addUserInfo(userInfo);
                break;
            }
            catch (DuplicateKeyException e){
                retries++;
                if(retries >= Constants.MAX_RETRIES)
                {
                    throw new RegisterFailedException("注册失败,请稍后再试");
                }
            }
        }

    }

    @Override
    public TokenUserInfoDTO login(String email, String password, String ip) {

        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        if(userInfo==null||!userInfo.getPassword().equals(password))
        {
            throw new BaseException("用户或密码错误");
        }
        if(userInfo.getStatus()!=UserStatusEnum.ENABLE.getStatus())
        {
            throw new BaseException("用户状态异常");
        }

        userInfo.setLastLoginTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userInfo.setLastLoginIp(ip);
        userInfoMapper.updateUserInfo(userInfo);

        TokenUserInfoDTO tokenUserInfoDTO = CopyTools.copy(userInfo, TokenUserInfoDTO.class);

        tokenUserInfoDTO.setExpiredAt(System.currentTimeMillis()+Constants.REDIS_EXPIRE_TIME_DAY*7);
        redisComponent.setTokenUserInfo(tokenUserInfoDTO);

        return tokenUserInfoDTO;

    }


}
