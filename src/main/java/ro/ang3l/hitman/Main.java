package ro.ang3l.hitman;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {
    static boolean status = false, afterTimer = false, m10 = false, m5 = false, m1 = false;
    static UUID hitman, target;
    static long currentTimer;
    static int time = 0;


    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(new Evnts(), this);
        getCommand("hitman").setExecutor(new Cmds());
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                if (status) {
                    if (afterTimer) {
                        long end = TimeUnit.MILLISECONDS.toMinutes(currentTimer);
                        long current = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
                        if ((end - current) <= 1) {
                            if (!m1) {
                                m1 = true;
                                Bukkit.getPlayer(hitman).sendTitle(Utils.color("&e1 minute"), Utils.color("&euntil contract is failed!"), 10, 10, 10);
                            }
                        } else if ((end - current) <= 5) {
                            if (!m5) {
                                m5 = true;
                                Bukkit.getPlayer(hitman).sendTitle(Utils.color("&e5 minutes"), Utils.color("&euntil contract is failed!"), 10, 10, 10);
                            }
                        } else if ((end - current) <= 10) {
                            if (!m10) {
                                m10 = true;
                                Bukkit.getPlayer(hitman).sendTitle(Utils.color("&e10 minutes"), Utils.color("&euntil contract is failed!"), 10, 10, 10);
                            }
                        }
                        if (end - current <= 0) {
                            Bukkit.getOnlinePlayers().stream().forEach(pla -> pla.sendTitle(Utils.color("&eHitman contract expired!"), "", 20, 20, 20));
                            Bukkit.getPlayer(Main.hitman).damage(200);
                            status = false;
                            afterTimer = false;
                            m10 = false;
                            m5 = false;
                            m1 = false;
                        }
                    } else {
                        long end = TimeUnit.MILLISECONDS.toMinutes(currentTimer);
                        long current = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
                        if ((end - current) <= time) {
                            afterTimer = true;
                            hitman = Bukkit.getOnlinePlayers().stream().filter(pf -> !pf.hasPermission("hitman.nothitman")).findAny().get().getUniqueId();
                            target = Bukkit.getOnlinePlayers().stream().filter(pf -> !Main.hitman.equals(pf.getUniqueId())).findAny().get().getUniqueId();
                            Bukkit.getOnlinePlayers().stream().forEach(pla -> pla.sendTitle(Utils.color("&eThe hitman was selected!"), "", 10, 10, 10));
                            Bukkit.getPlayer(Main.hitman).sendTitle(Utils.color("&eYou are the hitman!"), Utils.color("&cIf you leave, you will die!"), 20, 20, 20);
                            Bukkit.getPlayer(Main.hitman).sendMessage(Utils.color("&eThe target is " + Bukkit.getPlayer(Main.target).getName()));
                        }
                    }
                }
            }
        }, 40, 40);
    }

    public void loadConfig() {
        File f = new File(getDataFolder(), "config.yml");
        if (!f.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        time = getConfig().getInt("Time");
    }
}
