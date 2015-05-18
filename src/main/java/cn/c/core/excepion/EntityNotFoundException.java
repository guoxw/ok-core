package cn.c.core.excepion;

/**
 * 
 * @author hz453@126.com
 */
public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException() {
		super("请求的数据不存在,或已被删除!!!");
		// TODO Auto-generated constructor stub
	}
	
	public EntityNotFoundException(String key, String value) {
		super(String.format("请求的数据,%s: %s,不存在！", new Object[]{key, value}));
	}

	public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EntityNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -5076018168685836953L;

}
