package ar.utn.dds.tpa.monitoreo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Incidencia {
    private String codigoDeCatalogo;
    private LocalDate fechaCreacion;
    private String descripcion;
    private Estado estado;
    private Operador operador;
    private String usuario;
    private LocalDate fechaCierre;
    private String motivoRechazo;
    private String lugar;

    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    
    public Incidencia(String codigoDeCatalogo, String fechaCreacion,
			String descripcion, Estado estado, Operador operador, String usuario, String fechaCierre,
			String motivoRechazo, String lugar){
		super();
		this.codigoDeCatalogo = codigoDeCatalogo;   
		this.fechaCreacion = LocalDate.parse(fechaCreacion,formatter );
		this.descripcion = descripcion;
		this.estado = estado;
		this.operador = operador;
		this.usuario = usuario;
		this.fechaCierre = LocalDate.parse(fechaCierre,formatter );
		this.motivoRechazo = motivoRechazo;
		this.lugar = lugar;
	}


	public Incidencia() {
		super();
	}



	public String getCodigoDeCatalogo() {
		return codigoDeCatalogo;
	}
	
	
	public void setCodigoDeCatalogo(String codigoDeCatalogo) {
		this.codigoDeCatalogo = codigoDeCatalogo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	


	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
	

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}


	public String getLugar() {
		return lugar;
	}


	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	@Override
	public String toString()
	{
		String mensaje = "\n\nCodigo de Catalogo: "+codigoDeCatalogo+"\nFecha Creacion: "+fechaCreacion+"\nDescripcion: "+descripcion+"\nEstado: "+estado+"\nOperador: "+operador+"\nUsuario: "+usuario+"\nFechaCierre: "+fechaCierre+"\nMotivoRechazo: "+motivoRechazo+"\nLugar: "+lugar;
		return mensaje;
	}
}


