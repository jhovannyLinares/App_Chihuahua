package mx.morena.security.controller;

import javax.servlet.http.HttpServletRequest;

public class MasterController {

	protected long getPerfil(HttpServletRequest request) {

		return toLong(request.getSession().getAttribute("Perfil"));

	}

	protected long getUsuario(HttpServletRequest request) {

		return toLong(request.getSession().getAttribute("Usuario"));

	}
	
	private long toLong(Object o) {
		
		String stringToConvert = String.valueOf(o);
	    long convertedLong = Long.parseLong(stringToConvert);
	    
	    return convertedLong;

	}
	

}
