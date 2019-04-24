package org.jeecg.modules.frontend.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 日常类操作
 * @author： jeecg-boot
 * @date：   2019-04-22
 * @version： V1.0
 */
@Data
@TableName("daily_operate")
public class Daily_operate implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**dailyOperateId*/
	@Excel(name = "dailyOperateId", width = 15)
	private String dailyOperateId;
	/**dailyId*/
	@Excel(name = "dailyId", width = 15)
	private String dailyId;
	/**creatTime*/
	@Excel(name = "creatTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date creatTime;
	/**userId*/
	@Excel(name = "userId", width = 15)
	private String userId;

	public String getDailyOperateId() {
		return dailyOperateId;
	}

	public void setDailyOperateId(String dailyOperateId) {
		this.dailyOperateId = dailyOperateId;
	}

	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**operateType*/
	@Excel(name = "operateType", width = 15)
	private String operateType;
	/**value*/
	@Excel(name = "value", width = 15)
	private String value;
}
