package net.smartboyssite.minecraft.hiddenswitches;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class HiddenSwitches extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new RightClickBlockListener(), this);
    }

    @Override
    public void onDisable()
    {
        HandlerList.unregisterAll(this);
    }
}
