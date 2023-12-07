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

public class RepositorioProducto {

    private ProveedorRepositorio pr= new ProveedorRepositorio();
    private static final String ALL_PRODUCTOS = "select * from productos";

    private Connection conn = null;

    public RepositorioProducto() {

        conn=ConexionBD.getConection();

    }

    public Producto obtenerProductoPorId(int id) {
        Producto producto = null;
        String query = "select * from productos where id = " + id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                producto = new Producto(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    public List<Producto> filtrarProductos(String argument) {
        List<Producto> productos = new ArrayList<>();
        Producto producto;
        System.out.println("ARGUMENTO: " + argument);
        String query = "select * from productos where nombre like '%" + argument + "%' OR descripcion like '%" + argument + "%'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                producto = new Producto(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getDouble("precio")
                );
                producto.setProveedor(pr.getProveedor(rs.getInt("proveedor_id")));
                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productos;
    }

    public void updateProducto(Producto p) {
        int id = p.getId();

        String updateQuery = "UPDATE productos SET nombre='" + p.getNombre() +
                "', descripcion='" + p.getDescripcion() +
                "', stock='" + p.getStock() +
                "', precio=" + p.getPrecio() +
                " WHERE ID=" + id;


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteProducto(int id) {


        String deleteQuery = "DELETE FROM productos WHERE id=" + id;


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void insertProducto(Producto p) {
        String insertquery = "INSERT INTO productos(nombre,descripcion,stock,precio,proveedor_id)" +
                "VALUES('" + p.getNombre() + "' , '" + p.getDescripcion() + "' , " + p.getStock() + "," + p.getPrecio()+ ","+p.getProveedor().getId()+ ")";


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertquery);
            System.out.println(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public List<Producto> listarProductos() {

        List<Producto> productos = new ArrayList<>();
        Producto producto;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_PRODUCTOS);

            while (rs.next()) {

                producto = new Producto(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getDouble("precio")
                );
                producto.setProveedor(pr.getProveedor(rs.getInt("proveedor_id")));
                productos.add(producto);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productos;

    }


    public void generarReporte() {
        try {
            String sql = "SELECT * FROM productos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Crear un nuevo libro de trabajo de Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");

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
            FileOutputStream outputStream = new FileOutputStream("C://Users//steve//OneDrive//Escritorio//Proyectos programcion//Sistema-Maranon-final//src//main//java//reporte_productos.xlsx");
            workbook.write(outputStream);
            workbook.close();

            // Cerrar recursos
            rs.close();
            stmt.close();
            conn.close();

            System.out.println("El reporte se generó exitosamente.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }
}

