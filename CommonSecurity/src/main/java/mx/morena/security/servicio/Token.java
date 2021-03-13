package mx.morena.security.servicio;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token extends JWTAuthorizationFilter {
	
	private static final Integer milisegundos = 6000000;

	public static final Integer segundos = (milisegundos / 1000);

	public static String create(String username, long idPerfil, long idUsuario) {
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("morenaJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("perfil", idPerfil)
				.claim("usuario", idUsuario)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + milisegundos))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

		return "Bearer " + token;
	}

}