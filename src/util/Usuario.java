
package util;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Clase para obtener los datos de los usuarios 
 * Estos datos son para colocar al tableView para modificar usuarios
 */

public class Usuario {
    
     public StringProperty id;
     public StringProperty nombre;
     public StringProperty nombre_usuario;
     
     public Usuario(String id, String nombre, String username){
         this.id = new SimpleStringProperty(id);
         this.nombre = new SimpleStringProperty(nombre);
         this.nombre_usuario = new SimpleStringProperty(username);
     }

    /**
     * @return the id
     */
    public String getId() {
        return id.get();
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * @return the nombre_usuario
     */
    public String getNombre_usuario() {
        return nombre_usuario.get();
    }
    
    public static Usuario[] obtenerDatos(){
        Usuario [] usuarios = null;
        String sql = "select id, nombre, usuario from usuarios";
        
         try {
             Connection cn;
             PreparedStatement pst;
             ResultSet rs;
             
             cn = new Conexion().establecerConexion();
             pst = cn.prepareStatement(sql);
             rs = pst.executeQuery();
             int i;
             
             try {
                 if(rs.last()){
                     i = 0;
                     usuarios = new Usuario[rs.getRow()];
                     rs.beforeFirst();
                       
                     while (rs.next()) {
                         String _id, _nombre, _nombre_usuario;
                         
                         _id = rs.getString("id");
                         _nombre = rs.getString("nombre");
                         _nombre_usuario = rs.getString("usuario");
                         
                         usuarios[i] = new Usuario(_id, _nombre, _nombre_usuario);
                         
                         i++;
                     }
                 }
             } catch (SQLException e) {
                 Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
             }
             
         } catch (IOException | SQLException ex) {
             Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return usuarios;
        
    }
     
     
    
}
