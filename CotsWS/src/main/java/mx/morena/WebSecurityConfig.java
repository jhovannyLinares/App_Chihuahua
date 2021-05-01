package mx.morena;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import mx.morena.security.servicio.JWTAuthorizationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Override
	    public void configure(WebSecurity webSecurity) throws Exception {
	        webSecurity.ignoring().antMatchers("/swagger-ui");
	    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/swagger-ui").permitAll()
				.antMatchers("/cots").authenticated()
				.antMatchers("/reportes").authenticated()
				.antMatchers("/instalacionCasillas").authenticated()
				.antMatchers("/reporteCasillas").authenticated()
				.antMatchers("/casillasAsignadas").authenticated()
				.antMatchers("/horaCierre").authenticated()
				.antMatchers("/reporte").authenticated()
				.antMatchers("/casillas").authenticated()
				.anyRequest().authenticated()
				.and().addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
