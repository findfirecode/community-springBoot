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
 * @Description: 收藏
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
@Data
@TableName("collect")
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**collectId*/
	@TableId(type = IdType.UUID)
	@Excel(name = "collectId", width = 15)
	private java.lang.String collectId;
	/**createBy*/
	@Excel(name = "createBy", width = 15)
	private java.lang.String createBy;
	/**belongBy*/
	@Excel(name = "belongBy", width = 15)
	private java.lang.String belongBy;
	/**keyId*/
	@Excel(name = "keyId", width = 15)
	private java.lang.String keyId;
	/**datetime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getBelongBy() {
		return belongBy;
	}

	public void setBelongBy(String belongBy) {
		this.belongBy = belongBy;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
}
