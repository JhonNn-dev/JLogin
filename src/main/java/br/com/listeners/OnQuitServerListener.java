package br.com.listeners;

import br.com.utils.JLogin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OnQuitServerListener implements Listener {

    public static final Map<String, ItemStack[]> playerItemsMap = new HashMap<>();
    public static final Map<String, ItemStack[]> playerArmorMap = new HashMap<>();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        JLogin.lc.loggedPlayers.remove(p.getName());
        savePlayerItems(p);
        savePlayerArmor(p);
        p.getInventory().setArmorContents(null);
        JLogin.instance.getLogger().info("O player " + p.getName() + " foi removido da lista de logados!");
    }


    private void savePlayerItems(Player player) {
        // Obter os itens do jogador
        ItemStack[] items = player.getInventory().getContents();

        // Salvar os itens na estrutura de dados
        playerItemsMap.put(player.getName(), items);
    }

    // Você pode fornecer um método para restaurar os itens do jogador, se necessário
    public ItemStack[] getPlayerItems(String playerName) {
        return playerItemsMap.get(playerName);
    }
    private void savePlayerArmor(Player player) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        playerArmorMap.put(player.getName(), armor);
    }
    public ItemStack[] getPlayerArmor(String playerName) {
        return playerArmorMap.get(playerName);
    }
}
