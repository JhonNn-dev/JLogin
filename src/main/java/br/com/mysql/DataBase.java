package br.com.mysql;

import br.com.utils.JLogin;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DataBase {


    private Connection connection;
    private String host, database, username, password;
    private int port;

    public void connectToDataBase() {

        host = "localhost";
        port = 3306;
        database = "jlogintest";
        username = "root";
        password = "jhonathan.senha";

        //Conectar ao banco de dados
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));
                JLogin.instance.getLogger().info("Conexão com MySQL estabelecida com sucesso!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        //Fechar conexão com banco de dados
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                JLogin.instance.getLogger().info("Conexão com MySQL fechada com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerPlayer(String username, String password) {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDataBase(); // Reestabelece a conexão se não estiver conectada ou se estiver fechada
            }
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO usuarios (nick, senha) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Retorna true se o jogador foi registrado com sucesso

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void loginPlayer(String username, String password) {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDataBase(); // Reestabelece a conexão se não estiver conectada ou se estiver fechada
            }
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM usuarios WHERE nick=? AND senha=?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerRegistered(String playerName) {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDataBase(); // Reestabelece a conexão se não estiver conectada ou se estiver fechada
            }
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE nick=?");
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Retorna true se houver um jogador registrado com o nome playerName
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Em caso de erro, assume que o jogador não está registrado
        }
    }

    public boolean verifyPassword(String playerName, String password) {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDataBase(); // Reestabelece a conexão se não estiver conectada ou se estiver fechada
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE nick = ? AND senha = ?");
            statement.setString(1, playerName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Retorna true se o jogador está registrado e a senha está correta
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Em caso de erro, assume que o jogador não está registrado ou a senha está incorreta
        }
    }

    public boolean updatePassword(String playerName, String novaSenha) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            if (conn == null) {
                return false;
            }

            // Atualizar a senha do jogador no banco de dados
            String query = "UPDATE usuarios SET senha = ? WHERE nick = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, novaSenha);
            stmt.setString(2, playerName);
            int rowsAffected = stmt.executeUpdate();

            // Verificar se a atualização foi bem-sucedida
            if (rowsAffected > 0) {
                return true; // Sucesso
            } else {
                return false; // Falha ao atualizar a senha
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Fechar a conexão e o statement
            closeResources(conn, stmt);
        }
    }

    // Método para fechar a conexão, o statement e o resultSet
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Método sobrecarregado para fechar a conexão e o statement (sem ResultSet)
    private void closeResources(Connection conn, PreparedStatement stmt) {
        closeResources(conn, stmt, null);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
