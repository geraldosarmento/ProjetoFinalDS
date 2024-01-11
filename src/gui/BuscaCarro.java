package gui;

import dao.CarroDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscaCarro extends JInternalFrame {
    private JTextField campoBusca;
    private JButton botaoBuscar, botaoNovo, botaoEditar, botaoExcluir;
    private JTable tabela;
    private DefaultTableModel model;
    private CarroDAO carroDAO;
    JDesktopPane desktopPane;

    public BuscaCarro(JDesktopPane desktopPane) {
        setTitle("Buscar Carro");
        setSize(400, 300);
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        this.desktopPane = desktopPane; // para carregar o formulário de cadastro a partir da busca

        JPanel panel = new JPanel(new BorderLayout());

        JPanel buscaPanel = new JPanel();
        campoBusca = new JTextField(20);
        botaoBuscar = new JButton("Buscar Modelo");

        buscaPanel.add(campoBusca);
        buscaPanel.add(botaoBuscar);

        tabela = new JTable();
        model = new DefaultTableModel(new Object[]{"Código", "Modelo", "Marca", "Ano"}, 0);
        tabela.setModel(model);

        JScrollPane scrollPane = new JScrollPane(tabela);

        panel.add(buscaPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

        // Botões Novo, Editar e Excluir
        JPanel botoesPanel = new JPanel();
        botaoNovo = new JButton("Novo");
        botaoEditar = new JButton("Editar");
        botaoExcluir = new JButton("Excluir");

        botoesPanel.add(botaoNovo);
        botoesPanel.add(botaoEditar);
        botoesPanel.add(botaoExcluir);

        panel.add(botoesPanel, BorderLayout.SOUTH);

        add(panel);

        carroDAO = new CarroDAO();
        try {
            preencherTabelaInicial();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeBuscado = campoBusca.getText();
                ResultSet resultSet = carroDAO.buscarModelo(nomeBuscado);
                try {
                    preencherTabela(resultSet);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Funcionalidade ainda não implementada");
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifique se uma linha foi selecionada na tabela
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    // Obtém o código da linha selecionada (coluna 0)
                    int codigoExcluir = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

                    // Realiza a exclusão do registro com o código obtido do banco de dados
                    carroDAO.excluirCarro(codigoExcluir);

                    // Atualiza a tabela após a exclusão
                    try {
                        preencherTabelaInicial();
                        JOptionPane.showMessageDialog(null, "Registro excluído com sucesso.");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum carro selecionado.");
                }
            }
        });

        botaoNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar e exibir o frame interno CadastroCarro
                CadastroCarro cadastroCarro = new CadastroCarro(BuscaCarro.this);
                desktopPane.add(cadastroCarro);
                cadastroCarro.setVisible(true);
            }
        });

    }



    // Método para preencher a tabela com os resultados da busca no banco de dados
    private void preencherTabela(ResultSet resultSet) throws SQLException {
        // Limpar tabela antes de preencher com novos resultados
        model.setRowCount(0);

         while (resultSet.next()) {
             String codigo = resultSet.getString("codigo");
             String modelo = resultSet.getString("modelo");
             String marca = resultSet.getString("marca");
             String ano = resultSet.getString("ano");
             model.addRow(new Object[]{codigo, modelo, marca, ano});
         }
    }

    public void preencherTabelaInicial() throws SQLException {
        // Limpar tabela antes de preencher com novos resultados
        model.setRowCount(0);

        try (ResultSet resultSet = carroDAO.buscarTudo()) {

            while (resultSet.next()) {
                String codigo = resultSet.getString("codigo");
                String modelo = resultSet.getString("modelo");
                String marca = resultSet.getString("marca");
                String ano = resultSet.getString("ano");
                model.addRow(new Object[]{codigo, modelo, marca, ano});
            }
        }
    }


}
