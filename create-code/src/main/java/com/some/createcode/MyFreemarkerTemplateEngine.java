package com.some.createcode;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.some.web.utils.BeanUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since  JDK1.6
 */
public class MyFreemarkerTemplateEngine extends FreemarkerTemplateEngine{
	/**
	 * 重写 自定义输出路径
	 * @return
	 * @author qzq
	 */
	@Override
	public AbstractTemplateEngine batchOutput() {
		try {
			List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
			for (TableInfo tableInfo : tableInfoList) {
				Map<String, Object> objectMap = this.getObjectMap(tableInfo);
				Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
				Map<String, String> packageInfo = (Map<String, String>) objectMap.get("package");
				TemplateConfig template = this.getConfigBuilder().getTemplate();
				// Mp.java
				String entityName = tableInfo.getEntityName();
				if (null != entityName) {
					//String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + this.suffixJavaOrKt()), entityName);
					String entityFile =String.format( MpGenerator.filePaths.get("entityFile"),entityName);
					if (this.isCreate(FileType.ENTITY,entityFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/entity.java"), entityFile);
					}
				}
				// MpMapper.java
				if (null != tableInfo.getMapperName()) {
					//String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt()), entityName);
					String mapperFile =String.format( MpGenerator.filePaths.get("mapperFile"),entityName);
					if (this.isCreate(FileType.MAPPER,mapperFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/mapper.java"), mapperFile);
					}
				}
				// MpMapper.xml
				if (null != tableInfo.getXmlName()) {
					//String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
					String xmlFile =String.format( MpGenerator.filePaths.get("mapperXmlFile"),entityName);
					if (this.isCreate(FileType.XML,xmlFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/mapper.xml"), xmlFile);
					}
				}
				// IMpService.java
				if (null != tableInfo.getServiceName()) {
					//String serviceFile = String.format((pathInfo.get(ConstVal.SERIVCE_PATH) + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt()), entityName);
					String serviceFile =String.format( MpGenerator.filePaths.get("serviceFile"),entityName);
					if (this.isCreate(FileType.SERVICE,serviceFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/service.java"), serviceFile);
					}
				}
				// MpServiceImpl.java
				if (null != tableInfo.getServiceImplName()) {
//	                    String implFile = String.format((pathInfo.get(ConstVal.SERVICEIMPL_PATH) + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt()), entityName);
//	                    if (this.isCreate(implFile)) {
//	                        this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), implFile);
//	                    }
				}
				// MpController.java
				if (null != tableInfo.getControllerName()) {
					//String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt()), entityName);
					String controllerFile =String.format( MpGenerator.filePaths.get("controllerFile"),entityName,entityName);
					if (this.isCreate(FileType.CONTROLLER,controllerFile)) {
						if(MpGenerator.isHadHtml){
							this.writer(objectMap, this.templateFilePath("/templates/new/controller-hadPage.java"), controllerFile);
						}else {
							this.writer(objectMap, this.templateFilePath("/templates/new/controller.java"), controllerFile);
						}
					}
				}

				//输出jsp
				if(MpGenerator.filePaths.get("jspFile")!=null&&MpGenerator.isHadHtml){
					String jspFile =String.format( MpGenerator.filePaths.get("jspFile"),entityName);
					if (this.isCreate(null,jspFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/list_jsp"), jspFile);
					}

					String addJspFile =String.format( MpGenerator.filePaths.get("jspFile"),"add"+entityName);
					if (this.isCreate(null,addJspFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/add_jsp"), addJspFile);
					}
				}

				//输出js
				if(MpGenerator.filePaths.get("jsFile")!=null&&MpGenerator.isHadHtml){
					String jsFile =String.format( MpGenerator.filePaths.get("jsFile"),entityName);
					if (this.isCreate(null,jsFile)) {
						//输出列表js
						this.writer(objectMap, this.templateFilePath("/templates/new/list_js"), jsFile);
					}
					String addJsFile =String.format( MpGenerator.filePaths.get("jsFile"),"add"+entityName);
					if (this.isCreate(null,addJsFile)) {
						//输出添加js
						this.writer(objectMap, this.templateFilePath("/templates/new/add_js"), addJsFile);
					}

				}



				//输出查询VO
				if(MpGenerator.filePaths.get("queryVoFile")!=null){
					String queryVoFile =String.format( MpGenerator.filePaths.get("queryVoFile"),entityName);
					if (this.isCreate(null,queryVoFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/queryVo.java"), queryVoFile);
					}
				}
				if(MpGenerator.filePaths.get("addVoFile")!=null){
					String queryVoFile =String.format( MpGenerator.filePaths.get("addVoFile"),entityName);
					if (this.isCreate(null,queryVoFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/addVo.java"), queryVoFile);
					}
				}
				if(MpGenerator.filePaths.get("updateVoFile")!=null){
					String queryVoFile =String.format( MpGenerator.filePaths.get("updateVoFile"),entityName);
					if (this.isCreate(null,queryVoFile)) {
						this.writer(objectMap, this.templateFilePath("/templates/new/updateVo.java"), queryVoFile);
					}
				}
			}
		} catch (Exception e) {
			logger.error("无法创建文件，请检查配置信息！", e);
		}
		return this;
	}

	@Override
	public Map<String, Object> getObjectMap(TableInfo tableInfo) {
		Map<String, Object> objectMap = super.getObjectMap(tableInfo);
		String entityName = tableInfo.getEntityName();
		Map<String,String> myPackage = MpGenerator.initPath(tableInfo);
		objectMap.put("package", myPackage);
		String jsp_packagepath = MpGenerator.JSP_PACKAGEPATH.indexOf("WEB-INF")!=-1?MpGenerator.JSP_PACKAGEPATH.substring(MpGenerator.JSP_PACKAGEPATH.indexOf("WEB-INF")):MpGenerator.JSP_PACKAGEPATH;
		String js_packagepath = MpGenerator.JS_PACKAGEPATH.indexOf("WEB-INF")!=-1?MpGenerator.JS_PACKAGEPATH.substring(MpGenerator.JS_PACKAGEPATH.indexOf("WEB-INF")):MpGenerator.JS_PACKAGEPATH;
		jsp_packagepath = jsp_packagepath.endsWith("/")?jsp_packagepath:jsp_packagepath+"/";
		js_packagepath = js_packagepath.endsWith("/")?js_packagepath:js_packagepath+"/";
		objectMap.put("JSP_PACKAGEPATH", jsp_packagepath);
		objectMap.put("JS_PACKAGEPATH",js_packagepath);
		String requestmapping_pre = tableInfo.getEntityPath();
		if(StringUtils.isNotEmpty(MpGenerator.REQUESTMAPPING_PRE)){
			requestmapping_pre = MpGenerator.REQUESTMAPPING_PRE+"/"+tableInfo.getEntityPath();
		}
		//requestmapping_pre = requestmapping_pre.startsWith("/")?requestmapping_pre:"/"+requestmapping_pre;
		objectMap.put("requestmapping_pre",requestmapping_pre);

		objectMap.put("lowEntity", org.apache.commons.lang3.StringUtils.uncapitalize(entityName));
		String[] hiddenLs = new String[]{"id","create_userid","create_time","modify_userid","modify_time","status"};
		Arrays.sort(hiddenLs);
		//自动排序 针对jsp界面的输出
		List<JspField> ls = new ArrayList<>();
		for(TableField tableField:tableInfo.getFields()){
			JspField jspField = new JspField();
			BeanUtils.copyPropertiesIgnoreNull(tableField,jspField);
			if(Arrays.binarySearch(hiddenLs,jspField.getName())>=0){
				jspField.setHidden(true);
				jspField.setHiddenStr("hidden=\"true\"");
			}
			ls.add(jspField);
		}
		ls.sort(new Comparator<JspField>() {
			@Override
			public int compare(JspField o1, JspField o2) {
				if(o1.isHidden()&&!o2.isHidden()){
					return 1;
				}else if(o1.isHidden()&&o2.isHidden()){
					return o1.getPropertyName().compareTo(o2.getPropertyName());
				}else if(!o1.isHidden()&&o2.isHidden()){
					return -1;
				}else{
					return o1.getPropertyName().compareTo(o2.getPropertyName());
				}
			}
		});
		objectMap.put("jspFields",ls);
		return objectMap;
	}
	/**
	 * @param fileType
	 * @param filePath
	 * @return
	 * @author qzq
	 * @date 2018年11月23日 下午2:39:19
	 */
	@Override
	protected boolean isCreate(FileType fileType, String filePath) {
		//不存在文件包则创建文件包
		File file = new File(filePath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
		super.writer(objectMap, templatePath, outputFile);
	}
	@Override
	public String templateFilePath(String filePath) {
		StringBuilder fp = new StringBuilder();
		fp.append(filePath).append(".ftl");
		return fp.toString();
	}
	/**
	 * 这里不创建 自定义创建
	 * @return
	 * @author qzq
	 */
	@Override
	public AbstractTemplateEngine mkdirs() {
		return this;
	}
}
