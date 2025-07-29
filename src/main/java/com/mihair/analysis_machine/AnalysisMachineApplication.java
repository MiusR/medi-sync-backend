package com.mihair.analysis_machine;

import com.mihair.analysis_machine.util.KeyProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class AnalysisMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalysisMachineApplication.class, args);
	}




	@GetMapping("/add_patient")
	public String add_patient() {
		KeyProvider keyProvider = new KeyProvider("patientkvault");
		keyProvider.requestKey("test");

		return "Done!";
	}

}
