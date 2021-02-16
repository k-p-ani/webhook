package com.example.sslserver;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
		ConfigurableApplicationContext ctx = SpringApplication.run(SslServerApplication.class, args);
		SecuredServerController ctr = ctx.getBean(SecuredServerController.class);
		String jsonRequest = "{\"kind\":\"AdmissionReview\",\"apiVersion\":\"admission.k8s.io/v1beta1\",\"request\":{\"uid\":\"26c40227-c719-4df2-8079-a5e5efa9f2fa\",\"kind\":{\"group\":\"\",\"version\":\"v1\",\"kind\":\"Pod\"},\"resource\":{\"group\":\"\",\"version\":\"v1\",\"resource\":\"pods\"},\"requestKind\":{\"group\":\"\",\"version\":\"v1\",\"kind\":\"Pod\"},\"requestResource\":{\"group\":\"\",\"version\":\"v1\",\"resource\":\"pods\"},\"name\":\"sleep-1-1-deploy\",\"namespace\":\"test-webhooks\",\"operation\":\"CREATE\",\"userInfo\":{\"username\":\"system:serviceaccount:openshift-infra:deployer-controller\",\"uid\":\"4cd77d35-ce05-4c6b-a627-70f279663f10\",\"groups\":[\"system:serviceaccounts\",\"system:serviceaccounts:openshift-infra\",\"system:authenticated\"]},\"object\":{\"kind\":\"Pod\",\"apiVersion\":\"v1\",\"metadata\":{\"name\":\"sleep-1-1-deploy\",\"namespace\":\"test-webhooks\",\"uid\":\"4848a6d7-b959-436a-a304-04bd84c28c20\",\"creationTimestamp\":\"2021-02-15T15:14:02Z\",\"labels\":{\"openshift.io/deployer-pod-for.name\":\"sleep-1-1\"},\"annotations\":{\"openshift.io/deployment-config.name\":\"sleep-1\",\"openshift.io/deployment.name\":\"sleep-1-1\",\"openshift.io/scc\":\"restricted\"},\"ownerReferences\":[{\"apiVersion\":\"v1\",\"kind\":\"ReplicationController\",\"name\":\"sleep-1-1\",\"uid\":\"243016b6-1561-4844-9a7b-4c3afa2237ce\"}],\"managedFields\":[{\"manager\":\"openshift-controller-manager\",\"operation\":\"Update\",\"apiVersion\":\"v1\",\"time\":\"2021-02-15T15:14:02Z\",\"fieldsType\":\"FieldsV1\",\"fieldsV1\":{\"f:metadata\":{\"f:annotations\":{\".\":{},\"f:openshift.io/deployment-config.name\":{},\"f:openshift.io/deployment.name\":{}},\"f:labels\":{\".\":{},\"f:openshift.io/deployer-pod-for.name\":{}},\"f:ownerReferences\":{\".\":{},\"k:{\\\"uid\\\":\\\"243016b6-1561-4844-9a7b-4c3afa2237ce\\\"}\":{\".\":{},\"f:apiVersion\":{},\"f:kind\":{},\"f:name\":{},\"f:uid\":{}}}},\"f:spec\":{\"f:activeDeadlineSeconds\":{},\"f:containers\":{\"k:{\\\"name\\\":\\\"deployment\\\"}\":{\".\":{},\"f:env\":{\".\":{},\"k:{\\\"name\\\":\\\"OPENSHIFT_DEPLOYMENT_NAME\\\"}\":{\".\":{},\"f:name\":{},\"f:value\":{}},\"k:{\\\"name\\\":\\\"OPENSHIFT_DEPLOYMENT_NAMESPACE\\\"}\":{\".\":{},\"f:name\":{},\"f:value\":{}}},\"f:image\":{},\"f:imagePullPolicy\":{},\"f:name\":{},\"f:resources\":{},\"f:terminationMessagePath\":{},\"f:terminationMessagePolicy\":{}}},\"f:dnsPolicy\":{},\"f:enableServiceLinks\":{},\"f:restartPolicy\":{},\"f:schedulerName\":{},\"f:securityContext\":{},\"f:serviceAccount\":{},\"f:serviceAccountName\":{},\"f:shareProcessNamespace\":{},\"f:terminationGracePeriodSeconds\":{}}}}]},\"spec\":{\"volumes\":[{\"name\":\"deployer-token-dgxfw\",\"secret\":{\"secretName\":\"deployer-token-dgxfw\"}}],\"containers\":[{\"name\":\"deployment\",\"image\":\"quay.io/openshift-release-dev/ocp-v4.0-art-dev@sha256:9f146eacac22f28c97d41fd0e4da5f35abe69affb77fb25c736e826601da30d7\",\"env\":[{\"name\":\"OPENSHIFT_DEPLOYMENT_NAME\",\"value\":\"sleep-1-1\"},{\"name\":\"OPENSHIFT_DEPLOYMENT_NAMESPACE\",\"value\":\"test-webhooks\"}],\"resources\":{},\"volumeMounts\":[{\"name\":\"deployer-token-dgxfw\",\"readOnly\":true,\"mountPath\":\"/var/run/secrets/kubernetes.io/serviceaccount\"}],\"terminationMessagePath\":\"/dev/termination-log\",\"terminationMessagePolicy\":\"File\",\"imagePullPolicy\":\"IfNotPresent\",\"securityContext\":{\"capabilities\":{\"drop\":[\"KILL\",\"MKNOD\",\"SETGID\",\"SETUID\"]},\"runAsUser\":1001050000}}],\"restartPolicy\":\"Never\",\"terminationGracePeriodSeconds\":10,\"activeDeadlineSeconds\":21600,\"dnsPolicy\":\"ClusterFirst\",\"serviceAccountName\":\"deployer\",\"serviceAccount\":\"deployer\",\"shareProcessNamespace\":false,\"securityContext\":{\"seLinuxOptions\":{\"level\":\"s0:c32,c29\"},\"fsGroup\":1001050000},\"imagePullSecrets\":[{\"name\":\"deployer-dockercfg-kl2rz\"}],\"schedulerName\":\"default-scheduler\",\"tolerations\":[{\"key\":\"node.kubernetes.io/not-ready\",\"operator\":\"Exists\",\"effect\":\"NoExecute\",\"tolerationSeconds\":300},{\"key\":\"node.kubernetes.io/unreachable\",\"operator\":\"Exists\",\"effect\":\"NoExecute\",\"tolerationSeconds\":300}],\"priority\":0,\"enableServiceLinks\":true},\"status\":{\"phase\":\"Pending\",\"qosClass\":\"BestEffort\"}},\"oldObject\":null,\"dryRun\":false,\"options\":{\"kind\":\"CreateOptions\",\"apiVersion\":\"meta.k8s.io/v1\"}}}";
		ResponseEntity<String> resp = ctr.validate(jsonRequest);
		System.out.println(" respon \n "+resp.getBody()+"\n");
	}

}

@RestController
@RequestMapping("/")
class SecuredServerController {
	@Value("classpath:template/pass-response.json")
	private Resource passResponseTemplate;

	@Value("classpath:template/failed-response.json")
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

		//System.out.println(" json request is \n" + jsonRequest + "\n");
		JsonNode jsonNode = null;
		String responseBody = null;
		try {
			jsonNode = objectMapper.readTree(jsonRequest);
			String apiVersion = jsonNode.at("/apiVersion").asText();
			String requestId = jsonNode.at("/request/uid").asText();
			String failed = getFailedPayload(apiVersion,requestId);
			//String passed = getPassedPayload(apiVersion,requestId);
			responseBody = failed;

			//System.out.println(" failed is \n" + failed + "\n");
			System.out.println(" responseBody is \n" + responseBody + "\n");
			
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
		//System.out.println("template "+template);
		//String renderedTemplate = jinjava.render(template, context);

		return template;
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
		System.out.println(" getPassedPayload Context "+context);
		String renderedTemplate = jinjava.render(template, context);

		return renderedTemplate;
	}
}
