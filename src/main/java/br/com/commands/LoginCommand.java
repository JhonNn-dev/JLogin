package br.com.commands;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class LoginCommand implements CommandExecutor {


    public static Set<String> loggedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s instanceof Player)){
            s.sendMessage("§cComando exclusivo para players!");
            return false;
        }
        Player p = (Player) s;
        if(cmd.getName().equalsIgnoreCase("login")) {
            if (args.length != 1) {
                p.sendMessage("§cUso correto: /login <senha>");
                return true;
            }
            if(!new DataBase().isPlayerRegistered(p.getName())){
                p.sendMessage("§cVoce nao esta registrado, use: /register <senha> <confirmar-senha>");
                return true;
            }
            String password = args[0];
            // Verificar se o jogador já está logado
            if (!loggedPlayers.contains(p.getName())) {
                // Verificar se a senha está correta e realizar o login
                if (new DataBase().verifyPassword(p.getName(), password)) {
                    loggedPlayers.add(p.getName());
                    restorePlayerItems(p);
                    restorePlayerArmor(p);
                    p.sendMessage("§aLogin bem-sucedido!");
                } else {
                    p.sendMessage("§cSenha incorreta. Tente novamente.");
                }
            }else {
                p.sendMessage("§c§lVoce ja esta logado!");

            }
        }
        return false;
    }

    public void logout(String playerName) {
        loggedPlayers.remove(playerName);
    }

    public boolean isPlayerAllowed(String playerName) {
        // Retorna true se o jogador estiver na lista loggedPlayers, indicando que está logado
        return loggedPlayers.contains(playerName);
    }

    private void restorePlayerItems(Player player) {
        // Obter os itens do jogador do mapa
        ItemStack[] items = JLogin.oq.getPlayerItems(player.getName());

        // Restaurar os itens no inventário do jogador
        if (items != null) {
            player.getInventory().setContents(items);
        }
    }
    private void restorePlayerArmor(Player player) {
        ItemStack[] armor = JLogin.oq.getPlayerArmor(player.getName());
        if (armor != null) {
            player.getInventory().setArmorContents(armor); // Restaurar a armadura do jogador
        }
    }

}
