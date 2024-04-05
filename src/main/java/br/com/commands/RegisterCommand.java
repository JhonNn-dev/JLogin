package br.com.commands;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s instanceof Player)){
            s.sendMessage("§cComando exclusivo para players!");
            return false;
        }
        Player p = (Player) s;
        if(cmd.getName().equalsIgnoreCase("register")){
            if (args.length != 2) {
                p.sendMessage("§cUso correto: /register <senha> <confirmar-senha>");
                return true;
            }
            String password = args[0];
            String confirmPassword = args[1];
            if(! new DataBase().isPlayerRegistered(p.getName())){
                if(password.length() < 6){
                    p.sendMessage("§cA senha deve ter no minimo 6 caracteres.");
                    return true;
                }
                // Verificar se as senhas fornecidas são iguais
                if (!password.equals(confirmPassword)) {
                    p.sendMessage("§cAs senhas fornecidas não correspondem. Tente novamente.");
                    return true;
                }
                // Registrar o jogador
                if (new DataBase().registerPlayer(p.getName(), password)) {
                    p.sendMessage("§aRegistrado com sucesso!");
                    p.kickPlayer("§6Rede§eyDoom\n§f\n§eVoce foi registrado com sucesso\n§eLogue novamente no servidor com a §asenha criada\n§eTenha um bom jogo :)\n§f\n§cwww.redeydoom.com");
                } else {
                    p.sendMessage("§cErro ao registrar. Por favor, tente novamente mais tarde.");
                }
            }else{
                p.sendMessage("§cConta ja registrada, utilize: /login <senha>");
            }
        }
        return false;
    }
}
