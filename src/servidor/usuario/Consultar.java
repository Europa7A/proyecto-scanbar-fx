package servidor.usuario;

import java.io.IOException;
import java.sql.SQLException;

public class Consultar extends Usuario {

    private boolean encontrado = false;

    public void consultarUsuarioPorID(String id) throws SQLException, IOException {
        basedatos.usuarios.Consultar consultar = new basedatos.usuarios.Consultar();

        if (consultar.consultarPorID(id)) {
            encontrado = true;
            nombres = consultar.getNombres();
            apellidos = consultar.getApellidos();
            usuario = consultar.getUsuario();
            password = consultar.getPassword();
            telefono = consultar.getTelefono();
            direccion = consultar.getDireccion();
            privilegio = consultar.getPrivilegio();
            estado = consultar.getEstado();
            fecha_ingreso = consultar.getFechaIngreso();
            estado_sesion = consultar.getEstadoSesion();
            fecha_inicio_sesion = consultar.getFechaInicioSesion();
            fecha_fin_sesion = consultar.getFechaFinSesion();
            super.id = consultar.getID();
        }
    }

    public void consultarUsuarioPorUsuario(String nombre) throws SQLException, IOException {
        basedatos.usuarios.Consultar consultar = new basedatos.usuarios.Consultar();

        if (consultar.consultarPorNombreUsuario(nombre)) {
            encontrado = true;
            nombres = consultar.getNombres();
            apellidos = consultar.getApellidos();
            usuario = consultar.getUsuario();
            password = consultar.getPassword();
            telefono = consultar.getTelefono();
            direccion = consultar.getDireccion();
            privilegio = consultar.getPrivilegio();
            estado = consultar.getEstado();
            fecha_ingreso = consultar.getFechaIngreso();
            estado_sesion = consultar.getEstadoSesion();
            fecha_inicio_sesion = consultar.getFechaInicioSesion();
            fecha_fin_sesion = consultar.getFechaFinSesion();
            super.id = consultar.getID();
        }
    }

    public Usuario[] consultarTodosLosUsuarios() throws IOException, SQLException {

        basedatos.usuarios.Consultar _consultar_usuario;
        basedatos.usuarios.Usuario[] _usuarios_bd;
        Usuario[] _usuarios_servidor;
        
        _consultar_usuario = new basedatos.usuarios.Consultar();
        _usuarios_bd = _consultar_usuario.consultarTodosLosUsuarios();
        _usuarios_servidor = null;
        
        if (_usuarios_bd != null) {
            
            _usuarios_servidor = new Usuario[_usuarios_bd.length];
            
            for (int i = 0; i < _usuarios_servidor.length; i++) {
                _usuarios_servidor[i] = new Usuario();
                _usuarios_servidor[i].setId(_usuarios_bd[i].getID());                
                _usuarios_servidor[i].setNombres(_usuarios_bd[i].getNombres());
                _usuarios_servidor[i].setApellidos(_usuarios_bd[i].getApellidos());
                _usuarios_servidor[i].setTelefono(_usuarios_bd[i].getTelefono());
                _usuarios_servidor[i].setDireccion(_usuarios_bd[i].getDireccion());
                _usuarios_servidor[i].setEstado(_usuarios_bd[i].getEstado());
                _usuarios_servidor[i].setPrivilegio(_usuarios_bd[i].getPrivilegio());
                _usuarios_servidor[i].setFechaIngreso(_usuarios_bd[i].getFechaIngreso());
                _usuarios_servidor[i].setPassword(_usuarios_bd[i].getPassword());
                _usuarios_servidor[i].setUsuario(_usuarios_bd[i].getUsuario());
                _usuarios_servidor[i].setEstadoSesion(_usuarios_bd[i].getEstadoSesion());
                _usuarios_servidor[i].setFechaInicioSesion(_usuarios_bd[i].getFechaInicioSesion());               
                _usuarios_servidor[i].setFechaFinSesion(_usuarios_bd[i].getFechaFinSesion());

            }
            
            
        }
        return _usuarios_servidor;
    }

    public boolean usuarioEntontrado() {
        return encontrado;
    }

}
