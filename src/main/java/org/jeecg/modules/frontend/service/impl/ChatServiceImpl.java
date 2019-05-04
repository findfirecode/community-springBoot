package org.jeecg.modules.frontend.service.impl;

import org.jeecg.modules.frontend.entity.Chat;
import org.jeecg.modules.frontend.mapper.ChatMapper;
import org.jeecg.modules.frontend.service.IChatService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 聊天
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {
    @Override
    public List<Map<String, Object>> getChatScroll(String send_id, String recive_id) {
        List<Map<String, Object>> data = this.baseMapper.getChatScroll(send_id, recive_id);
        for (int i = 0; i <data.size() ; i++) {
            if (data.get(i).get("send_id").equals(send_id)){
                data.get(i).put("direction", 2);
            }else {
                data.get(i).put("direction", 1);
            }
        }

        return data;
    }
}
