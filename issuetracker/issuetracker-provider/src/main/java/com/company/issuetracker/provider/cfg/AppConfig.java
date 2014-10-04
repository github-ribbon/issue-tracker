package com.company.issuetracker.provider.cfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration 
//Specifies which package to scan
@ComponentScan("com.company.issuetracker")
//Enables Spring's annotations
@EnableWebMvc
@Import({JpaConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);

		PageableHandlerMethodArgumentResolver res=new PageableHandlerMethodArgumentResolver();
		//		res.setOneIndexedParameters(true);

		argumentResolvers.add(res);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
		.useJaf(false)
		//		.ignoreAcceptHeader(true)
		.mediaType("html", MediaType.TEXT_HTML)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.defaultContentType(MediaType.TEXT_HTML);
	}

	@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename("messages");

		return messageSource;
	}

	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter(){
		MappingJackson2HttpMessageConverter m=new MappingJackson2HttpMessageConverter();

		ObjectMapper mapper = new ObjectMapper();
		//Registering Hibernate4Module to support lazy objects
		Hibernate4Module module=new Hibernate4Module();
		//		module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		//		module.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		mapper.registerModule(module);
		m.setObjectMapper(mapper);

		return m;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//Here we add our custom-configured HttpMessageConverter
		converters.add(jacksonConverter());
		super.configureMessageConverters(converters);
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager manager) {

		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		InternalResourceViewResolver r1 = new InternalResourceViewResolver();
		r1.setPrefix("/WEB-INF/views/");
		r1.setSuffix(".jsp");
		r1.setViewClass(JstlView.class);
		resolvers.add(r1);

		JsonViewResolver r2 = new JsonViewResolver();
		resolvers.add(r2);

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}

	/**
	 * View resolver for returning JSON in a view-based system. 
	 */
	public class JsonViewResolver implements ViewResolver {
		public View resolveViewName(String viewName, Locale locale)
				throws Exception {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.setPrettyPrint(true);
			return view;
		}
	} 

	@Bean
	public DozerCustomFieldMapper dozerCustomFieldMappper(){
		return new DozerCustomFieldMapper();
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper(){
		DozerBeanMapper mapper=new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("dozer.xml"));
		mapper.setCustomFieldMapper(dozerCustomFieldMappper());

		return mapper;
	}
}