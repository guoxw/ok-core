package cn.c.core.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.c.core.common.constant.PaginationType;
import cn.c.core.domain.IdEntity;
import cn.c.core.excepion.ValidationExcepion;
import cn.c.core.service.CrudService;
import cn.c.core.util.EntityUtils;
import cn.c.core.util.StringUtils;

/**
 * 
 * @author hz453@126.com
 */
public abstract class CrudController<T extends IdEntity, S extends CrudService<T>> extends SimpleController<T, S> {


//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(ModelAndView modelAndView) {
//		modelAndView.setViewName(this.getPrefix() + this.getUriDirector() + LIST_PATH);
//		Iterable<T> items = this.getService().findAll();
//		this.initList(items);
//		this.initList(items, modelAndView);
//		modelAndView.addObject("items", items);
//		return modelAndView;
//	}
	
	/**
	 * 分页+查询 
	 * @param pageable url看起来像这样的:  .../page?size=3&page=2&sort=name,desc
	 * @param paginationType 分页方式
	 * @param keyword url看起来像这样的:  .../page?keyword=aaa bbb ccc
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = {"/list", "/page"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView list(Pageable pageable, 
			@RequestParam(required = false, defaultValue=PaginationType.BACKSTAGE_PAGINATION+"") int paginationType,
			@RequestParam(required = false, defaultValue="") String keyword,
			@RequestParam(required = false, defaultValue="") String search,
			ModelAndView modelAndView) {
		modelAndView.setViewName(this.to(LIST_PATH));
		modelAndView.addObject("paginationType", paginationType);
		if(StringUtils.isNullOrEmpty(keyword)) {
			keyword = search;
		}
		if(!StringUtils.isNullOrEmpty(keyword)) {
			
			// TODO 此处因为使用的容器是tomcat且tomcat的Connector的URIEconding是ISO-8859-1
			if("GET".equals(this.getHttpServletRequest().getMethod().toUpperCase())) {
				try {
					keyword = new String(keyword.getBytes("ISO-8859-1"), this.getHttpServletRequest().getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			modelAndView.addObject("keyword", "keyword=" + keyword);
		}
		if(pageable.getPageNumber()>0) {
			pageable = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
		}
		Iterable<T> items = this.getService().getItems(keyword, paginationType, pageable);
		
		this.initList(items);
		this.initList(items, modelAndView);
		modelAndView.addObject("items", items);
		return modelAndView;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView create(@ModelAttribute T t, ModelAndView modelAndView) {
		modelAndView.setViewName(this.to(FORM_PATH));
		this.initForm(t);
		this.initForm(t, modelAndView);
		modelAndView.addObject("entity", t);
		return modelAndView;
	}

	
//	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
//	public String load(@PathVariable("id") String idString, @ModelAttribute V dto) {
//		if (idString != null && idString.matches("^\\d+$")) {
//			T t = this.simpleService.load(Integer.parseInt(idString));
//			EntityUtils.copy(dto, t);
//		}
//		return this.getUriDirector() + "/load";
//	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id, ModelAndView modelAndView) {
		modelAndView.addObject("operation", "edit");
		return this.load(id, modelAndView);
	}
	
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") Long id, ModelAndView modelAndView) {
		modelAndView.addObject("operation", "show");
		return this.load(id, modelAndView);
	}
	
	public ModelAndView load(Long id, ModelAndView modelAndView) {
		modelAndView.setViewName(this.to(FORM_PATH));
		T t = this.getService().findOne(id);
		this.initForm(t);
		this.initForm(t, modelAndView);
		modelAndView.addObject("entity", t);
		return modelAndView;
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Long id, ModelAndView modelAndView) {
		modelAndView.setViewName(this.to(VIEW_PATH));
		T t = this.getService().findOne(id);
		this.initView(t, modelAndView);
		modelAndView.addObject("entity", t);
		return modelAndView;
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("entity") T t, BindingResult br, ModelAndView modelAndView) {
		modelAndView.setViewName(this.redirectTo("/edit/"+t.getId()));
		if (br.hasErrors()) {
			modelAndView.setViewName(this.to(FORM_PATH));
			new ValidationExcepion(this.getAllErrorToString(br)).printStackTrace();
			return modelAndView;
		}
		this.beforeSave(t);
		this.beforeSave(t, modelAndView);
		this.getService().save(t);
		this.afterSave(t);
		this.afterSave(t, modelAndView);
		return modelAndView;
	}
	
//	@RequestMapping(value = "/list/ajax", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> listByAjax() {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Iterable<T> items = this.getService().findAll();
//		this.initList(items);
//		this.initList(items, resultMap);
//		if(!resultMap.containsKey("items")) {
//			resultMap.put("items", items);
//		}
//		return resultMap;
//	}
	
	@RequestMapping(value = {"/list/ajax", "/page/ajax"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listByAjax(Pageable pageable, 
			@RequestParam(required = false, defaultValue=PaginationType.BACKSTAGE_PAGINATION+"") int paginationType,
			@RequestParam(required = false, defaultValue="") String keyword,
			@RequestParam(required = false, defaultValue="") String search) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("paginationType", paginationType);
		if(StringUtils.isNullOrEmpty(keyword)) {
			keyword = search;
		}
		if(!StringUtils.isNullOrEmpty(keyword)) {
			
			// TODO 此处因为使用的容器是tomcat且tomcat的Connector的URIEconding是ISO-8859-1
			if("GET".equals(this.getHttpServletRequest().getMethod().toUpperCase())) {
				try {
					keyword = new String(keyword.getBytes("ISO-8859-1"), this.getHttpServletRequest().getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			resultMap.put("keyword", keyword);
		}
		Iterable<T> items = this.getService().getItems(keyword, paginationType, pageable);
		
		this.initList(items);
		this.initList(items, resultMap);
		if(!resultMap.containsKey("items")) {
			resultMap.put("items", items);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/load/ajax/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadByAjax(@PathVariable("id") Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		T t = this.getService().findOne(id);
		this.initForm(t);
		this.initForm(t, resultMap);
		if(!resultMap.containsKey("entity")) {
			resultMap.put("entity", t);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/save/ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveByAjax(@Valid @ModelAttribute T t, BindingResult br) {
		if (br.hasErrors()) {
			if(br.hasFieldErrors()) {
				throw new ValidationExcepion(this.getFieldErrorToString(br), new ValidationExcepion(this.getAllErrorToString(br)));
			} else {
				throw new ValidationExcepion(this.getAllErrorToString(br));
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.beforeSave(t);
		this.beforeSave(t, resultMap);
		this.getService().save(t);
		this.afterSave(t);
		this.afterSave(t, resultMap);
		if(!resultMap.containsKey("id")) {
			resultMap.put("id", t.getId());
		}
		if(!resultMap.containsKey("message")) {
			resultMap.put("message", "保存成功!");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/update/ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateByAjax(@RequestBody Map<String, Object> submitMap, BindingResult br) {
		// TODO 现在只支持普通属性修改
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long id = Long.parseLong(String.valueOf(submitMap.get("id")));
		submitMap.remove("id");
		T t = this.getService().findOne(id);
		
		for(String key : submitMap.keySet()) {
			Object o = submitMap.get(key);
			if(o instanceof Map) {
				continue;
			} else if(o instanceof Iterable) {
				continue;
			} else {
				Method method = EntityUtils.getSeterMethod(t.getClass(), key, true);
				EntityUtils.invoke(t, method, submitMap.get(key));
				//m.invoke(t, submitMap.get(key));	//反射
			}
		}
		// TODO 验证!!
		
		this.beforeSave(t);
		this.beforeSave(t, resultMap);
		this.getService().save(t);
		this.afterSave(t);
		this.afterSave(t, resultMap);
		if(!resultMap.containsKey("message")) {
			resultMap.put("message", "更新成功!");
		}
		return resultMap;
	}

	@RequestMapping(value = "/delete/ajax/{ids}")
	@ResponseBody
	public Map<String, Object> deleteByAjax(@PathVariable("ids") String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		this.beforeDelete(ids, resultMap);
		this.getService().delete(ids);
		this.afterDelete(ids, resultMap);
		if(!resultMap.containsKey("message")) {
			resultMap.put("message", "删除成功!");
		}
		return resultMap;
	}
	
	protected String getAllErrorToString(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			StringBuffer message = new StringBuffer();
			for(ObjectError error : allErrors) {
				//System.out.println(error.toString());
				message.append(error.toString()).append('\r').append('\n');
			}
			return message.toString();
		}
		return "";
	}
	
	protected String getFieldErrorToString(BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			StringBuffer message = new StringBuffer("[");
			for(FieldError error : fieldErrors) {
				//System.out.println(error.toString());
				message.append('{').append(error.getField()).append(": ").append(error.getDefaultMessage()).append('}');
			}
			message.append("]");
	
			return message.toString().replaceAll("\\}\\{", "},\\\\r\\\\n\\{");
		}
		return "";
	}
	
	public void initList(Iterable<T> items) {}
	public void initList(Iterable<T> items, ModelAndView modelAndView) {}
	public void initList(Iterable<T> items, Map<String, Object> resultMap) {}
	public void initForm(T t) {}
	public void initForm(T t, ModelAndView modelAndView) {}
	public void initForm(T t, Map<String, Object> resultMap) {}
	public void initView(T t, ModelAndView modelAndView) {}
	

	public void beforeSave(T t) {}
	public void beforeSave(T t, ModelAndView modelAndView) {}
	public void beforeSave(T t, Map<String, Object> resultMap) {}
	public void afterSave(T t) {}
	public void afterSave(T t, ModelAndView modelAndView) {}
	public void afterSave(T t, Map<String, Object> resultMap) {}
	
	public void beforeDelete(String ids, Map<String, Object> resultMap) {}
	public void afterDelete(String ids, Map<String, Object> resultMap) {}
	

}
