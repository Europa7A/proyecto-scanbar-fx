package servidor.usuario;

public class Usuario {

    protected String id, nombres, apellidos, direccion, telefono, privilegio, estado, usuario, password, fecha_ingreso, estado_sesion, fecha_inicio_sesion, fecha_fin_sesion;
    public final static String USUARIO_ACTIVO = "Activo", USUARIO_INACTIVO = "Inactivo";

    public Usuario() {
        id = "";
        nombres = "";
        apellidos = "";
        direccion = "";
        telefono = "";
        privilegio = "";
        estado = "";
        usuario = "";
        password = "";
        fecha_ingreso = "";
        estado_sesion = "";
        fecha_inicio_sesion = "";
        fecha_fin_sesion = "";

    }

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaIngreso() {
        return fecha_ingreso;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public String getEstadoSesion() {
        return estado_sesion;
    }

    public String getFechaInicioSesion() {
        return fecha_inicio_sesion;
    }

    public String getFechaFinSesion() {
        return fecha_fin_sesion;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaIngreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEstadoSesion(String estado_sesion) {
        this.estado_sesion = estado_sesion;
    }

    public void setFechaInicioSesion(String fecha_inicio_sesion) {
        this.fecha_inicio_sesion = fecha_inicio_sesion;
    }

    public void setFechaFinSesion(String fecha_fin_sesion) {
        this.fecha_fin_sesion = fecha_fin_sesion;
    }

}
