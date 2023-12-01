import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientesPanel extends JPanel {

    public ClientesPanel() {

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

        JLabel label = new JLabel("HOLAAAAAA MODULO CLIENTES");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label,BorderLayout.NORTH);

        JButton insertB = new JButton("Ingresar");
        JButton listarB = new JButton("Ver");
        JButton updateB = new JButton("Actualizar");
        JButton deleteB = new JButton("Eliminar");

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
            frame.add(new ClientesPanel());
            frame.setVisible(true);
        });
    }
}
