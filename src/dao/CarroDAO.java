package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarroDAO {
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/projetofinal?useSSL=false";
    private final String user = "root";
    private final String password = "root";

    public CarroDAO() {
        try {
            // Use o driver do MySQL (MariaDB usa o mesmo driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet buscarCodigo(int codigo) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM carro WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, codigo);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet buscarModelo(String modelo) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM carro WHERE modelo LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + modelo + "%");
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet buscarTudo() {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM carro";
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void inserirCarro(String modelo, String marca, String cor, int ano, boolean condicao) {
        String query = "INSERT INTO carro (modelo, marca, cor, ano, condicao) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, modelo);
            statement.setString(2, marca);
            statement.setString(3, cor);
            statement.setInt(4, ano);
            statement.setBoolean(5, condicao);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void excluirCarro(int codigo) {
        try {
            String query = "DELETE FROM carro WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) throws SQLException {
        CarroDAO carroDAO = new CarroDAO();
        ResultSet resultSet = carroDAO.buscarCodigo(1);
        // Utilize o ResultSet para obter os dados e preencher a tabela
         while (resultSet.next()) {
             String marca = resultSet.getString("marca");
             System.out.println(marca);
         }
        carroDAO.fecharConexao();
        }
     */

}
