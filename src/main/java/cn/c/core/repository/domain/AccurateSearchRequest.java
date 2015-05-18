package cn.c.core.repository.domain;

import java.io.Serializable;

public class AccurateSearchRequest implements AccurateSearch, Serializable {
	
	private static final long serialVersionUID = 4101338273835591756L;
	
	private final String field;
	private final String bind;
	private final Object value;
	
	public AccurateSearchRequest(String field, String bind, Object value){
		this.field = field;
		this.bind = bind;
		this.value = value;
	}

	public String getField() {
		return this.field;
	}
	
	public String getBind(){
		return this.bind;
	}
	
	public Object getValue(){
		return this.value;
	}

}
