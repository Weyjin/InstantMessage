package com.instant.message;


import com.instant.message.shiro.CloseListener;
import com.instant.message.shiro.StartListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.instant.message.dao")
@ComponentScan("com.instant.message.*")
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(MessageApplication.class);
        sa.addListeners(new StartListener());
        sa.addListeners(new CloseListener());
        sa.run(args);
    }

}
