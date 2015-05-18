package cn.c.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author hz453@126.com
 */
@MappedSuperclass
public abstract class DateEntity extends IdEntity {

	private static final long serialVersionUID = 3269319296715630928L;
	
	/**
	 * 创建时间
	 */
	@Column(updatable = false)
	private Date createTime;
	/**
	 * 最后更新时间
	 */
	private Date updateTime;

	public DateEntity() {
		super();
		this.createTime = new Date();
		this.updateTime = this.createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
