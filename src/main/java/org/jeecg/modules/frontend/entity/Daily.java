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
	private String dailyId;
	/**title*/
	@Excel(name = "title", width = 15)
	private String title;
	/**content*/
	@Excel(name = "content", width = 15)
	private String content;
	/**type*/
	@Excel(name = "type", width = 15)
	private String type;
	/**isExist*/
	@Excel(name = "isExist", width = 15)
	private Integer isExist;
	/**isExist*/
	@Excel(name = "createBy", width = 15)
	private String createBy;
	/**isExist*/
	@Excel(name = "buyNum", width = 15)
	private Integer buyNum;
	/**isExist*/
	@Excel(name = "price", width = 15)
	private Integer price;



	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
