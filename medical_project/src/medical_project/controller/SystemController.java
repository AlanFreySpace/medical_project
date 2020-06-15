package medical_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/system")
public class SystemController {
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		return "system/index";
	}
}
