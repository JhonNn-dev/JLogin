package br.com.listeners;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashSet;
import java.util.Set;

public class OnChatListener implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String message = e.getMessage();

        if (!JLogin.lc.isPlayerAllowed(p.getName())) {
            e.setCancelled(true); // Cancelar o evento se o jogador não estiver na lista LoggedPlayers
            p.sendMessage("§cVocê não pode enviar mensagens até fazer login.");
        }
    }

}
