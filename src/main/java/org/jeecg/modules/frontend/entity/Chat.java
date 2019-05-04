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
 * @Description: 聊天
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
@Data
@TableName("chat")
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**chatId*/
	@TableId(type = IdType.UUID)
	@Excel(name = "chatId", width = 15)
	private java.lang.String chatId;
	/**sendId*/
	@Excel(name = "sendId", width = 15)
	private java.lang.String sendId;
	/**reciveId*/
	@Excel(name = "reciveId", width = 15)
	private java.lang.String reciveId;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**type*/
	@Excel(name = "type", width = 15)
	private java.lang.String type;
	/**content*/
	@Excel(name = "content", width = 15)
	private java.lang.String content;

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getReciveId() {
		return reciveId;
	}

	public void setReciveId(String reciveId) {
		this.reciveId = reciveId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
