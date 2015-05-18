package cn.c.core.common.constant;

/**
 * 
 * @author hz453@126.com
 */
public class PaginationType {
	/**
	 * 不分页
	 */
	public static final int NONE = 0;
	/**
	 * 前台分页
	 */
	public static final int RECEPTION_PAGINATION = 1;
	/**
	 * 后台分页
	 */
	public static final int BACKSTAGE_PAGINATION = 2;
	
	public static String getPaginationText(int type) {
		String text = null;
		switch(type){
			case 0:
				text = "不分页";
				break;
			case 1:
				text = "前台分页";
				break;
			case 2:
				text = "后台分页";
				break;
			default: 
				text ="";
		}
		return text;
	}
}
