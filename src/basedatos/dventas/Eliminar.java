
package basedatos.dventas;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Eliminar extends ProductoDetalles{
    
    public Eliminar(String _id){
       super.id = _id; 
    }
    
    public boolean eliminarProducto(){
        
        
        Connection _cn;
        Conexion _cx;
        PreparedStatement _pst;
        String _sql;
        
        
        try {          
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _sql = "delete from detalles_venta where id = " + id;
            _pst = _cn.prepareStatement(_sql);
            final int _execute_update = _pst.executeUpdate();
            System.out.println(_execute_update + " : number");
            if(_execute_update != 2){
                
                return true;
            }
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
}
