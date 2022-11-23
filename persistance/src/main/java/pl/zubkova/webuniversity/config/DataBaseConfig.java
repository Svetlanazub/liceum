package pl.zubkova.webuniversity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Svetlana_Zubkova
 */
    @Configuration
    @EnableTransactionManagement
    @Profile("Test")
    public class DataBaseConfig {
        /**
         * Creates dataSource h2 database
         *
         * @return dataSource
         */
        @Bean
        public EmbeddedDatabase dataSource() {

            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2).addScript("sql/create-db.sql").addScript("sql/insert-data.sql").build();

        }
}
