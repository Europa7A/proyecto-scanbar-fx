
package servidor.dventas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Registrar extends ProductoDetalles{
    
    private boolean registrado = false;
    
    public Registrar(String id) {
        super(id);
    }
    
    public void registrar(){
        
        int _longitud_filas_detalles_venta = 0, _temp = 0;
        basedatos.dventas.Registrar _registrar_producto;
        basedatos.dventas.Consultar _consultar_producto;
        basedatos.dventas.ProductoDetalles[] _producto_detalles;
        
        _registrar_producto = new basedatos.dventas.Registrar();
        _consultar_producto = new basedatos.dventas.Consultar();
        
        _producto_detalles = _consultar_producto.consultarTodo();
        if(_consultar_producto.consultarTodo() != null){
            _longitud_filas_detalles_venta = Integer.parseInt(_producto_detalles[_producto_detalles.length - 1].getId());
        }
        
        if(_longitud_filas_detalles_venta >= 1){
            _temp = _longitud_filas_detalles_venta + 1;
            id = String.valueOf(_temp);
        } else if(_longitud_filas_detalles_venta <= 0) {
            id = String.valueOf(1);
        }
        
        _registrar_producto.setId(id);
        _registrar_producto.setCodigo_barra(codigo_barra);
        _registrar_producto.setNombre(nombre);
        _registrar_producto.setRegistrado_por(registrado_por);
        _registrar_producto.setModificado_por(modificado_por);
        _registrar_producto.setDevolucion(devolucion);
        _registrar_producto.setPrecio(precio);
        _registrar_producto.setCantidad(cantidad);
        _registrar_producto.setTotal(total);
        _registrar_producto.setFecha_registro(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        _registrar_producto.setHora_registro(new SimpleDateFormat("hh:mm:ss").format(new Date()));       
        _registrar_producto.setSesion("abierta");

        
        
        if(_registrar_producto.registrarProductoDetalles()){
            registrado = true;
        }
    }
    
    public boolean estaRegistrado(){
        return registrado;
    }
    
}
