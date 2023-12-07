import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentasPanel extends JPanel {
    private JTable table;
    private  JPanel  operaciones= new JPanel();
    private RepositorioVentas rp;
    private RepositorioCliente rpCli;

    public VentasPanel() {

        rp= new RepositorioVentas();
        setLayout(new BorderLayout());
        JPanel panelCabecera= new JPanel();
        panelCabecera.setLayout(new GridLayout(3,1));
        JLabel titulo = new JLabel("MODULO VENTAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBusqueda= new JPanel();


        JLabel labelBusqueda = new JLabel("Buscar:");
        JTextField buscarText = new JTextField(20); // Puedes especificar el número de columnas deseadas
        panelBusqueda.add(labelBusqueda);
        panelBusqueda.add(buscarText);


        // Establecer un layout manager más apropiado, como FlowLayout
        panelBusqueda.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Ajusta los valores según tus preferencias



        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // 10, 10 son los espacios entre botones
        JButton boton1 = new JButton("Nuevo Venta");
        JButton boton2 = new JButton("Listar Ventas");
        JButton boton3 = new JButton("Anular Venta");
        JButton boton4 = new JButton("ver detalles");
        JButton boton5 = new JButton("Reporte");
        boton1.setBackground(Color.GREEN);
        boton2.setBackground(Color.CYAN);
        boton3.setBackground(new Color(155, 10, 176));
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
                // Acción a realizar cuando se inserta texto

                List<Venta> ventas=rp.filtrarVentas(buscarText.getText());

                operaciones.removeAll();
                operaciones.add(new PanelListado(ventas), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();


                System.out.println("Texto insertado: " + buscarText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                List<Venta> ventas=rp.filtrarVentas(buscarText.getText());

                operaciones.removeAll();
                operaciones.add(new PanelListado(ventas), BorderLayout.CENTER);
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
            operaciones.add(new FormularioPanel (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();


        });

        boton2.addActionListener((a)->{

            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();


        });

        boton3.addActionListener((a)->{

            Venta v=ventaSelected();
            if (v == null) {
                return;
            }
            rp.deleteVenta(v.getId());
            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();
            JOptionPane.showMessageDialog(null, "VENTA con ID:"+v.getId()+" anulada con exito");



        });

        boton4.addActionListener((a)->{



            Venta v=ventaSelected();
            if(v==null)
                return;
            operaciones.removeAll();
            operaciones.add(new PanelDetalles (v.getId()), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();


        });

        boton5.addActionListener((a)->{
            rp.generarReporteVentas();
        });

    }

    public Venta ventaSelected(){
        int filaSeleccionada = table.getSelectedRow();
        Venta venta= new Venta();
        Object[] fila= new Object[5];
        if (filaSeleccionada != -1) {
            TableModel modelo = table.getModel();

            for (int columna = 0; columna < modelo.getColumnCount(); columna++) {
                Object valorCelda = modelo.getValueAt(filaSeleccionada, columna);
                  fila[columna]= modelo.getValueAt(filaSeleccionada, columna);
                System.out.println("Valor en la fila " + filaSeleccionada + ", columna " + columna + ": " + valorCelda);
            }
            venta.setId((int) fila[0]);
            return venta;

        } else {
            JOptionPane
                    .showMessageDialog(this,
                            "Tienes que seleccionar una venta",
                            "Error", JOptionPane.ERROR_MESSAGE);

            System.out.println("Ninguna fila seleccionada.");
            return  null;
        }
    }


    class FormularioPanel extends JPanel {

        private JButton botonConfirmar= new JButton();
        private JComboBox<Cliente> clienteJComboBox;
        private JComboBox<Producto> productosJComboBox;
        private Map<Producto,Integer> productsXCantidad= new HashMap<>();
        private Cliente cliente;
        private JTextField cantidad;
        private  DefaultListModel<String> productosListModel;
        private   JList<String> productosJList;

        public FormularioPanel() {


            RepositorioProducto rpP= new RepositorioProducto();
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            rpCli=new RepositorioCliente();
            JLabel titulo = new JLabel("CREAR VENTA");
            titulo.setFont(new Font("Arial", Font.BOLD, 24));
            titulo.setBackground(Color.GREEN);
            JPanel formPanel= new JPanel(new GridLayout(2, 3, 5, 5));
            formPanel.setSize(800,500);
            JLabel clienteLabel= new JLabel("Cliente:");
            JButton guardarCliente=new JButton("guardar");
            JButton guardarProducto=new JButton("agregar");
            clienteJComboBox= new JComboBox<>(rpCli.listarClientes().toArray(new Cliente[0]));
            productosJComboBox=new JComboBox<>(rpP.listarProductos().toArray(new Producto[0]));
            cantidad=new JTextField();



            botonConfirmar.setBackground(Color.ORANGE);
            botonConfirmar.setText("Aceptar");

            guardarCliente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cliente= (Cliente) clienteJComboBox.getSelectedItem();
                    clienteJComboBox.setEnabled(false);
                }
            });

            guardarProducto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Producto producto=(Producto) productosJComboBox.getSelectedItem();
                    int can=Integer.parseInt( cantidad.getText());
                    productsXCantidad.put(producto,can);
                    cantidad.setText("");
                    updateProductosList();
                }
            });

            botonConfirmar.addActionListener((a)->{
                crearVenta();
            });
             productosListModel = new DefaultListModel<>();
             productosJList = new JList<>(productosListModel);


            formPanel.add(clienteLabel);
            formPanel.add(clienteJComboBox);
            formPanel.add(guardarCliente);
            formPanel.add(productosJComboBox);
            formPanel.add(cantidad);
            formPanel.add(guardarProducto);

            add(titulo,BorderLayout.NORTH);
            add(formPanel,BorderLayout.CENTER);
            add(productosJList,BorderLayout.WEST);
            add(botonConfirmar,BorderLayout.SOUTH);


        }

        private void updateProductosList() {
            productosListModel.clear();
            for (Map.Entry<Producto, Integer> entry : productsXCantidad.entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                productosListModel.addElement(producto.getNombre() + " - Cantidad: " + cantidad);
            }
        }


        public void crearVenta(){
            Venta venta= new Venta();
            venta.setFecha(new Date());
            venta.setCliente(cliente);
            venta.setUsuario(RepositorioUsuario.usuarioActual);

            rp.insertVenta(venta,productsXCantidad);
            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();
            JOptionPane.showMessageDialog(null, "Venta registrada con exito");
        }


    }


    class PanelDetalles extends JPanel{
        private RepositorioDetallesVentas rpDV= new RepositorioDetallesVentas();
        public PanelDetalles(int ventaId){
            List<DetalleVenta> detalles=rpDV.obtenerDetallesPorVenta(ventaId);
            String[] columnas = {"Producto","Precio", "Cantidad", "Importe"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            double total=0;
            for(DetalleVenta d:detalles ){
                double importe=d.getPrecio()*d.getCantidad();
                total+=importe;
                model.addRow(new Object[]{d.getProducto().getNombre(),d.getPrecio(),d.getCantidad(),importe});
            }
            model.addRow(new Object[]{"","","TOTAL:",total});

            table = new JTable(model);
            table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
            }
            table.getColumn("Producto").setPreferredWidth(300);
            table.setFont(new Font("Arial", Font.PLAIN, 16));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 100));


            table.setFillsViewportHeight(true);
            JLabel titulo= new JLabel("DETALLES VENTA N°:"+ventaId);
            titulo.setFont(new Font("Arial", Font.ITALIC, 18));


            add(scrollPane, BorderLayout.CENTER);


        }
    }


    class PanelListado extends JPanel{
        public PanelListado(){
            List<Venta> ventas= rp.listarVentas();
             String[] columnas = {"Id","Vendedor", "Cliente", "total", "fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            for(Venta v:ventas ){

                model.addRow(new Object[]{v.getId(),v.getUsuario().getNombre(), v.getCliente().getNombre(),
                        v.getTotal(), v.getFecha()});
            }

            table = new JTable(model);

            table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(200);
            }
            table.getColumn("Vendedor").setPreferredWidth(300);
            table.getColumn("Cliente").setPreferredWidth(300);
            table.setFont(new Font("Arial", Font.PLAIN, 16));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));


            table.setFillsViewportHeight(true);

            add(scrollPane, BorderLayout.CENTER);


        }
        public PanelListado(List<Venta> ventaList){
            List<Venta> ventas= ventaList;
            String[] columnas = {"Id","Vendedor", "Cliente", "total", "fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            for(Venta v:ventas ){

                model.addRow(new Object[]{v.getId(),v.getUsuario().getNombre(), v.getCliente().getNombre(),
                        v.getTotal(), v.getFecha()});
            }

            table = new JTable(model);

            table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(200);
            }
            table.getColumn("Vendedor").setPreferredWidth(300);
            table.getColumn("Cliente").setPreferredWidth(300);

            table.setFont(new Font("Arial", Font.PLAIN, 16));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));


            table.setFillsViewportHeight(true);

            add(scrollPane, BorderLayout.CENTER);


        }
    }
}
