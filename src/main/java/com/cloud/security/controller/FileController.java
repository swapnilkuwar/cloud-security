package com.cloud.security.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.security.service.FileService;

@Controller
@RequestMapping(value="files")
public class FileController {
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="upload", method = RequestMethod.GET)
	public ModelAndView uploadFile(){
		ModelAndView view =new ModelAndView("upload");
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST ,value = "upload/status")
	public @ResponseBody
	String uploadFileHandler1(@RequestParam("dataset") int dataset_id,
			@RequestParam MultipartFile file) {
		return fileService.addFile(dataset_id, file);
	}
	
	@RequestMapping(value="/download/{type}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") int file_id) throws IOException {
		File file = new File(fileService.getFile(file_id).getFilepath());
         
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}