package org.jeecg.modules.frontend.service;

import org.jeecg.modules.frontend.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 聊天
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
public interface IChatService extends IService<Chat> {
    List<Map<String, Object>> getChatScroll(String send_id, String recive_id);
}
