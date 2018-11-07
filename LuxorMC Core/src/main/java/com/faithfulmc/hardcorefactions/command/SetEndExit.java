package com.faithfulmc.hardcorefactions.command;

import com.faithfulmc.hardcorefactions.ConfigurationService;
import com.faithfulmc.hardcorefactions.HCF;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetEndExit implements CommandExecutor {
    private final HCF hcf;

    public SetEndExit(HCF hcf) {
        this.hcf = hcf;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation().clone();
            location.setPitch(Math.round(location.getPitch() / 45.0F) * 45.0F);
            location.setYaw(Math.round(location.getYaw() / 45.0F) * 45.0F);
            location.setX(location.getBlockX() + 0.5);
            location.setY(location.getBlockY());
            location.setZ(location.getBlockZ() + 0.5);
            int x = location.getBlockX();
            int z = location.getBlockZ();
            player.teleport(location);
            hcf.setEndExit(location);
            sender.sendMessage(ConfigurationService.YELLOW + "End exit set to " + ConfigurationService.GRAY + x + ", " + z);
        } else {
            sender.sendMessage(ConfigurationService.RED + "You must be a player to do this");
        }
        return true;
    }
}
