package com.faithfulmc.hardcorefactions.command;

import com.faithfulmc.hardcorefactions.ConfigurationService;
import com.faithfulmc.hardcorefactions.HCF;
import com.faithfulmc.hardcorefactions.faction.claim.Claim;
import com.faithfulmc.hardcorefactions.faction.type.ClaimableFaction;
import com.faithfulmc.hardcorefactions.faction.type.Faction;
import com.faithfulmc.util.cuboid.Cuboid;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddWorldRegion implements CommandExecutor {
    private final HCF hcf;

    public AddWorldRegion(HCF hcf) {
        this.hcf = hcf;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(hcf.getWorldEdit() == null){
            sender.sendMessage(ConfigurationService.RED + "You need WorldEdit for this");
        }
        else if(!(sender instanceof Player)){
            sender.sendMessage(ConfigurationService.RED + "You need to be a player to do this");
        }
        else{
            Player player = (Player) sender;
            if(hcf.getWorldEdit().getSelection(player) == null){
                player.sendMessage(ConfigurationService.RED + "Please make a worldedit selection first");
            }
            else if(args.length != 1){
                sender.sendMessage(ConfigurationService.YELLOW + "Usage: " + ConfigurationService.GRAY + "/" + label + " <faction>");
            }
            else{
                String factionname = args[0];
                Faction faction = hcf.getFactionManager().getFaction(factionname);
                if(faction == null || !(faction instanceof ClaimableFaction)){
                    sender.sendMessage(ConfigurationService.RED + "Invalid faction name");
                }
                else{
                    ClaimableFaction claimableFaction = (ClaimableFaction) faction;
                    Selection selection = hcf.getWorldEdit().getSelection(player);
                    Cuboid cuboid = new Cuboid(selection.getMinimumPoint(), selection.getMaximumPoint());
                    Claim claim = new Claim(faction, cuboid);
                    claimableFaction.addClaim(claim, null);
                    hcf.getFactionManager().updateFaction(faction);
                    sender.sendMessage(ConfigurationService.YELLOW +  "Added " + ConfigurationService.GRAY + 1 + ConfigurationService.YELLOW + ", there are now " + ConfigurationService.GRAY + claimableFaction.getClaims().size());
                }
            }
        }
        return true;
    }
}
