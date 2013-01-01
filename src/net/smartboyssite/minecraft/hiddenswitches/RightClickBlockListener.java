package net.smartboyssite.minecraft.hiddenswitches;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_4_6.CraftWorld;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Material;

public class RightClickBlockListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Block clicked_block = event.getClickedBlock();
            if(blockIsRedstoneInteractable(clicked_block) || event.getItem() != null)
            {
                return;
            }
            Location clicked_location = clicked_block.getLocation();
            World world = clicked_location.getWorld();
            for(int i = 0; i < 6; ++i)
            {
                Location orthological_location = clicked_location.clone();
                int div = i / 2;
                int mod = i % 2 == 0 ? 1 : -1;
                if(div == 0)
                {
                    orthological_location.setX(clicked_location.getX() + mod);
                }
                else if(div == 1)
                {
                    orthological_location.setY(clicked_location.getY() + mod);
                }
                else if(div == 2)
                {
                    orthological_location.setZ(clicked_location.getZ() + mod);
                }
                Block orthological_block = world.getBlockAt(orthological_location);
                if(blockIsRedstoneInteractable(orthological_block))
                {
                    interactWithBlock(orthological_block);
                }
            }
        }
    }
    
    public boolean blockIsRedstoneInteractable(Block block)
    {
        return block.getType() == Material.LEVER || block.getType() == Material.WOOD_BUTTON || 
               block.getType() == Material.STONE_BUTTON;
    }
    
    public void interactWithBlock(Block block)
    {
        net.minecraft.server.v1_4_6.Block pure_minecraft_block = 
                net.minecraft.server.v1_4_6.Block.byId[block.getType().getId()];
        net.minecraft.server.v1_4_6.World pure_minecraft_world = 
                ((CraftWorld) block.getWorld()).getHandle();
        net.minecraft.server.v1_4_6.EntityHuman pure_minecraft_human = null;
        pure_minecraft_block.interact(pure_minecraft_world,
                block.getX(), block.getY(), block.getZ(),
                pure_minecraft_human, 0, (float)0.0, (float)0.0, (float)0.0);
    }
}
