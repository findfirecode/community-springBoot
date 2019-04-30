package org.jeecg.common.system.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.frontend.entity.Resource;
import org.jeecg.modules.frontend.service.IResourceService;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.jeecg.common.util.UUIDGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/common")
public class CommonController {

	@Value(value = "${jeecg.path.upload}")
	private String uploadpath;

	@Autowired
	private IResourceService resourceService;

	@PostMapping(value = "/upload")
	public Result<SysUser> upload(HttpServletRequest request, HttpServletResponse response,
								  @RequestParam(name="typeFolder", defaultValue="default") String typeFolder,
								  @RequestParam(name="type") String type,
								  @RequestParam(name="belong_id") String belong_id
								  ) {
		Result<SysUser> result = new Result<>();
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = typeFolder;
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
			File file = new File(ctxPath + File.separator + bizPath + File.separator + belong_id);
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
			String orgName = mf.getOriginalFilename();// 获取文件名
			fileName = type +"_"+orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
			String savePath = file.getPath() + File.separator + fileName;
			File savefile = new File(savePath);
			FileCopyUtils.copy(mf.getBytes(), savefile);
			String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}
			result.setMessage(dbpath);
			result.setSuccess(true);

//			存入数据库
			Resource r = new Resource();
			r.setType(type);
			r.setUrl(typeFolder+"/"+belong_id+"/"+fileName);
			r.setBelong_id(belong_id);
			resourceService.save(r);
		} catch (IOException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@PostMapping(value = "/uploadMultipart")
	public Result<SysUser> uploadMultipart(@RequestParam("files") MultipartFile[] files,
										   @RequestParam(name="typeFolder", defaultValue="default") String typeFolder,
										   @RequestParam(name="type") String type,
										   @RequestParam(name="belong_id") String belong_id) {
		Result<SysUser> result = new Result<>();
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = typeFolder;
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
			File file = new File(ctxPath + File.separator + bizPath + File.separator + belong_id);
			File savefile = null;
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			for (int i = 0; i < files.length; i++) {
				String orgName = files[i].getOriginalFilename();// 获取文件名
				fileName = type+"_"+orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
				String savePath = file.getPath() + File.separator + fileName;
				savefile = new File(savePath);
				FileCopyUtils.copy(files[i].getBytes(), savefile);
				String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
				if (dbpath.contains("\\")) {
					dbpath = dbpath.replace("\\", "/");
				}
				result.setMessage(dbpath);
				result.setSuccess(true);

				//			存入数据库
				Resource r = new Resource();
				r.setType(type);
				r.setUrl(typeFolder+"/"+belong_id+"/"+fileName);
				r.setBelong_id(belong_id);
				resourceService.save(r);
			}

		} catch (IOException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping(value = "/uuid")
	public String getUUID(){
		return UUIDGenerator.generate();
	}
}