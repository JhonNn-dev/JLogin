package br.com.utils;

import br.com.commands.ChangePasswordCommand;
import br.com.commands.LoginCommand;
import br.com.commands.RegisterCommand;
import br.com.listeners.*;
import br.com.mysql.DataBase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class JLogin extends JavaPlugin {

    public static JLogin instance;
    public static LoginCommand lc;
    public static OnQuitServerListener oq;
    PluginManager pm = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        instance = this;
        lc = new LoginCommand();
        oq = new OnQuitServerListener();
        onCommands();
        onListeners();
        new DataBase().connectToDataBase();
        getLogger().info("Inciado com sucesso!");
    }

    @Override
    public void onDisable() {
        new DataBase().closeConnection();
        getLogger().info("Finalizado com sucesso!");
    }

    public void onCommands(){
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("trocarsenha").setExecutor(new ChangePasswordCommand());

    }
    public void onListeners(){
        pm.registerEvents(new OnjoinServerListener(), this);
        pm.registerEvents(new OnQuitServerListener(), this);
        pm.registerEvents(new OnMoveListener(), this);
        pm.registerEvents(new OnChatListener(), this);
        pm.registerEvents(new OnPreprocessCommandListener(), this);
    }
}
