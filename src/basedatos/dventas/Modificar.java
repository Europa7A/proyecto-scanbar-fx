package basedatos.dventas;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Modificar extends ProductoDetalles {

    public Modificar(String id){
        super.id = id;
    }
    
    public enum StringsModificacion {
        id, codigo_barra, nombre, registrado_por, modificado_por, devolucion, precio, cantidad, total, fecha_registro, hora_registro, sesion;
    }

    public boolean modificarProductoDetallesConValor(StringsModificacion _sm, String _valor) {

        Connection _cn;
        Conexion _cx;
        PreparedStatement _pst;
        String _sql;

        _cn = null;

        try {

            _sql = "update detalles_venta set " + _sm + " = ? where id = " +id;
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(1, _valor);

            if (_pst.executeUpdate() != 0) {
                return true;
            }

        } catch (IOException | SQLException ex) {
            
            Logger.getLogger(Modificar.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            
            if (_cn != null) {
                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Modificar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return false;
    }

}
