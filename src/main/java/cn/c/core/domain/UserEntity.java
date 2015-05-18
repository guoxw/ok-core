package cn.c.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author hz453@126.com
 */
@MappedSuperclass
public abstract class UserEntity extends DateEntity {
	
	private static final long serialVersionUID = 3269319296715630928L;
	
	/**
	 * 创建时用户编码
	 */
	@Column(updatable = false)
	private String createUserCode;
	/**
	 * 最后更新时用户编码
	 */
	private String updateUserCode;

	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getUpdateUserCode() {
		return updateUserCode;
	}
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}
}
