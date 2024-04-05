package br.com.listeners;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OnjoinServerListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        if(!new DataBase().isPlayerRegistered(p.getName())){
            e.setJoinMessage(null);
            clearChat(p);
            p.sendMessage("§eRegistre-se utilizando: §7/register <senha> <senha>");
        }else {
            e.setJoinMessage(null);
            clearChat(p);
            p.sendMessage("§aFaça login no servidor: §7/login <senha>");
        }
        // Agendar a expulsão do jogador após 10 segundos
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!JLogin.lc.isPlayerAllowed(p.getName())) {
                    p.kickPlayer("§6Rede§eyDoom\n§f\n§cVocê não fez login a tempo.\n§cTente fazer login mais rapido\n§f\n§cwww.redeydoom.com");
                }
            }
        }.runTaskLater(JLogin.instance, 20 * 10); // 20 ticks = 1 segundo, 20 * 10 = 10 segundos
    }
    private void clearChat(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage(""); // Enviar 100 mensagens em branco para limpar o chat
        }
    }


}
