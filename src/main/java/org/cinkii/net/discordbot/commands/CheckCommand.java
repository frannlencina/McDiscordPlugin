package org.cinkii.net.discordbot.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.sql.*;

public class CheckCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            String user = "";
            String host = "";
            int port = 3306;
            String database = "";
            String password = "";

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=UTF-8", user, password);

                Statement statement = connection.createStatement();
                System.out.println("Conectado a la base de datos MYSQL para DiscordBot");
                ResultSet resultSet = statement.executeQuery("SELECT allPlayers FROM players_on_discord;");
                System.out.println("Query enviada");
                System.out.println(resultSet);

                while (resultSet.next()) {
                    int players = resultSet.getInt("allPlayers");
                    // Haz algo con el valor obtenido
                    player.sendMessage("El número de jugadores es: " + players);
                    System.out.println(players);
                }


                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
