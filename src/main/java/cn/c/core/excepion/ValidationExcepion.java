package cn.c.core.excepion;

/**
 * 
 * @author hz453@126.com
 */
public class ValidationExcepion extends BusinessException {

	private static final long serialVersionUID = 2155182923806210481L;
	
	public ValidationExcepion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidationExcepion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ValidationExcepion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ValidationExcepion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ValidationExcepion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

//	public ValidationExcepion(BindingResult bindingResult) {
//		super("数据验证不通过!");
//		if(bindingResult.hasErrors()) {
//			List<ObjectError> allErrors = bindingResult.getAllErrors();
//			StringBuffer message = new StringBuffer();
//			for(ObjectError error : allErrors) {
//				//System.out.println(error.toString());
//				message.append(error.toString()).append('\r').append('\n');
//			}
//			this.initCause(new ValidationExcepion(message.toString()));
//		}
//		
//	}

	

}
