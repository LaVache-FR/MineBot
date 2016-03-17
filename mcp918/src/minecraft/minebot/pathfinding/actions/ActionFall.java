/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minebot.pathfinding.actions;

import minebot.MineBot;
import minebot.pathfinding.PathFinder;
import minebot.util.Out;
import minebot.util.ToolSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

/**
 *
 * @author leijurv
 */
public class ActionFall extends ActionPlaceOrBreak {
    public ActionFall(BlockPos start) {
        super(start, start.down(), new BlockPos[]{start.down()}, new BlockPos[0]);
    }
    int numTicks = 0;
    @Override
    protected boolean tick0() {
        numTicks++;
        if (numTicks > 10) {
            MineBot.moveTowardsBlock(to);
        }
        EntityPlayerSP thePlayer = Minecraft.theMinecraft.thePlayer;
        BlockPos whereAmI = new BlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ);
        if (whereAmI.equals(to)) {
            Out.log("Done falling to " + to);
            MineBot.clearMovement();
            return true;
        }
        return false;
    }
    @Override
    protected double calculateCost(ToolSet ts) {
        if (!MineBot.allowVerticalMotion || !canWalkOn(to.down())) {
            return PathFinder.COST_INF;
        }
        return FALL_ONE_BLOCK_COST + getTotalHardnessOfBlocksToBreak(ts);
    }
}
