package mx.morena.negocio.exception;

public class UsuarioException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4288823933975129028L;

	private int codeError;

	public UsuarioException(String errorMessage, int codeError) {
		super(errorMessage);
		this.codeError = codeError;
	}

	public int getCodeError() {
		return codeError;
	}

	public void setCodeError(int codeError) {
		this.codeError = codeError;
	}


}