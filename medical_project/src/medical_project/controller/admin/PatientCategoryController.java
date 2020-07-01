package medical_project.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import medical_project.entity.common.PatientCategory;
import medical_project.page.admin.Page;
import medical_project.service.common.PatientCategoryService;

@RequestMapping("/admin/patient_category")
@Controller
public class PatientCategoryController {
	
	
	@Autowired
	private PatientCategoryService patientCategoryService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("patient_category/list");
		return model;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="name",defaultValue = "")String name,
			Page page) {
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize",page.getRows());
		ret.put("rows", patientCategoryService.findList(queryMap));
		ret.put("total", patientCategoryService.getTotal(queryMap));
		return ret;
	}
	
	
	@RequestMapping(value="/tree_list",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> treeList() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		return getTreeCategory(patientCategoryService.findList(queryMap));
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(PatientCategory patientCategory) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(patientCategory==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的分类信息！");
			return ret;
		}
		if(StringUtils.isEmpty(patientCategory.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写分类名称！");
			return ret;
		}
		if(patientCategory.getParentId()!=null) {
			PatientCategory patientCategoryParent = patientCategoryService.findById(patientCategory.getParentId());
			if(patientCategoryParent!=null) {
				String tags="";
				if(patientCategoryParent.getTags()!=null) {
					tags+=patientCategoryParent.getTags()+",";
				}
				patientCategory.setTags(tags+patientCategory.getParentId());
			}
			
		}
		if(patientCategoryService.add(patientCategory)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(PatientCategory patientCategory) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(patientCategory==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的分类信息！");
			return ret;
		}
		if(StringUtils.isEmpty(patientCategory.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写分类名称！");
			return ret;
		}
		if(patientCategory.getParentId()!=null) {
			PatientCategory patientCategoryParent = patientCategoryService.findById(patientCategory.getParentId());
			if(patientCategoryParent!=null) {
				String tags="";
				if(patientCategoryParent.getTags()!=null) {
					tags+=patientCategoryParent.getTags()+",";
				}
				patientCategory.setTags(tags+patientCategory.getParentId());
			}
			
		}
		if(patientCategoryService.edit(patientCategory)<=0) {
			ret.put("type", "error");
			ret.put("msg", "编辑失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "编辑成功！");
		return ret;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Long id) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(id==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写要删除的分类！");
			return ret;
		}
		try {
			if(patientCategoryService.delete(id)<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该分类下存在分类信息，不允许删除！");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
	
	private List<Map<String, Object>> getTreeCategory(List<PatientCategory> patientCategoryList){
		List<Map<String, Object>> ret=new ArrayList<Map<String,Object>>();
		for(PatientCategory patientCategory:patientCategoryList) {
			if(patientCategory.getParentId()==null) {
				Map<String, Object> top=new HashMap<String, Object>();
				top.put("id", patientCategory.getId());
				top.put("text", patientCategory.getName());
				top.put("children", new ArrayList<Map<String, Object>>());
				ret.add(top);
			}
		}
		for(PatientCategory patientCategory:patientCategoryList) {
			if(patientCategory.getParentId()!=null) {
				for(Map<String,Object> map:ret) {
					if(patientCategory.getParentId().longValue()==Long.valueOf(map.get("id")+"")) {
						List children=(List)map.get("children");
						Map<String, Object> child=new HashMap<String, Object>();
						child.put("id", patientCategory.getId());
						child.put("text", patientCategory.getName());
						children.add(child);
					}
				}
			}
		}
		return ret;
	}
}
