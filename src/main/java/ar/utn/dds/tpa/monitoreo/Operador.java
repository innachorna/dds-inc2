package ar.utn.dds.tpa.monitoreo;

public class Operador {
    private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Operador(String nombre) {
		super();
		this.nombre = nombre;
	}

	@Override
	public String toString()
	{
		String mensaje = nombre;
		return mensaje;
	}
}
