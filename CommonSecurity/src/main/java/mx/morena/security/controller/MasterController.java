package mx.morena.security.controller;

import javax.servlet.http.HttpServletRequest;

public class MasterController {

	protected String getPerfil(HttpServletRequest request) {

		return (String) request.getSession().getAttribute("Perfil");
		
	}

}
