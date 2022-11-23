package pl.zubkova.webuniversity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

/**
 * @author Svetlana_Zubkova
 */
@ComponentScan("pl.zubkova.webuniversity")
@ActiveProfiles({"Test"})
@Configuration
public class ApplicationConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "Students")
    public SimpleJdbcInsert getSimpleJdbcInsertCertificate() {
        return new SimpleJdbcInsert(dataSource).withTableName("students").
                usingGeneratedKeyColumns("id");
    }

    @Bean(name = "Teachers")
    public SimpleJdbcInsert getSimpleJdbcInsertTag() {
        return new SimpleJdbcInsert(dataSource).withTableName("teachers").
                usingGeneratedKeyColumns("id");
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
