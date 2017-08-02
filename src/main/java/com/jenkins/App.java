package com.jenkins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.offbytwo.jenkins.JenkinsServer;

/**
 * @author zhangjinyang
 *
 */
public class App {
	static String url = "http://localhost:8080/jenkins";
	static JenkinsServer server;
	static StringBuilder build = new StringBuilder();
	static String name = "admin";
	static String password = "admin";
	static {
		try {
			server = new JenkinsServer(new URI(url), name, password);
			InputStream in = App.class.getResourceAsStream("config.xml");
			InputStreamReader read = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				build.append(lineTxt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String jobName = "test-job-" + UUID.randomUUID().toString();
		try {
			//创建job
			server.createJob(jobName, build.toString(), false);
			//自定义属性
			Map<String, String> map = new HashMap<String, String>();
			map.put("qq", "11111111");
			//开始build
			server.getJob(jobName).build(map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
