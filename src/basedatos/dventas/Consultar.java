package basedatos.dventas;

import basedatos.conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends ProductoDetalles {

    public boolean consultarPorID(String _id) {

        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        String _sql;
        ResultSet _rs;

        _cn = null;

        try {

            _sql = "select * from detalles_venta where id = " + _id;
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();

            if (_rs.next()) {

                super.id = _rs.getString("id");
                codigo_barra = _rs.getString("codigo_barra");
                nombre = _rs.getString("nombre");
                registrado_por = _rs.getString("registrado_por");
                modificado_por = _rs.getString("modificado_por");
                devolucion = _rs.getString("devolucion");
                precio = _rs.getString("precio");
                cantidad = _rs.getString("cantidad");
                total = _rs.getString("total");
                fecha_registro = _rs.getString("fecha_registro");
                hora_registro = _rs.getString("hora_registro");
                sesion = _rs.getString("sesion");

                return true;

            }

        } catch (IOException | SQLException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {
                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return false;
    }

    public boolean consultarPorFecha(String _fecha) {

        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        String _sql;
        ResultSet _rs;

        _cn = null;

        try {

            _sql = "select * from detalles_venta where fecha_registro = " + _fecha;
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();

            if (_rs.next()) {

                super.id = _rs.getString("id");
                codigo_barra = _rs.getString("codigo_barra");
                nombre = _rs.getString("nombre");
                registrado_por = _rs.getString("registrado_por");
                modificado_por = _rs.getString("modificado_por");
                devolucion = _rs.getString("devolucion");
                precio = _rs.getString("precio");
                cantidad = _rs.getString("cantidad");
                total = _rs.getString("total");
                fecha_registro = _rs.getString("fecha_registro");
                hora_registro = _rs.getString("hora_registro");
                sesion = _rs.getString("sesion");
                return true;

            }

        } catch (IOException | SQLException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {
                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return false;

    }

    public ProductoDetalles[] consultarTodoPorFecha(String _fecha) {

        String _sql;
        Connection _cn;
        ProductoDetalles[] _productos;
        PreparedStatement _pst;
        ResultSet _rs;

        _productos = null;
        _cn = null;

        try {

            _sql = "select * from detalles_venta where fecha_registro = " + _fecha;
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();
            int i = 0;

            if (_rs.last()) {

                _productos = new ProductoDetalles[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _productos[i].id = _rs.getString("id");
                        _productos[i].codigo_barra = _rs.getString("codigo_barra");
                        _productos[i].nombre = _rs.getString("nombre");
                        _productos[i].registrado_por = _rs.getString("registrado_por");
                        _productos[i].modificado_por = _rs.getString("modificado_por");
                        _productos[i].devolucion = _rs.getString("devolucion");
                        _productos[i].precio = _rs.getString("precio");
                        _productos[i].cantidad = _rs.getString("cantidad");
                        _productos[i].total = _rs.getString("total");
                        _productos[i].fecha_registro = _rs.getString("fecha_registro");
                        _productos[i].hora_registro = _rs.getString("hora_registro");                        
                        _productos[i].sesion = _rs.getString("sesion");

                        i++;
                    } while (_rs.next());
                }
            }

        } catch (SQLException | IOException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {

                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return _productos;

    }

    public boolean consultarPorFechaYUsuario(String _fecha, String _usuario) {

        Connection _cn;
        PreparedStatement _pst;
        Conexion _cx;
        String _sql;
        ResultSet _rs;

        _cn = null;

        try {

            _sql = "select * from detalles_venta where fecha_registro = ? && registrado_por = ?";
            _cx = new Conexion();
            _cn = _cx.establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(1, _fecha);
            _pst.setString(2, _usuario);

            _rs = _pst.executeQuery();

            if (_rs.next()) {

                super.id = _rs.getString("id");
                codigo_barra = _rs.getString("codigo_barra");
                nombre = _rs.getString("nombre");
                registrado_por = _rs.getString("registrado_por");
                modificado_por = _rs.getString("modificado_por");
                devolucion = _rs.getString("devolucion");
                precio = _rs.getString("precio");
                cantidad = _rs.getString("cantidad");
                total = _rs.getString("total");
                fecha_registro = _rs.getString("fecha_registro");
                hora_registro = _rs.getString("hora_registro");                
                sesion = _rs.getString("sesion");


                return true;

            }

        } catch (IOException | SQLException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {
                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return false;
    }

    public ProductoDetalles[] consultarTodoPorFechaYUsuario(String _fecha, String _usuario) {

        String _sql;
        Connection _cn;
        ProductoDetalles[] _productos;
        PreparedStatement _pst;
        ResultSet _rs;

        _productos = null;
        _cn = null;

        try {

            _sql = "select * from detalles_venta where fecha_registro = ? && registrado_por = ?";
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(1, _fecha);
            _pst.setString(2, _usuario);

            _rs = _pst.executeQuery();
            int i = 0;

            if (_rs.last()) {

                _productos = new ProductoDetalles[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _productos[i] = new ProductoDetalles();

                        _productos[i].setId(_rs.getString("id"));
                        _productos[i].codigo_barra = _rs.getString("codigo_barra");
                        _productos[i].nombre = _rs.getString("nombre");
                        _productos[i].registrado_por = _rs.getString("registrado_por");
                        _productos[i].modificado_por = _rs.getString("modificado_por");
                        _productos[i].devolucion = _rs.getString("devolucion");
                        _productos[i].precio = _rs.getString("precio");
                        _productos[i].cantidad = _rs.getString("cantidad");
                        _productos[i].total = _rs.getString("total");
                        _productos[i].fecha_registro = _rs.getString("fecha_registro");
                        _productos[i].hora_registro = _rs.getString("hora_registro");                        
                        _productos[i].sesion = _rs.getString("sesion");

                        i++;
                    } while (_rs.next());
                }
            }

        } catch (SQLException | IOException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {

                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return _productos;

    }
    
    public ProductoDetalles[] consultarTodoPorFechaUsuarioYSesion(String _fecha, String _usuario, String _sesion) {

        String _sql;
        Connection _cn;
        ProductoDetalles[] _productos;
        PreparedStatement _pst;
        ResultSet _rs;

        _productos = null;
        _cn = null;

        try {

            _sql = "select * from detalles_venta where fecha_registro = ? && registrado_por = ? && sesion = ?";
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);

            _pst.setString(1, _fecha);
            _pst.setString(2, _usuario);
            _pst.setString(3, _sesion);

            _rs = _pst.executeQuery();
            int i = 0;

            if (_rs.last()) {

                _productos = new ProductoDetalles[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _productos[i] = new ProductoDetalles();

                        _productos[i].setId(_rs.getString("id"));
                        _productos[i].codigo_barra = _rs.getString("codigo_barra");
                        _productos[i].nombre = _rs.getString("nombre");
                        _productos[i].registrado_por = _rs.getString("registrado_por");
                        _productos[i].modificado_por = _rs.getString("modificado_por");
                        _productos[i].devolucion = _rs.getString("devolucion");
                        _productos[i].precio = _rs.getString("precio");
                        _productos[i].cantidad = _rs.getString("cantidad");
                        _productos[i].total = _rs.getString("total");
                        _productos[i].fecha_registro = _rs.getString("fecha_registro");
                        _productos[i].hora_registro = _rs.getString("hora_registro");                        
                        _productos[i].sesion = _rs.getString("sesion");

                        i++;
                    } while (_rs.next());
                }
            }

        } catch (SQLException | IOException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {

                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return _productos;

    }

    public ProductoDetalles[] consultarTodo() {

        String _sql;
        Connection _cn;
        ProductoDetalles[] _productos;
        PreparedStatement _pst;
        ResultSet _rs;

        _productos = null;
        _cn = null;

        try {

            _sql = "select * from detalles_venta";
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            _rs = _pst.executeQuery();
            int i = 0;

            if (_rs.last()) {

                _productos = new ProductoDetalles[_rs.getRow()];
                _rs.beforeFirst();

                if (_rs.next()) {
                    do {
                        _productos[i] = new ProductoDetalles();
                        
                        _productos[i].id = _rs.getString("id");
                        _productos[i].codigo_barra = _rs.getString("codigo_barra");
                        _productos[i].nombre = _rs.getString("nombre");
                        _productos[i].registrado_por = _rs.getString("registrado_por");
                        _productos[i].modificado_por = _rs.getString("modificado_por");
                        _productos[i].devolucion = _rs.getString("devolucion");
                        _productos[i].precio = _rs.getString("precio");
                        _productos[i].cantidad = _rs.getString("cantidad");
                        _productos[i].total = _rs.getString("total");
                        _productos[i].fecha_registro = _rs.getString("fecha_registro");
                        _productos[i].hora_registro = _rs.getString("hora_registro");                       
                        _productos[i].sesion = _rs.getString("sesion");

                        i++;
                    } while (_rs.next());
                }
            }

        } catch (SQLException | IOException e) {

            Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, e);

        } finally {

            if (_cn != null) {

                try {
                    _cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consultar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return _productos;
    }

}
