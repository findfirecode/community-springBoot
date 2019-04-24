package org.jeecg.modules.frontend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.frontend.entity.Comments;
import org.jeecg.modules.frontend.mapper.CommentsMapper;
import org.jeecg.modules.frontend.service.ICommentsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 评论
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {
    @Override
    public Page<Map<String, Object>> getCommentsUser(Page<Map<String, Object>> page, String condition, String parentId) {
        return page.setRecords(this.baseMapper.getCommentsUser(page,condition, parentId));
    }
}
