import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
        public MenuPrincipal() {
            setTitle("Sistema de Ventas mara駉n");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            add(new PanelPrincipal());


        }
}



class PanelPrincipal extends JPanel{
    public PanelPrincipal() {
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Menu princiapl Sistema de Ventas");


        headerPanel.add(titleLabel);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
        menuPanel.setBackground(new Color(220, 193, 206, 107));

<<<<<<< HEAD
        JButton btnOpcion1 = new JButton("Opci髇 1");
        JButton btnOpcion2 = new JButton("Opci髇 2");
        JButton btnOpcion3 = new JButton("Opci髇 3");

=======
        JButton btnOpcion1 = new JButton("Opci贸n 1");
        JButton btnOpcion2 = new JButton("Opci贸n 2");
        JButton btnOpcion3 = new JButton("Opci贸n 3");
        JButton btnClientes = new JButton("Clientes");
>>>>>>> 98b8be42910e939834f3516198194f1b6fa8cd7c
        menuPanel.add(btnOpcion1);
        menuPanel.add(btnOpcion2);
        menuPanel.add(btnOpcion3);
        menuPanel.add(btnClientes);


        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(new JLabel("Vista por defecto"), BorderLayout.CENTER);


        add(headerPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.WEST);
        add(viewPanel, BorderLayout.CENTER);

        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new ClientesPanel(), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });


        btnOpcion1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opci髇 1"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnOpcion2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opci髇 2"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnOpcion3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opci贸n 3"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();

            }
        });
    }



}


