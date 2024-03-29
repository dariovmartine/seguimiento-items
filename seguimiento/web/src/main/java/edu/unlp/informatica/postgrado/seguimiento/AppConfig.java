package edu.unlp.informatica.postgrado.seguimiento;


import java.sql.Driver;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dariovmartine
 *
 */
@Import({RepositoryConfig.class})
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class AppConfig {

    @Bean
    public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("item.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
}

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
class RepositoryConfig {

    @Value("${hibernate.connection.driver_class}")
    private String driverClassName;
    
    @Value("${hibernate.connection.url}")                 
    private String url;
    
    @Value("${hibernate.connection.username}")             
    private String username;
    
    @Value("${hibernate.connection.password}")             
    private String password;
    
    @Value("${hibernate.dialect}")         
    private String hibernateDialect;
    
    @Value("${hibernate.show_sql}")     
    private String hibernateShowSql;
        
    @Bean    
    public DataSource getDataSource()
    {
    	try {
    		return new SimpleDriverDataSource((Driver)BeanUtils.instantiateClass(Class.forName(driverClassName)), url, username, password);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
    	return new HibernateTransactionManager(sessionFactory);
    }
    
    @Bean
    @Autowired
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
    {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }
        
    @Bean
    public AnnotationSessionFactoryBean getSessionFactory()
    {
        AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
        asfb.setDataSource(getDataSource());
        asfb.setHibernateProperties(getHibernateProperties());        
        asfb.setPackagesToScan(new String[]{
        		"edu.unlp.informatica.postgrado.seguimiento.item.model",
        		"edu.unlp.informatica.postgrado.seguimiento.item.mapper",
        		"edu.unlp.informatica.postgrado.seguimiento.item.service"
        });
        return asfb;
    }

    @Bean
    public Properties getHibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        
        return properties;
    }
}
