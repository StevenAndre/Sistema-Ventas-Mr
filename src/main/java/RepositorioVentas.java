import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepositorioVentas {

    private static final String ALL_VENTAS = "select * from Ventas";
    private Connection conn = null;
    private RepositorioUsuario repU;
    private RepositorioCliente repC;
    private RepositorioDetallesVentas rpDV;

    public RepositorioVentas() {
        this.repU= new RepositorioUsuario();
        this.repC= new RepositorioCliente();
        rpDV= new RepositorioDetallesVentas();
        conn=ConexionBD.getConection();
    }

    public List<Venta> filtrarVentas(String argument) {
        List<Venta> ventas = new ArrayList<>();
        Venta venta;
        System.out.println("ARGUMENTO: " + argument);
        String query = "select * from Ventas where id_cliente in (select id from clientes where nombre like '%" + argument + "%') OR id_vendedor in (select id from usuarios where nombre like '%" + argument + "%')";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                venta = new Venta(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"),
                        obtenerClientePorId(rs.getInt("id_cliente")),
                        obtenerUsuarioPorId(rs.getInt("id_vendedor"))
                );
                ventas.add(venta);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ventas;
    }


    public Venta obtenerVentaPorId(int id) {
        Venta venta = null;
        String query = "select * from ventas where id = " + id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                venta = new Venta(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"),
                        obtenerClientePorId(rs.getInt("id_cliente")),
                        obtenerUsuarioPorId(rs.getInt("id_vendedor"))
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return venta;
    }



    public void deleteVenta(int id) {
        String deleteQuery = "DELETE FROM Ventas WHERE id=" + id;

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertVenta(Venta v, Map<Producto,Integer> productosXCantidad) {
        Date fecha = v.getFecha();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        String fechaFormateada = formatoFecha.format(fecha);
        double total=0;
        for (Map.Entry<Producto, Integer> entry : productosXCantidad.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
           total+= producto.getPrecio()*cantidad;
        }

        String insertQuery = "INSERT INTO Ventas(fecha, total, id_cliente, id_vendedor) VALUES('" +fechaFormateada + "'," +total + "," + v.getCliente().getId() + "," + v.getUsuario().getId() + ")";

        int ventaId = -1;

        try {
            Statement stmtVenta = conn.createStatement();
            stmtVenta.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = stmtVenta.getGeneratedKeys();
            if (generatedKeys.next()) {
                ventaId = generatedKeys.getInt(1);
            }
            for (Map.Entry<Producto, Integer> entry : productosXCantidad.entrySet()) {
                Producto producto = entry.getKey();
                Integer cantidad = entry.getValue();
               rpDV.insertDetalle(ventaId, producto.getId(), cantidad, producto.getPrecio());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        Venta venta;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_VENTAS);

            while (rs.next()) {
                venta = new Venta(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"),
                        obtenerClientePorId(rs.getInt("id_cliente")),
                        obtenerUsuarioPorId(rs.getInt("id_vendedor"))
                );
                ventas.add(venta);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ventas;
    }

    public void generarReporteVentas() {
        try {
            String sql = "SELECT * FROM Ventas";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Crear un nuevo libro de trabajo de Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Ventas");

            // Crear el encabezado de las columnas
            Row headerRow = sheet.createRow(0);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(metaData.getColumnName(i));
            }

            // Llenar el archivo Excel con los datos
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(rs.getString(i));
                }
            }

            // Guardar el libro de trabajo en un archivo
            FileOutputStream outputStream = new FileOutputStream("ruta/del/archivo/reporte_ventas.xlsx");
            workbook.write(outputStream);
            workbook.close();

            // Cerrar recursos
            rs.close();
            stmt.close();
            conn.close();

            System.out.println("El reporte de ventas se generó exitosamente.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private Cliente obtenerClientePorId(int id) {
        return repC.obtenerClientePorId(id);
    }

    private Usuario obtenerUsuarioPorId(int id) {
        return repU.obtenerUsuarioPorId(id);
    }
}
