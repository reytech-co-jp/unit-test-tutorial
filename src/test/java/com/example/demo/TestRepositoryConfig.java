package com.example.demo;

import com.example.demo.mapper.AnimeMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EntityScan(basePackageClasses = AnimeMapper.class)
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestRepositoryConfig {
}
