
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servidor.producto.Consultar;
import servidor.producto.Modificar;
import util.FxDialogs;
import util.Producto;
import util.UtilStage;


public class DevolverProductoController implements Initializable {

    
    @FXML 
    private TableView<Producto> tableDevoluciones;
    @FXML
    private Button btnConfirmar, btnCancelar; 
    
    private TableColumn tcNombre, tcPrecio, tcCantidad;
    private Stage stageDialog;
    private Producto productoOriginal;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tcNombre = new TableColumn<Producto, String>("Nombre");
        tcPrecio = new TableColumn<Producto, String>("Precio");
        tcCantidad = new TableColumn<Producto, Spinner>("Cantidad");
        
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory("spinner"));
        
        tableDevoluciones.getColumns().addAll(tcNombre, tcPrecio, tcCantidad);
        btnConfirmar.setOnAction(event -> actionBtnConfirmar());
    }    
    
    public void inicializarTableDevoluciones(Producto _producto){
        
        ObservableList<Producto> _list;
        
        productoOriginal = _producto;
        
        _list = FXCollections.observableArrayList(_producto);
        tableDevoluciones.getItems().addAll(_list);
        
    }
    
    public void setStage(Stage stageDialog){
        this.stageDialog = stageDialog;
    }
    
    private void mostrarDialogOperacionExitosa() throws IOException{
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
    
    private void actionBtnConfirmar(){
        
        PrincipalController _controller_principal;
        int _cant_spinner, _cant_producto_db, _cant_total_producto;
        Modificar _modificar_producto;
        Consultar _consultar_producto;
        Producto _producto_a_devolver;
        servidor.dventas.Modificar _modificar_detalles_venta;
        servidor.dventas.Eliminar _eliminar_detalles_venta;
        
        _controller_principal = PrincipalController.getController();
        _producto_a_devolver = tableDevoluciones.getItems().get(0);
        
        try {
            
            _consultar_producto = new Consultar(_producto_a_devolver.getId());
            _consultar_producto.consultarProducto();
            
            if(_consultar_producto.productoEncontrado()){
                
                _cant_spinner = Integer.parseInt(_producto_a_devolver.spinner.getValue().toString());
                _cant_producto_db = Integer.parseInt(_consultar_producto.getCantidad());
                _modificar_producto = new Modificar(_producto_a_devolver.getId());
                _cant_total_producto = _cant_producto_db + _cant_spinner;
                
                _modificar_producto.setNombre(_consultar_producto.getNombre());
                _modificar_producto.setCantidad(String.valueOf(_cant_total_producto));
                _modificar_producto.setPrecio(_consultar_producto.getPrecio());
                _modificar_producto.setDescripcion(_consultar_producto.getDescripcion());
                _modificar_producto.setFechaVencimiento(_consultar_producto.getFechaVencimiento());
                _modificar_producto.setModificadoPor(_consultar_producto.getModificadoPor());
                _modificar_producto.setRutaImagen(_consultar_producto.getRutaImagen());
                _modificar_producto.modificar();
                
                if(_modificar_producto.estaModificado()){
                    
                    Spinner _spinner;
                    ObservableList<Producto> _list, _datos;
                    Producto[] _productos;
                    
                    final int _cant_producto_original = productoOriginal.getCantidad_int();
                    
                    if(_cant_spinner == _cant_producto_original){
                        
                        _eliminar_detalles_venta = new servidor.dventas.Eliminar(productoOriginal.getId_base_datos());
                        _eliminar_detalles_venta.elimnarProducto();
                        if(_eliminar_detalles_venta.productoEliminado()){
                            System.out.println("Producto devolucion eliminado");
                        }
                        _controller_principal.eliminarProductoEnTableDetalles(productoOriginal.getIndex());
                        mostrarDialogOperacionExitosa();
                        
                    } else if(_cant_spinner < _cant_producto_original) {
                        
                        _modificar_detalles_venta = new servidor.dventas.Modificar(productoOriginal.getId_base_datos());
                        _cant_total_producto = _cant_producto_original - _cant_spinner;
                        _list = tableDevoluciones.getItems();
                        _productos = new Producto("", "", null, null).obtenerDatosRegistradosParaTablaDetallesVenta(_list);
                        _spinner = new Spinner(1, _cant_total_producto, 1);
                        
                        _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.cantidad, String.valueOf(_cant_total_producto));
                        _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.total, String.valueOf(_cant_total_producto * Double.parseDouble(_productos[0].getPrecio())));
                        _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.devolucion, _list.get(0).getSpinner().getValue().toString());
                        
                        if(_modificar_detalles_venta.estaModificado()){
                            System.out.println("MOdificacion detalles venta devolucion exitosa");
                        }
                        
                        _productos[0].setId_base_datos(productoOriginal.getId_base_datos());
                        _productos[0].setCantidad_int(_cant_total_producto);
                        _productos[0].setSpinner(_spinner);
                        _productos[0].setTotal_dbl(_cant_total_producto * Double.parseDouble(_productos[0].getPrecio()));
                        
                        _datos = FXCollections.observableArrayList(_productos);
                        
                        _controller_principal.modificarProductoEnTableDetalles(productoOriginal.getIndex(), _datos);
                        PrincipalController.getController().actualizarTableProductosRegistrados();
                        mostrarDialogOperacionExitosa();
                    }
                }
                
            }
            
        } catch (IOException | NumberFormatException e) {
            FxDialogs.showError("", "Error al intentar devolver producto\n"+e);
        }
    }
    
}
