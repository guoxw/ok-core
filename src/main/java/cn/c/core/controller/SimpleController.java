package cn.c.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.c.core.domain.IdEntity;
import cn.c.core.service.SimpleService;

/**
 * 
 * @author hz453@126.com
 */
public abstract class SimpleController<T extends IdEntity, S extends SimpleService<T>> {
	private static final String WEB_PREFIX = "/web";
	public static final String URI_DIRECTOR_KEY = "uriDirector";
	
	public static final String LIST_PATH = "/list";
	public static final String FORM_PATH = "/form";
	public static final String VIEW_PATH = "/view";
	
	
	private S service;
	private HttpServletRequest httpServletRequest;
	
	public S getService() {
		return service;
	}
	@Autowired
	public void setService(S service) {
		this.service = service;
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	@Autowired
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model) {
		String message = this.getService().sayHello();
		model.addAttribute("message", message);
		return this.to(FORM_PATH);
	}
	
	public void addAttribute(String key, Object object) {
		this.httpServletRequest.setAttribute(key, object);
	}
	
	protected String getUriDirector(){
		String uriDirector = "";
		RequestMapping requestMapping = (RequestMapping)AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
        if(requestMapping != null && requestMapping.value().length > 0) {
        	uriDirector = requestMapping.value()[0];
        }
        this.addAttribute(URI_DIRECTOR_KEY, uriDirector);
        return uriDirector;
	}
	protected String getPathDirector(){
		return this.getUriDirector();
	}
	protected String getPrefix(){
		return WEB_PREFIX;
	}
	protected String to(String path){
		return this.getPrefix() + this.getPathDirector() + path;
	}
	protected String redirectTo(String path){
		if(path.startsWith("/") || path.startsWith("\\")) {
			return "redirect:" + path;
		}
		return "redirect:" + this.getUriDirector() + "/" + path;
	}
}
