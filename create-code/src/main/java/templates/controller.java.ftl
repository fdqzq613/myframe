package ${package.Controller};

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.some.common.result.RespResult;
import com.some.common.utils.AssertUtils;
import com.some.common.utils.IdUtils;
import ${package.Service}.${entity}Service;
import ${package.Entity}.${entity};
import ${package.queryVo}.${entity}QueryVo;
import ${package.queryVo}.${entity}AddVo;
import ${package.queryVo}.${entity}UpdateVo;
import com.some.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@RestController
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
@Api(value = "<#if table.comment?? && table.comment!="">${table.comment}<#else>${table.controllerName}</#if>接口",tags="<#if table.comment?? && table.comment!="">${table.comment}<#else>${table.controllerName}</#if>接口")
@Slf4j
public class ${table.controllerName} extends BaseController {
</#if>
    @Autowired
    private ${entity}Service ${lowEntity}Service;

	@ApiOperation(value = "获取${table.comment}列表", notes = "获取${table.comment}列表", httpMethod = "GET")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public RespResult<IPage<${entity}>>  list(@Validated ${entity}QueryVo ${lowEntity}QueryVo) {
		${entity} ${lowEntity} = new ${entity}();
		copyPropertiesIgnoreNull(${lowEntity}QueryVo,${lowEntity} );
		QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>(${lowEntity});
        queryWrapper.eq("status",1);
        queryWrapper.orderByDesc("create_time");
		IPage<${entity}> ${lowEntity}ListPage = ${lowEntity}Service.page(buildPage(${lowEntity}QueryVo), queryWrapper);
		return getSuccessRespResult(${lowEntity}ListPage);
	}
	
	@ApiOperation(value = "保存${table.comment}", notes = "保存${table.comment}", httpMethod = "POST")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RespResult<String>  save(@Validated ${entity}AddVo ${lowEntity}AddVo) {
	    ${entity} ${lowEntity} = new ${entity}();
	    copyPropertiesIgnoreNull(${lowEntity}AddVo,${lowEntity} );
		${lowEntity}.setId(IdUtils.getId());
		LocalDateTime timestamp = LocalDateTime.now();
		${lowEntity}.setCreateTime(timestamp);
		${lowEntity}.setCreateUserid(getUserId());
		boolean result = ${lowEntity}Service.save(${lowEntity});
		return result?getSuccessRespResult():getFailMsg("保存失败");
	}
	
	@ApiOperation(value = "更新${table.comment}", notes = "更新${table.comment}", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RespResult<String>  update(@Validated ${entity}UpdateVo ${lowEntity}UpdateVo) {
	    ${entity} ${lowEntity} = new ${entity}();
	    copyPropertiesIgnoreNull(${lowEntity}UpdateVo,${lowEntity} );
		LocalDateTime timestamp = LocalDateTime.now();
		${lowEntity}.setModifyTime(timestamp);
		${lowEntity}.setModifyUserid(getUserId());
		boolean result = ${lowEntity}Service.updateById(${lowEntity});
		return result?getSuccessRespResult():getFailMsg("更新失败");
	}
	
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RespResult<String>  delete(String ids) {
	    AssertUtils.assertIsNotEmpty(ids, "删除id不能为空");
		${lowEntity}Service.delete(ids);
		return getSuccessRespResult();
	}
	
	@ApiOperation(value = "获取详情", notes = "获取详情", httpMethod = "GET")
	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	public RespResult<${entity}> getDetail(String id) {
		AssertUtils.assertIsNotEmpty(id, "id不能为空");
		return getSuccessRespResult(${lowEntity}Service.getById(id));
	}
}
</#if>
