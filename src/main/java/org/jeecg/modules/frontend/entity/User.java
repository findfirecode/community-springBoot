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
 * @Description: 用户
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**userId*/
	@TableId
	@Excel(name = "userId", width = 15)
	private java.lang.String userId;
	/**name*/
	@Excel(name = "name", width = 15)
	private java.lang.String name;
	/**imgId*/
	@Excel(name = "imgId", width = 15)
	private java.lang.String imgId;
	/**account*/
	@Excel(name = "account", width = 15)
	private java.lang.String account;
	/**password*/
	@Excel(name = "password", width = 15)
	private java.lang.String password;
	/**role*/
	@Excel(name = "role", width = 15)
	private java.lang.Integer role;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
}
