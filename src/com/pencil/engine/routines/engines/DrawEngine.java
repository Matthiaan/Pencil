package com.pencil.engine.routines.engines;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.routines.engines.utils.Voxel;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * @author Matthias Kovacic
 *
 * The draw engine will draw voxels in the world
 *
 * This will be a very complex algorithm that takes in current
 * TPS, player ping and distance to other generating structures
 * for the most optimal experience!
 *
 * //TODO: Switch drawing from RenderEngine to here!
 */
public class DrawEngine {

    public static void draw(ArrayList<Voxel> voxels) {
        int a = 0;
        int maxVoxels = voxels.size() - 1;

        while (a <= maxVoxels) {
            Voxel voxel = voxels.get(a);

            new BukkitRunnable() {
                @Override
                public void run() {
                    World world = voxel.getWorld();
                    Vector vector = voxel.getPosition();

                    world.getBlockAt(vector.toLocation(world)).setType(voxel.getMaterial());
                }
            }.runTaskLater(Pencil.getPlugin(), 1);

            a++;
        }
    }

}