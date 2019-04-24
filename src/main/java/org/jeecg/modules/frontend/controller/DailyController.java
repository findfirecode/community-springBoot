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
import org.jeecg.modules.frontend.entity.Daily;
import org.jeecg.modules.frontend.service.IDailyService;

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
 * @Description: 生活常用品
 * @author： jeecg-boot
 * @date：   2019-04-21
 * @version： V1.0
 */
@RestController
@RequestMapping("/frontend/daily")
@Slf4j
public class DailyController {
	@Autowired
	private IDailyService dailyService;
	
	/**
	  * 分页列表查询
	 * @param daily
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Page<Map<String, Object>> queryPageList(@RequestParam(name="daily_id", defaultValue="-1") String daily_id,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Page<Map<String,Object>> pages = new Page(pageNo, pageSize);
		Page<Map<String, Object>> pageList = dailyService.getDailyUser(pages);
		return pageList;
	}

	/**
	  *   添加
	 * @param daily
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Daily> add(@RequestBody Daily daily) {
		Result<Daily> result = new Result<Daily>();
		try {
			dailyService.save(daily);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param daily
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Daily> edit(@RequestBody Daily daily) {
		Result<Daily> result = new Result<Daily>();
		Daily dailyEntity = dailyService.getById(daily.getDailyId());
		if(dailyEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = dailyService.updateById(daily);
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
	public Result<Daily> delete(@RequestParam(name="id",required=true) String id) {
		Result<Daily> result = new Result<Daily>();
		Daily daily = dailyService.getById(id);
		if(daily==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = dailyService.removeById(id);
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
	public Result<Daily> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Daily> result = new Result<Daily>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.dailyService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Map<String, Object> queryById(@RequestParam(name="id",required=true) String id) {
		Map<String, Object> result = dailyService.getDailyById(id);
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
      QueryWrapper<Daily> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Daily daily = JSON.parseObject(deString, Daily.class);
              queryWrapper = QueryGenerator.initQueryWrapper(daily, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Daily> pageList = dailyService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "生活常用品列表");
      mv.addObject(NormalExcelConstants.CLASS, Daily.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("生活常用品列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Daily> listDailys = ExcelImportUtil.importExcel(file.getInputStream(), Daily.class, params);
              for (Daily dailyExcel : listDailys) {
                  dailyService.save(dailyExcel);
              }
              return Result.ok("文件导入成功！数据行数：" + listDailys.size());
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
