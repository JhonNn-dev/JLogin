package br.com.listeners;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

public class OnMoveListener implements Listener {

    public Set<String> loggedPlayers = new HashSet<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!JLogin.lc.isPlayerAllowed(p.getName())) {
            // Cancelar o evento de movimento
            e.setCancelled(true);
            // Obter a localização atual do jogador
            Location location = p.getLocation();

            // Definir o yaw para a direção "para a frente"
            location.setYaw(getNewYaw(p));
            // Definir o pitch para olhar diretamente para frente (0 graus)
            location.setPitch(0);

            // Teleportar o jogador para a nova localização
            p.teleport(location);
        }
    }

    private float getNewYaw(Player player) {
        float yaw = player.getLocation().getYaw();

        // Ajustar o yaw para um valor próximo a 0 (norte) ou 180 (sul)
        if (yaw < 0) {
            yaw += 360; // Transforma valores negativos em positivos
        }

        // Definir o yaw para o valor mais próximo de 0 ou 180
        if (yaw >= 0 && yaw < 90) {
            return 45; // Ajusta para 45 graus (nordeste)
        } else if (yaw >= 90 && yaw < 180) {
            return 135; // Ajusta para 135 graus (noroeste)
        } else if (yaw >= 180 && yaw < 270) {
            return 225; // Ajusta para 225 graus (sudoeste)
        } else {
            return 315; // Ajusta para 315 graus (sudeste)
        }
    }
}
