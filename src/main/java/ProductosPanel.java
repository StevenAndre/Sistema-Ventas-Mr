/*import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductosPanel extends JPanel {

   public ProductosPanel() {

        setLayout(new BorderLayout());
        JPanel  operaciones= new JPanel();
        JPanel  panelBotones= new JPanel(new GridLayout(1,4));


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Email");
        model.addColumn("Edad");
        model.addColumn("Dirección");
        model.addRow(new Object[]{"Juan", "juan@example.com", 25, "Calle 123"});
        model.addRow(new Object[]{"María", "maria@example.com", 30, "Avenida 456"});
        model.addRow(new Object[]{"Pedro", "pedro@example.com", 28, "Plaza Principal"});
        model.addRow(new Object[]{"Laura", "laura@example.com", 22, "Callejon 789"});
        JTable table = new JTable(model);
        operaciones.add(table);

        JLabel label = new JLabel(" MODULO PRODUCTOS");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label,BorderLayout.NORTH);

        JButton insertB = new JButton("Nuevo Producto");
        JButton listarB = new JButton("Ver Productos");
        JButton updateB = new JButton("Editar");
        JButton deleteB = new JButton("Eliminar");
        JButton reportB = new JButton("Reporte");

        insertB.setBackground(Color.CYAN);
        listarB.setBackground(Color.GREEN);
        updateB.setBackground(Color.YELLOW);
        deleteB.setBackground(Color.RED);

        panelBotones.add(insertB);
        panelBotones.add(listarB);
        panelBotones.add(updateB);
        panelBotones.add(deleteB);


        add(panelBotones,BorderLayout.SOUTH);
        add(operaciones,BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ejemplo Layout");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 200);
            frame.add(new ProductosPanel());
            frame.setVisible(true);
        });
    }

}*/

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductosPanel extends JPanel {

    private JPanel opcionProductoPanel;
    private JPanel viewPanel;
    private JTable table;
    private  JPanel  operaciones= new JPanel();
    private RepositorioProducto rp;
    private ProveedorRepositorio proveedorRp;

    public ProductosPanel() {
            rp= new RepositorioProducto();
            setLayout(new BorderLayout());
            JPanel panelCabecera= new JPanel();
            panelCabecera.setLayout(new GridLayout(3,1));
            JLabel titulo = new JLabel("MODULO PRODUCTOS");
            titulo.setFont(new Font("Arial", Font.BOLD, 24));

            JPanel panelBusqueda= new JPanel();


            JLabel labelBusqueda = new JLabel("Buscar:");
            JTextField buscarText = new JTextField(20);
            panelBusqueda.add(labelBusqueda);
            panelBusqueda.add(buscarText);


            panelBusqueda.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            titulo.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
            JButton boton1 = new JButton("Nuevo Producto");
            JButton boton2 = new JButton("Listar Productos");
            JButton boton3 = new JButton("Eliminar Producto");
            JButton boton4 = new JButton("Actualizar Producto");
            JButton boton5 = new JButton("Reporte");
            boton1.setBackground(Color.GREEN);
            boton2.setBackground(Color.CYAN);
            boton3.setBackground(Color.RED);
            boton4.setBackground(Color.YELLOW);


            panelBotones.add(boton1);
            panelBotones.add(boton2);
            panelBotones.add(boton3);
            panelBotones.add(boton4);
            panelBotones.add(boton5);

            add(panelBotones, BorderLayout.CENTER);
            panelCabecera.add(titulo);
            panelCabecera.add(panelBotones);
            panelCabecera.add(panelBusqueda);


            operaciones.add(new PanelListado(), BorderLayout.CENTER);





            add(panelCabecera,BorderLayout.NORTH);
            add(operaciones,BorderLayout.CENTER);

            buscarText.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {


                    java.util.List<Producto> productos=rp.filtrarProductos(buscarText.getText());

                    operaciones.removeAll();
                    operaciones.add(new PanelListado(productos), BorderLayout.CENTER);
                    operaciones.revalidate();
                    operaciones.repaint();


                    System.out.println("Texto insertado: " + buscarText.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    java.util.List<Producto> productos=rp.filtrarProductos(buscarText.getText());

                    operaciones.removeAll();
                    operaciones.add(new PanelListado(productos), BorderLayout.CENTER);
                    operaciones.revalidate();
                    operaciones.repaint();
                    System.out.println("Texto eliminado: " + buscarText.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });


            boton1.addActionListener((a)->{
                operaciones.removeAll();
                operaciones.add(new FormularioPanel(), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();


            });

            boton2.addActionListener((a)->{

                operaciones.removeAll();
                operaciones.add(new PanelListado(), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();


            });

            boton3.addActionListener((a)->{

                Producto p=actualizarForm();
                if (p == null) {
                    return;
                }
                rp.deleteProducto(p.getId());
                operaciones.removeAll();
                operaciones.add(new PanelListado(), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();
                JOptionPane.showMessageDialog(null, "Producto con ID:"+p.getId()+" actulizado con exito");



            });

            boton4.addActionListener((a)->{

                Producto producto=actualizarForm();
                if(producto==null)
                    return;
                operaciones.removeAll();
                operaciones.add(new FormularioPanel(producto), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();


            });

            boton5.addActionListener((a)->{
                rp.generarReporte();
            });

        }

        public Producto actualizarForm(){
            int filaSeleccionada = table.getSelectedRow();
            Producto producto= new Producto();
            Object[] fila= new Object[6];
            if (filaSeleccionada != -1) {
                TableModel modelo = table.getModel();

                for (int columna = 0; columna < modelo.getColumnCount(); columna++) {
                    Object valorCelda = modelo.getValueAt(filaSeleccionada, columna);
                    fila[columna]= modelo.getValueAt(filaSeleccionada, columna);
                    System.out.println("Valor en la fila " + filaSeleccionada + ", columna " + columna + ": " + valorCelda);
                }
                producto.setId((int) fila[0]);
                producto.setNombre((String) fila[1]);
                producto.setDescripcion((String) fila[2]);
                producto.setStock((int) fila[3]);
                producto.setPrecio((double) fila[4]);
                System.out.println(producto);
                return producto;

            } else {
                JOptionPane
                        .showMessageDialog(this,
                                "Tienes que seleccionar un producto",
                                "Error", JOptionPane.ERROR_MESSAGE);

                System.out.println("Ninguna fila seleccionada.");
                return  null;
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Ejemplo Layout");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 200);
                frame.add(new ClientesPanel());
                frame.setVisible(true);
            });
        }

        class FormularioPanel extends JPanel {

            private JTextField nombreField;
            private JTextField descripcionField;
            private JTextField stockField;
            private JTextField precioField;
            private JComboBox<Proveedor> comboBox;
            private JButton botonConfirmar= new JButton();


            public FormularioPanel() {
                proveedorRp= new ProveedorRepositorio();
                setLayout(new BorderLayout());
                JLabel titulo = new JLabel("INSERTAR PRODUCTO");
                titulo.setFont(new Font("Arial", Font.BOLD, 24));
                titulo.setBackground(Color.GREEN);
                JPanel formPanel= new JPanel(new GridLayout(6, 2, 5, 5));
                JLabel nombreLabel = new JLabel("Nombre:");
                nombreField = new JTextField();

                JLabel descriptioLabel = new JLabel("Descripcion:");
                descripcionField = new JTextField();

                JLabel stockLable = new JLabel("Stock:");
                stockField = new JTextField();

                JLabel precioLabel = new JLabel("Precio:");
                precioField = new JTextField();

                JLabel proveedorLabel= new JLabel("Proveedor");
                comboBox= new JComboBox<>(proveedorRp.listarProveedores().toArray(new Proveedor[0]));

                botonConfirmar.setBackground(Color.ORANGE);
                botonConfirmar.setText("Ingresar");

                botonConfirmar.addActionListener((a)->{
                    crearProducto();
                });
                comboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Proveedor selectedValue =(Proveedor) comboBox.getSelectedItem();
                        System.out.println(selectedValue);
                    }
                });


                formPanel.add(nombreLabel);
                formPanel.add(nombreField);
                formPanel.add(descriptioLabel);
                formPanel.add(descripcionField);
                formPanel.add(stockLable);
                formPanel.add(stockField);
                formPanel.add(precioLabel);
                formPanel.add(precioField);
                formPanel.add(proveedorLabel);
                formPanel.add(comboBox);
                add(titulo,BorderLayout.NORTH);
                add(formPanel,BorderLayout.CENTER);
                add(botonConfirmar,BorderLayout.SOUTH);


            }

            public FormularioPanel(Producto producto) {
                setLayout(new BorderLayout());
                JLabel titulo = new JLabel("ACTUALIZAR PRODUCTO");
                titulo.setFont(new Font("Arial", Font.BOLD, 24));
                titulo.setBackground(Color.GREEN);
                JPanel formPanel= new JPanel(new GridLayout(6, 2, 5, 5));
                JLabel nombreLabel = new JLabel("Nombre:");
                nombreField = new JTextField();

                JLabel descriptioLabel = new JLabel("Descripcion:");
                descripcionField = new JTextField();

                JLabel stockLable = new JLabel("Stock:");
                stockField = new JTextField();

                JLabel precioLabel = new JLabel("Precio:");
                precioField = new JTextField();
                botonConfirmar.setBackground(Color.ORANGE);
                botonConfirmar.setText("Ingresar");

                nombreField.setText(producto.getNombre());
                descripcionField.setText(producto.getDescripcion());
                stockField.setText(producto.getStock()+"");
                precioField.setText(producto.getPrecio()+"");
                botonConfirmar.setBackground(Color.yellow);
                botonConfirmar.setText("Actualizar");




                formPanel.add(nombreLabel);
                formPanel.add(nombreField);
                formPanel.add(descriptioLabel);
                formPanel.add(descripcionField);
                formPanel.add(stockLable);
                formPanel.add(stockField);
                formPanel.add(precioLabel);
                formPanel.add(precioField);
                add(titulo,BorderLayout.NORTH);
                add(formPanel,BorderLayout.CENTER);
                add(botonConfirmar,BorderLayout.SOUTH);

                botonConfirmar.addActionListener((a)->{
                    actualizarProducto(producto.getId());
                });
                add(botonConfirmar,BorderLayout.SOUTH);

            }


            public void actualizarProducto(int id){
                Producto producto= new Producto();
                producto.setId(id);
                producto.setNombre(nombreField.getText());
                producto.setDescripcion(descripcionField.getText());
                producto.setStock(Integer.parseInt(stockField.getText()));
                producto.setPrecio(Double.parseDouble(precioField.getText()));

                rp.updateProducto(producto);
                operaciones.removeAll();
                operaciones.add(new PanelListado(), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();
                JOptionPane.showMessageDialog(null, "Producto con ID:"+id+" actulizado con exito");
            }

            public void crearProducto(){
                Producto producto= new Producto();
                producto.setNombre(nombreField.getText());
                producto.setDescripcion(descripcionField.getText());
                producto.setStock(Integer.parseInt(stockField.getText()));
                producto.setPrecio(Double.parseDouble(precioField.getText()));
                producto.setProveedor((Proveedor)comboBox.getSelectedItem());


                rp.insertProducto(producto);
                operaciones.removeAll();
                operaciones.add(new PanelListado(), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();
                JOptionPane.showMessageDialog(null, "Producto ingresado con exito");
            }



        }

        class PanelListado extends JPanel{
            public PanelListado(){
                java.util.List<Producto> productos= rp.listarProductos();
                String[] columnas = {"Id","Nombre", "Descripcion", "stock", "Precio","Proveedor"};
                DefaultTableModel model = new DefaultTableModel(columnas, 0);
                for(Producto p:productos ){

                    model.addRow(new Object[]{p.getId(),p.getNombre(), p.getDescripcion(), p.getStock(), p.getPrecio(),p.getProveedor().getNombre()});
                }

                table = new JTable(model);

                table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(100);
                }
                table.getColumn("Descripcion").setPreferredWidth(300);
                table.getColumn("Nombre").setPreferredWidth(300);
                table.getColumn("Proveedor").setPreferredWidth(300);

                table.setFont(new Font("Arial", Font.PLAIN, 16));


                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 400));


                table.setFillsViewportHeight(true);

                add(scrollPane, BorderLayout.CENTER);


            }
            public PanelListado(java.util.List<Producto> productosF){
                java.util.List<Producto> productos=productosF;
                String[] columnas = {"Id","Nombre", "Descripcion", "stock", "Precio","Proveedor"};
                DefaultTableModel model = new DefaultTableModel(columnas, 0);
                for(Producto p:productos ){

                    model.addRow(new Object[]{p.getId(),p.getNombre(), p.getDescripcion(), p.getStock(), p.getPrecio(),p.getProveedor().getNombre()});
                }

                table = new JTable(model);

                table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(100);
                }
                table.getColumn("Descripcion").setPreferredWidth(300);
                table.getColumn("Nombre").setPreferredWidth(300);
                table.getColumn("Proveedor").setPreferredWidth(300);

                table.setFont(new Font("Arial", Font.PLAIN, 16));


                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 400));


                table.setFillsViewportHeight(true);

                add(scrollPane, BorderLayout.CENTER);


            }
        }
    }
