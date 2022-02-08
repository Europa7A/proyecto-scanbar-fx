package basedatos.productos;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends Producto {

    public Consultar(String id) {
        super(id);
    }

    public boolean consultarProducto() throws IOException {

        Connection cn = new Conexion().establecerConexion();

        try {
            PreparedStatement pst;
            ResultSet rs;

            pst = cn.prepareStatement("select * from productos where id = ?");
            pst.setString(1, id);

            rs = pst.executeQuery();
            
            if(rs.next()){
                nombre = rs.getString("nombre");               
                descripcion = rs.getString("descripcion");
                precio = rs.getString("precio");
                cantidad = rs.getString("cantidad");
                fecha_registro = rs.getString("fecha_registro");
                fecha_vencimiento = rs.getString("fecha_vencimiento");
                registrado_por = rs.getString("registrado_por");
                modificado_por = rs.getString("modificado_por");                
                ruta_img = rs.getString("imagen");
                return true;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Producto[] consultarTodo() {

        String _sql;
        Connection _cn;
        Producto[] _productos;
        PreparedStatement _pst;
        ResultSet _rs;

        _productos = null;
        _cn = null;

        try {

            _sql = "select * from productos";
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();
            int i = 0;

            if (_rs.last()) {

                _productos = new Producto[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _productos[i] = new Producto("");
                        
                        _productos[i].id = _rs.getString("id");
                        _productos[i].nombre = _rs.getString("nombre");
                        _productos[i].descripcion = _rs.getString("descripcion");
                        _productos[i].precio = _rs.getString("precio");
                        _productos[i].cantidad = _rs.getString("cantidad");
                        _productos[i].fecha_registro = _rs.getString("fecha_registro");
                        _productos[i].fecha_vencimiento = _rs.getString("fecha_vencimiento");
                        _productos[i].registrado_por = _rs.getString("registrado_por");
                        _productos[i].modificado_por = _rs.getString("modificado_por");
                        _productos[i].ruta_img = _rs.getString("imagen");                       

                        i++;
                    } while (_rs.next());
                }
            }

        } catch (SQLException | IOException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {

                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return _productos;
    }

}
