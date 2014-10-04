package com.company.issuetracker.client.cfg;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);

		ctx.setServletContext(servletContext);			

		//		Manages the lifecycle of the root application context
		servletContext.addListener(new ContextLoaderListener(ctx));	     

		//		Registers and maps the dispatcher servlet
		ServletRegistration.Dynamic dispatcher=servletContext.addServlet("appServlet", new DispatcherServlet(ctx));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");		

		DelegatingFilterProxy delegatingFilterProxy=new DelegatingFilterProxy();		

		//	    Registers Spring security filter
		FilterRegistration.Dynamic springSecurityFilterChain=servletContext.addFilter("springSecurityFilterChain",delegatingFilterProxy);
		springSecurityFilterChain.addMappingForUrlPatterns(null, true, "/*");

		//	    Registers encoding filter
		CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		FilterRegistration.Dynamic encodingFilter=servletContext.addFilter("encodingFilter",characterEncodingFilter);
		encodingFilter.addMappingForUrlPatterns(null,true,"/*"); 
	}
}