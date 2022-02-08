/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.CustomPasswordField;
import servidor.usuario.Consultar;
import servidor.usuario.Usuario;
import util.FxDialogs;
import util.UtilStage;

/**
 *
 * @author CLIENTE
 */
public class LoginController implements Initializable {

    public static Stage stagePrimary;

    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Hyperlink linkRestaurarBD;
    
    
    public static String nombre_usuario, nombre_administrador;

    public boolean registrarUsuario = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnIniciarSesion.setOnAction((ActionEvent event) -> {
            try {

                clickBtnIniciarSesion(registrarUsuario);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error al iniciar ventanas", ex);
            }
        });
        txtNombre.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    clickBtnIniciarSesion(registrarUsuario);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        txtPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    clickBtnIniciarSesion(registrarUsuario);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        linkRestaurarBD.setOnAction(event -> actionLinkRestore());
    }

    private void actionLinkRestore(){
        Parent _root;
        Scene _scene;
        LoginAdminController _controller;
        FXMLLoader _loader;
        Stage _stage;

        _stage = new Stage();
        _loader = new FXMLLoader(getClass().getResource("/ventanas/LoginAdmin.fxml"));

        try {
            _root = _loader.load();
            _controller = _loader.getController();
            _scene = new Scene(_root);

            _controller.contexto = "restore";
            _stage.setScene(_scene);
            _stage.getIcons().add(UtilStage.getIconStage());
            _stage.initModality(Modality.APPLICATION_MODAL);
            _stage.setResizable(false);
            _controller.stageDialog = _stage;
            _stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void clickBtnIniciarSesion(boolean _registrar_producto) throws SQLException, IOException {

        final String user = txtNombre.getText();
        final String password = txtPassword.getText();

        ingresar(user, password, _registrar_producto);

    }

    //Metodo para ingresar al sistema
    private void ingresar(String user, String password, boolean _registrar_producto) throws SQLException, IOException {

        Consultar cons_usuario = new Consultar();
        cons_usuario.consultarUsuarioPorUsuario(user);

        if (cons_usuario.usuarioEntontrado()) {
            if (cons_usuario.getPassword().equals(password)) {
                if (cons_usuario.getEstado().equals("Activo")) {
                    if (!_registrar_producto) {
                        switch (cons_usuario.getPrivilegio()) {
                            case "Administrador":
                                nombre_administrador = cons_usuario.getUsuario();
                                iniciarVentana(cons_usuario.getPrivilegio());
                                break;
                            case "Usuario":
                                nombre_usuario = cons_usuario.getUsuario();
                                iniciarVentana(cons_usuario.getPrivilegio());
                                break;
                            default:
                                break;

                        }
                    } else {
                        switch (cons_usuario.getPrivilegio()) {
                            case "Administrador":
                                nombre_administrador = cons_usuario.getUsuario();
                                iniciarVentana(cons_usuario.getPrivilegio());
                                break;
                            default:
                                break;
                        }
                    }

                } else {
                    FxDialogs.showWarning("", "Usuario inactivo");
                }
            } else {
                FxDialogs.showError("", "Contraseña incorrecta");
            }
        } else {
            FxDialogs.showError("", "El usuario no existe");

        }

    }

    //inicia y muestra la ventana despues de hacer login
    private void iniciarVentana(String privilegio) throws IOException {
        if (privilegio.equals("Administrador")) {
            mostrarVentanaAdministrador();
        } else if (privilegio.equals("Usuario")) {
            mostrarVentanaUsuario();
        }
    }

    //metodo para mostrar la ventana Administrador
    private void mostrarVentanaAdministrador() throws IOException {

        Parent administrador = FXMLLoader.load(getClass().getResource("/ventanas/Admin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(administrador);
        stage.getIcons().add(UtilStage.getIconStage());
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
        LoginController.stagePrimary.close();

    }

    //metodo para mostrar la ventana Usuario
    private void mostrarVentanaUsuario() throws IOException {

        Parent usuario;
        FXMLLoader loader;
        PrincipalController controller;
        Stage stage;
        Scene scene;

        loader = new FXMLLoader(getClass().getResource("/ventanas/Principal.fxml"));
        usuario = loader.load();
        controller = loader.getController();
        stage = new Stage();
        scene = new Scene(usuario);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.F1) {
                controller.focusedTfCodigo();
            }
            if (event.getCode() == KeyCode.F2) {
                controller.focusedTfNombre();
            }
            if (event.getCode() == KeyCode.F5) {
                try {
                   
                    controller.actualizarTableProductosRegistrados();
                } catch (Exception e) {
                    FxDialogs.showError("", "Error al actualizar la tabla productos\n" + e.getMessage());
                }

            }
        });
        stage.getIcons().add(UtilStage.getIconStage());
        stage.setTitle("Mi tienda - Sesión de " + nombre_usuario);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        controller.setStage(stage);
        PrincipalController.setController(controller);
        controller.modificarInicioDeSesion(nombre_usuario);
        stage.setScene(scene);

        stage.show();
        LoginController.stagePrimary.close();
    }

    //metodo para obtener el stage de la clase main
    public static void setStagePrimary(Stage stage) {
        stagePrimary = stage;
    }

}
