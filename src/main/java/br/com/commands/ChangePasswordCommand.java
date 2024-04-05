package br.com.commands;

import br.com.mysql.DataBase;
import br.com.utils.JLogin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePasswordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s instanceof Player)){
            s.sendMessage("§cComando exclusivo para players!");
            return true;
        }
        Player p = (Player) s;
        if (args.length != 2) {
            p.sendMessage("§cUso correto: /trocarsenha <senha-atual> <nova-senha>");
            return true;
        }
        String senhaAtual = args[0];
        String novaSenha = args[1];

        // Verificar a senha atual do jogador
        if (!new DataBase().verifyPassword(p.getName(), senhaAtual)) {
            p.sendMessage("Senha atual incorreta. Tente novamente.");
            return true;
        }

        // Validar a nova senha (adicione aqui suas regras de validação)
        if (novaSenha.length() < 6) {
            p.sendMessage("A nova senha deve ter no mínimo 6 caracteres.");
            return true;
        }
        // Atualizar a senha no banco de dados
        if (new DataBase().updatePassword(p.getName(), novaSenha)) {
            p.sendMessage("§aSenha alterada com sucesso!");
        } else {
            p.sendMessage("§cOcorreu um erro ao alterar a senha. Por favor, tente novamente mais tarde.");
        }


        return false;
    }
}
