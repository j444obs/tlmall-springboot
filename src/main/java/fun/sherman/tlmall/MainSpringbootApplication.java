package fun.sherman.tlmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "fun.sherman.tlmall.dao")
@SpringBootApplication
public class MainSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainSpringbootApplication.class, args);
    }
}
