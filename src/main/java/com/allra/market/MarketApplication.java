package com.allra.market;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Locale;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class MarketApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MarketApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
