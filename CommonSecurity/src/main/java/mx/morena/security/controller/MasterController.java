package mx.morena.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MasterController {

	protected final Logger logger = Logger.getLogger(this.getClass());

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
