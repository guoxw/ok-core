package cn.c.core.excepion;

/**
 * 方法之间调用时发现参数不满足要求时使用
 * @author hz453@126.com
 */
public class NecessaryParametersNotSatisfiedException extends PlatformException {

	private static final long serialVersionUID = 8790318668666247184L;
	
	public NecessaryParametersNotSatisfiedException(String... key) {
		super("必要参数不能为空: " + key.toString());
	}
	
	public NecessaryParametersNotSatisfiedException() {
		super("必要参数不能为空!!!");
	}

	public NecessaryParametersNotSatisfiedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NecessaryParametersNotSatisfiedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NecessaryParametersNotSatisfiedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NecessaryParametersNotSatisfiedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	

}
