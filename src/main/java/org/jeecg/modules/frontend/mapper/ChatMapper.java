package org.jeecg.modules.frontend.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.frontend.entity.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 聊天
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
public interface ChatMapper extends BaseMapper<Chat> {
    @Select("Select c.* from chat c " +
            "where (send_id=${send_id} AND recive_id=${recive_id}) or (send_id=${recive_id} AND recive_id=${send_id}) " +
            "ORDER BY create_time ASC ")
    List<Map<String, Object>> getChatScroll(@Param("send_id") String send_id, @Param("recive_id") String recive_id);
}
