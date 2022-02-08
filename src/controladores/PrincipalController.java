package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import servidor.dventas.ProductoDetalles;
import servidor.dventas.Registrar;
import servidor.producto.Consultar;
import servidor.producto.Modificar;
import util.FxDialogs;
import util.InventarioPDF;
import util.Producto;
import util.UtilStage;

public class PrincipalController implements Initializable {

    public TableView<Producto> getTableProductos() {
        return tableProductos;
    }

    public TableView<Producto> getTableDetalles() {
        return tableDetalles;
    }

    @FXML
    private TableView<Producto> tableProductos, tableDetalles, tableProductosRegistrados;
    @FXML
    private Button btnConfirmar, btnVerTotal, btnAnadir;
    @FXML
    private TextField tfCodigo, tfDineroActual, tfNombre, tfPrecio;
    @FXML
    private MenuItem item_eliminar, item_intercambiar, item_devolver;
    @FXML
    private Hyperlink menuCerrarSesion, menuInventarioProductos, menuRegistrarProductos, menuRestaurar, menuBackup;

    private static PrincipalController controller;
    private Stage stageDialog;

    private ObservableList<Producto> datos;
    private TableColumn tcProductosImagen, tcProductosNombre, tcProductoDescripcion, tcProductosPrecio, tcProductosUnidadesDisponibles, tcProductosCantidad;
    private TableColumn tcDetallesNombre, tcDetallesCantidad, tcDetallesTotal;
    private TableColumn tcID, tcNombre, tcDescripcion, tcPrecio, tcCantidad;
    private double monto_actual_detalles_venta;

    public final static String ESTADO_INICIADO = "iniciado", ESTADO_FINALIZADO = "finalizado", ESTADO_NUEVO = "nuevo";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tcProductosImagen = new TableColumn<Producto, ImageView>("Imagen");
        tcProductosNombre = new TableColumn<Producto, String>("Nombre");
        tcProductoDescripcion = new TableColumn<Producto, String>("Descripción");

        tcProductosPrecio = new TableColumn<Producto, String>("Precio (Bs.)");
        tcProductosUnidadesDisponibles = new TableColumn<Producto, String>("Unidades disponibles");
        tcProductosCantidad = new TableColumn<Producto, Spinner>("Cantidad");

        tcDetallesNombre = new TableColumn<Producto, String>("Nombre");
        tcDetallesCantidad = new TableColumn<Producto, String>("Cantidad");
        tcDetallesTotal = new TableColumn<Producto, String>("Total (Bs.)");

        tcID = new TableColumn<Producto, String>("Código de barra");
        tcNombre = new TableColumn<Producto, String>("Nombre");
        tcDescripcion = new TableColumn<Producto, String>("Descripcion");
        tcPrecio = new TableColumn<Producto, String>("Precio");
        tcCantidad = new TableColumn<Producto, String>("Cantidad");

        tcProductosImagen.setCellValueFactory(new PropertyValueFactory("image"));
        tcProductosNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcProductoDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        tcProductosPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcProductosCantidad.setCellValueFactory(new PropertyValueFactory("spinner"));
        tcProductosUnidadesDisponibles.setCellValueFactory(new PropertyValueFactory("unidades_disponibles"));

        //tcDetallesImagen.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcDetallesNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcDetallesCantidad.setCellValueFactory(new PropertyValueFactory("cantidad_int"));
        tcDetallesTotal.setCellValueFactory(new PropertyValueFactory("total_dbl"));

        tcID.setEditable(true);
        tcID.setCellValueFactory(new PropertyValueFactory("codigo_de_barra"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory("unidades_disponibles"));

        final int _ancho_column = 140;
        tcProductosPrecio.setPrefWidth(_ancho_column);
        tcProductosImagen.setPrefWidth(_ancho_column);
        tcProductosNombre.setPrefWidth(_ancho_column);
        tcProductosCantidad.setPrefWidth(_ancho_column);
        tcProductosUnidadesDisponibles.setPrefWidth(_ancho_column);

        tableProductosRegistrados.setEditable(true);
        tableProductosRegistrados.getSelectionModel().setCellSelectionEnabled(true);
        tableProductosRegistrados.getColumns().addAll(tcID, tcNombre, tcDescripcion, tcPrecio, tcCantidad);
        tableDetalles.getColumns().addAll(tcDetallesNombre, tcDetallesCantidad, tcDetallesTotal);
        tableProductos.getColumns().addAll(tcProductosImagen, tcProductosNombre, tcProductoDescripcion, tcProductosPrecio, tcProductosUnidadesDisponibles, tcProductosCantidad);
        final Producto[] _list_product = Producto.obtenerDatosParaTableProductosRegistrados();

        if (_list_product != null) {
            tableProductosRegistrados.getItems().addAll(_list_product);
        }

        tableProductosRegistrados.setOnKeyReleased(evt -> {
            if (evt.isControlDown() && evt.getCode() == KeyCode.C) {
                List<TablePosition> selectedCells = tableProductosRegistrados.getSelectionModel().getSelectedCells();
                if (!selectedCells.isEmpty()) {
                    TablePosition selectedCell = selectedCells.get(0);
                    if (selectedCell.getTableColumn() == tcID) {
                        String value = tcID.getCellData(selectedCell.getRow()).toString();
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putString(value);
                        clipboard.setContent(content);
                    }
                }
            }
        });
        tfCodigo.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    agregarItems();
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tfNombre.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {

                actionBtnAnadir();

            }
        });
        tfPrecio.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {

                actionBtnAnadir();

            }
        });
        btnConfirmar.setOnAction(event -> actionBtnConfirmar());
        btnVerTotal.setOnAction(event -> actionBtnVerTotal());
        item_eliminar.setOnAction(event -> eliminarItem());
        item_intercambiar.setOnAction(event -> intercambiarItem());
        item_devolver.setOnAction(event -> devolverItem());
        //menuCerrarSesion.setOnAction(event -> actionMenuCerrarSesion());        
        menuCerrarSesion.setOnAction(event -> actionMenuCerrarSesion());
        menuInventarioProductos.setOnAction(event -> actionMenuInventarioProducto());
        menuRegistrarProductos.setOnAction(event -> actionMenuRegistrarProductos());
        menuBackup.setOnAction(event -> actionMenuBackup());
        menuRestaurar.setOnAction(event -> actionMenuRestore());
        btnAnadir.setOnAction(event -> actionBtnAnadir());

    }

    /**
     * Metodo para el evento MenuItem item_intercambiar
     */
    private void intercambiarItem() {
        final int index = tableDetalles.getSelectionModel().getSelectedIndex();
        if (index >= 0) {

            Producto _producto;

            _producto = tableDetalles.getItems().get(index);
            _producto.setIndex(index);
            mostrarVentanaItercambiarProducto(_producto);
        }
    }

    /**
     * Metodo para el evento MenuItem item_devolver
     */
    private void devolverItem() {
        System.out.println(" ACAA");
        final int index = tableDetalles.getSelectionModel().getSelectedIndex();
        if (index >= 0) {

            Producto _producto;

            _producto = tableDetalles.getItems().get(index);

            _producto.setIndex(index);
            mostrarVentanaDevolverProducto(_producto);
        }
    }

    private void actionMenuBackup() {
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

            _controller.contexto = "backup";
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

    private void actionMenuRestore() {
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

    private void actionMenuInventarioProducto() {
        InventarioPDF.crearReporte();
        FxDialogs.showInformation("", "Inventario PDF creado");
    }

    private void actionMenuRegistrarProductos() {
        try {
            mostrarVentanaLogin();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarTableProductosRegistrados() {
        tableProductosRegistrados.getItems().clear();
        tableProductosRegistrados.getItems().addAll(Producto.obtenerDatosParaTableProductosRegistrados());
    }

    private void mostrarVentanaLogin() throws IOException {

        Parent _root;
        Scene _scene;
        LoginController _controller;
        FXMLLoader _loader;
        Stage _stage;

        _stage = new Stage();
        _loader = new FXMLLoader(getClass().getResource("/ventanas/Login.fxml"));
        _root = _loader.load();
        _controller = _loader.getController();
        _scene = new Scene(_root);

        _controller.registrarUsuario = true;
        _stage.setScene(_scene);
        _stage.getIcons().add(UtilStage.getIconStage());
        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.setResizable(false);
        _stage.show();
        LoginController.setStagePrimary(_stage);

    }

    private void mostrarVentanaItercambiarProducto(Producto _producto) {
        Stage stageDialog;
        Scene scene;
        Parent root;
        IntercambiarProductoController _controller;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(getClass().getResource("/ventanas/IntercambiarProducto.fxml"));
            root = loader.load();
            _controller = loader.getController();
            scene = new Scene(root);
            stageDialog = new Stage();

            _controller.setStage(stageDialog);
            _controller.inicializarTablePrincipal(_producto);
            stageDialog.setScene(scene);
            stageDialog.getIcons().add(UtilStage.getIconStage());
            stageDialog.initModality(Modality.APPLICATION_MODAL);
            stageDialog.setResizable(false);
            stageDialog.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void focusedTfCodigo() {
        tfCodigo.requestFocus();
    }

    public void focusedTfNombre() {
        tfNombre.requestFocus();
    }

    private void mostrarVentanaDevolverProducto(Producto _producto) {

        Stage _stageDialog;
        Scene scene;
        Parent root;
        DevolverProductoController _controller;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(getClass().getResource("/ventanas/DevolverProducto.fxml"));
            root = loader.load();
            _controller = loader.getController();
            scene = new Scene(root);
            _stageDialog = new Stage();

            _controller.setStage(_stageDialog);
            _controller.inicializarTableDevoluciones(_producto);
            _stageDialog.setScene(scene);
            _stageDialog.getIcons().add(UtilStage.getIconStage());
            _stageDialog.initModality(Modality.APPLICATION_MODAL);
            _stageDialog.setResizable(false);
            _stageDialog.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para el evento MenuItem item_eliminar
     */
    private void eliminarItem() {
        final int index = tableProductos.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            tableProductos.getItems().remove(index);
            actionBtnVerTotal();
        }

    }

    /**
     * Agregar items productos a la tabla en venta productos
     */
    private void agregarItems() throws IOException {
        Producto producto;
        producto = new Producto("", "", "", null);
        producto.id = tfCodigo.getText();
        Consultar _consultar_producto;

        _consultar_producto = new Consultar(producto.id);
        _consultar_producto.consultarProducto();
        if (_consultar_producto.productoEncontrado()) {
            if (Integer.parseInt(_consultar_producto.getCantidad()) >= 1) {
                datos = FXCollections.observableArrayList(producto.obtenerDatosParaTablaProductos());
                tableProductos.getItems().addAll(datos);
                actionBtnVerTotal();
            } else {
                FxDialogs.showWarning("", "Cantidad insuficiente, porfavor registre más productos.");
            }
        } else {
            FxDialogs.showError("", "Este producto no existe.");
        }
        tfCodigo.setText("");

    }

    private void actionBtnAnadir() {
        Producto _producto_nuevo;
        String _nombre, _precio;

        _nombre = tfNombre.getText();
        _precio = tfPrecio.getText();

        if (_nombre.isEmpty()) {
            _nombre = "Sin nombre";
        }
        if (!_nombre.isEmpty() && !_precio.isEmpty()) {
            _producto_nuevo = new Producto(_nombre, _precio, "", null);
            tableProductos.getItems().add(_producto_nuevo);
            actionBtnVerTotal();
            tfNombre.clear();
            tfPrecio.clear();
        }

    }

    private void actionMenuCerrarSesion() {
        Stage _stageDialog;
        Scene _scene;
        Parent _root;
        FXMLLoader _loader;
        TerminarSesionController _controller;

        try {
            _loader = new FXMLLoader(getClass().getResource("/ventanas/TerminarSesion.fxml"));
            _root = _loader.load();
            _scene = new Scene(_root);
            _stageDialog = new Stage();
            _controller = _loader.getController();

            _controller.setStageDialogPrincipal(stageDialog);
            _controller.setStage(_stageDialog);
            _controller.DINERO_VENTAS = SALDO_ACTUAL;
            _controller.inicializarTextFieldDineroEnVentas(String.valueOf(SALDO_ACTUAL));
            _stageDialog.setScene(_scene);
            _stageDialog.getIcons().add(UtilStage.getIconStage());
            _stageDialog.initModality(Modality.APPLICATION_MODAL);
            _stageDialog.setResizable(false);
            _stageDialog.show();

        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double actionBtnVerTotal() {

        double _monto_total, _monto_sub_total, _precio, _cantidad;
        ObservableList<Producto> lista;

        _monto_total = 0d;
        lista = tableProductos.getItems();

        for (int i = 0; i < lista.toArray().length; i++) {

            if (!lista.get(i).getId().isEmpty()) {
                _precio = Double.parseDouble(lista.get(i).getPrecio());
                _cantidad = Double.parseDouble(lista.get(i).getSpinner().getValue().toString());

                _monto_sub_total = _precio * _cantidad;
                _monto_total += _monto_sub_total;
            } else {
                _monto_total += Double.parseDouble(lista.get(i).getPrecio());
            }

        }

        btnVerTotal.setText(_monto_total + " Bs.");
        return _monto_total;
    }

    protected double obtenerMontoTotalProductos() {

        double _monto_total, _monto_sub_total, _precio, _cantidad;
        ObservableList<Producto> lista;

        _monto_total = 0d;
        lista = tableProductos.getItems();

        for (int i = 0; i < lista.toArray().length; i++) {

            if (!lista.get(i).getId().isEmpty()) {
                _precio = Double.parseDouble(lista.get(i).getPrecio());
                _cantidad = Double.parseDouble(lista.get(i).getSpinner().getValue().toString());

                _monto_sub_total = _precio * _cantidad;
                _monto_total += _monto_sub_total;
            } else {
                _monto_total += Double.parseDouble(lista.get(i).getPrecio());
            }

        }

        return _monto_total;
    }

    private void actionBtnConfirmar() {

        if(!tableProductos.getItems().isEmpty()){
            Stage _stageDialog;
            Scene _scene;
            Parent _root;
            FXMLLoader _loader;
            ConfirmarController _controller;

            try {
                _loader = new FXMLLoader(getClass().getResource("/ventanas/Confirmar.fxml"));
                _root = _loader.load();
                _scene = new Scene(_root);
                _stageDialog = new Stage();
                _controller = _loader.getController();

                _controller.setStage(_stageDialog);
                _stageDialog.setScene(_scene);
                _stageDialog.getIcons().add(UtilStage.getIconStage());
                _stageDialog.initModality(Modality.APPLICATION_MODAL);
                _stageDialog.setResizable(false);
                _stageDialog.show();

            } catch (IOException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FxDialogs.showInformation("", "Porfavor realize una venta");
        }
        

    }

    public void actualizarSaldoTotal(ObservableList<Producto> _list) {

        double _dinero_actual = 0d, _precio = 0d;

        for (int i = 0; i < _list.toArray().length; i++) {

            _precio = _list.get(i).getTotal_dbl();

            _dinero_actual += _precio;
            SALDO_ACTUAL = _dinero_actual;

        }

        monto_actual_detalles_venta = _dinero_actual;

        tfDineroActual.setText(String.valueOf(_dinero_actual) + " Bs.");

    }

    /**
     * INICIO Metodos para agregar, modificar, eliminar filas en la tabla
     * detalles de venta
     */
    public void agregarProductoATablaDetalles(ObservableList<Producto> _list) {
        tableDetalles.getItems().addAll(_list);
        actualizarSaldoTotal(obtenerFilasDeTableDetalles());
    }

    public void modificarProductoEnTableDetalles(int _index, ObservableList<Producto> _list) {
        tableDetalles.getItems().remove(_index);
        tableDetalles.getItems().addAll(_index, _list);
        actualizarSaldoTotal(obtenerFilasDeTableDetalles());
    }

    public void eliminarProductoEnTableDetalles(int _index) {
        tableDetalles.getItems().remove(_index);
        actualizarSaldoTotal(obtenerFilasDeTableDetalles());
    }

    /**
     * FIN Metodos para agregar, modificar, eliminar filas en la tabla detalles
     * de venta
     */
    public ObservableList<Producto> obtenerFilasDeTableDetalles() {
        return tableDetalles.getItems();
    }

    public static void setController(PrincipalController _controller) {
        controller = _controller;
    }

    public static PrincipalController getController() {
        return controller;
    }

    public void setStage(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

    public Stage getStage() {
        return stageDialog;
    }

    public void modificarInicioDeSesion(String _usuario) {

        servidor.usuario.Modificar _modificar_usuario;
        servidor.usuario.Consultar _consultar_usuario;

        _consultar_usuario = new servidor.usuario.Consultar();

        try {

            _consultar_usuario.consultarUsuarioPorUsuario(_usuario);

            if (_consultar_usuario.usuarioEntontrado()) {

                _modificar_usuario = new servidor.usuario.Modificar(_consultar_usuario.getId());

                _modificar_usuario.setNombres(_consultar_usuario.getNombres());
                _modificar_usuario.setApellidos(_consultar_usuario.getApellidos());
                _modificar_usuario.setTelefono(_consultar_usuario.getTelefono());
                _modificar_usuario.setDireccion(_consultar_usuario.getDireccion());
                _modificar_usuario.setPassword(_consultar_usuario.getPassword());
                _modificar_usuario.setEstado(_consultar_usuario.getEstado());
                _modificar_usuario.setPrivilegio(_consultar_usuario.getPrivilegio());
                _modificar_usuario.setUsuario(_consultar_usuario.getUsuario());
                _modificar_usuario.setEstadoSesion(PrincipalController.ESTADO_INICIADO);
                _modificar_usuario.setFechaFinSesion(_consultar_usuario.getFechaFinSesion());
                _modificar_usuario.setFechaInicioSesion(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                _modificar_usuario.moficiarUsuario();

                if (_modificar_usuario.usuarioModificado()) {
                    System.out.println("Sesion iniciada - datos de inicio modificados");
                }
            }

        } catch (SQLException | IOException ex) {

            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
    private double SALDO_ACTUAL = 0d;

}
