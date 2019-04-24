package org.jeecg.modules.frontend.service.impl;

import org.jeecg.modules.frontend.entity.Resource;
import org.jeecg.modules.frontend.mapper.ResourceMapper;
import org.jeecg.modules.frontend.service.IResourceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 资源文件
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
