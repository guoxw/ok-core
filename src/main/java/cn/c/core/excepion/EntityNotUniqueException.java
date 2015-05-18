package cn.c.core.excepion;

/**
 * 
 * @author hz453@126.com
 */
public class EntityNotUniqueException extends BusinessException {

	private static final long serialVersionUID = -8505681447362110460L;

	public EntityNotUniqueException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EntityNotUniqueException(String key, String value) {
		super(String.format("提交的数据,%s: %s,重复！", new Object[]{key, value}));
	}

	public EntityNotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EntityNotUniqueException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EntityNotUniqueException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EntityNotUniqueException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
