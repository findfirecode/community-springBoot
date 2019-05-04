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
import org.jeecg.modules.frontend.entity.Collect;
import org.jeecg.modules.frontend.service.ICollectService;

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
 * @Description: 收藏
 * @author： jeecg-boot
 * @date：   2019-05-02
 * @version： V1.0
 */
@RestController
@RequestMapping("/frontend/collect")
@Slf4j
public class CollectController {
	@Autowired
	private ICollectService collectService;

	 private static final Logger log = LoggerFactory.getLogger(AutoLogAspect.class);
	
	/**
	  * 分页列表查询
	 * @param collect
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<Collect>> queryPageList(Collect collect,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Collect>> result = new Result<IPage<Collect>>();
		QueryWrapper<Collect> queryWrapper = QueryGenerator.initQueryWrapper(collect, req.getParameterMap());
		Page<Collect> page = new Page<Collect>(pageNo, pageSize);
		IPage<Collect> pageList = collectService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param collect
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Collect> add(@RequestBody Collect collect) {
		Result<Collect> result = new Result<Collect>();
		try {
			collectService.save(collect);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param collect
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Collect> edit(@RequestBody Collect collect) {
		Result<Collect> result = new Result<Collect>();
		Collect collectEntity = collectService.getById(collect.getCollectId());
		if(collectEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = collectService.updateById(collect);
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
	public Result<Collect> delete(@RequestParam(name="id",required=true) String id) {
		Result<Collect> result = new Result<Collect>();
		Collect collect = collectService.getById(id);
		if(collect==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = collectService.removeById(id);
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
	public Result<Collect> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Collect> result = new Result<Collect>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.collectService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<Collect> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Collect> result = new Result<Collect>();
		Collect collect = collectService.getById(id);
		if(collect==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(collect);
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
      QueryWrapper<Collect> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Collect collect = JSON.parseObject(deString, Collect.class);
              queryWrapper = QueryGenerator.initQueryWrapper(collect, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Collect> pageList = collectService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "收藏列表");
      mv.addObject(NormalExcelConstants.CLASS, Collect.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("收藏列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Collect> listCollects = ExcelImportUtil.importExcel(file.getInputStream(), Collect.class, params);
              for (Collect collectExcel : listCollects) {
                  collectService.save(collectExcel);
              }
              return Result.ok("文件导入成功！数据行数：" + listCollects.size());
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
