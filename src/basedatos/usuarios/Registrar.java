
package basedatos.usuarios;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registrar extends Usuario{
    
    
    public boolean registrarUsuario() throws IOException{
        
        try {
            int registrado;
            Connection cn;
            PreparedStatement pst;
            String sql;
            
            sql = "insert into usuarios values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            cn = new Conexion().establecerConexion();
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, "0");
            pst.setString(2, nombres);
            pst.setString(3, "");
            pst.setString(4, "");
            pst.setString(5, "");
            pst.setString(6, password);
            pst.setString(7, fecha_ingreso);
            pst.setString(8, estado);
            pst.setString(9, privilegio);
            pst.setString(10, usuario);
            pst.setString(11, "nuevo");
            pst.setString(12, "");
            pst.setString(13, "");
            
            
            registrado = pst.executeUpdate();
            
            if(registrado != 0){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
