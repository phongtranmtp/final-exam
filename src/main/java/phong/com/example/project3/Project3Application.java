package phong.com.example.project3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import phong.com.example.project3.service.SecurityInterceptor;

import java.util.Locale;

@SpringBootApplication
@EnableJpaAuditing

public class Project3Application {

//	@Autowired
//	SecurityInterceptor securityInterceptor ;

	public static void main(String[] args) {
		SpringApplication.run(Project3Application.class, args);
	}

//	public void addInterceptors(InterceptorRegistry registry){
//		registry.addInterceptor(localeChangeInterceptor());
//		registry.addInterceptor(securityInterceptor);
//	}
	@Bean
	public LocaleResolver localeResolver(){
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("en"));
		return slr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

}
