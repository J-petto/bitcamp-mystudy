package bitcamp.myapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@ComponentScan("bitcamp.myapp")
@EnableWebMvc
@PropertySource({
        "classpath:config/jdbc.properties",
        "file:${user.home}/config/ncp.properties"})
@EnableTransactionManagement
@MapperScan("bitcamp.myapp.dao")
public class AppConfig {

    ApplicationContext appCtx;

    public AppConfig(ApplicationContext appCtx) {
        this.appCtx = appCtx;
    }

    @Bean
    public DataSource dataSource(
            @Value("${jdbc.driver}") String jdbcDriver,
            @Value("${jdbc.url}") String jdbcUrl,
            @Value("${jdbc.username}") String jdbcUserName,
            @Value("${jdbc.password}") String jdbcPassword) {

        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName(jdbcDriver);
        ds.setUrl(jdbcUrl);
        ds.setUsername(jdbcUserName);
        ds.setPassword(jdbcPassword);

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean()
    public ViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/jsp/");
        vr.setSuffix(".jsp");
        return vr;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("bitcamp.myapp.vo");
        factoryBean.setMapperLocations(appCtx.getResources("classpath:mappers/*Mapper.xml"));

        return factoryBean.getObject();
    }

}
