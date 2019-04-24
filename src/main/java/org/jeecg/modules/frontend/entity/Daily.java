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
 * @Description: 生活常用品
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
@Data
@TableName("daily")
public class Daily implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**dailyId*/
	@TableId(type = IdType.UUID)
	@Excel(name = "dailyId", width = 15)
	private java.lang.String dailyId;
	/**imgId*/
	@Excel(name = "imgId", width = 15)
	private java.lang.Integer imgId;
	/**title*/
	@Excel(name = "title", width = 15)
	private java.lang.String title;
	/**content*/
	@Excel(name = "content", width = 15)
	private java.lang.String content;
	/**type*/
	@Excel(name = "type", width = 15)
	private java.lang.String type;
	/**isExist*/
	@Excel(name = "isExist", width = 15)
	private java.lang.Integer isExist;
	/**isExist*/
	@Excel(name = "userId", width = 15)
	private java.lang.String userId;

	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsExist() {
		return isExist;
	}

	public void setIsExist(Integer isExist) {
		this.isExist = isExist;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
