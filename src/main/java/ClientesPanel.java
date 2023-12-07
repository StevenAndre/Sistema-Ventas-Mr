import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class ClientesPanel extends JPanel {
    private JTable table;
    private  JPanel  operaciones= new JPanel();
    private RepositorioCliente rp;

    public ClientesPanel() {

        rp= new RepositorioCliente();
        setLayout(new BorderLayout());
        JPanel panelCabecera= new JPanel();
        panelCabecera.setLayout(new GridLayout(3,1));
        JLabel titulo = new JLabel("MODULO CLIENTES");
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
        JButton boton1 = new JButton("Nuevo Cliente");
        JButton boton2 = new JButton("Listar Clientes");
        JButton boton3 = new JButton("Eliminar");
        JButton boton4 = new JButton("Actualizar");
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
                // Acción a realizar cuando se inserta texto

                List<Cliente> clientes=rp.filtrarClientes(buscarText.getText());

                operaciones.removeAll();
                operaciones.add(new PanelListado(clientes), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();


                System.out.println("Texto insertado: " + buscarText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                List<Cliente> clientes=rp.filtrarClientes(buscarText.getText());

                operaciones.removeAll();
                operaciones.add(new PanelListado(clientes), BorderLayout.CENTER);
                operaciones.revalidate();
                operaciones.repaint();
                System.out.println("Texto eliminado: " + buscarText.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Este método se usa principalmente en documentos que pueden cambiar atributos
                // no se usa normalmente en campos de texto simples
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

            Cliente c=actualizarForm();
            if (c == null) {
                return;
            }
            rp.deleteCliente(c.getId());
            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();
            JOptionPane.showMessageDialog(null, "Cliente con ID:"+c.getId()+" actulizado con exito");



        });

        boton4.addActionListener((a)->{

            Cliente cliente=actualizarForm();
            if(cliente==null)
                return;
            operaciones.removeAll();
            operaciones.add(new FormularioPanel (cliente), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();


        });

        boton5.addActionListener((a)->{
            rp.generarReporte();
        });

    }

    public Cliente actualizarForm(){
        int filaSeleccionada = table.getSelectedRow();
        Cliente cliente= new Cliente();
        Object[] fila= new Object[5];
        if (filaSeleccionada != -1) {
            TableModel modelo = table.getModel();

            for (int columna = 0; columna < modelo.getColumnCount(); columna++) {
                Object valorCelda = modelo.getValueAt(filaSeleccionada, columna);
                  fila[columna]= modelo.getValueAt(filaSeleccionada, columna);
                System.out.println("Valor en la fila " + filaSeleccionada + ", columna " + columna + ": " + valorCelda);
            }
            cliente.setId((int) fila[0]);
            cliente.setNombre((String) fila[1]);
            cliente.setCorreo((String) fila[2]);
            cliente.setEdad((int) fila[3]);
            cliente.setDireccion((String) fila[4]);
            System.out.println(cliente);
            return cliente;

        } else {
            JOptionPane
                    .showMessageDialog(this,
                            "Tienes que seleccionar un cliente",
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
        private JTextField emailField;
        private JTextField edadField;
        private JTextField direccionField;
        private JButton botonConfirmar= new JButton();


        public FormularioPanel() {
            setLayout(new BorderLayout());
            JLabel titulo = new JLabel("INSERTAR CLIENTE");
            titulo.setFont(new Font("Arial", Font.BOLD, 24));
            titulo.setBackground(Color.GREEN);
            JPanel formPanel= new JPanel(new GridLayout(5, 2, 5, 5));
            JLabel nombreLabel = new JLabel("Nombre:");
            nombreField = new JTextField();

            JLabel emailLabel = new JLabel("Email:");
            emailField = new JTextField();

            JLabel edadLabel = new JLabel("Edad:");
            edadField = new JTextField();

            JLabel direccionLabel = new JLabel("Dirección:");
            direccionField = new JTextField();
            botonConfirmar.setBackground(Color.ORANGE);
            botonConfirmar.setText("Aceptar");

            botonConfirmar.addActionListener((a)->{
                crearCliente();
            });



            formPanel.add(nombreLabel);
            formPanel.add(nombreField);
            formPanel.add(emailLabel);
            formPanel.add(emailField);
            formPanel.add(edadLabel);
            formPanel.add(edadField);
            formPanel.add(direccionLabel);
            formPanel.add(direccionField);
            add(titulo,BorderLayout.NORTH);
            add(formPanel,BorderLayout.CENTER);
            add(botonConfirmar,BorderLayout.SOUTH);


        }

        public FormularioPanel(Cliente cliente) {
            setLayout(new BorderLayout());
            JLabel titulo = new JLabel("ACTUALIZAR CLIENTE");
            titulo.setFont(new Font("Arial", Font.BOLD, 24));
            titulo.setBackground(Color.GREEN);
            JPanel formPanel= new JPanel(new GridLayout(5, 2, 5, 5));
            JLabel nombreLabel = new JLabel("Nombre:");
            nombreField = new JTextField();

            JLabel emailLabel = new JLabel("Email:");
            emailField = new JTextField();

            JLabel edadLabel = new JLabel("Edad:");
            edadField = new JTextField();

            JLabel direccionLabel = new JLabel("Dirección:");
            direccionField = new JTextField();
            nombreField.setText(cliente.getNombre());
            emailField.setText(cliente.getCorreo());
            edadField.setText(cliente.getEdad()+"");
            direccionField.setText(cliente.getDireccion());
            botonConfirmar.setBackground(Color.yellow);
            botonConfirmar.setText("Actualizar");

            formPanel.add(nombreLabel);
            formPanel.add(nombreField);
            formPanel.add(emailLabel);
            formPanel.add(emailField);
            formPanel.add(edadLabel);
            formPanel.add(edadField);
            formPanel.add(direccionLabel);
            formPanel.add(direccionField);
            add(titulo,BorderLayout.NORTH);
            add(formPanel,BorderLayout.CENTER);
            botonConfirmar.addActionListener((a)->{
                actualizarCliente(cliente.getId());
            });
            add(botonConfirmar,BorderLayout.SOUTH);

        }


        public void actualizarCliente(int id){
            Cliente cliente= new Cliente();
            cliente.setId(id);
            cliente.setNombre(nombreField.getText());
            cliente.setCorreo(emailField.getText());
            cliente.setDireccion(direccionField.getText());
            cliente.setEdad(Integer.parseInt(edadField.getText()));

            rp.updateCliente(cliente);
            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();
            JOptionPane.showMessageDialog(null, "CLiente con ID:"+id+" actulizado con exito");
        }

        public void crearCliente(){
            Cliente cliente= new Cliente();
            cliente.setNombre(nombreField.getText());
            cliente.setCorreo(emailField.getText());
            cliente.setDireccion(direccionField.getText());
            cliente.setEdad(Integer.parseInt(edadField.getText()));

            rp.insertCliente(cliente);
            operaciones.removeAll();
            operaciones.add(new PanelListado (), BorderLayout.CENTER);
            operaciones.revalidate();
            operaciones.repaint();
            JOptionPane.showMessageDialog(null, "CLiente ingresado con exito");
        }



    }

    class PanelListado extends JPanel{
        public PanelListado(){
            List<Cliente> clientes= rp.listarClientes();
             String[] columnas = {"Id","Nombre", "Email", "edad", "Dirección"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            for(Cliente c:clientes ){

                model.addRow(new Object[]{c.getId(),c.getNombre(), c.getCorreo(), c.getEdad(), c.getDireccion()});
            }

            table = new JTable(model);

            table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(200);
            }
            table.getColumn("Email").setPreferredWidth(300);

            table.setFont(new Font("Arial", Font.PLAIN, 16));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));


            table.setFillsViewportHeight(true);

            add(scrollPane, BorderLayout.CENTER);


        }
        public PanelListado(List<Cliente> clientesF){
            List<Cliente> clientes= clientesF;
            String[] columnas = {"Id","Nombre", "Email", "edad", "Dirección"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            for(Cliente c:clientes ){

                model.addRow(new Object[]{c.getId(),c.getNombre(), c.getCorreo(), c.getEdad(), c.getDireccion()});
            }

            table = new JTable(model);

            table.setPreferredScrollableViewportSize(new Dimension(400, table.getPreferredSize().height));

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(200);
            }
            table.getColumn("Email").setPreferredWidth(300);

            table.setFont(new Font("Arial", Font.PLAIN, 16));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));


            table.setFillsViewportHeight(true);

            add(scrollPane, BorderLayout.CENTER);


        }
    }
}
