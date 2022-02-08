package main;

import controladores.LoginAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import controladores.LoginController;
import static controladores.LoginController.nombre_usuario;
import controladores.PrincipalController;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javax.swing.JOptionPane;
import servidor.dventas.ProductoDetalles;
import servidor.usuario.Consultar;
import servidor.usuario.Usuario;
import util.FxDialogs;
import util.Producto;
import util.UtilStage;

public class ScanBarProjectFX extends Application {

    private static ServerSocket SERVER_SOCKET;

    @Override
    public void start(Stage stage) throws Exception {

        Consultar _consultar;
        Usuario[] _usuarios;
        Usuario _usuario_iniciado;
        ObservableList<Producto> _list;
        boolean _nuevo = false, _iniciado = false, _finalizado = false;

        _consultar = new Consultar();
        _usuarios = _consultar.consultarTodosLosUsuarios();
        _usuario_iniciado = null;
        _list = null;

        if (_usuarios != null) {

            for (int i = 0; i < _usuarios.length; i++) {
                if (_usuarios[i].getEstadoSesion().equals(PrincipalController.ESTADO_INICIADO)) {
                    _iniciado = true;
                    _usuario_iniciado = _usuarios[i];
                }
            }
            if (_iniciado) {

                final String _fecha_actual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                if (_usuario_iniciado.getFechaInicioSesion().equals(_fecha_actual)) {
                    LoginController.nombre_usuario = _usuario_iniciado.getUsuario();
                    _list = Producto.obtenerListaProductosPorFechaUsuarioYSesion(_usuario_iniciado.getFechaInicioSesion(), _usuario_iniciado.getUsuario(), "abierta");

                    mostrarVentanaPrincipal(stage, _list);

                } else {
                    mostrarVentanaLogin(stage);
                }

            } else {
                mostrarVentanaLogin(stage);
            }
        } else {
            FxDialogs.showError("", "Ha ocurrido algún problema con la base de datos.\\nPor favor restaure su archivo de base de datos.");
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

    }

    private void mostrarVentanaLogin(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ventanas/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(UtilStage.getIconStage());
        stage.show();
        LoginController.setStagePrimary(stage);

    }

    private void mostrarVentanaPrincipal(Stage stage, ObservableList<Producto> _list) throws IOException {
        Parent usuario;
        FXMLLoader loader;
        PrincipalController _controller;
        Scene scene;

        loader = new FXMLLoader(getClass().getResource("/ventanas/Principal.fxml"));
        usuario = loader.load();
        _controller = loader.getController();
        stage = new Stage();
        scene = new Scene(usuario);
        
        _controller.setStage(stage);
        PrincipalController.setController(_controller);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.F1) {
                _controller.focusedTfCodigo();
            }
            if (event.getCode() == KeyCode.F2) {
                _controller.focusedTfNombre();
            }
            if (event.getCode() == KeyCode.F5) {
                try {
                    System.out.println("Holasebas");
                    _controller.actualizarTableProductosRegistrados();
                } catch (Exception e) {
                    FxDialogs.showError("", "Error al actualizar la tabla productos\n" + e.getMessage());
                }

            }
        });

        stage.getIcons().add(UtilStage.getIconStage());
        stage.setTitle("Mi tienda - Sesión de " + nombre_usuario);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        
        if (_list != null) {
            _controller.agregarProductoATablaDetalles(_list);
        }

        stage.setScene(scene);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            SERVER_SOCKET = new ServerSocket(7557);
            launch(args);
        } catch (IOException ex) {
            Logger.getLogger(ScanBarProjectFX.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ya existe otra instancia ejecutandosé");
            System.exit(0);
        }

    }

}
