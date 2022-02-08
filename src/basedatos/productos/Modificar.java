
package basedatos.productos;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Modificar extends Producto {
    
    
    
    public Modificar(String id) {
        super(id);
    }
    
    
    public boolean modificarProducto() throws IOException{
        
        try {
            Connection cn = new Conexion().establecerConexion();
            
            PreparedStatement pst = cn.prepareStatement("update productos set nombre = ?, descripcion = ?, precio = ?, cantidad = ?, fecha_vencimiento = ?, modificado_por = ?, imagen = ? where id = " + id);
            
            pst.setString(1, getNombre());            
            pst.setString(2, getDescripcion());
            pst.setString(3, getPrecio());
            pst.setString(4, getCantidad());
            pst.setString(5, getFechaVencimiento());
            pst.setString(6, getModificadoPor());
            pst.setString(7, getRutaImagen());
            
            if(pst.executeUpdate() != 0){
                return true;
            }     
            
        } catch (SQLException ex) {
            Logger.getLogger(Modificar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

     
}
