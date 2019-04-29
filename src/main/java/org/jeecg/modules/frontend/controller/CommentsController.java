package org.jeecg.modules.frontend.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.AutoLogAspect;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.frontend.entity.Comments;
import org.jeecg.modules.frontend.service.ICommentsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Title: Controller
 * @Description: 评论
 * @author： jeecg-boot
 * @date：   2019-04-17
 * @version： V1.0
 */
@RestController
@RequestMapping("/frontend/comments")
@Slf4j
public class CommentsController {
	 private static final Logger log = LoggerFactory.getLogger(AutoLogAspect.class);

	@Autowired
	private ICommentsService commentsService;
	
	/**
	  * 分页列表查询
	 * @param comments
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Page<Map<String, Object>> queryPageList(Comments comments,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										@RequestParam(name="contentCondition", defaultValue="%") String contentCondition,
										@RequestParam(name="parent_id", defaultValue="-1") String parent_id,
									  HttpServletRequest req) {
		Page<Map<String,Object>> pages = new Page(pageNo, pageSize);
		Page<Map<String, Object>> list=commentsService.getCommentsUser(pages, contentCondition, parent_id);
		return list;
	}

	@GetMapping(value = "/total")
	public long getTotal(){
		QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
		queryWrapper
				.eq("parent_id","-1");
		long conut  = (long)commentsService.count(queryWrapper);
		return conut;
	}
	/**
	  *   添加
	 * @param comments
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Comments> add(@RequestBody Comments comments) {
		Result<Comments> result = new Result<Comments>();
		try {
			comments.setIsdisplay(0);
			comments.setIsreply(0);
			comments.setType("论坛");
			comments.setUserId("111");
			commentsService.save(comments);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
//			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param comments
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Comments> edit(@RequestBody Comments comments) {
		Result<Comments> result = new Result<Comments>();
		Comments commentsEntity = commentsService.getById(comments.getCommentsId());
		if(commentsEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = commentsService.updateById(comments);
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
	public Result<Comments> delete(@RequestParam(name="id",required=true) String id) {
		Result<Comments> result = new Result<Comments>();
		Comments comments = commentsService.getById(id);
		if(comments==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = commentsService.removeById(id);
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
	public Result<Comments> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Comments> result = new Result<Comments>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.commentsService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<Comments> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Comments> result = new Result<Comments>();
		Comments comments = commentsService.getById(id);
		if(comments==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(comments);
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
      QueryWrapper<Comments> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Comments comments = JSON.parseObject(deString, Comments.class);
              queryWrapper = QueryGenerator.initQueryWrapper(comments, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Comments> pageList = commentsService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "评论列表");
      mv.addObject(NormalExcelConstants.CLASS, Comments.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("评论列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Comments> listCommentss = ExcelImportUtil.importExcel(file.getInputStream(), Comments.class, params);
              for (Comments commentsExcel : listCommentss) {
                  commentsService.save(commentsExcel);
              }
              return Result.ok("文件导入成功！数据行数：" + listCommentss.size());
          } catch (Exception e) {
              log.error(e.getMessage());
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
