import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDetallesVentas {

    private static final String ALL_VENTAS = "select * from detalles_ventas";
    private Connection conn = null;
    private RepositorioProducto repP;
    private RepositorioVentas repVe;

    public RepositorioDetallesVentas() {
        repP= new RepositorioProducto();

        conn=ConexionBD.getConection();
    }



    public void insertDetalle(int ventaId, int productoId, int cantidad, double precio) {
        String insertQuery = "INSERT INTO detalles_ventas(id_venta, id_producto, cantidad, precio) " +
                "VALUES (" + ventaId + ", " + productoId + ", " + cantidad + ", " + precio + ")";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DetalleVenta> obtenerDetallesPorVenta(int ventaId) {
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        repVe= new RepositorioVentas();

        String query = "SELECT * FROM detalles_ventas WHERE id_venta = " + ventaId;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                DetalleVenta detalle = new DetalleVenta(
                        rs.getInt("id"),
                       repVe.obtenerVentaPorId( rs.getInt("id_venta")),
                       repP.obtenerProductoPorId( rs.getInt("id_producto")),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );
                detallesVenta.add(detalle);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return detallesVenta;
    }


}
