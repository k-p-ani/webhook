package com.example.sslserver;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController
@RequestMapping("/")
class SecuredServerController {
	@Value("classpath:payload/pass-response.json")
	private Resource passResponseTemplate;

	@Value("classpath:payload/failed-response.json")
	private Resource failedResponseTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final String unhandleException = "{\n" + 
			"  \"apiVersion\": \"API_VERSION\",\n" + 
			"  \"kind\": \"AdmissionReview\",\n" + 
			"  \"response\": {\n" + 
			"    \"uid\": \"REQUEST_ID\",\n" + 
			"    \"allowed\": false,\n" + 
			"    \"status\": {\n" + 
			"      \"code\": 500,\n" + 
			"      \"message\": \"ERROR_MESSAGE\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> validate(@RequestBody String jsonRequest) {

		System.out.println(" json request is \n" + jsonRequest + "\n");
		JsonNode jsonNode = null;
		String responseBody = null;
		try {
			jsonNode = objectMapper.readTree(jsonRequest);
			String apiVersion = jsonNode.at("/apiVersion").asText();
			String requestId = jsonNode.at("/request/uid").asText();
			String failed = getFailedPayload(apiVersion,requestId);
			String passed = getPassedPayload(apiVersion,requestId);
			responseBody = failed;

			System.out.println(" failed is \n" + failed + "\n");
			System.out.println(" passed is \n" + passed + "\n");
			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody = unhandleException.replace("ERROR_MESSAGE", "AMOS-Error while proccessing input request "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
	private String getFailedPayload(String apiVersion, String uid) {

		Jinjava jinjava = new Jinjava();
		Map<String, Object> context = Maps.newHashMap();
		context.put("API_VERSION", apiVersion);
		context.put("REQUEST_ID", uid);
		String template = null;
		try {
			template = Resources.toString(failedResponseTemplate.getURL(), Charsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String renderedTemplate = jinjava.render(template, context);

		return renderedTemplate;
	}
	
	private String getPassedPayload(String apiVersion, String uid) {

		Jinjava jinjava = new Jinjava();
		Map<String, Object> context = Maps.newHashMap();
		context.put("API_VERSION", apiVersion);
		context.put("REQUEST_ID", uid);
		String template = null;
		try {
			template = Resources.toString(passResponseTemplate.getURL(), Charsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String renderedTemplate = jinjava.render(template, context);

		return renderedTemplate;
	}
}
