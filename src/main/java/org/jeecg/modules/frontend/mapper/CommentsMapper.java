package org.jeecg.modules.frontend.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.frontend.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 评论
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
public interface CommentsMapper extends BaseMapper<Comments> {
    @Select("SELECT c.*,u.`name`,r.url FROM comments c " +
            "LEFT JOIN `user` u on c.user_id = u.user_id " +
            "LEFT JOIN resource r on u.img_id = r.resource_id " +
            "where c.content like '%${condition}%' and c.type='论坛' and c.parent_id = #{parentId}")
    List<Map<String, Object>> getCommentsUser(Page<Map<String, Object>> page, String condition, String parentId);
}
