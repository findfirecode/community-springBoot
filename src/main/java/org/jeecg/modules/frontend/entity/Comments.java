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
 * @Description: 评论
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
@Data
@TableName("comments")
public class Comments implements Serializable {
    private static final long serialVersionUID = 1L;

	/**commentsId*/
	@TableId(type = IdType.UUID)
	@Excel(name = "commentsId", width = 32)
	private String commentsId;
	/**userId*/
	@Excel(name = "userId", width = 15)
	private String userId;
	/**content*/
	@Excel(name = "content", width = 15)
	private String content;
	/**isreply*/
	@Excel(name = "isreply", width = 15)
	private Integer isreply;
	@Excel(name = "isdisplay", width = 15)
	private Integer isdisplay;
	/**datetime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**type*/
	@Excel(name = "type", width = 15)
	private String type;
	/**parentId*/
	@Excel(name = "parentId", width = 15)
	private String parentId;
	/**title*/
	@Excel(name = "title", width = 15)
	private String title;

	public Comments(){
		this.isdisplay = 0;
		this.isreply=0;
	}

	public String getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(String commentsId) {
		this.commentsId = commentsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsreply() {
		return isreply;
	}

	public void setIsreply(Integer isreply) {
		this.isreply = isreply;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getIsdisplay() {
		return isdisplay;
	}

	public void setIsdisplay(Integer isdisplay) {
		this.isdisplay = isdisplay;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
