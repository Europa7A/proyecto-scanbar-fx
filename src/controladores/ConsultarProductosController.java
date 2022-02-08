
package controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servidor.producto.Consultar;

public class ConsultarProductosController implements Initializable {

    @FXML
    private ImageView imgProducto;
    @FXML
    private TextField tfID, tfNombre, tfUnidades, tfPrecio, tfDescripcion;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    /*Consulta el id del producto para posteriormente colocarlos en los textFields*/
    public void colocarDatosEnTextFields(String id) throws IOException{
        Consultar consultar = new Consultar(id);
        consultar.consultarProducto();
        
        if(consultar.productoEncontrado()){
            Image img;
            
            tfID.setText(id);           
            tfNombre.setText(consultar.getNombre());
            tfUnidades.setText(consultar.getCantidad());
            tfPrecio.setText(consultar.getPrecio());
            tfDescripcion.setText(consultar.getDescripcion());
            
            if(consultar.getRutaImagen().isEmpty()){
                img = new Image(getClass().getResourceAsStream("/assets/icon/no-image.png"));
                imgProducto.setImage(img);
            } else {
                img = new Image("file:/"+consultar.getRutaImagen());
                imgProducto.setImage(img);
            }
            //Image image = new Image("file:/C:/Users/410N50/Pictures/qsitos.png");
                        
            //imgProducto.setImage(new Image(getClass().getResourceAsStream("/assets/icon/no-image.png")));
            
        } 
    }
    
}
