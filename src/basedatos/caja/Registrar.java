
package basedatos.caja;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registrar extends Caja{

    public Registrar(String id) {
        super(id);
    }
    
    public boolean registrarCaja(){
        String _sql;
        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        int _index;

        _cn = null;

        try {

            _index = 0;
            _sql = "insert into caja values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(++_index, "0");
            _pst.setString(++_index, usuario);
            _pst.setString(++_index, b200);
            _pst.setString(++_index, b100);
            _pst.setString(++_index, b50);
            _pst.setString(++_index, b20);
            _pst.setString(++_index, b10);
            _pst.setString(++_index, m5);
            _pst.setString(++_index, m2);
            _pst.setString(++_index, m1);
            _pst.setString(++_index, m050);            
            _pst.setString(++_index, m020);           
            _pst.setString(++_index, total_dinero);
            _pst.setString(++_index, total_ventas);
            _pst.setString(++_index, descuadre);
            _pst.setString(++_index, fecha_fin_sesion);
            _pst.setString(++_index, hora_fin_sesion);


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
