package com.example.spring.config;

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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 *  2.编写两个数据源连接的Config文件
 *  因为在启动类中定义好了@MapperScan("net.nature.easier_movie.dal")  默认的mapper包，所以默认库（主库）配置 就不需要写@MapperScan注解了
 *  启动类： @MapperScan("net.nature.easier_movie.dal")
 *  其他库需要写 @MapperScan，位置定义到其他库的dao包中，其中sqlSessionTemplateRef参数指的是SqlSessionTemplate的Bean
 *  如：@MapperScan(basePackages = "net.nature.easier_movie.dal.automatic.**", sqlSessionTemplateRef = "automaticSqlSessionTemplate")
 *  不写@Value值，就需要将application中spring.datasource.url改为spring.datasource.jdbc-url
 *  增加 mybatis.mapper-locations=classpath:mapping/*.xml, classpath*:mapping/automatic/*.xml
 *  其中，第一个为主库，其余为其他库所在的mapper.xml包
 *
 */
@Configuration
//除主库外都需要写@MapperScan注解，注入mapper，
@MapperScan(basePackages = "com.example.spring.daos.second.**", sqlSessionTemplateRef = "automaticSqlSessionTemplate")
public class SecondDataSourceConfig {

    @Value("${spring.datasource.second.name}")
    private String poolName;

    @Value("${spring.datasource.second.url}")
    private String url;

    @Value("${spring.datasource.second.username}")
    private String username;

    @Value("${spring.datasource.second.password}")
    private String password;

    @Value("${spring.datasource.second.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.second.type}")
    private Class<DataSource> type;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.automatic")
    public DataSource secondDatasource() {
        return DataSourceBuilder.create()
                .username(username).password(password)
                .url(url).driverClassName(driverClassName)
                .type(type).build();
    }

    /**
     * 必须指定扫描哪个文件夹里的mapper.xml文件
     */
    @Bean
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/second/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager automaticTransactionManager(@Qualifier("secondDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate automaticSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}