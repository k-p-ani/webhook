package com.example.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController
@RequestMapping("/")
class SecuredServerController {

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> validate(@RequestBody String jsonRequest) {

		System.out.println(" json request is \n" + jsonRequest + "\n");
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
