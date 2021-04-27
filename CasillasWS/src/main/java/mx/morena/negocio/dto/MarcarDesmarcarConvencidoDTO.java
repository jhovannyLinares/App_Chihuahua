package mx.morena.negocio.dto;

public class MarcarDesmarcarConvencidoDTO {
	
	private Long idConvencido;
	private boolean isNotificado;
	
	public Long getIdConvencido() {
		return idConvencido;
	}
	public void setIdConvencido(Long idConvencido) {
		this.idConvencido = idConvencido;
	}
	public boolean getIsNotificado() {
		return isNotificado;
	}
	public void setIsNotificado(boolean isNotificado) {
		this.isNotificado = isNotificado;
	}
	
}
