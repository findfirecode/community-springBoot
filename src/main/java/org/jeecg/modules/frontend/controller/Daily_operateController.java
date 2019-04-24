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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.frontend.entity.Daily_operate;
import org.jeecg.modules.frontend.service.IDaily_operateService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 日常类操作
 * @author： jeecg-boot
 * @date：   2019-04-22
 * @version： V1.0
 */
@RestController
@RequestMapping("/frontend/daily_operate")
@Slf4j
public class Daily_operateController {
	@Autowired
	private IDaily_operateService daily_operateService;
	
	/**
	  * 分页列表查询
	 * @param daily_operate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<Daily_operate>> queryPageList(Daily_operate daily_operate,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Daily_operate>> result = new Result<IPage<Daily_operate>>();
		QueryWrapper<Daily_operate> queryWrapper = QueryGenerator.initQueryWrapper(daily_operate, req.getParameterMap());
		Page<Daily_operate> page = new Page<Daily_operate>(pageNo, pageSize);
		IPage<Daily_operate> pageList = daily_operateService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param daily_operate
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Daily_operate> add(@RequestBody Daily_operate daily_operate) {
		Result<Daily_operate> result = new Result<Daily_operate>();
		try {
			daily_operateService.save(daily_operate);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param daily_operate
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Daily_operate> edit(@RequestBody Daily_operate daily_operate) {
		Result<Daily_operate> result = new Result<Daily_operate>();
		Daily_operate daily_operateEntity = daily_operateService.getById(daily_operate.getDailyId());
		if(daily_operateEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = daily_operateService.updateById(daily_operate);
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
	public Result<Daily_operate> delete(@RequestParam(name="id",required=true) String id) {
		Result<Daily_operate> result = new Result<Daily_operate>();
		Daily_operate daily_operate = daily_operateService.getById(id);
		if(daily_operate==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = daily_operateService.removeById(id);
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
	public Result<Daily_operate> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Daily_operate> result = new Result<Daily_operate>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.daily_operateService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<Daily_operate> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Daily_operate> result = new Result<Daily_operate>();
		Daily_operate daily_operate = daily_operateService.getById(id);
		if(daily_operate==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(daily_operate);
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
      QueryWrapper<Daily_operate> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Daily_operate daily_operate = JSON.parseObject(deString, Daily_operate.class);
              queryWrapper = QueryGenerator.initQueryWrapper(daily_operate, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Daily_operate> pageList = daily_operateService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "日常类操作列表");
      mv.addObject(NormalExcelConstants.CLASS, Daily_operate.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("日常类操作列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Daily_operate> listDaily_operates = ExcelImportUtil.importExcel(file.getInputStream(), Daily_operate.class, params);
              for (Daily_operate daily_operateExcel : listDaily_operates) {
                  daily_operateService.save(daily_operateExcel);
              }
              return Result.ok("文件导入成功！数据行数：" + listDaily_operates.size());
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
