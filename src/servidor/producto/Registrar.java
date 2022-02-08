package servidor.producto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registrar extends Producto {

    public Registrar(String id) throws IOException {
        super(id);
    }

    public void registrar() throws IOException {
        registrado = false;
        registrar = new basedatos.productos.Registrar(id);
        registrar.setNombre(nombre);
        registrar.setDescripcion(descripcion);
        registrar.setCantidad(cantidad);
        registrar.setPrecio(precio);
        registrar.setFechaRegistro(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        registrar.setRegistradoPor(registrado_por);
        registrar.setRutaImagen(ruta_img);
        registrado = registrar.registrarProducto();
    }
    public boolean estaRegistrado() {
        return registrado;
    }

}
