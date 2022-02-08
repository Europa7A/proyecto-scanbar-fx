package servidor.dventas;

public class Consultar extends ProductoDetalles {

    private boolean encontrado = false;

    public Consultar(String id) {
        super(id);
    }

    public boolean productoEncontrado() {
        return encontrado;
    }

    public void consultarPorID() {

        basedatos.dventas.Consultar _consultar;

        _consultar = new basedatos.dventas.Consultar();

        if (_consultar.consultarPorID(id)) {
            id = _consultar.getId();
            codigo_barra = _consultar.getCodigo_barra();
            nombre = _consultar.getNombre();
            registrado_por = _consultar.getRegistrado_por();
            modificado_por = _consultar.getModificado_por();
            devolucion = _consultar.getDevolucion();
            precio = _consultar.getPrecio();
            cantidad = _consultar.getCantidad();
            total = _consultar.getTotal();
            fecha_registro = _consultar.getFecha_registro();
            hora_registro = _consultar.getHora_registro();
            sesion = _consultar.getSesion();
            encontrado = true;
        }

    }

    public void consultarPorFecha(String _fecha) {
        basedatos.dventas.Consultar _consultar;

        _consultar = new basedatos.dventas.Consultar();

        if (_consultar.consultarPorFecha(_fecha)) {
            id = _consultar.getId();
            codigo_barra = _consultar.getCodigo_barra();
            nombre = _consultar.getNombre();
            registrado_por = _consultar.getRegistrado_por();
            modificado_por = _consultar.getModificado_por();
            devolucion = _consultar.getDevolucion();
            precio = _consultar.getPrecio();
            cantidad = _consultar.getCantidad();
            total = _consultar.getTotal();
            fecha_registro = _consultar.getFecha_registro();
            hora_registro = _consultar.getHora_registro();
            sesion = _consultar.getSesion();
            encontrado = true;
        }
    }

    public ProductoDetalles[] consultarTodoPorFecha(String _fecha) {

        ProductoDetalles[] _productos;
        basedatos.dventas.Consultar _consultar;
        basedatos.dventas.ProductoDetalles[] _productos_bd;

        _consultar = new basedatos.dventas.Consultar();
        _productos_bd = _consultar.consultarTodoPorFecha(_fecha);
        _productos = null;

        if (_productos_bd != null) {

            _productos = new ProductoDetalles[_productos_bd.length];

            for (int i = 0; i < _productos.length; i++) {
                _productos[i] = new ProductoDetalles("");
                _productos[i].id = _productos_bd[i].getId();
                _productos[i].codigo_barra = (_productos_bd[i].getCodigo_barra());
                _productos[i].nombre = (_productos_bd[i].getNombre());
                _productos[i].registrado_por = (_productos_bd[i].getRegistrado_por());
                _productos[i].modificado_por = (_productos_bd[i].getModificado_por());
                _productos[i].devolucion = (_productos_bd[i].getDevolucion());
                _productos[i].precio = (_productos_bd[i].getPrecio());
                _productos[i].cantidad = (_productos_bd[i].getCantidad());
                _productos[i].total = (_productos_bd[i].getTotal());
                _productos[i].fecha_registro = (_productos_bd[i].getFecha_registro());
                _productos[i].hora_registro = (_productos_bd[i].getHora_registro());
                _productos[i].sesion = (_productos_bd[i].getSesion());

            }
        }

        return _productos;

    }

    public void consultarPorFechaYUsuario(String _fecha, String _usuario) {
        basedatos.dventas.Consultar _consultar;

        _consultar = new basedatos.dventas.Consultar();

        if (_consultar.consultarPorFechaYUsuario(_fecha, _usuario)) {
            id = _consultar.getId();
            codigo_barra = _consultar.getCodigo_barra();
            nombre = _consultar.getNombre();
            registrado_por = _consultar.getRegistrado_por();
            modificado_por = _consultar.getModificado_por();
            devolucion = _consultar.getDevolucion();
            precio = _consultar.getPrecio();
            cantidad = _consultar.getCantidad();
            total = _consultar.getTotal();
            fecha_registro = _consultar.getFecha_registro();
            hora_registro = _consultar.getHora_registro();            
            sesion = _consultar.getSesion();

            encontrado = true;
        }

    }

    public ProductoDetalles[] consultarTodoPorFechaYUsuario(String _fecha, String _usuario) {

        ProductoDetalles[] _productos;
        basedatos.dventas.Consultar _consultar;
        basedatos.dventas.ProductoDetalles[] _productos_bd;

        _consultar = new basedatos.dventas.Consultar();
        _productos_bd = _consultar.consultarTodoPorFechaYUsuario(_fecha, _usuario);
        _productos = null;

        if (_productos_bd != null) {

            _productos = new ProductoDetalles[_productos_bd.length];

            for (int i = 0; i < _productos.length; i++) {
                _productos[i] = new ProductoDetalles("");
                _productos[i].id = _productos_bd[i].getId();
                _productos[i].codigo_barra = (_productos_bd[i].getCodigo_barra());
                _productos[i].nombre = (_productos_bd[i].getNombre());
                _productos[i].registrado_por = (_productos_bd[i].getRegistrado_por());
                _productos[i].modificado_por = (_productos_bd[i].getModificado_por());
                _productos[i].devolucion = (_productos_bd[i].getDevolucion());
                _productos[i].precio = (_productos_bd[i].getPrecio());
                _productos[i].cantidad = (_productos_bd[i].getCantidad());
                _productos[i].total = (_productos_bd[i].getTotal());
                _productos[i].fecha_registro = (_productos_bd[i].getFecha_registro());
                _productos[i].hora_registro = (_productos_bd[i].getHora_registro());                
                _productos[i].sesion = (_productos_bd[i].getSesion());


            }
        }

        return _productos;
    }

    public ProductoDetalles[] consultarTodoPorFechaUsuarioYSesion(String _fecha, String _usuario, String _sesion) {

        ProductoDetalles[] _productos;
        basedatos.dventas.Consultar _consultar;
        basedatos.dventas.ProductoDetalles[] _productos_bd;

        _consultar = new basedatos.dventas.Consultar();
        _productos_bd = _consultar.consultarTodoPorFechaUsuarioYSesion(_fecha, _usuario, _sesion);
        _productos = null;

        if (_productos_bd != null) {

            _productos = new ProductoDetalles[_productos_bd.length];

            for (int i = 0; i < _productos.length; i++) {
                _productos[i] = new ProductoDetalles("");
                _productos[i].id = _productos_bd[i].getId();
                _productos[i].codigo_barra = (_productos_bd[i].getCodigo_barra());
                _productos[i].nombre = (_productos_bd[i].getNombre());
                _productos[i].registrado_por = (_productos_bd[i].getRegistrado_por());
                _productos[i].modificado_por = (_productos_bd[i].getModificado_por());
                _productos[i].devolucion = (_productos_bd[i].getDevolucion());
                _productos[i].precio = (_productos_bd[i].getPrecio());
                _productos[i].cantidad = (_productos_bd[i].getCantidad());
                _productos[i].total = (_productos_bd[i].getTotal());
                _productos[i].fecha_registro = (_productos_bd[i].getFecha_registro());
                _productos[i].hora_registro = (_productos_bd[i].getHora_registro());                
                _productos[i].sesion = (_productos_bd[i].getSesion());


            }
        }

        return _productos;
    }
    
    public ProductoDetalles[] consultarTodo() {

        ProductoDetalles[] _productos;
        basedatos.dventas.Consultar _consultar;
        basedatos.dventas.ProductoDetalles[] _productos_bd;

        _consultar = new basedatos.dventas.Consultar();
        _productos_bd = _consultar.consultarTodo();
        _productos = null;

        if (_productos_bd != null) {

            _productos = new ProductoDetalles[_productos_bd.length];

            for (int i = 0; i < _productos.length; i++) {
                _productos[i] = new ProductoDetalles("");
                _productos[i].id = _productos_bd[i].getId();
                _productos[i].codigo_barra = (_productos_bd[i].getCodigo_barra());
                _productos[i].nombre = (_productos_bd[i].getNombre());
                _productos[i].registrado_por = (_productos_bd[i].getRegistrado_por());
                _productos[i].modificado_por = (_productos_bd[i].getModificado_por());
                _productos[i].devolucion = (_productos_bd[i].getDevolucion());
                _productos[i].precio = (_productos_bd[i].getPrecio());
                _productos[i].cantidad = (_productos_bd[i].getCantidad());
                _productos[i].total = (_productos_bd[i].getTotal());
                _productos[i].fecha_registro = (_productos_bd[i].getFecha_registro());
                _productos[i].hora_registro = (_productos_bd[i].getHora_registro());                
                _productos[i].sesion = (_productos_bd[i].getSesion());


            }
        }

        return _productos;
    }
}
