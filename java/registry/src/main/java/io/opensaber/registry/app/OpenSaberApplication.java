package io.opensaber.registry.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@ComponentScan(basePackages = { "io.opensaber.registry", "io.opensaber.pojos"})
public class OpenSaberApplication {
	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(OpenSaberApplication.class);
		if(args.length != 0 && args[0].equals(WebApplicationType.NONE.toString())){
			application.setWebApplicationType(WebApplicationType.NONE);
		} else {
			application.setWebApplicationType(WebApplicationType.SERVLET);
		}

		context = application.run(args);
	}

	public static ApplicationContext getContext() {
		return context;
	}

	@Value("${cors.allowedOrigin}")
	public String corsAllowedOrigin;

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(corsAllowedOrigin);
		config.addAllowedHeader("*");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("GET");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}
