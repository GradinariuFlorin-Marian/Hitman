package ro.ang3l.hitman;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Evnts implements Listener {
    @EventHandler
    public void dth(PlayerDeathEvent e) {
        if (Main.afterTimer) {
            if (e.getEntity().getUniqueId().equals(Main.hitman)) {
                Main.status = false;
                Main.afterTimer = false;
                Main.m10 = false;
                Main.m5 = false;
                Main.m1 = false;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(Utils.color("&eThe Hitman was killed!"), "", 10, 10, 10);
                }
            } else if (e.getEntity().getUniqueId().equals(Main.target)) {
                Main.status = false;
                Main.afterTimer = false;
                Main.m10 = false;
                Main.m5 = false;
                Main.m1 = false;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(Utils.color("&eThe Hitman succeeded in the task!"), "", 10, 210, 10);
                }
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        if (Main.afterTimer) {
            if (e.getPlayer().getUniqueId().equals(Main.target)) {
                if (Bukkit.getOnlinePlayers().size() >= 2) {
                    Main.target = Bukkit.getOnlinePlayers().stream().filter(pf -> !Main.hitman.equals(pf.getUniqueId())).findAny().get().getUniqueId();
                    Bukkit.getPlayer(Main.hitman).sendMessage(Utils.color("&eThe target is " + Bukkit.getPlayer(Main.target).getName()));
                } else {
                    Main.status = false;
                    Main.afterTimer = false;
                    Main.m10 = false;
                    Main.m5 = false;
                    Main.m1 = false;
                    Bukkit.getPlayer(Main.hitman).sendMessage(Utils.color("&eAll players left! Contract is closed!"));
                }
            } else if (e.getPlayer().getUniqueId().equals(Main.hitman)) {
                e.getPlayer().damage(200);
            }
        }
    }

    @EventHandler
    public void quit(PlayerKickEvent e) {
        if (Main.afterTimer) {
            if (e.getPlayer().getUniqueId().equals(Main.target)) {
                if (Bukkit.getOnlinePlayers().size() >= 2) {
                    Main.target = Bukkit.getOnlinePlayers().stream().filter(pf -> !Main.hitman.equals(pf.getUniqueId())).findAny().get().getUniqueId();
                    Bukkit.getPlayer(Main.hitman).sendMessage(Utils.color("&eThe target is " + Bukkit.getPlayer(Main.target).getName()));
                } else {
                    Main.status = false;
                    Main.afterTimer = false;
                    Main.m10 = false;
                    Main.m5 = false;
                    Main.m1 = false;
                    Bukkit.getPlayer(Main.hitman).sendMessage(Utils.color("&eAll players left! Contract is closed!"));
                }
            } else if (e.getPlayer().getUniqueId().equals(Main.hitman)) {
                e.getPlayer().damage(200);
            }
        }
    }
}
