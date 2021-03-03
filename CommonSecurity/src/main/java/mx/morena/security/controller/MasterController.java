package mx.morena.security.controller;

import javax.servlet.http.HttpServletRequest;

public class MasterController {

	protected int getPerfil(HttpServletRequest request) {

		return (int) request.getSession().getAttribute("Perfil");
		
	}

}
