package servidor.producto;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends Producto {

    private boolean encontrado;

    public Consultar(String id) {
        super(id);
    }

    public void consultarProducto() throws IOException {
        basedatos.productos.Consultar consultar = new basedatos.productos.Consultar(id);

        if (consultar.consultarProducto()) {
            id = consultar.getID();
            nombre = consultar.getNombre();
            descripcion = consultar.getDescripcion();
            precio = consultar.getPrecio();
            cantidad = consultar.getCantidad();
            fecha_registro = consultar.getFechaRegistro();
            fecha_vencimiento = consultar.getFechaVencimiento();
            registrado_por = consultar.getRegistradoPor();
            modificado_por = consultar.getModificadoPor();
            ruta_img = consultar.getRutaImagen();
            encontrado = true;
        }

    }

    public Producto[] consultarTodo() {

        Producto[] _productos;
        basedatos.productos.Consultar _consultar;
        basedatos.productos.Producto[] _productos_bd;

        _consultar = new basedatos.productos.Consultar("");
        _productos_bd = _consultar.consultarTodo();
        _productos = null;

        if (_productos_bd != null) {

            _productos = new Producto[_productos_bd.length];

            for (int i = 0; i < _productos.length; i++) {

                _productos[i] = new Producto("");
                _productos[i].id = _productos_bd[i].getID();
                _productos[i].nombre = (_productos_bd[i].getNombre());
                _productos[i].descripcion = (_productos_bd[i].getDescripcion());
                _productos[i].precio = (_productos_bd[i].getPrecio());
                _productos[i].cantidad = (_productos_bd[i].getCantidad());
                _productos[i].fecha_registro = (_productos_bd[i].getFechaRegistro());
                _productos[i].fecha_vencimiento = (_productos_bd[i].getFechaVencimiento());
                _productos[i].registrado_por = (_productos_bd[i].getRegistradoPor());
                _productos[i].modificado_por = (_productos_bd[i].getModificadoPor());
                _productos[i].ruta_img = (_productos_bd[i].getRutaImagen());

            }
        }

        return _productos;
    }

    public boolean productoEncontrado() {
        return encontrado;
    }

}
