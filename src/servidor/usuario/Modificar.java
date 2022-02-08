package servidor.usuario;

public class Modificar extends Usuario {

    private boolean modificado;

    public Modificar(String id) {
        super.id = id;
    }

    public void moficiarUsuario() {
        basedatos.usuarios.Modificar modificar = new basedatos.usuarios.Modificar(id);
        modificar.setNombres(nombres);
        modificar.setApellidos(apellidos);
        modificar.setTelefono(telefono);
        modificar.setDireccion(direccion);
        modificar.setPassword(password);
        modificar.setEstado(estado);
        modificar.setPrivilegio(privilegio);
        modificar.setUsuario(usuario);
        modificar.setEstadoSesion(estado_sesion);
        modificar.setFechaInicioSesion(fecha_inicio_sesion);
        modificar.setFechaFinSesion(fecha_fin_sesion);

        if (modificar.modificarUsuario()) {
            modificado = true;
        }
    }

    public boolean usuarioModificado() {
        return modificado;
    }

}
