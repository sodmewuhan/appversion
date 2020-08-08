package com.datasensor.fish.appversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableAsync(proxyTargetClass = true)
//@ComponentScan( basePackages = "${application.base-packages}" )
public class AppVersionApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppVersionApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppVersionApplication.class);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory config = new MultipartConfigFactory();
		config.setMaxFileSize("200MB");
		config.setMaxRequestSize("200MB");
		return config.createMultipartConfig();
	}

	//Tomcat large file upload connection reset  支持tomcat的大文件上传
//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
//			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
//				//-1 means unlimited
//				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
//			}
//		});
//		return tomcat;
//	}
}
