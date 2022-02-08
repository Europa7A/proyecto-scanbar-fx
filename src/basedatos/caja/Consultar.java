package basedatos.caja;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends Caja {

    public Consultar(String id) {
        super(id);
    }

    public boolean consultarCaja() {

        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        String _sql;
        ResultSet _rs;

        _cn = null;
        try {

            _sql = "select * from caja where id = " + id;
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();

            if (_rs.next()) {
                usuario = _rs.getString("usuario");
                b200 = _rs.getString("b200");
                b100 = _rs.getString("b100");
                b50 = _rs.getString("b50");
                b20 = _rs.getString("b20");
                b10 = _rs.getString("b10");
                m5 = _rs.getString("m5");
                m2 = _rs.getString("m2");
                m1 = _rs.getString("m1");
                m050 = _rs.getString("m050");
                m020 = _rs.getString("m020");
                total_dinero = _rs.getString("total_dinero");
                total_ventas = _rs.getString("total_ventas");
                descuadre = _rs.getString("descuadre");
                fecha_fin_sesion = _rs.getString("fecha_fin_sesion");
                hora_fin_sesion = _rs.getString("hora_fin_sesion");
                return true;

            }

        } catch (IOException | SQLException ex) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (_cn != null) {
                    _cn.close();
                }

            } catch (SQLException ex1) {
                Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }

    public boolean consultarUltimo() {
        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        String _sql;
        ResultSet _rs;
        
        _cn = null;
        try {

            _sql = "select * from caja";
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();
            
            
            

            if (_rs.last()) {
                usuario = _rs.getString("usuario");
                b200 = _rs.getString("b200");
                b100 = _rs.getString("b100");
                b50 = _rs.getString("b50");
                b20 = _rs.getString("b20");
                b10 = _rs.getString("b10");
                m5 = _rs.getString("m5");
                m2 = _rs.getString("m2");
                m1 = _rs.getString("m1");
                m050 = _rs.getString("m050");
                m020 = _rs.getString("m020");
                total_dinero = _rs.getString("total_dinero");
                total_ventas = _rs.getString("total_ventas");
                descuadre = _rs.getString("descuadre");
                fecha_fin_sesion = _rs.getString("fecha_fin_sesion");
                hora_fin_sesion = _rs.getString("hora_fin_sesion");
               return  true;

            }

        } catch (IOException | SQLException ex) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (_cn != null) {
                    _cn.close();
                }

            } catch (SQLException ex1) {
                Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return  false;
    }

}
