package com.some.createcode;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;
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
	                        this.writer(objectMap, this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
	                    }
	                }
	                // MpMapper.java
	                if (null != tableInfo.getMapperName()) {
	                    //String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt()), entityName);
	                    String mapperFile =String.format( MpGenerator.filePaths.get("mapperFile"),entityName);
	                    if (this.isCreate(FileType.MAPPER,mapperFile)) {
	                        this.writer(objectMap, this.templateFilePath(template.getMapper()), mapperFile);
	                    }
	                }
	                // MpMapper.xml
	                if (null != tableInfo.getXmlName()) {
	                    //String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
	                    String xmlFile =String.format( MpGenerator.filePaths.get("mapperXmlFile"),entityName);
	                    if (this.isCreate(FileType.XML,xmlFile)) {
	                        this.writer(objectMap, this.templateFilePath(template.getXml()), xmlFile);
	                    }
	                }
	                // IMpService.java
	                if (null != tableInfo.getServiceName()) {
	                    //String serviceFile = String.format((pathInfo.get(ConstVal.SERIVCE_PATH) + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt()), entityName);
	                    String serviceFile =String.format( MpGenerator.filePaths.get("serviceFile"),entityName);
	                    if (this.isCreate(FileType.SERVICE,serviceFile)) {
	                        this.writer(objectMap, this.templateFilePath(template.getService()), serviceFile);
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
	                        this.writer(objectMap, this.templateFilePath(template.getController()), controllerFile);
	                    }
	                }
	                
	                //输出jsp
	                if(MpGenerator.filePaths.get("jspFile")!=null){
	                	String jspFile =String.format( MpGenerator.filePaths.get("jspFile"),entityName);
	                    if (this.isCreate(null,jspFile)) {
	                        this.writer(objectMap, this.templateFilePath("/templates/list_jsp"), jspFile);
	                    }
	                }
	                
	                //输出查询VO
	                if(MpGenerator.filePaths.get("queryVoFile")!=null){
	                	String queryVoFile =String.format( MpGenerator.filePaths.get("queryVoFile"),entityName);
	                    if (this.isCreate(null,queryVoFile)) {
	                        this.writer(objectMap, this.templateFilePath("/templates/queryVo.java"), queryVoFile);
	                    }
	                }
	                if(MpGenerator.filePaths.get("addVoFile")!=null){
	                	String queryVoFile =String.format( MpGenerator.filePaths.get("addVoFile"),entityName);
	                    if (this.isCreate(null,queryVoFile)) {
	                        this.writer(objectMap, this.templateFilePath("/templates/addVo.java"), queryVoFile);
	                    }
	                }
	                if(MpGenerator.filePaths.get("updateVoFile")!=null){
	                	String queryVoFile =String.format( MpGenerator.filePaths.get("updateVoFile"),entityName);
	                    if (this.isCreate(null,queryVoFile)) {
	                        this.writer(objectMap, this.templateFilePath("/templates/updateVo.java"), queryVoFile);
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
	        objectMap.put("lowEntity", org.apache.commons.lang3.StringUtils.uncapitalize(entityName));
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
