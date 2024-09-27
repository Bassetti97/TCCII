package tcc.fundatec.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tcc.fundatec.org")
public class TccIiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccIiApplication.class, args);
	}

}
