package medical_project.controller.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import medical_project.entity.common.Patient;
import medical_project.entity.common.PatientCategory;
import medical_project.page.admin.Page;
import medical_project.service.common.PatientCategoryService;
import medical_project.service.common.PatientService;
import net.sf.json.JSONArray;

@RequestMapping("/admin/patient")
@Controller
public class PatientController {
	
	
	@Autowired
	private PatientCategoryService patientCategoryService;
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("patient/list");
		model.addObject("patientCategoryList",JSONArray.fromObject(patientCategoryService.findList(new HashMap<String, Object>())));
		return model;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add(ModelAndView model) {
		model.setViewName("patient/add");
		return model;
	}
	
	@RequestMapping(value="/pic")
	@ResponseBody
	public ModelAndView pic(
			@RequestParam(name="userpic",required = true)String userpic,
			@RequestParam(name="id",required=true)Integer id,
			ModelAndView model,HttpServletRequest request) {
		model.addObject("userpic",userpic);
		model.addObject("id",id);
		model.setViewName("patient/pic");
		return model;
	}
	
	@RequestMapping(value="/picdone",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> picdone(Patient patient) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(patient==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的患者信息！");
			return ret;
		}
		if(StringUtils.isEmpty(patient.getContent())) {
			ret.put("type", "error");
			ret.put("msg", "请填写病历！");
			return ret;
		}
		System.out.println(patient.getContent());
		
		Process proc;
		String bingli=patient.getContent();
		try {
			String[] args1=new String[] {"python","C:\\Users\\youwd\\Desktop\\project\\medical_entity_recognize-master\\medical_entity_recognize-master\\main.py","--train",bingli};
			proc=Runtime.getRuntime().exec(args1);
			BufferedReader reader=new BufferedReader(new InputStreamReader(proc.getInputStream(),"gb2312"));
			BufferedReader reader1=new BufferedReader(new InputStreamReader(proc.getErrorStream(),"gb2312"));
			String line=null;
			String line1=null;
			String picString=null;
			while((line=reader.readLine())!=null) {
				System.out.println(line);
				picString+=line;
			}
			reader.close();
			while((line1=reader1.readLine())!=null) {
				System.out.println(line1);
			}
			reader1.close();
			int result =proc.waitFor();
			System.out.println("执行结果："+result);
			System.out.println(picString);
			Vector<String> vec=new Vector<String>();
			for(int i=0;i<picString.length()-30;i++) {
				if(picString.substring(i, i+4).equals("type")) {
					
					if(picString.substring(i+8, i+11).equals("SYM")) {
						int j=1;
						while(true) {
							String a=picString.substring(i+j+23,i+j+24);
							if(a.equals("'")) {
								vec.add("症状:"+picString.substring(i+23,i+j+23));
								break;
							}
							j++;
						}
					}	
					
					if(picString.substring(i+8, i+11).equals("REG")) {
						int j=1;
						while(true) {
							String a=picString.substring(i+j+23,i+j+24);
							if(a.equals("'")) {
								vec.add("器官:"+picString.substring(i+23,i+j+23));
								break;
							}
							j++;
						}
					}
					
					if(picString.substring(i+8, i+11).equals("TES")) {
						int j=1;
						while(true) {
							String a=picString.substring(i+j+23,i+j+24);
							if(a.equals("'")) {
								vec.add("检查:"+picString.substring(i+23,i+j+23));
								break;
							}
							j++;
						}
					}	
					
				}
			}
			ret.put("userpic", vec);
			for(int i=0;i<vec.size();i++)
				System.out.println(vec.get(i));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		ret.put("type", "success");
		ret.put("msg", "生成画像成功！");
		return ret;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public ModelAndView edit(ModelAndView model,Long id) {
		model.setViewName("patient/edit");
		model.addObject("patient",patientService.findById(id));
		return model;
	}
	
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="name",defaultValue = "")String name,
			@RequestParam(name="patientCategoryId",required=false)Long productCategoryId,
			Page page) {
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("name", name);
		if(productCategoryId!=null) {
			queryMap.put("tags", productCategoryId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize",page.getRows());
		ret.put("rows", patientService.findList(queryMap));
		ret.put("total", patientService.getTotal(queryMap));
		return ret;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Patient patient) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(patient==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的患者信息！");
			return ret;
		}
		if(StringUtils.isEmpty(patient.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写姓名！");
			return ret;
		}
		if(patient.getPatientCategoryId()==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写患者分类！");
			return ret;
		}
		if(StringUtils.isEmpty(patient.getImageUrl())) {
			ret.put("type", "error");
			ret.put("msg", "请上传患者照片！");
			return ret;
		}
		PatientCategory patientCategory = patientCategoryService.findById(patient.getPatientCategoryId());
		patient.setTags(patientCategory.getTags()+","+patientCategory.getId());
		patient.setCreateTime(new Date());
		if(patientService.add(patient)<=0) {
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
	public Map<String, Object> edit(Patient patient) {
		Map<String, Object> ret=new HashMap<String, Object>();
		if(patient==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写正确的患者信息！");
			return ret;
		}
		if(StringUtils.isEmpty(patient.getName())) {
			ret.put("type", "error");
			ret.put("msg", "请填写姓名！");
			return ret;
		}
		if(patient.getPatientCategoryId()==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写患者分类！");
			return ret;
		}
		if(StringUtils.isEmpty(patient.getImageUrl())) {
			ret.put("type", "error");
			ret.put("msg", "请上传照片！");
			return ret;
		}
		PatientCategory patientCategory = patientCategoryService.findById(patient.getPatientCategoryId());
		patient.setTags(patientCategory.getTags()+","+patientCategory.getId());
		patient.setCreateTime(new Date());
		if(patientService.edit(patient)<=0) {
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
			ret.put("msg", "请选择要删除的患者！");
			return ret;
		}
		try {
			if(patientService.delete(id)<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			//ret.put("type", "error");
			//ret.put("msg", "该商品下存在订单信息，不允许删除！");
			//return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
	
	@RequestMapping(value="/tree_list",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> treeList() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		return getTreeCategory(patientCategoryService.findList(queryMap));
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
						child.put("children", new ArrayList<Map<String, Object>>());
						children.add(child);
					}
				}
			}
		}
		for(PatientCategory patientCategory:patientCategoryList) {
			if(patientCategory.getParentId()!=null) {
				for(Map<String,Object> map:ret) {
					List<Map<String,Object>> children=(List<Map<String,Object>>)map.get("children");
					for(Map<String,Object> child:children) {
						if(patientCategory.getParentId().longValue()==Long.valueOf(child.get("id")+"")) {
							List grandsons=(List)child.get("children");
							Map<String, Object> grandson=new HashMap<String, Object>();
							grandson.put("id", patientCategory.getId());
							grandson.put("text", patientCategory.getName());
							grandsons.add(grandson);
						}
					}

				}
			}
		}
		return ret;
	}
}
