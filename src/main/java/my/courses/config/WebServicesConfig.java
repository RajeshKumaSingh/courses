package my.courses.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Enable Spring web services
@EnableWs

// Spring configuration class
@Configuration
public class WebServicesConfig extends WsConfigurerAdapter{
	
	// Message Dispatcher Servlet,  Identify to endpoint, handle all soap request
	// pass applicationContext and url to expose ws
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
		MessageDispatcherServlet dispatcher = new MessageDispatcherServlet();
		dispatcher.setApplicationContext(ctx);
		dispatcher.setTransformWsdlLocations(true);  // for wsdl
		return new ServletRegistrationBean<MessageDispatcherServlet>(dispatcher, "/ws/*");
	}
	
	// ws/courses.wsdl
	// PortType - CoursePort
	// Namespace
	// courses-details.xsd
	
	@Bean(name="courses")  //name same as courses.wsdl filename
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition defination = new DefaultWsdl11Definition();
		defination.setPortTypeName("CoursePort");
		defination.setTargetNamespace("http://my/courses/course-details");
		defination.setLocationUri("/ws");
		defination.setSchema(coursesSchema);
		return defination;
	}
	
	// Define schema
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
	
	// XWS Security Interceptor
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	public CallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("user","password");
		handler.setUsersMap(userMap);
		return handler;
	}
	
	//Interceptors.add -> XwsSecurityInterceptor
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}
	
	
}
