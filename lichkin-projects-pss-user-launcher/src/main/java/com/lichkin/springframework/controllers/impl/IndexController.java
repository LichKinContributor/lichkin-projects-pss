package com.lichkin.springframework.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lichkin.framework.defines.LKFrameworkStatics;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping(value = { "/index", "/index" + LKFrameworkStatics.WEB_MAPPING_PAGES })
	public ModelAndView toIndex() {
		return new ModelAndView("redirect:/user/index" + LKFrameworkStatics.WEB_MAPPING_PAGES);
	}

}
