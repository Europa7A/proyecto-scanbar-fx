package basedatos.usuarios;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends Usuario {

    //Metodo para consultar usuarios atravez de un ID
    public boolean consultarPorID(String id) throws SQLException, IOException {

        Connection cn = new Conexion().establecerConexion();
        try {

            PreparedStatement pst = cn.prepareStatement("select * from usuarios where id = ?");
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                this.id = rs.getString("id");
                nombres = rs.getString("nombre");
                apellidos = rs.getString("apellido");
                telefono = rs.getString("telefono");
                direccion = rs.getString("direccion");
                password = rs.getString("password");
                estado = rs.getString("estado");
                privilegio = rs.getString("tipo");
                usuario = rs.getString("usuario");
                fecha_ingreso = rs.getString("fecha_ingreso");
                estado_sesion = rs.getString("estado_sesion");
                fecha_inicio_sesion = rs.getString("fecha_inicio_sesion");
                fecha_fin_sesion = rs.getString("fecha_fin_sesion");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, "Error al consultar usuarios", ex);
        } finally {
            cn.close();
        }
        return false;
    }

    //Metodo para consultar usuarios atravez de nombre de usuario
    public boolean consultarPorNombreUsuario(String username) throws SQLException, IOException {
        Connection cn = new Conexion().establecerConexion();
        try {

            PreparedStatement pst = cn.prepareStatement("select * from usuarios where usuario = ?");
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                id = rs.getString("id");
                nombres = rs.getString("nombre");
                apellidos = rs.getString("apellido");
                telefono = rs.getString("telefono");
                direccion = rs.getString("direccion");
                password = rs.getString("password");
                estado = rs.getString("estado");
                privilegio = rs.getString("tipo");
                usuario = rs.getString("usuario");
                fecha_ingreso = rs.getString("fecha_ingreso");
                estado_sesion = rs.getString("estado_sesion");
                fecha_inicio_sesion = rs.getString("fecha_inicio_sesion");
                fecha_fin_sesion = rs.getString("fecha_fin_sesion");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, "Error al consultar usuarios", ex);
        } finally {
            cn.close();
        }
        return false;
    }

    public Usuario[] consultarTodosLosUsuarios() throws IOException, SQLException {

        Connection _cn;
        Usuario[] _usuarios;
        PreparedStatement _pst;
        ResultSet _rs;
        
        _usuarios = null;
        _cn = new Conexion().establecerConexion();

        try {
            _pst = _cn.prepareStatement("select * from usuarios");
            _rs = _pst.executeQuery();
            int i = 0;
            

            if (_rs.last()) {

                _usuarios = new Usuario[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _usuarios[i] = new Usuario();
                        _usuarios[i].setID(_rs.getString("id"));
                        _usuarios[i].setNombres(_rs.getString("nombre"));
                        _usuarios[i].setApellidos(_rs.getString("apellido"));
                        _usuarios[i].setTelefono(_rs.getString("telefono"));
                        _usuarios[i].setDireccion(_rs.getString("direccion"));
                        _usuarios[i].setPassword(_rs.getString("password"));
                        _usuarios[i].setFechaIngreso(_rs.getString("fecha_ingreso"));
                        _usuarios[i].setEstado(_rs.getString("estado"));
                        _usuarios[i].setPrivilegio(_rs.getString("tipo"));
                        _usuarios[i].setUsuario(_rs.getString("usuario"));
                        _usuarios[i].setEstadoSesion(_rs.getString("estado_sesion"));
                        _usuarios[i].setFechaInicioSesion(_rs.getString("fecha_inicio_sesion"));
                        _usuarios[i].setFechaFinSesion(_rs.getString("fecha_fin_sesion"));
                        i++;
                    } while (_rs.next());
                }
            }
            

        } catch (SQLException e) {
            System.out.println("Error al consultar todos los usuarios: " + e);
        } finally{
            _cn.close();
        }
        return _usuarios;
    }

}
