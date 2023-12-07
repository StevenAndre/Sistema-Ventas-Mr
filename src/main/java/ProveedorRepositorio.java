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

public class ProveedorRepositorio {



    private static final String ALL_PROVEEDORES= "select * from Proveedores";

    private Connection conn = null;

    public ProveedorRepositorio() {

        conn=ConexionBD.getConection();

    }

    public List<Proveedor> filtrarProveedores(String argument) {
        List<Proveedor> proveedores = new ArrayList<>();
        Proveedor proveedor;
        System.out.println("ARGUMENTO: " + argument);
        String query = "SELECT * FROM Proveedores WHERE nombre LIKE '%" + argument + "%' OR telefono LIKE '%" + argument + "%' OR direccion LIKE '%" + argument + "%' OR email LIKE '%" + argument + "%'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                proveedor = new Proveedor(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email")
                );
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return proveedores;
    }

    public void updateProveedor(Proveedor p) {
        int id = p.getId();

        String updateQuery = "UPDATE Proveedores SET nombre='" + p.getNombre() +
                "', telefono='" + p.getTelefono() +
                "', direccion='" + p.getDireccion() +
                "', email='" + p.getEmail() +
                "' WHERE id=" + id;



        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteProveedor(int id) {


        String deleteQuery = "DELETE FROM Proveedores WHERE id=" + id;


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public Proveedor getProveedor(int id){
        String getQuery = "select * from Proveedores where id="+id;

        Proveedor proveedor=null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getQuery);
            while (rs.next()) {

                proveedor = new Proveedor(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return proveedor;
    }


    public void insertProveedor(Proveedor p) {
        String insertQuery = "INSERT INTO Proveedores(nombre, telefono, direccion, email)" +
                " VALUES('" + p.getNombre() + "' , '" + p.getTelefono() + "' , '" +
                p.getDireccion() + "' , '" + p.getEmail() + "')";


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);
            System.out.println(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public List<Proveedor> listarProveedores() {

        List<Proveedor> proveedores = new ArrayList<>();
        Proveedor proveedor;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_PROVEEDORES);

            while (rs.next()) {

                proveedor = new Proveedor(rs.getInt("Id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email")
                );
                proveedores.add(proveedor);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return proveedores;

    }


    public void generarReporte() {
        try {
            String sql = "SELECT * FROM Proveedores";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Crear un nuevo libro de trabajo de Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Proveedores");

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
            FileOutputStream outputStream = new FileOutputStream("C://Users//steve//OneDrive//Escritorio//Proyectos programcion//Sistema-Maranon-final//src//main//java//reporte_proveedores.xlsx");
            workbook.write(outputStream);
            workbook.close();

            // Cerrar recursos
            rs.close();
            stmt.close();
            System.out.println("El reporte se generó exitosamente.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }
}


