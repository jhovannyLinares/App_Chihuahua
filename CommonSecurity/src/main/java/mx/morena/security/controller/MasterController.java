package mx.morena.security.controller;

import javax.servlet.http.HttpServletRequest;

public class MasterController {

	protected long getPerfil(HttpServletRequest request) {

		return (long) request.getSession().getAttribute("Perfil");

	}

	protected long getUsuario(HttpServletRequest request) {

		return (long) request.getSession().getAttribute("usuario");

	}

}
