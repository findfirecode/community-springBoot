package org.jeecg.modules.frontend.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.frontend.entity.Chat;
import org.jeecg.modules.frontend.entity.User;
import org.jeecg.modules.frontend.service.IChatService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.frontend.service.IUserService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Title: Controller
 * @Description: 聊天
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
@RestController
@RequestMapping("/frontend/chat")
@Slf4j
public class ChatController {

	@Autowired
	private IChatService chatService;
	 @Autowired
	 private IUserService userService;
	/**
	  * 分页列表查询
	 * @param chat
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Map<String, Object> queryPageList(Chat chat,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										@RequestParam(name="send_id", required = true) String send_id,
									  @RequestParam(name="recive_id", required = true) String recive_id,
									  HttpServletRequest req) {
		User sendUser = userService.getById(send_id);
		User reciveUser = userService.getById(recive_id);
		Map<String, Object> map = new HashMap<>();
		map.put("send", sendUser);
		map.put("recive", reciveUser);
		map.put("data", chatService.getChatScroll(send_id, recive_id));
		return map;
	}
	
	/**
	  *   添加
	 * @param chat
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Chat> add(@RequestBody Chat chat) {
		Result<Chat> result = new Result<Chat>();
		try {
			chatService.save(chat);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param chat
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Chat> edit(@RequestBody Chat chat) {
		Result<Chat> result = new Result<Chat>();
		Chat chatEntity = chatService.getById(chat.getChatId());
		if(chatEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = chatService.updateById(chat);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<Chat> delete(@RequestParam(name="id",required=true) String id) {
		Result<Chat> result = new Result<Chat>();
		Chat chat = chatService.getById(id);
		if(chat==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = chatService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<Chat> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Chat> result = new Result<Chat>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.chatService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<Chat> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Chat> result = new Result<Chat>();
		Chat chat = chatService.getById(id);
		if(chat==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(chat);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<Chat> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Chat chat = JSON.parseObject(deString, Chat.class);
              queryWrapper = QueryGenerator.initQueryWrapper(chat, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Chat> pageList = chatService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "聊天列表");
      mv.addObject(NormalExcelConstants.CLASS, Chat.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("聊天列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<Chat> listChats = ExcelImportUtil.importExcel(file.getInputStream(), Chat.class, params);
              for (Chat chatExcel : listChats) {
                  chatService.save(chatExcel);
              }
              return Result.ok("文件导入成功！数据行数：" + listChats.size());
          } catch (Exception e) {
              return Result.error("文件导入失败！");
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
