package org.beaconmc.pvptoggle.datamanager;

import me.dave.chatcolorhandler.ChatColorHandler;
import org.beaconmc.pvptoggle.PvpTogglePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final PvpTogglePlugin plugin = PvpTogglePlugin.getInstance();
    private FileConfiguration config;
    private final List<String> defaultWorldList = new ArrayList<>();

    public ConfigManager() {
        plugin.saveDefaultConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        reloadWorldList();
    }

    private void reloadWorldList() {
        defaultWorldList.clear();
        defaultWorldList.addAll(config.getStringList("worlds"));
    }

    // Config sections
    public boolean isPvpStateRemembered() {
        return config.getBoolean("remember-pvp-state");
    }

    public long getCommandCooldown() {
        return config.getLong("command-cooldown");
    }

    public int getPvpCooldown() {
        return config.getInt("pvp-cooldown");
    }

    public int getCommandWaitTime() {
        return config.getInt("command-wait");
    }

    public boolean getDefaultPvpMode() {
        return config.getBoolean("default-pvp");
    }

    public int getParticlesDisplayMode() {
        return config.getInt("particles");
    }

    public boolean isWorldEnabled(String worldName) {
        return !defaultWorldList.contains(worldName);
    }


    public String getPvpEnabledPlaceholder() {
        return config.getString("placeholder-api.pvp-enabled");
    }

    public String getPvpDisabledPlaceholder() {
        return config.getString("placeholder-api.pvp-disabled");
    }

    public String getLangMessage(String messageName, String parameter, boolean pvpState) {
        String message = config.getString("messages." + messageName.toUpperCase());
        if (message == null) return null;
        if (parameter != null) message = message.replaceAll("<parameter>", parameter);
        if (pvpState) message = message.replaceAll("<pvpstate>", "on");
        else message = message.replaceAll("<pvpstate>", "off");
        message = config.getString("messages.PREFIX") + message;
        message = ChatColorHandler.translateAlternateColorCodes(message);
        return message;
    }

    public void sendLangMessage(Player player, String messageName) {
        ChatColorHandler.sendMessage(player, getLangMessage(messageName, null, false));
    }

    public void sendLangMessage(Player player, String messageName, String parameter) {
        ChatColorHandler.sendMessage(player, getLangMessage(messageName, parameter, false));
    }

    public void sendLangMessage(Player player, String messageName, boolean pvpState) {
        ChatColorHandler.sendMessage(player, getLangMessage(messageName, null, pvpState));
    }

    public void sendLangMessage(Player player, String messageName, String parameter, boolean pvpState) {
        ChatColorHandler.sendMessage(player, getLangMessage(messageName, parameter, pvpState));
    }
}
