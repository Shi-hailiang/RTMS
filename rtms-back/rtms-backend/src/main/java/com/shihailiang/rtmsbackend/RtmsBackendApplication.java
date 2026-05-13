package com.shihailiang.rtmsbackend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@MapperScan("com.shihailiang.rtmsbackend.mapper")
public class RtmsBackendApplication {
        public static void main(String[] args) {
            ConfigurableApplicationContext context = SpringApplication.run(RtmsBackendApplication.class, args);
            Environment env = context.getEnvironment();
            String port = env.getProperty("server.port");
            log.info("启动成功", port);
        }
}
