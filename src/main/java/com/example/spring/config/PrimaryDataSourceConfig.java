package com.example.spring.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 如果不在启动类中添加MapperScan，就要在主、其他库配置文件加
 * 主库需要添加@primary 注解，其他库不用添加
 * 不同库的mapper文件及mapper.xml不要放在同一个包中
 * 定义要扫描的mapper.xml包路径
 * new SqlSessionFactoryBean().setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
 */
@Configuration
@MapperScan("com.example.spring.daos")
public class PrimaryDataSourceConfig {

    @Value("${spring.datasource.name}")
    private String poolName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.type}")
    private Class<DataSource> type;

    /**
     * 定义默认主数据源和从数据源的Bean
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create()
                .username(username).password(password)
                .url(url).driverClassName(driverClassName)
                .type(type).build();
    }

    //定义要扫描的mapper.xml包路径
    //new SqlSessionFactoryBean().setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
    @Bean
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置对应的xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}