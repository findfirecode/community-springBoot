package org.jeecg.modules.frontend.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.frontend.entity.Daily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 生活常用品
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
public interface DailyMapper extends BaseMapper<Daily> {
    @Select("SELECT d.*,r.url as p_url,u.avatar FROM daily d "+
            "LEFT JOIN resource r on d.img_id = r.resource_id "+
            "LEFT JOIN `user` u on d.user_id = u.user_id")
    List<Map<String, Object>> getDailyUser(Page<Map<String, Object>> page);
    @Select("SELECT d.*,r.url as p_url,u.avatar FROM daily d "+
            "LEFT JOIN resource r on d.img_id = r.resource_id "+
            "LEFT JOIN `user` u on d.user_id = u.user_id " +
            "where daily_id = #{daily_id}")
    Map<String, Object> getDailyById(String daily_id);
}
