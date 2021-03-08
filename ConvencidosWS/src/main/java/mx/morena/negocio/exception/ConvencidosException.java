package mx.morena.negocio.exception;

public class ConvencidosException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codeError;

	public ConvencidosException(String errorMessage, int codeError) {
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
