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
        model.addColumn("DirecciÃ³n");
        model.addRow(new Object[]{"Juan", "juan@example.com", 25, "Calle 123"});
        model.addRow(new Object[]{"MarÃ­a", "maria@example.com", 30, "Avenida 456"});
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductosPanel extends JPanel {

    private JPanel opcionProductoPanel;
    private JPanel viewPanel;

    public ProductosPanel() {
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        JLabel labelSuperior = new JLabel("MODULO PRODUCTOS");
        labelSuperior.setHorizontalAlignment(SwingConstants.CENTER);
        labelSuperior.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(labelSuperior);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel de opciones de productos
        opcionProductoPanel = new OpcionProductoPanel();
        add(opcionProductoPanel, BorderLayout.CENTER);

        // Panel para la vista en la parte inferior con GridBagLayout
        viewPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Ancho máximo
        gbc.weighty = 1.0; // Altura máxima
        gbc.fill = GridBagConstraints.BOTH; // Relleno en ambas direcciones

        viewPanel.add(new JLabel("Vista por defecto"), gbc);

        add(viewPanel, BorderLayout.SOUTH);
    }

    private void actualizarVista(String contenido) {
        viewPanel.removeAll();
        JPanel panelVista = new JPanel();
        panelVista.setBackground(Color.WHITE);
        panelVista.add(new JLabel(contenido));
        viewPanel.add(panelVista);
        viewPanel.revalidate();
        viewPanel.repaint();
    }

    private class OpcionProductoPanel extends JPanel {
        public OpcionProductoPanel() {
            setLayout(new GridLayout(1, 4));

            JButton insertB = new JButton("Nuevo Producto");
            JButton listarB = new JButton("Ver Producto");
            JButton updateB = new JButton("Editar");
            JButton deleteB = new JButton("Eliminar");
            JButton reportB = new JButton("Reporte");

            add(insertB);
            add(listarB);
            add(updateB);
            add(deleteB);
            add(reportB);

            // Acciones de los botones
            insertB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarVista("Vista de Nuevo Producto");
                }
            });

            listarB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarVista("Vista de Ver Producto");
                }
            });

            updateB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarVista("Vista de Editar");
                }
            });

            deleteB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarVista("Vista de Eliminar");
                }
            });

            reportB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarVista("Vista de Reporte");
                }
            });
        }
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
}
