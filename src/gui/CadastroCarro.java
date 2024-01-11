package gui;

import dao.CarroDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroCarro extends JInternalFrame {
    private JPanel panel;
    private JLabel labelMarca, labelModelo, labelCor, labelCondicao, labelAno;
    private JTextField fieldModelo;
    private JButton buttonSalvar;
    private JComboBox<String> comboBoxMarca, comboBoxCor;
    private JRadioButton radioButtonNovo, radioButtonUsado;
    private JSpinner spinnerAno;
    private CarroDAO carroDAO;
    private BuscaCarro framePai;

    public CadastroCarro(BuscaCarro framePai) {
        super("Cadastro de Carros", true, true, true, true);
        setSize(400, 250);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        labelMarca = new JLabel("Marca:");
        comboBoxMarca = new JComboBox<>(new String[]{"Toyota", "Ford", "Honda", "Chevrolet", "Fiat"});
        panel.add(labelMarca);
        panel.add(comboBoxMarca);

        labelModelo = new JLabel("Modelo:");
        fieldModelo = new JTextField();
        panel.add(labelModelo);
        panel.add(fieldModelo);

        labelCor = new JLabel("Cor:");
        comboBoxCor = new JComboBox<>(new String[]{"Branco", "Prata", "Preto", "Vermelho", "Azul", "Amarelo", "Verde", "Outra"});
        panel.add(labelCor);
        panel.add(comboBoxCor);

        labelCondicao = new JLabel("Condição:");
        radioButtonNovo = new JRadioButton("Novo");
        radioButtonUsado = new JRadioButton("Usado");
        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonNovo);
        group.add(radioButtonUsado);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(radioButtonNovo);
        radioPanel.add(radioButtonUsado);

        panel.add(labelCondicao);
        panel.add(radioPanel);

        labelAno = new JLabel("Ano:");
        spinnerAno = new JSpinner(new SpinnerNumberModel(2024, 2010, 2024, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerAno, "#");
        spinnerAno.setEditor(editor);
        panel.add(labelAno);
        panel.add(spinnerAno);

        buttonSalvar = new JButton("Salvar");
        panel.add(new JLabel(""));
        panel.add(buttonSalvar);

        add(panel, BorderLayout.CENTER);

        this.framePai = framePai;
        carroDAO = new CarroDAO();

        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marca = comboBoxMarca.getSelectedItem().toString();
                String modelo = fieldModelo.getText();
                String cor = comboBoxCor.getSelectedItem().toString();
                boolean condicao = radioButtonNovo.isSelected();
                int ano = (int) spinnerAno.getValue();

                carroDAO.inserirCarro(modelo, marca, cor, ano, condicao);
                JOptionPane.showMessageDialog(null, "Registro incluído com sucesso.");
                try {
                    framePai.preencherTabelaInicial();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    }
}
