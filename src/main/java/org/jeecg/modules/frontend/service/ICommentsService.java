package org.jeecg.modules.frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.frontend.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 评论
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
public interface ICommentsService extends IService<Comments> {
    Page<Map<String, Object>> getCommentsUser(Page<Map<String, Object>> page,
                                              String condition, String parentId,
                                              String type);
}
