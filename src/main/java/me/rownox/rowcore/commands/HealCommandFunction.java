package me.rownox.rowcore.commands;

import me.rownox.rowcore.utils.command.CommandCore;
import me.rownox.rowcore.utils.command.CommandFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.rownox.rowcore.utils.PlayerUtils.checkPerms;
import static me.rownox.rowcore.utils.PlayerUtils.healPlr;

public class HealCommandFunction extends CommandCore implements CommandFunction {

    public HealCommandFunction() {
        super("heal", "heal", "heal");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args[0] != null) { //check if they actually have another argument in the command
            checkPerms(sender, "rowcore.heal.others");
            Player target = Bukkit.getPlayer(args[0]); //set the target variable to the first argument in the command
            if (target != null) healPlr(target);
        } else {
            if (sender instanceof Player p) {
                healPlr(p);
            }
        }
    }
}