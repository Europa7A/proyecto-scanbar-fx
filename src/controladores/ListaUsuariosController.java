
package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servidor.usuario.Consultar;
import util.Usuario;


public class ListaUsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tableUsuarios;

    private TableColumn tcID, tcNombre, tcUsuario;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Usuario> datos;
        
        tcID = new TableColumn<Usuario, String> ("Id");
        tcNombre = new TableColumn<Usuario, String>("Nombre");        
        tcUsuario = new TableColumn<Usuario, String>("Usuario");
        datos = FXCollections.observableArrayList(Usuario.obtenerDatos());
        
        tcID.setCellValueFactory(new PropertyValueFactory("id"));       
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcUsuario.setCellValueFactory(new PropertyValueFactory("nombre_usuario"));
        
        tableUsuarios.getColumns().setAll(tcID, tcNombre, tcUsuario);
        tableUsuarios.getItems().setAll(datos);
        
        tableUsuarios.setOnMouseClicked(event -> {
            String id_user = "";
            
            if(event.getClickCount() >= 1){
                id_user = tableUsuarios.getSelectionModel().getSelectedItem().getId();
                
                try {
                    mostrarVentanaModificarUsuario(id_user);
                } catch (IOException | SQLException ex) {
                    Logger.getLogger(ListaUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }    
    
    private void mostrarVentanaModificarUsuario(String id) throws IOException, SQLException{
        FXMLLoader loader;
        Parent root;
        ModificarUsuariosController controller;
        Scene scene;
        Stage stageDialog;
        ObtenerDatosUsuarios datos;
        
        loader = new FXMLLoader(getClass().getResource("/ventanas/ModificarUsuarios.fxml"));
        root = loader.load();
        controller = loader.getController();
        scene = new Scene(root);
        stageDialog = new Stage();
        datos = new ObtenerDatosUsuarios(id);
        
        controller.getTfID().setText(datos.id);
        controller.getTfFechaRegistro().setText(datos.fecha_registro);
        controller.getTfNombre().setText(datos.nombre);
        controller.getTfUsuario().setText(datos.usuario);
        controller.getCbPrivilegios().setValue(datos.privilegios);
        controller.getTfPassword().setText(datos.password);
        controller.getTfDireccion().setText(datos.direccion);
        controller.getCbEstado().setValue(datos.estado);
        controller.getTfTelefono().setText(datos.telefono);
        stageDialog.setScene(scene);
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setResizable(false);
        stageDialog.show();
    }
    /**
     * Obtiene los datos de la base de datos
     */
    private class ObtenerDatosUsuarios{
        
        public String usuario, password, nombre, telefono, direccion, estado, privilegios, fecha_registro, id;
        
        public ObtenerDatosUsuarios(String id) throws SQLException, IOException{
            
            Consultar consultar;
            
            consultar = new Consultar();
            
            consultar.consultarUsuarioPorID(id);
            
            if(consultar.usuarioEntontrado()){
                System.out.println("ASSALALAS AQUI PAPAR");
                usuario = consultar.getUsuario();
                password = consultar.getPassword();
                nombre = consultar.getNombres();
                telefono = consultar.getTelefono();
                direccion = consultar.getDireccion();
                estado = consultar.getEstado();
                privilegios = consultar.getPrivilegio();
                this.id = consultar.getId();
                fecha_registro = consultar.getFechaIngreso();
                
                
            }
            
            
        }

    }
 
 
}
