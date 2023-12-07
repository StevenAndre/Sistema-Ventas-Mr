
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class RepositorioCliente {
    private static final String ALL_CLIENTES = "select * from clientes";

    private Connection conn = null;
        public RepositorioCliente(){

            try {
                String url       = "jdbc:mysql://localhost:3306/sisvenmaranon";
                String user      = "root";
                String password  = "steandsql03";
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    public List<Cliente> filtrarClientes(String argument){
        List<Cliente> clientes= new ArrayList<>();
        Cliente cliente;
        System.out.println("ARGUMENTO: "+argument );
        String query="select * from clientes where Nombre like '%"+argument+"%' OR Direccion like '%"+argument+"%' OR Email like'%"+argument+"%'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){

                cliente = new Cliente( rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Direccion"),
                        rs.getInt("Edad"),
                        rs.getString("Email")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clientes;
    }

    public void updateCliente(Cliente c){
            int id=c.getId();

        String updateQuery= "UPDATE clientes SET Nombre='" + c.getNombre() +
                "', Email='" + c.getCorreo() +
                "', Direccion='" + c.getDireccion() +
                "', edad=" + c.getEdad() +
                " WHERE ID=" + id;


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteCliente(int id){


        String deleteQuery= "DELETE FROM clientes WHERE ID=" + id;


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void insertCliente(Cliente c){
            String insertquery="INSERT INTO clientes(nombre,email,direccion,edad)" +
                    "VALUES('"+c.getNombre()+"' , '"+c.getCorreo()+"' ,'"+c.getDireccion()+"','"+c.getEdad()+"')";


        try {
            Statement stmt = conn.createStatement();
             stmt.executeUpdate(insertquery);
            System.out.println(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


        public List<Cliente> listarClientes(){

            List<Cliente> clientes= new ArrayList<>();
            Cliente cliente;

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(ALL_CLIENTES);

                while(rs.next()){

                    cliente = new Cliente( rs.getInt("Id"),
                            rs.getString("Nombre"),
                            rs.getString("Direccion"),
                            rs.getInt("Edad"),
                            rs.getString("Email")
                            );
                    clientes.add(cliente);

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return clientes;

        }



    public void generarReporte(){
    try
    {
        String sql = "SELECT * FROM clientes";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Crear un nuevo libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clientes");

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
        FileOutputStream outputStream = new FileOutputStream("C://Users//steve//OneDrive//Escritorio//Proyectos programcion//Sistema-Maranon-final//src//main//java//reporte_clientes.xlsx");
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
