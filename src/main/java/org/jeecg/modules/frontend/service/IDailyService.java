package org.jeecg.modules.frontend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.frontend.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 生活常用品
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
public interface IDailyService extends IService<Daily> {
    Page<Map<String, Object>> getDailyUser(Page<Map<String, Object>> page);
    Map<String, Object> getDailyById(String daily_id);
}
