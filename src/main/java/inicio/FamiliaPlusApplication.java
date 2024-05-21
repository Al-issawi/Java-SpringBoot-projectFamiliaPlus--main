package inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controllers"})
public class FamiliaPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamiliaPlusApplication.class, args);
	}

}
