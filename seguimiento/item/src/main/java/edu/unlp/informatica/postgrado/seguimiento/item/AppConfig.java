package edu.unlp.informatica.postgrado.seguimiento.item;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ImportResource("classpath:item.properties")
public class AppConfig {
  private @Value("${hibernate.connection.url}") String url;
  private @Value("${hibernate.connection.username}") String username;
  private @Value("${hibernate.connection.password}") String password;

  public @Bean DataSource dataSource() {
      return new DriverManagerDataSource(url, username, password);
  }
}