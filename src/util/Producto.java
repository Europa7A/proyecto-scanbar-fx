package util;

import controladores.PrincipalController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servidor.dventas.ProductoDetalles;
import servidor.producto.Consultar;

public class Producto {

    public ImageView getImage() {
        return image.get();
    }

    public void setImage(ImageView image) {
        this.image = new SimpleObjectProperty<>(image);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public String getCodigo_de_barra() {
        return codigo_de_barra.get();
    }

    public void setCodigo_de_barra(String codigo_de_barra) {
        this.codigo_de_barra = new SimpleStringProperty(codigo_de_barra);
    }

    public String getId_base_datos() {
        return id_base_datos;
    }

    public void setId_base_datos(String id_base_datos) {
        this.id_base_datos = id_base_datos;
    }

    public String id;
    public StringProperty nombre, unidades_disponibles, precio;
    private StringProperty descripcion;
    private StringProperty codigo_de_barra;
    public IntegerProperty cantidad_int;
    public DoubleProperty total_dbl;
    public Spinner spinner;
    public int posicion_table;
    private int index;
    private String id_base_datos;
    private ObjectProperty<ImageView> image;
    

    public Producto(String nombre, String precio, String unidades_disponibles, Spinner spinner) {
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleStringProperty(precio);
        this.unidades_disponibles = new SimpleStringProperty(unidades_disponibles);
        this.spinner = spinner;
        id = "";
        cantidad_int = new SimpleIntegerProperty(0);
        image = new SimpleObjectProperty<>();
        descripcion = new SimpleStringProperty("");
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCantidad_int(int _cantidad_int) {
        cantidad_int = new SimpleIntegerProperty(_cantidad_int);
    }

    public void setTotal_dbl(double _total_dbl) {
        total_dbl = new SimpleDoubleProperty(_total_dbl);
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public void setUnidades_disponibles(String unidades_disponibles) {
        this.unidades_disponibles = new SimpleStringProperty(unidades_disponibles);
    }

    public void setPrecio(String _precio) {
        precio = new SimpleStringProperty(_precio);
    }

    public void setSpinner(Spinner _spinner) {
        spinner = _spinner;
    }

    public void setIndex(int _index) {
        this.index = _index;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getPrecio() {
        return precio.get();
    }

    public String getUnidades_disponibles() {
        return unidades_disponibles.get();
    }

    public int getCantidad_int() {
        return cantidad_int.get();
    }

    public double getTotal_dbl() {
        return total_dbl.get();
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    /**
     * Obtiene los datos para colocarlos en la tabla detalles de venta de la
     * ventana Principal
     *
     * @param list : observableList para abstraer los datos de una tabla
     * @return
     */
    public Producto[] obtenerDatosRegistradosParaTablaDetallesVenta(ObservableList<Producto> list) {

        String _nombre_str, _descripcion;
        int _cantidad_int;
        double _total_dbl, _temp;
        Producto[] productos;
        Spinner _spinner;

        productos = new Producto[list.toArray().length];

        for (int i = 0; i < list.toArray().length; i++) {
            _spinner = new Spinner();

            _nombre_str = list.get(i).getNombre();
            _descripcion = list.get(i).getDescripcion();
            _cantidad_int = Integer.parseInt(list.get(i).getSpinner().getValue().toString());
            _temp = _cantidad_int * Double.parseDouble(list.get(i).getPrecio());
            _total_dbl = _temp;
            productos[i] = new Producto(_nombre_str, "", "", null);

            _spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, _cantidad_int));
            /*_spinner.getValueFactory().setValue(_cantidad_int);*/
            productos[i].setCantidad_int(_cantidad_int);
            productos[i].setTotal_dbl(_total_dbl);
            productos[i].setDescripcion(_descripcion);
            productos[i].setPrecio(list.get(i).getPrecio());
            productos[i].setSpinner(_spinner);
            productos[i].setId(list.get(i).getId());
        }

        return productos;
    }

    public Producto[] obtenerDatosNoRegistradosParaTablaDetallesVenta(ObservableList<Producto> list) {

        String _nombre_str;
        double _total_dbl;
        Producto[] productos;

        productos = new Producto[list.toArray().length];

        for (int i = 0; i < list.toArray().length; i++) {
            _nombre_str = list.get(i).getNombre();
            _total_dbl = Double.parseDouble(list.get(i).getPrecio());
            productos[i] = new Producto(_nombre_str, "", "", null);

            productos[i].setTotal_dbl(_total_dbl);
            productos[i].setPrecio(list.get(i).getPrecio());

        }

        return productos;
    }

    public static ObservableList<Producto> obtenerListaProductosPorFechaUsuarioYSesion(String _fecha, String _usuario, String _sesion) {

        servidor.dventas.Consultar _consultar_detalles_venta;
        ObservableList<Producto> _list;
        ProductoDetalles[] _productos_detalles;
        Producto[] _productos;
        Spinner _spinner;

        _consultar_detalles_venta = new servidor.dventas.Consultar("");
        System.out.println(_fecha);
        _productos_detalles = _consultar_detalles_venta.consultarTodoPorFechaUsuarioYSesion(_fecha, _usuario, _sesion);

//        System.out.println(_productos_detalles[0].getNombre() + " : nombre");
        if (_productos_detalles != null) {
            _productos = new Producto[_productos_detalles.length];
            for (int i = 0; i < _productos_detalles.length; i++) {

                if (!_productos_detalles[i].getCodigo_barra().isEmpty()) {
                    _spinner = new Spinner(1, Integer.parseInt(_productos_detalles[i].getCantidad()), 1);
                    _productos[i] = new Producto(_productos_detalles[i].getNombre(), _productos_detalles[i].getPrecio(), "", null);

                    
                    _productos[i].setId_base_datos(_productos_detalles[i].getId());
                    _productos[i].setCantidad_int(Integer.parseInt(_productos_detalles[i].getCantidad()));
                    _productos[i].setTotal_dbl(Double.parseDouble(_productos_detalles[i].getTotal()));
                    _productos[i].setSpinner(_spinner);
                    _productos[i].setId(_productos_detalles[i].getCodigo_barra());
                } else {
                    //_spinner = new Spinner(1, Integer.parseInt(_productos_detalles[i].getCantidad()), 1);
                    _productos[i] = new Producto(_productos_detalles[i].getNombre(), _productos_detalles[i].getPrecio(), "", null);

                    _productos[i].setId_base_datos(_productos_detalles[i].getId());
                    //_productos[i].setCantidad_int(Integer.parseInt(_productos_detalles[i].getCantidad()));
                    _productos[i].setTotal_dbl(Double.parseDouble(_productos_detalles[i].getTotal()));
                    //_productos[i].setSpinner(_spinner);
                    //_productos[i].setId(_productos_detalles[i].getCodigo_barra());
                }

            }
            _list = FXCollections.observableArrayList(_productos);
            return _list;

        }

        return null;
    }

    /**
     * Obtiene los datos para colocarlos en la tabla productos de la ventana
     * Principal
     */
    public Producto[] obtenerDatosParaTablaProductos() {

        String _nombre, _descripcion, _precio, _unidades_disponibles;
        Spinner _spinner;
        Consultar consultar;
        Producto[] producto;
        ImageView _image;

        producto = null;

        _nombre = "";
        _unidades_disponibles = "";

        try {
            consultar = new Consultar(id);
            consultar.consultarProducto();
            _image = null;

            if (consultar.productoEncontrado()) {

                if(!consultar.getRutaImagen().isEmpty()){
                    File _file = new File(consultar.getRutaImagen());
                    if(_file.exists()){
                        try {
                            _image = new ImageView(new Image(new FileInputStream(_file))); 
                        } catch (FileNotFoundException e) {
                            FxDialogs.showError("", "Error al intentar cargar la imagen\n"+e);
                        }
                        
                    }
                    
                }
                
                _nombre = consultar.getNombre();
                _precio = consultar.getPrecio();
                _descripcion = consultar.getDescripcion();
                _unidades_disponibles = consultar.getCantidad();
                _spinner = new Spinner();
                _spinner.setOnMousePressed(event -> {
                    PrincipalController.getController().actionBtnVerTotal();
                });
                if(_image != null){
                    _image.setFitWidth(70d);
                    _image.setPreserveRatio(true); 
                }
                

                try {
                    if (Integer.parseInt(_unidades_disponibles) >= 1) {
                        _spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.parseInt(_unidades_disponibles)));
                    }

                } catch (NumberFormatException e) {
                }

                producto = new Producto[1];
                producto[0] = new Producto(_nombre, _precio, _unidades_disponibles, _spinner);
                producto[0].setDescripcion(_descripcion);
                if(_image != null){
                    producto[0].setImage(_image);  
                }
                
                producto[0].setId(id);

            }

        } catch (IOException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return producto;
    }

    public static Producto[] obtenerDatosParaTableProductosRegistrados() {

        Producto[] _productos;
        _productos = null;
        try {

            servidor.producto.Producto[] _productos_servidor;
            Consultar _consultar_productos;

            _consultar_productos = new Consultar("");
            _productos_servidor = _consultar_productos.consultarTodo();

            if (_productos_servidor != null) {
                _productos = new Producto[_productos_servidor.length];
                for (int i = 0; i < _productos_servidor.length; i++) {
                    _productos[i] = new Producto(_productos_servidor[i].getNombre(), _productos_servidor[i].getPrecio(), _productos_servidor[i].getCantidad(), null);
                    _productos[i].setCodigo_de_barra(_productos_servidor[i].getID());
                    _productos[i].setDescripcion(_productos_servidor[i].getDescripcion());

                }
            }

            return _productos;
        } catch (Exception e) {
            FxDialogs.showError("", "Error al obtener datos para table productos registrados\n" + e);

        }
        return _productos;
    }

}
