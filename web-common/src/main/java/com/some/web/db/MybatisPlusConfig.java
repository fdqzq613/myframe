package com.some.web.db;//package com.some.common.ext.db;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.type.JdbcType;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//
//@Configuration
//@MapperScan("com.some.common*")
//public class MybatisPlusConfig {
//	@Autowired
//	@Qualifier("commonDataSource")
//	private DataSource commonDataSource;
//	@Autowired
//	@Qualifier("zykDataSource")
//	private DataSource zykDataSource;
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    @Profile({"kf","test","gray"})
//    public PerformanceInterceptor performanceInterceptor() {
//    	PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//    	performanceInterceptor.setFormat(true);
//    	performanceInterceptor.setWriteInLog(true);
//    	performanceInterceptor.setMaxTime(5000);
//        return performanceInterceptor;
//    }
//
//    /**
//     * mybatis-plus分页插件<br>
//     * 文档：http://mp.baomidou.com<br>
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//    	 PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//         return paginationInterceptor;
//    }
//    /**
//     * 动态数据源配置
//     * @return
//     */
//    @Bean
//    public DataSource multipleDataSource(@Qualifier("zykDataSource") DataSource zyk, @Qualifier("commonDataSource") DataSource common) {
//        MultipleDataSource multipleDataSource = new MultipleDataSource();
//        Map< Object, Object > targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceEnum.ZYK.getValue(), zyk);
//        targetDataSources.put(DataSourceEnum.common.getValue(), common);
//        //添加数据源
//        multipleDataSource.setTargetDataSources(targetDataSources);
//        //设置默认数据源
//        multipleDataSource.setDefaultTargetDataSource(common);
//        return multipleDataSource;
//    }
//
//    @Bean("sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(multipleDataSource(zykDataSource, commonDataSource));
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(false);
//        sqlSessionFactory.setConfiguration(configuration);
//        sqlSessionFactory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
//                paginationInterceptor()//添加分页功能
//        });
//        //sqlSessionFactory.setGlobalConfig(globalConfiguration());
//        return sqlSessionFactory.getObject();
//    }
//}
