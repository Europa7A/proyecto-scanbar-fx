package basedatos.productos;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registrar extends Producto {

    public Registrar(String id) {
        super(id);
    }

    public boolean registrarProducto() throws IOException {
        Connection cn;
        PreparedStatement pst;
        
        cn = null;
        
        try {
            cn = new Conexion().establecerConexion();
            pst = cn.prepareStatement("insert into productos values (?,?,?,?,?,?,?,?,?,?)");

            pst.setString(1, id);
            pst.setString(2, nombre);
            pst.setString(3, descripcion);
            pst.setString(4, precio);
            pst.setString(5, cantidad);
            pst.setString(6, fecha_registro);
            pst.setString(7, "");
            pst.setString(8, registrado_por);
            pst.setString(9, "");
            pst.setString(10, ruta_img);
            
            int registrado = pst.executeUpdate();

            if (registrado != 0) {
                return true;
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            
            try {
                
                cn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;

    }

}
