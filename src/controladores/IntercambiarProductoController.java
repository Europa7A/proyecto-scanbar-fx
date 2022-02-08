package controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servidor.dventas.Eliminar;
import servidor.dventas.Registrar;
import servidor.producto.Consultar;
import servidor.producto.Modificar;
import util.FxDialogs;
import util.Producto;
import util.UtilStage;

public class IntercambiarProductoController implements Initializable {

    @FXML
    private Button btnIntercambiar;
    @FXML
    private TableView<Producto> tablePrincipal, tableIntercambiado;
    @FXML
    private TextField tfCodigo;

    private Stage stageDialog;

    private TableColumn tcPrincipalNombre, tcPrincipalPrecio, tcPrincipalCantidad;
    private TableColumn tcIntercambioNombre, tcIntercambioPrecio, tcIntercambioUnidadesDisponibles, tcIntercambioCantidad;

    private Producto productoOriginal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tcPrincipalNombre = new TableColumn<Producto, String>("Nombre");
        tcPrincipalPrecio = new TableColumn<Producto, String>("Precio (Bs.)");
        tcPrincipalCantidad = new TableColumn<Producto, Spinner>("Cantidad");

        tcIntercambioNombre = new TableColumn<Producto, String>("Nombre");
        tcIntercambioPrecio = new TableColumn<Producto, String>("Precio (Bs.)");
        tcIntercambioUnidadesDisponibles = new TableColumn<Producto, String>("Unidades disponibles");
        tcIntercambioCantidad = new TableColumn<Producto, Spinner>("Cantidad");

        tcPrincipalNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcPrincipalPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcPrincipalCantidad.setCellValueFactory(new PropertyValueFactory("spinner"));

        tcIntercambioNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcIntercambioPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcIntercambioUnidadesDisponibles.setCellValueFactory(new PropertyValueFactory("unidades_disponibles"));
        tcIntercambioCantidad.setCellValueFactory(new PropertyValueFactory("spinner"));

        tablePrincipal.getColumns().addAll(tcPrincipalNombre, tcPrincipalPrecio, tcPrincipalCantidad);
        tableIntercambiado.getColumns().addAll(tcIntercambioNombre, tcIntercambioPrecio, tcIntercambioUnidadesDisponibles, tcIntercambioCantidad);

        tfCodigo.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                agregarItem();
            }
        });
        btnIntercambiar.setOnAction(event -> actionBtnIntercambiar());

    }

    /**
     * Esto inicializa el table principal
     *
     * @param _producto - es el producto que se mostrara en el tableOriginal
     */
    public void inicializarTablePrincipal(Producto _producto) {
        try {
            productoOriginal = _producto;
            ObservableList<Producto> _list;

            _list = FXCollections.observableArrayList(_producto);
            tablePrincipal.getItems().addAll(_list);
        } catch (Exception e) {
            FxDialogs.showError("", "Error al intentar inicializar tabla producto intercambio\n" + e);
        }

    }

    /**
     * Metodo para agregar producto a el tableIntercambiado
     */
    private void agregarItem() {
        try {
            Producto _producto;
            ObservableList<Producto> _list;

            _producto = new Producto("", "", "", null);
            _producto.id = tfCodigo.getText();

            _list = FXCollections.observableArrayList(_producto.obtenerDatosParaTablaProductos());

            tableIntercambiado.getItems().addAll(_list);
        } catch (Exception e) {
            FxDialogs.showError("", "Error al intentar agregar item a table intercambio\n" + e);
        }

    }

    /**
     * Accion del boton intercambiar
     */
    private void actionBtnIntercambiar() {

        int _cantidad_a, _cantidad_b, _cantidad_c;// variables de ayuda para realizar operaciones de cantidad del producto
        boolean _aux_1 = false, _aux_2 = false; // variables auxiliares para determinar si son verdaderas o falsas las modificaciones a la tabla y a la base de datos
        Modificar _modificar_producto; //para modificar el producto en la base de datos
        Consultar _consultar_producto; //para consultar el producto en la base de datos
        Producto _producto_original, _producto_intercambio; // producto_original de la tablePrincipal - producto_intercambio de la tableIntercambiado
        PrincipalController controller;

        controller = PrincipalController.getController();
        _producto_original = tablePrincipal.getItems().get(0);
        _producto_intercambio = tableIntercambiado.getItems().get(0);

        /**
         * Esto modifica el producto seleccionado para intercambiar Realiza la
         * suma de la cantidad a intercambiar para modificar en la base de datos
         * producto
         */
        try {
            _cantidad_a = Integer.parseInt(_producto_original.getSpinner().getValue().toString());// Se obtiene la cantidad del producto a intercambiar seleccionado
            _consultar_producto = new Consultar(_producto_original.getId());// Consultar a la base de datos del producto seleccionado para conocer su cantidad actual
            _consultar_producto.consultarProducto();

            if (_consultar_producto.productoEncontrado()) {

                _cantidad_b = Integer.parseInt(_consultar_producto.getCantidad());
                _cantidad_c = _cantidad_a + _cantidad_b;
                _modificar_producto = new Modificar(_producto_original.getId());

                /*Modificacion del producto a intercambiar seleccionado*/
                _modificar_producto.setNombre(_consultar_producto.getNombre());
                _modificar_producto.setDescripcion(_consultar_producto.getDescripcion());
                _modificar_producto.setPrecio(_consultar_producto.getPrecio());
                _modificar_producto.setCantidad(String.valueOf(_cantidad_c));
                _modificar_producto.setFechaVencimiento(_consultar_producto.getFechaVencimiento());
                _modificar_producto.setModificadoPor(_consultar_producto.getModificadoPor());
                _modificar_producto.setRutaImagen(_consultar_producto.getRutaImagen());
                _modificar_producto.modificar();

                if (_modificar_producto.estaModificado()) {
                    /**
                     * Esto realiza la modificacion de la tabla de la interfaz
                     * Principal Modifica el producto a intercambiar
                     * seleccionado Solo modifica la cantidad, que es lo que
                     * importa en realidad
                     */
                    _aux_1 = true;
                    int _cant_org = productoOriginal.getCantidad_int();
                    Spinner _spinner;
                    ObservableList<Producto> _list, _datos;
                    Producto _producto;
                    Producto[] _productos;
                    Eliminar _eliminar_detalles_venta;
                    servidor.dventas.Modificar _modificar_detalles_venta;

                    if (_cantidad_a == _cant_org) {
                        _eliminar_detalles_venta = new Eliminar(productoOriginal.getId_base_datos());

                        _eliminar_detalles_venta.elimnarProducto();
                        if (_eliminar_detalles_venta.productoEliminado()) {
                            System.out.println("ELIMINADO EXITOSAMENTE");
                        }
                        controller.eliminarProductoEnTableDetalles(productoOriginal.getIndex());

                    } else if (_cantidad_a < _cant_org) {

                        _cantidad_c = _cant_org - _cantidad_a;
                        _list = tablePrincipal.getItems();
                        _producto = new Producto("", "", null, null);
                        _productos = _producto.obtenerDatosRegistradosParaTablaDetallesVenta(_list);
                        _spinner = new Spinner(1, _cantidad_c, 1);
                        _modificar_detalles_venta = new servidor.dventas.Modificar(productoOriginal.getId_base_datos());

                        _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.cantidad, String.valueOf(_cantidad_c));
                        _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.total, String.valueOf(_cantidad_c * Double.parseDouble(_productos[0].getPrecio())));

                        if (_modificar_detalles_venta.estaModificado()) {
                            System.out.println("mODIFICADO DETALLES VENTA");
                        }

                        _productos[0].setId_base_datos(productoOriginal.getId_base_datos());
                        _productos[0].setCantidad_int(_cantidad_c);
                        _productos[0].setSpinner(_spinner);
                        //_productos[0].getSpinner().getValueFactory().setValue(_cantidad_c);
                        _productos[0].setTotal_dbl(_cantidad_c * Double.parseDouble(_productos[0].getPrecio()));

                        _datos = FXCollections.observableArrayList(_productos);

                        controller.modificarProductoEnTableDetalles(productoOriginal.getIndex(), _datos);

                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(IntercambiarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            FxDialogs.showError("", "Error 1  al intentar intercambiar producto\n" + ex);

        }

        /**
         * Esto modifica el producto para el intercambio del producto anterior
         * Modifica el tableDetalles en la interfaz Principal y tambien el
         * producto en la base de datos Realiza la operacion de resta, para
         * modificar el producto en la base de datos y en el tableDetalles en la
         * interfaz Principal
         */
        try {

            _cantidad_a = Integer.parseInt(_producto_intercambio.getSpinner().getValue().toString());
            _consultar_producto = new Consultar(_producto_intercambio.getId());
            _consultar_producto.consultarProducto();

            if (_consultar_producto.productoEncontrado()) {
                //System.out.println("Producto intercambio encontrado");
                _cantidad_b = Integer.parseInt(_consultar_producto.getCantidad());
                _cantidad_c = _cantidad_b - _cantidad_a;
                _modificar_producto = new Modificar(_producto_intercambio.getId());

                _modificar_producto.setNombre(_consultar_producto.getNombre());
                _modificar_producto.setDescripcion(_consultar_producto.getDescripcion());
                _modificar_producto.setPrecio(_consultar_producto.getPrecio());
                _modificar_producto.setCantidad(String.valueOf(_cantidad_c));
                _modificar_producto.setFechaVencimiento(_consultar_producto.getFechaVencimiento());
                _modificar_producto.setModificadoPor(_consultar_producto.getModificadoPor());
                _modificar_producto.setRutaImagen(_consultar_producto.getRutaImagen());
                _modificar_producto.modificar();

                /**
                 * Aqui cambia o modifica los datos de el tableDetalles de la
                 * interfaz Principal Es todo
                 */
                if (_modificar_producto.estaModificado()) {
                    ObservableList<Producto> _list, _datos;
                    Producto _producto;
                    Registrar _registrar_detalles_venta;

                    _registrar_detalles_venta = new Registrar("");
                    _aux_2 = true;
                    _list = tableIntercambiado.getItems();
                    _producto = new Producto("", "", null, null);
                    _datos = FXCollections.observableArrayList(_producto.obtenerDatosRegistradosParaTablaDetallesVenta(_list));

                    /**
                     * Esto registra en la tabla detalles venta en la base de
                     * datos Registra el producto seleccionado para intercambiar
                     */
                    for (int i = 0; i < _list.toArray().length; i++) {
                        _registrar_detalles_venta.setCodigo_barra(_list.get(i).getId());
                        _registrar_detalles_venta.setNombre(_list.get(i).getNombre());
                        _registrar_detalles_venta.setRegistrado_por(LoginController.nombre_usuario);
                        _registrar_detalles_venta.setPrecio(_list.get(i).getPrecio());
                        _registrar_detalles_venta.setCantidad(String.valueOf(_list.get(i).getSpinner().getValue().toString()));
                        _registrar_detalles_venta.setTotal(String.valueOf(Double.parseDouble(_list.get(i).getSpinner().getValue().toString()) * Double.parseDouble(_list.get(i).getPrecio())));
                        _registrar_detalles_venta.registrar();
                        if (_registrar_detalles_venta.estaRegistrado()) {
                            System.out.println("Registrado intercambiardo");
                        }
                    }

                    controller.agregarProductoATablaDetalles(_datos);
                }
            }
        } catch (IOException ex) {
            FxDialogs.showError("", "Error 2  al intentar intercambiar producto\n" + ex);
            Logger.getLogger(IntercambiarProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (_aux_1 && _aux_2) {

            FxDialogs.showInformation("", "Intercambio realizado exitosamente");

        }
    }

 

    public void setStage(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

}
