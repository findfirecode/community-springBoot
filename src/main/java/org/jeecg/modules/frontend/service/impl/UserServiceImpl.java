package org.jeecg.modules.frontend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.frontend.entity.User;
import org.jeecg.modules.frontend.mapper.UserMapper;
import org.jeecg.modules.frontend.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
