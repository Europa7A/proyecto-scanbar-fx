package basedatos.dventas;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registrar extends ProductoDetalles {

    public boolean registrarProductoDetalles() {

        String _sql;
        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        int _index;

        _cn = null;

        try {

            _index = 0;
            _sql = "insert into detalles_venta values(?,?,?,?,?,?,?,?,?,?,?,?)";
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(++_index, id);
            _pst.setString(++_index, codigo_barra);
            _pst.setString(++_index, nombre);
            _pst.setString(++_index, registrado_por);
            _pst.setString(++_index, "");
            _pst.setString(++_index, "");
            _pst.setString(++_index, precio);
            _pst.setString(++_index, cantidad);
            _pst.setString(++_index, total);
            _pst.setString(++_index, fecha_registro);
            _pst.setString(++_index, hora_registro);            
            _pst.setString(++_index, sesion);

            if (_pst.executeUpdate() != 0) {
                return true;
            }

        } catch (IOException | SQLException e) {

            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            try {
                if (_cn != null) {
                    _cn.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;

    }

}
