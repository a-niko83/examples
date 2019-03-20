package org.grabber.lg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcSun15HttpTransportFactory;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.serializer.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

@Configuration
@Slf4j
public class SpringConfig {

	@Value("${server.proxy.host}")
	private String proxyHost;
	@Value("${server.proxy.port}")
	private Integer proxyPort;

	@Bean
	RestTemplate getRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		if (proxyHost != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			requestFactory.setProxy(proxy);
			log.info("Proxy config found: {}", proxy);
		}
		return new RestTemplate(requestFactory);
	}


	@Bean
	XmlRpcClient getRpcClient() {
		XmlRpcClient client = new XmlRpcClient();
		XmlRpcSun15HttpTransportFactory transportFactory = new XmlRpcSun15HttpTransportFactory(client);
		if (proxyHost != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			transportFactory.setProxy(proxy);
			log.info("Proxy config found: {}", proxy);
		}
		client.setTransportFactory(transportFactory);

		return client;
	}

}
