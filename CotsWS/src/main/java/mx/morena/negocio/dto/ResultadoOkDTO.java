package mx.morena.negocio.dto;

public class ResultadoOkDTO {

	private Integer code;
	private String status;

	public ResultadoOkDTO(Integer code, String status) {
		this.code = code;
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
