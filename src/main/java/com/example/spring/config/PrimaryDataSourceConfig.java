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


/**
 * 1.在application.properties中定义好主库和其他库的连接信息 (两个数据源)
 * //主库：
 * spring.datasource.name=fast_ticket_issuing_solution
 * spring.datasource.url=jdbc:mysql://bj-cdb-nnc1565q.sql.tencentcdb.com:61626/fast_ticket_issuing_solution?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true
 * spring.datasource.username=root
 * spring.datasource.password=NIANchu81853239
 * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
 * //其他库
 * spring.datasource.other.name=automatic_ticket_maker_client_piaoshen
 * spring.datasource.other.url=jdbc:mysql://bj-cdb-nnc1565q.sql.tencentcdb.com:61626/automatic_ticket_maker_client_piaoshen?useUnicode=true&characterEncoding=UTF-8
 * spring.datasource.other.username=root
 * spring.datasource.other.password=NIANchu81853239
 * spring.datasource.other.driver-class-name=com.mysql.cj.jdbc.Driver
 *
 *  *  2.编写两个数据源连接的Config文件
 *  *  因为在启动类中定义好了@MapperScan("net.nature.easier_movie.dal")  默认的mapper包，所以默认库（主库）配置 就不需要写@MapperScan注解了
 *  *  启动类： @MapperScan("net.nature.easier_movie.dal")   //mapper文件的包位置
 *  *  ***遇到的问题：在primary中写了MapperScan，启动类中没写，报错（Invalid bound statement (not found): com.example.spring.daos.second.BasicConfigModelMapper.selectAll"）
 *  *  其他库需要写 @MapperScan，位置定义到其他库的dao包中
 *  *  如：@MapperScan(basePackages = "net.nature.easier_movie.dal.automatic.**", sqlSessionTemplateRef = "automaticSqlSessionTemplate")
 *  *  不写@Value值，就需要将application中spring.datasource.url改为spring.datasource.jdbc-url
 *  *  增加 mybatis.mapper-locations=classpath:mapping/*.xml, classpath*:mapping/automatic/*.xml
 *  *  其中，第一个为主库，其余为其他库所在的mapper.xml包
 *   * 主库需要添加@primary 注解，其他库不用添加
 *   * 不同库的mapper文件及mapper.xml不要放在同一个包中
 *   * 定义要扫描的mapper.xml包路径
 *   * new SqlSessionFactoryBean().setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
 */
@Configuration
//@MapperScan("com.example.spring.daos")
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