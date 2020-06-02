package com.some.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
public class DataSourceConfig {
    @Bean(name = "dataSource0")
    @Qualifier("dataSource0")
    @ConfigurationProperties(prefix = "spring.datasource.ds0")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }
    @Bean(name = "dataSource1")
    @Qualifier("dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    /**
     * 数据源
     *
     * @return
     */
    @Bean(name = "dataSource")
	@Primary //默认数据源
    public DataSource dataSource(@Qualifier("dataSource0") DataSource dataSource0, @Qualifier("dataSource1") DataSource dataSource1) {

          // 配置真实数据源
        Map dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", dataSource0);
        dataSourceMap.put("ds1", dataSource1);
          // 配置表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");


          // 配置分库 + 分表策略

        //Inline表达式分片策略。使用Groovy的Inline表达式，提供对SQL语句中的=和IN的分片操作支持  分库 按用户id 分库
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("create_userid", "ds${create_userid % 2}"));

        //按订单号分表
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_no", "t_order${order_no % 2}"));

		// 配置分片规则

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


        DataSource dataSource = null;
        try {
            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return dataSource;
    }
}
