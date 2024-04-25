package org.cinkii.net.discordbot;

import org.bukkit.plugin.java.JavaPlugin;
import org.cinkii.net.discordbot.commands.CheckCommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DiscordBot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        // Variables de configuracion archivo config.yml
        String host = getConfig().getString("mysql.host");
        String user = getConfig().getString("mysql.user");
        String password = getConfig().getString("mysql.password");
        String database = getConfig().getString("mysql.database");
        int port = getConfig().getInt("mysql.port");

        // Listener para el comando.
        getCommand("checkds").setExecutor(new CheckCommand());

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=UTF-8", user, password);

            Statement statement = connection.createStatement();
            System.out.println("Conectado a la base de datos MYSQL para DiscordBot");

            statement.close();

            System.out.println("Tabla [discord_bot] creada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos MYSQL para DiscordBot");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Guardar archivo de configuracion cuando son editados.
        saveConfig();
    }
}
