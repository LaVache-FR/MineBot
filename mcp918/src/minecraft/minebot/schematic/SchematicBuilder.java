/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minebot.schematic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import minebot.MineBot;
import minebot.pathfinding.goals.GoalComposite;
import minebot.util.Out;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Tuple;

/**
 *
 * @author leijurv
 */
public class SchematicBuilder {
    ArrayList<Tuple<BlockPos, Block>> plan = new ArrayList();
    BlockPos offset;
    Schematic schematic;
    public SchematicBuilder(Schematic schematic, BlockPos offset) {
        this.schematic = schematic;
        this.offset = offset;
        for (Entry<BlockPos, Block> entry : schematic.getEntries()) {
            plan.add(new Tuple(offset(entry.getKey()), entry.getValue()));
        }
    }
    public void tick() {
        HashSet<BlockPos> goal = getAllBlocksToPlaceShiftedUp();
        Out.log("Ticking " + goal);
        if (goal != null) {
            MineBot.goal = new GoalComposite(goal);
            if (MineBot.currentPath == null && !MineBot.isThereAnythingInProgress) {
                MineBot.findPathInNewThread(false);
            }
        } else {
            Out.gui("done building", Out.Mode.Standard);
        }
    }
    public HashSet<BlockPos> getAllBlocksToPlaceShiftedUp() {
        HashSet<BlockPos> toPlace = new HashSet<BlockPos>();
        Block air = Block.getBlockById(0);
        for (int y = 0; y < schematic.getHeight(); y++) {
            for (int x = 0; x < schematic.getWidth(); x++) {
                for (int z = 0; z < schematic.getLength(); z++) {
                    BlockPos inSchematic = new BlockPos(x, y, z);
                    BlockPos inWorld = offset(inSchematic);
                    Block current = Minecraft.theMinecraft.theWorld.getBlockState(inWorld).getBlock();
                    Block desired = schematic.getBlockFromBlockPos(inSchematic);
                    Out.log(inSchematic + " " + current + " " + desired);
                    boolean currentlyAir = air.equals(current);
                    boolean shouldBeAir = desired == null || air.equals(desired);
                    if (currentlyAir && !shouldBeAir) {
                        toPlace.add(inWorld.up());
                    }
                }
            }
        }
        return toPlace.isEmpty() ? null : toPlace;
    }
    private BlockPos offset(BlockPos original) {
        return new BlockPos(original.getX() + offset.getX(), original.getY() + offset.getY(), original.getZ() + offset.getZ());
    }
}
