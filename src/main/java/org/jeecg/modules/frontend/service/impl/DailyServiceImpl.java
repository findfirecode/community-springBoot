package org.jeecg.modules.frontend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.frontend.entity.Daily;
import org.jeecg.modules.frontend.mapper.DailyMapper;
import org.jeecg.modules.frontend.service.IDailyService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 生活常用品
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements IDailyService {
    @Override
    public Page<Map<String, Object>> getDailyUser(Page<Map<String, Object>> page) {
        return page.setRecords(this.baseMapper.getDailyUser(page));
    }

    @Override
    public Map<String, Object> getDailyById(String daily_id) {
        return this.baseMapper.getDailyById(daily_id);
    }

    @Override
    public List<String> getDetailImg(String belong_id) {
        return this.baseMapper.getDetailImg(belong_id);
    }
}
