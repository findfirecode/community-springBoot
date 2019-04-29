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
            "LEFT JOIN resource r on d.daily_id = r.belong_id "+
            "LEFT JOIN `user` u on d.create_by = u.user_id " +
            "where r.type = 'cover'")
    List<Map<String, Object>> getDailyUser(Page<Map<String, Object>> page);
    @Select("SELECT d.*,r.url as p_url,u.avatar,u.name FROM daily d "+
            "LEFT JOIN resource r on d.daily_id = r.belong_id "+
            "LEFT JOIN `user` u on d.create_by = u.user_id " +
            "where daily_id = #{daily_id} and r.type='cover' ")
    Map<String, Object> getDailyById(String daily_id);
    @Select("SELECT url FROM resource r "+
            "where belong_id = #{belong_id} and type='detail' ")
    List<String> getDetailImg(String belong_id);
}
