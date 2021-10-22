package ro.ang3l.hitman;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Cmds implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("hitman")) {
            if (args.length < 1) {
                commandSender.sendMessage(Utils.color("&e/hitman start"));
                commandSender.sendMessage(Utils.color("&e/hitman stop"));
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    if (commandSender.hasPermission("hitman.admin")) {
                        if (Main.status) {
                            commandSender.sendMessage(Utils.color("&eHitman is already started!"));
                        } else {
                            if (Bukkit.getOnlinePlayers().size() >= 2) {
                                int size = 0;
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (!p.hasPermission("hitman.nohitman")) {
                                        size++;
                                    }
                                }
                                if (size >= 1) {
                                    Main.currentTimer = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Main.time + 5);
                                    Bukkit.getOnlinePlayers().stream().forEach(pla -> pla.sendTitle(Utils.color("&eHitman will be selected"), Utils.color("&ein 5 minutes"), 20, 20, 20));
                                    Main.status = true;
                                } else {
                                    commandSender.sendMessage(Utils.color("&eNot enough players to start!"));
                                }
                            }

                        }
                    } else {
                        commandSender.sendMessage(Utils.color("&cYou don't have permission!"));
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (commandSender.hasPermission("hitman.admin")) {
                        if (Main.status) {
                            Main.status = false;
                            Main.afterTimer = false;
                            Main.m10 = false;
                            Main.m5 = false;
                            Main.m1 = false;
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(Utils.color("&eHitman contract expired!"), "", 20, 20, 20);
                            }
                        } else {
                            commandSender.sendMessage(Utils.color("&eHitman not started!"));
                        }
                    } else {
                        commandSender.sendMessage(Utils.color("&cYou don't have permission!"));
                    }
                }
            }
        }
        return false;
    }
}
