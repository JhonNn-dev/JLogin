package br.com.listeners;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

public class OnPreprocessCommandListener implements Listener {


    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String command = e.getMessage().split(" ")[0].substring(1); // Obter o comando sem a barra "/"

        if (!JLogin.lc.isPlayerAllowed(p.getName()) && !command.equalsIgnoreCase("login") && !command.equalsIgnoreCase("register")) {
            e.setCancelled(true); // Cancelar o evento de comando se o jogador não estiver na lista LoggedPlayers, exceto para os comandos /login e /register
            p.sendMessage("§cVocê não pode executar comandos até fazer login.");
        }
    }

}
