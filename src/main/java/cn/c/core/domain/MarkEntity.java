package cn.c.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author hz453@126.com
 */
@MappedSuperclass
public abstract class MarkEntity extends IdEntity {

	private static final long serialVersionUID = -6109712549730444386L;
	
	/**
	 * 编号[唯一且不能为空]
	 */
	@Column(unique = true, nullable = false)
	private String code;
	/**
	 * 名称
	 */
	private String name;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
