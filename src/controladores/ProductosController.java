package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.UtilStage;

public class ProductosController implements Initializable {

    @FXML
    private Button btnRegistrar, btnModificar, btnConsultar;

    private Stage stageProducto;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(event -> {
            try {
                mostrarVentanaCodigoBarra(true, false, false);
            } catch (IOException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnModificar.setOnAction(event -> {
            try {
                mostrarVentanaModificar();
            } catch (IOException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnConsultar.setOnAction(event -> {
            try {
                mostrarVentanaCodigoBarra(false, false, true);
            } catch (IOException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setStageProducto(Stage stage) {
        this.stageProducto = stage;
    }

    private void mostrarVentanaCodigoBarra(boolean ventanaRegistrar, boolean ventanaModificar, boolean ventanaConsultar) throws IOException {
        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        CodigoBarraController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/CodigoBarra.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();
        
        controller.ventanaRegistrar = ventanaRegistrar;
        controller.ventanaModificar = ventanaModificar;
        controller.ventanaConsultar = ventanaConsultar;
        
        controller.setStageCodigoBarra(dialogStage);
        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        stageProducto.close();
        dialogStage.show();
    }
    
    private void mostrarVentanaModificar() throws IOException {

        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        ModificarProductosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/ModificarProductos.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();

        dialogStage.getIcons().add(UtilStage.getIconStage());
        controller.setStageDialog(dialogStage);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();
    }

  

}
