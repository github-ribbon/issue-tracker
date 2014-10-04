package com.company.issuetracker.client.cfg;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration 
//Specifies which package to scan
@ComponentScan("com.company.issuetracker")
//Enables Spring's annotations
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

	@Resource
	private Environment env;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);

		PageableHandlerMethodArgumentResolver res=new PageableHandlerMethodArgumentResolver();
		//		res.setOneIndexedParameters(true);

		argumentResolvers.add(res);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
		.useJaf(false)
		//		.ignoreAcceptHeader(true)
		.mediaType("html", MediaType.TEXT_HTML)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.defaultContentType(MediaType.APPLICATION_FORM_URLENCODED);
	}

	@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename("messages");

		return messageSource;
	}

	/**
	 * Allows to use the @RequestBody for the DELETE HTTP request method.
	 * 
	 * @author Venkatesh Mothe
	 * 
	 * See <a href="http://knowledgebrowse.blogspot.com/2013/08/spring-resttemplate-calling-delete.html">solution</a>
	 * See <a href="https://jira.spring.io/browse/SPR-7867">JIRA: Add support for DELETE with body to RestTemplate</a>
	 */
	public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {

		public HttpEntityEnclosingDeleteRequest(final URI uri) {
			super();
			setURI(uri);
		}

		@Override
		public String getMethod() {
			return "DELETE";
		}

		public static void setRestTemplateRequestFactory(RestTemplate restTemplate){
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
				@Override
				protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
					if (HttpMethod.DELETE == httpMethod) {
						return new HttpEntityEnclosingDeleteRequest(uri);
					}
					return super.createHttpUriRequest(httpMethod, uri);
				}
			});
		}
	}


	@Bean 
	public RestTemplate restTemplate(){
		RestTemplate restTemplate=new RestTemplate();
		HttpEntityEnclosingDeleteRequest.setRestTemplateRequestFactory(restTemplate);

		return restTemplate;
	}

	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter(){
		MappingJackson2HttpMessageConverter m=new MappingJackson2HttpMessageConverter();

		ObjectMapper mapper = new ObjectMapper();
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
	 * {@link MappingJacksonJsonView}.
	 */
	public class JsonViewResolver implements ViewResolver {
		public View resolveViewName(String viewName, Locale locale)
				throws Exception {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.setPrettyPrint(true);
			return view;
		}
	} 
}