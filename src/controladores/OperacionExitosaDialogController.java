
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class OperacionExitosaDialogController implements Initializable {

    @FXML
    private Button btnAceptar;
    
    private Stage stage, stagePreview;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAceptar.setOnAction(event -> actionBtnAceptar());
    }    
    
    private void actionBtnAceptar(){
        stage.close();
        stagePreview.close();
    }
    public void setStagePreview(Stage stage){
        this.stagePreview = stage;
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
}
