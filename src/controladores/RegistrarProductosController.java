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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import servidor.producto.Registrar;
import util.FxDialogs;
import util.UtilStage;

public class RegistrarProductosController implements Initializable {

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField tfID, tfNombre, tfPrecio, tfCantidad, tfDescripcion;

    private Stage stageDialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(event -> {
            try {
                actionBtnRegistrar();
            } catch (IOException ex) {
                Logger.getLogger(RegistrarProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void actionBtnRegistrar() throws IOException {
        String id, usuario;
        id = tfID.getText();
        usuario = LoginController.nombre_administrador;
        Registrar registrar = new Registrar(id);

        registrar.setNombre(tfNombre.getText());
        registrar.setCantidad(tfCantidad.getText());
        registrar.setDescripcion(tfDescripcion.getText());
        registrar.setPrecio(tfPrecio.getText());
        registrar.setRegistradoPor(usuario);
        registrar.registrar();
        if (registrar.estaRegistrado()) {
            PrincipalController.getController().actualizarTableProductosRegistrados();
            FxDialogs.showInformation("", "Registro exitoso");
        } else {
            FxDialogs.showWarning("", "Este producto ya se encuentra registrado");
        }

    }

    private void mostrarDialogOperacionExitosa() throws IOException {
        Stage dialogOperacion;
        Scene scene;
        Parent root;
        OperacionExitosaDialogController controller;
        FXMLLoader loader;

        loader = new FXMLLoader(getClass().getResource("/ventanas/OperacionExitosaDialog.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogOperacion = new Stage();
        controller = loader.getController();

        controller.setStage(dialogOperacion);
        controller.setStagePreview(stageDialog);
        dialogOperacion.setScene(scene);
        dialogOperacion.getIcons().add(UtilStage.getIconStage());
        dialogOperacion.setResizable(false);
        dialogOperacion.initModality(Modality.APPLICATION_MODAL);
        dialogOperacion.show();
    }

    public void setTextID(String id) {
        tfID.setText(id);
    }

    public void setStageDialog(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

}
