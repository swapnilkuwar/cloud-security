package com.cloud.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.security.service.DataService;
import com.cloud.security.service.FileService;

@Controller
public class DatasetController {

	@Autowired
	FileService fileService;
	@Autowired
	DataService dataService;
	
	@RequestMapping(value = { "datasets", }, method = RequestMethod.GET)
	public ModelAndView getDataset() {
		ModelAndView view = new ModelAndView("datasets");
		return view;
	}
	
	@RequestMapping(value="datasets/list", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getDatasets(){
		Map<String,Object> map = new HashMap<String,Object>();
			//map.put("status","200");
			map.put("data", fileService.getLoggedInUserDataset());
		return map;
	}
	
	@RequestMapping(value="datasets/filelist", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getMyFiles(){
		Map<String,Object> map = new HashMap<String,Object>();
			//map.put("status","200");
			map.put("data", fileService.getLoggedInUserFiles());
		return map;
	}
	
	@RequestMapping(value="datasets/encAll", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> encAll(@RequestParam("time") int time, String enc_type){
		fileService.encryptLoggedInUserDatasets(time, enc_type);
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("status","200");
			map.put("response", "Encryption Request Sent!!!");
		return map;
	}
	
	@RequestMapping(value="datasets/create", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> createDataset(@RequestParam("weight") int weight){
		dataService.createDataset(weight);
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("status","200");
			map.put("response", "Dataset Created!!!");
		return map;
	}
	
	@RequestMapping(value="datasets/decAll", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> decAll(){
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("status","200");
			map.put("response", "Decrypt All Response is : " + fileService.decryptAllFiles());
		return map;
	}
	
	@RequestMapping(value="datasets/calcenctime", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> calcEncTimeOfUserFiles(){
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("data", dataService.calcEncTime());
		return map;
	}
}