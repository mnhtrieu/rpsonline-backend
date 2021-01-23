package eu.mnhtrieu.rpsonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.List;

@ServletComponentScan
@SpringBootApplication
public class RpsonlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpsonlineApplication.class, args);
	}
}
