
package servidor.producto;

import java.io.IOException;

public class Modificar extends Producto {
    
    private boolean modificado;
    
    public Modificar(String id) throws IOException {
        super(id);
    }
    
    public void modificar() throws IOException{
        basedatos.productos.Modificar modificar = new basedatos.productos.Modificar(id);
        modificar.setNombre(nombre);
        modificar.setDescripcion(descripcion);
        modificar.setPrecio(precio);
        modificar.setCantidad(cantidad);
        modificar.setFechaVencimiento(fecha_vencimiento);
        modificar.setModificadoPor(modificado_por);
        modificar.setRutaImagen(ruta_img);
        
        if(modificar.modificarProducto()){
            modificado = true;
        }
    }
    
    public boolean estaModificado(){
        return modificado;
    }

}
