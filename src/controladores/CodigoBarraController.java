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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.UtilStage;

public class CodigoBarraController implements Initializable {

    @FXML
    private TextField tfCodigoBarra;
    private Stage stageCodigoBarra;
    public boolean ventanaRegistrar = false, ventanaModificar = false, ventanaConsultar = false;
    public String tipo_ventana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tfCodigoBarra.setOnKeyPressed(event -> {
            if (!tfCodigoBarra.getText().equals("")) {
                if (event.getCode() == KeyCode.ENTER) {
                    mostrarVentanas();
                }
            }
        });

    }

    /**
     * muestra las diferentes ventanas posibles que abre el codigo de barra
     */
    private void mostrarVentanas() {

        if (ventanaRegistrar) {
            try {
                mostrarVentanaRegistrar();
            } catch (IOException ex) {
                Logger.getLogger(CodigoBarraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ventanaModificar) {
            try {
                mostrarVentanaModificar();
            } catch (IOException ex) {
                Logger.getLogger(CodigoBarraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ventanaConsultar) {
            try {
                mostrarVentanaConsultar();
            } catch (IOException ex) {
                Logger.getLogger(CodigoBarraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void mostrarVentanaRegistrar() throws IOException {

        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        RegistrarProductosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/RegistrarProductos.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();

        dialogStage.getIcons().add(UtilStage.getIconStage());
        controller.setTextID(tfCodigoBarra.getText());
        controller.setStageDialog(dialogStage);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        stageCodigoBarra.close();
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
        controller.setTextID(tfCodigoBarra.getText());
        controller.colocarDatosEnTextFields();
        controller.setStageDialog(dialogStage);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        stageCodigoBarra.close();
        dialogStage.show();
    }

    private void mostrarVentanaConsultar() throws IOException {

        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        ConsultarProductosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/ConsultarProductos.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();

        controller.colocarDatosEnTextFields(tfCodigoBarra.getText());
        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        stageCodigoBarra.close();
        dialogStage.show();
    }

    public void setStageCodigoBarra(Stage stage) {
        this.stageCodigoBarra = stage;
    }

}
