package com.ylb.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/upload")
public class UploadFile {

	private static String HEADER_DIR = "header";// 头像，包括群头像
	private static String OFFICIAL_ACCOUNT_DIR = "oaheader";// 头像，包括群头像
	private static String TMP = "tmp";// 其他上传文件
	private static String MEDIA = "media";// 其他上传文件
	private static String TOPIC = "topic";// 其他上传文件
	// private static String SERVER_BASE_DIR ="D:\\project\\server\\";
	private static String SERVER_BASE_DIR = "/usr/java/apache-tomcat-8.5.40/webapps/upload/";
	private static String BASE_URL = "http://file.gycdn1.com:2086/upload/";

	/**
	 * 上传公众号头像图片
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadOfficialAccountHeader", method = RequestMethod.POST)
	public String uploadHeader1(@RequestParam(value = "file", required = false) MultipartFile file,
			InputStream inputStream, HttpServletRequest request) {
		System.out.println("uploadOfficialAccountHeader:" + file.getName() + "/" + file.getSize());
		return uploadFile(file, request, OFFICIAL_ACCOUNT_DIR, ".jpg");
	}
	
	/**
	 * 上传图片
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadTopicPictrue", method = RequestMethod.POST)
	public String uploadTopicPictrue(@RequestParam(value = "file", required = false) MultipartFile file,
			InputStream inputStream, HttpServletRequest request) {
//	public String uploadHeader(@RequestParam("file") CommonsMultipartFile file, InputStream inputStream,
//			HttpServletRequest request) {
		System.out.println("uploadHeader:" + file.getName() + "/" + file.getSize());
		return uploadFile(file, request, TOPIC, ".jpg");
	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadHeader", method = RequestMethod.POST)
	public String uploadHeader(@RequestParam(value = "file", required = false) MultipartFile file,
			InputStream inputStream, HttpServletRequest request) {
//	public String uploadHeader(@RequestParam("file") CommonsMultipartFile file, InputStream inputStream,
//			HttpServletRequest request) {
		System.out.println("uploadHeader:" + file.getName() + "/" + file.getSize());
		return uploadFile(file, request, HEADER_DIR, ".jpg");
	}
	

	/**
	 * 上传声音
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadVoice", method = RequestMethod.POST)
	public String uploadVoice(@RequestParam(value = "file", required = false) MultipartFile file,
			InputStream inputStream, HttpServletRequest request) {
//	public String uploadHeader(@RequestParam("file") CommonsMultipartFile file, InputStream inputStream,
//			HttpServletRequest request) {
		System.out.println("uploadVoice:" + file.getName() + "/" + file.getSize());
		return uploadFile(file, request, MEDIA, ".m4a");
	}

	/**
	 * 上传声音
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, InputStream inputStream,
			HttpServletRequest request) {
//	public String uploadHeader(@RequestParam("file") CommonsMultipartFile file, InputStream inputStream,
//			HttpServletRequest request) {
		System.out.println("upload:" + file.getName() + "/" + file.getSize());
		return uploadFile(file, request, TMP, "");
	}

	private String uploadFile(MultipartFile file, HttpServletRequest request, String uploadDir, String subffix) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		int code = 0;
		String msg = "上传失败";
		String url = "";

		if (file == null) {
			code = 1;
			return JsonUtil.buildObjectJson(code, "file is null", url);
		}
		if (file.getSize() > 50*1024*1024) { // 50M
			code = 2;
			return JsonUtil.buildObjectJson(code, "file is too size beyong 5M", url);
		}
		System.out.println("uploadFile:" + file.getName() + "/" + file.getSize());

		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		try {
			String subffix1 = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			if (null != subffix1) {
				subffix = subffix1;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("exception:get file subffix ");
		}
//				file.getOriginalFilename().length());
		// String subffix=".jpg";
		String filename = format.format(now) + random + subffix;// 文件名
		// String path = request.getServletContext().getRealPath("/") +uploadDir+"/"
		// +filename;// 文件所在盘路径
		String path = SERVER_BASE_DIR + uploadDir + "/" + filename;// 文件所在盘路径

		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		url = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/" + filename;
		url = BASE_URL + uploadDir + "/" + filename;

		File oldFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		try {
			file.transferTo(oldFile);
			msg = "success";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			code = 3;
			return JsonUtil.buildObjectJson(code, "fail to save file for IllegalStateException", url);
		} catch (IOException e) {
			code = 4;
			e.printStackTrace();
			return JsonUtil.buildObjectJson(code, "fail to save file for IOException", url);
		}
		System.out.println("url:" + url);
		return JsonUtil.buildObjectJson(code, msg, url);
	}
}
