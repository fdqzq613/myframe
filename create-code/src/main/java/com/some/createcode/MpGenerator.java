package com.some.createcode;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能说明:
 *
 * @author qzq
 * @version V1.0
 * @since JDK1.6
 */
@Slf4j
public class MpGenerator {
    public static String[] TABLE = new String[]{"t_user"};
    public static String AUTHOR = "qzq";
    /**
     * 业务包路径 如："com.some.yw"
     */
    public static String YW_PACKAGEPATH;
    /**
     * 业务包Controller类路径 如com.some.yw.controller
     * 如果没设置，则根据业务包路径默认设置
     */
    public static String CONTROLLER_PACKAGEPATH ;

    /**
     * 业务包SERVICE类路径 如com.some.yw.service
     * 如果没设置，则根据业务包路径默认设置
     */
    public static String SERVICE_PACKAGEPATH ;

    /**
     * 业务包DAO类路径 如com.some.yw.dao
     * 如果没设置，则根据业务包路径默认设置
     */
    public static String DAO_PACKAGEPATH ;

    /**
     * 业务包VO类路径 如com.some.yw.vo
     * 如果没设置，则根据业务包路径默认设置
     */
    public static String VO_PACKAGEPATH ;
    /**
     * 实体包类路径 如com.some.domain
     */
    public static String ENTITY_PACKAGEPATH ;
    /**
     * 页面路径
     */
    public static String JSP_PACKAGEPATH = "/src/main/webapp/WEB-INF/view";
    /**
     * js路径
     */
    public static String JS_PACKAGEPATH = "/src/main/webapp/cdn/static/js";
    //模块前缀
    //public static final String ModuleName = "admin";
    public static String DB_USER ;
    public static String DB_PWD ;
    public static String DB_URL;
    public static Map<String, String> filePaths = new HashMap<>();
    /**
     * 项目路径
     */
    public static String rootPath;
    public static String classPath;
    public static String forwardPath;

    public static Map<String, String> initPath(TableInfo tableInfo) {
        if(StringUtils.isEmpty(YW_PACKAGEPATH)){
            log.error("业务包路径要设置 如 com.some.yw");
        }
        MpGenerator.CONTROLLER_PACKAGEPATH = StringUtils.isEmpty(MpGenerator.CONTROLLER_PACKAGEPATH)?YW_PACKAGEPATH+".controller":MpGenerator.CONTROLLER_PACKAGEPATH;
        MpGenerator.SERVICE_PACKAGEPATH = StringUtils.isEmpty(MpGenerator.SERVICE_PACKAGEPATH)?YW_PACKAGEPATH+".service":MpGenerator.SERVICE_PACKAGEPATH;
        MpGenerator.DAO_PACKAGEPATH = StringUtils.isEmpty(MpGenerator.DAO_PACKAGEPATH)?YW_PACKAGEPATH+".dao":MpGenerator.DAO_PACKAGEPATH;
        MpGenerator.VO_PACKAGEPATH  = StringUtils.isEmpty(MpGenerator.VO_PACKAGEPATH)?YW_PACKAGEPATH+".vo":MpGenerator.VO_PACKAGEPATH;
        MpGenerator.ENTITY_PACKAGEPATH = StringUtils.isEmpty(MpGenerator.ENTITY_PACKAGEPATH)?YW_PACKAGEPATH+".domain":MpGenerator.ENTITY_PACKAGEPATH;
        Map<String, String> myPackage = new HashMap<>();
        String entityName = tableInfo.getEntityName();
        String codePath = rootPath + "/src/main/java/";
        JSP_PACKAGEPATH = JSP_PACKAGEPATH.endsWith("/")?JSP_PACKAGEPATH:JSP_PACKAGEPATH+"/";
        JS_PACKAGEPATH = JS_PACKAGEPATH.endsWith("/")?JS_PACKAGEPATH:JS_PACKAGEPATH+"/";
        String jspPath = rootPath + JSP_PACKAGEPATH + entityName.toLowerCase();
        String jsPath = rootPath + JS_PACKAGEPATH + entityName.toLowerCase();
        myPackage.put("Controller", MpGenerator.CONTROLLER_PACKAGEPATH );
        myPackage.put("Entity", MpGenerator.ENTITY_PACKAGEPATH );
        myPackage.put("Mapper", MpGenerator.DAO_PACKAGEPATH+"." + entityName.toLowerCase() );
        myPackage.put("Service", MpGenerator.SERVICE_PACKAGEPATH );
        myPackage.put("queryVo", MpGenerator.VO_PACKAGEPATH + "." + entityName.toLowerCase() );

        //模块前缀
        //myPackage.put("ModuleName", ModuleName);
        filePaths.put("entityFile", codePath + myPackage.get("Entity").toLowerCase().replace(".", "/") + "/%s.java");
        filePaths.put("mapperFile", codePath + myPackage.get("Mapper").toLowerCase().replace(".", "/") + "/%sMapper.java");
        filePaths.put("mapperXmlFile", codePath + myPackage.get("Mapper").toLowerCase().replace(".", "/") + "/%sMapper.xml");
        filePaths.put("serviceFile", codePath + myPackage.get("Service").toLowerCase().replace(".", "/") + "/%sService.java");
        filePaths.put("controllerFile", codePath + myPackage.get("Controller").toLowerCase().replace(".", "/") + "/%sController.java");
        if (JSP_PACKAGEPATH != null) {
            forwardPath = String.format(jspPath + "/%s.jsp", entityName);
            int sub = forwardPath.indexOf("webapp");
            forwardPath = forwardPath.substring(sub + 6);
            filePaths.put("jspFile", jspPath + "/%s.jsp");
            filePaths.put("jsFile", jsPath + "/%s.jsp");
        }
        filePaths.put("queryVoFile", codePath + myPackage.get("queryVo").toLowerCase().replace(".", "/") + "/%sQueryVo.java");
        filePaths.put("addVoFile", codePath + myPackage.get("queryVo").toLowerCase().replace(".", "/") + "/%sAddVo.java");
        filePaths.put("updateVoFile", codePath + myPackage.get("queryVo").toLowerCase().replace(".", "/") + "/%sUpdateVo.java");

        return myPackage;
    }


    public static void create(Class clazz) {
        String path = clazz.getResource("").getPath();
        classPath = path.substring(1,path.indexOf("classes"))+"classes";
        if(path.indexOf("target")!=-1){

            path = path.substring(0,path.indexOf("/target"));
        }else if(path.indexOf("src")!=-1){
            path = path.substring(0,path.indexOf("/src"));
        }
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        createCode(path);
    }
    private static void createCode(String myRootPath) {
//      assert (false) : "代码生成属于危险操作，请确定配置后取消断言执行代码生成！";
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(AUTHOR);
        rootPath = myRootPath;
        if (rootPath == null) {
            File directory = new File("");
            rootPath = directory.getAbsolutePath();
        }
        String path = rootPath+ "/src/main/java/";

        //文件实际输出对应的业务父路径
        //final String realFilePath = path + PACKAGEPATH.replace(".", "/");
        gc.setOutputDir(path);
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        // gc.setMapperName("%sDao");
        gc.setServiceName("%sService");
        //不生成impl
        gc.setServiceImplName(null);
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】

            /**
             * @param globalConfig
             * @param fieldType
             * @return
             * @author qzq
             */
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PWD);
        dsc.setUrl(DB_URL);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"t_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(TABLE); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(CONTROLLER_PACKAGEPATH);
        // pc.setModuleName("test");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.author 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("author", AUTHOR);
                this.setMap(map);
            }
        };
        //
        // 自定义 xxList.jsp 生成
//       List<FileOutConfig> focList = new ArrayList<>();
//       focList.add(new FileOutConfig("/templates/listJsp.ftl") {
//       @Override
//       public String outputFile(TableInfo tableInfo) {
//       // 自定义输入文件名称
//       return directory.getAbsolutePath()+"/src/main/webapp/jsp/" + tableInfo.getEntityName() + ".jsp";
//       }
//       });
//      cfg.setFileOutConfigList(focList);
//       mpg.setCfg(cfg);
//       
//       //自定义mapper xml 生成
//       focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//       @Override
//       public String outputFile(TableInfo tableInfo) {
//       // 自定义输入文件名称
//       return directory.getAbsolutePath()+"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
//       }
//       });
//      cfg.setFileOutConfigList(focList);
//      
//      //自定义controller 生成
//      focList.add(new FileOutConfig("/templates/controller.ftl") {
//      @Override
//      public String outputFile(TableInfo tableInfo) {
//      // 自定义输入文件名称
//      return path+ tableInfo.getEntityName() + "/controller/"+tableInfo.getEntityName()+"Controller.java";
//      }
//      });
//     
//     
//     //自定义entity.java生成
////     focList.add(new FileOutConfig("/templates/entity.java.ftl") {
////     @Override
////     public String outputFile(TableInfo tableInfo) {
////     // 自定义输入文件名称
////    	 return path+  "/domain/"+tableInfo.getEntityName()+".java";
////     }
////     });
//    
//    //自定义mapper java  生成
//    focList.add(new FileOutConfig("/templates/mapper.java.ftl") {
//    @Override
//    public String outputFile(TableInfo tableInfo) {
//    // 自定义输入文件名称
//    	 return path+ tableInfo.getEntityName() + "/dao/"+tableInfo.getEntityName()+"Mapper.java";
//    }
//    });
//    
//    //自定义service  生成
//    focList.add(new FileOutConfig("/templates/service.java.ftl") {
//    @Override
//    public String outputFile(TableInfo tableInfo) {
//    // 自定义输入文件名称
//    	 return path+ tableInfo.getEntityName() + "/service/"+tableInfo.getEntityName()+"Service.java";
//    }
//    });
//   cfg.setFileOutConfigList(focList);
//    mpg.setCfg(cfg);
        //

        //
        // // 关闭默认 xml 生成，调整生成 至 根目录
//       TemplateConfig tc = new TemplateConfig();
//       tc.setXml(directory.getAbsolutePath()+"/src/main/resources/mapper/");
//       mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);


        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        // System.err.println(mpg.getCfg().getMap().get("abc"));
    }


    public static void main(String[] args) {
        MpGenerator.TABLE = new String[]{"t_role"};
        MpGenerator.AUTHOR = "qzq";
        //业务包路径
        MpGenerator.YW_PACKAGEPATH = "com.some.yw";
        //实体包路径
        MpGenerator.ENTITY_PACKAGEPATH = "com.some.yw.domain";
        MpGenerator.JSP_PACKAGEPATH = "/src/main/webapp/WEB-INF/view/";
        MpGenerator.JS_PACKAGEPATH = "/src/main/webapp/cdn/static/js";
        //模块前缀
        //public static final String ModuleName = "admin";
        MpGenerator.DB_USER = "root";
        MpGenerator.DB_PWD = "123456";
        MpGenerator.DB_URL = "jdbc:mysql://127.0.0.1:3366/lncd?useUnicode=true&characterEncoding=utf8&useOldAliasMetadataBehavior=true&useSSL=false&serverTimezone=GMT%2B8";
        MpGenerator.create(MpGenerator.class);
    }
}
