package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JDesktopPane desktopPane;
    private final String nomeSistema = "SCV - Sistema de Controle de Veículos";

    public TelaPrincipal() {
        setTitle(nomeSistema);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon backgroundImage = new ImageIcon("src/gui/bg.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH);

        //desktopPane = new JDesktopPane();
        desktopPane = new DesktopPaneWithBackground(scaledImage);
        setContentPane(desktopPane);

        JFrame.setDefaultLookAndFeelDecorated(true);

        JMenuBar menuBar = new JMenuBar();

        JMenu cadastrosMenu = new JMenu("Cadastros");
        JMenu vendasMenu = new JMenu("Vendas");
        JMenu relatoriosMenu = new JMenu("Relatórios");
        JMenu sistemaMenu = new JMenu("Sistema");

        JMenuItem carroItem = new JMenuItem("Carros");
        JMenuItem motoItem = new JMenuItem("Motos");
        JMenuItem vendedorItem = new JMenuItem("Vendedores");
        cadastrosMenu.add(carroItem);
        cadastrosMenu.add(motoItem);
        cadastrosMenu.add(vendedorItem);

        JMenuItem sobreItem = new JMenuItem("Sobre");
        JMenuItem sairItem = new JMenuItem("Sair");
        sistemaMenu.add(sobreItem);
        sistemaMenu.add(sairItem);

        menuBar.add(cadastrosMenu);
        menuBar.add(vendasMenu);
        menuBar.add(relatoriosMenu);
        menuBar.add(sistemaMenu);

        setJMenuBar(menuBar);


        carroItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscaCarro buscaCarro = new BuscaCarro(desktopPane);
                buscaCarro.setVisible(true);
                desktopPane.add(buscaCarro);
            }
        });

        motoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame cadastroMoto= new JInternalFrame("Cadastro de Motos", true, true, true, true);
                cadastroMoto.setSize(300, 200);
                cadastroMoto.setVisible(true);
                desktopPane.add(cadastroMoto);
            }
        });

        vendedorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame vendedorFrame = new JInternalFrame("Cadastro de Vendedores", true, true, true, true);
                vendedorFrame.setSize(300, 200);
                vendedorFrame.setVisible(true);
                desktopPane.add(vendedorFrame);
            }
        });

        sobreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,  nomeSistema+". v.1.0 \nDesenvolvido por Geraldo Sarmento");
            }
        });

        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }
}

class DesktopPaneWithBackground extends JDesktopPane {
    private final Image backgroundImage;

    public DesktopPaneWithBackground(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}