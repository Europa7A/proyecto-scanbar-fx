
package basedatos.usuarios;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Modificar extends Usuario{
    
    
    public Modificar(String id){
        super.id = id;
    }
    
    public boolean modificarUsuario(){
        
        Connection cn;
        PreparedStatement pst;
        Conexion cx;
        String sql;
        
        cx = new Conexion();
        sql = "update usuarios set nombre = ?, apellido = ?, telefono = ?, direccion = ?, password = ?, estado = ?, tipo = ?, usuario = ?, estado_sesion = ?, fecha_inicio_sesion = ?, fecha_fin_sesion = ? where id = " + id;
        
        try {
            cn = cx.establecerConexion();
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, telefono);
            pst.setString(4, direccion);
            pst.setString(5, password);
            pst.setString(6, estado);
            pst.setString(7, privilegio);
            pst.setString(8, usuario);
            pst.setString(9, estado_sesion);
            pst.setString(10, fecha_inicio_sesion);
            pst.setString(11, fecha_fin_sesion);
            

            
            if(pst.executeUpdate() != 0){
                return true;
            }
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Modificar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
