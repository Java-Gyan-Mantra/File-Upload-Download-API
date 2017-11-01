package com.spring.file.io.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.file.io.api.dto.UserDTO;
import com.spring.file.io.api.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	private static String uploadedFolder = "C:/Users/bahota/Desktop/ApplicationDataHouse/";

	@RequestMapping("/uploadFile")
	public String uploadFile(@ModelAttribute UserDTO dto, Model model) {
		String message = "";
		String fileInfo = "";
		MultipartFile file = dto.getFile();
		if (file.isEmpty()) {
			message = "Invalid File";
		}
		try {
			fileInfo = uploadedFolder + file.getOriginalFilename();
			file.transferTo(new File(uploadedFolder + file.getOriginalFilename()));
			service.saveUser(dto.getName(), fileInfo);
			message = "You successfully uploaded '" + file.getOriginalFilename() + "'";
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		model.addAttribute("users", service.getAll());
		return "upload";
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
			throws IOException {
		final int BUFFER_SIZE = 4096;
		// get absolute path of the application
		ServletContext context = request.getServletContext();
		String filePath = service.getSaveFilePathByUser(id);
		// construct the complete absolute path of the file
		String fullPath = filePath;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);
		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}
}
