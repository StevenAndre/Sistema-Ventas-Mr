import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
        public MenuPrincipal() {
            setTitle("Sistema de Ventas marañon");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            add(new PanelPrincipal());
            setBounds(200,200,900,500);


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

        JButton btnOpcion1 = new JButton("Opción 1");
        JButton btnOpcion2 = new JButton("Opción 2");
        JButton btnOpcion3 = new JButton("Opción 3");
        JButton btnClientes = new JButton("Clientes");
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
                viewPanel.add(new JLabel("Vista de la Opción 1"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnOpcion2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opción 2"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnOpcion3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opción 3"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();

            }
        });
    }



}


