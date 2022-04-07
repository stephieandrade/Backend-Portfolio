package project.Shockbyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
public class ShockbyteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShockbyteApplication.class, args);
	}

}
