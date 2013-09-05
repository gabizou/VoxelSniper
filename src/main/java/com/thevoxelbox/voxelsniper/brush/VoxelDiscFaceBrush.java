package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Voxel_Disc_Face_Brush
 *
 * @author Voxel
 */
public class VoxelDiscFaceBrush extends PerformBrush
{
    private static int timesUsed = 0;

    /**
     *
     */
    public VoxelDiscFaceBrush()
    {
        this.setName("Voxel Disc Face");
    }

    private void disc(final SnipeData v, Block targetBlock)
    {
        final int _bSize = v.getBrushSize();

        for (int _x = _bSize; _x >= -_bSize; _x--)
        {
            for (int _y = _bSize; _y >= -_bSize; _y--)
            {
                this.current.perform(this.clampY(targetBlock.getX() + _x, targetBlock.getY(), targetBlock.getZ() + _y));
            }
        }

        v.storeUndo(this.current.getUndo());
    }

    private void discNS(final SnipeData v, Block targetBlock)
    {
        final int _bSize = v.getBrushSize();

        for (int _x = _bSize; _x >= -_bSize; _x--)
        {
            for (int _y = _bSize; _y >= -_bSize; _y--)
            {
                this.current.perform(this.clampY(targetBlock.getX() + _x, targetBlock.getY() + _y, targetBlock.getZ()));
            }
        }

        v.storeUndo(this.current.getUndo());
    }

    private void discEW(final SnipeData v, Block targetBlock)
    {
        final int _bSize = v.getBrushSize();

        for (int _x = _bSize; _x >= -_bSize; _x--)
        {
            for (int _y = _bSize; _y >= -_bSize; _y--)
            {
                this.current.perform(this.clampY(targetBlock.getX(), targetBlock.getY() + _x, targetBlock.getZ() + _y));
            }
        }

        v.storeUndo(this.current.getUndo());
    }

    private void pre(final SnipeData v, final BlockFace bf, Block targetBlock)
    {
        if (bf == null)
        {
            return;
        }
        switch (bf)
        {
            case NORTH:
            case SOUTH:
                this.discNS(v, targetBlock);
                break;

            case EAST:
            case WEST:
                this.discEW(v, targetBlock);
                break;

            case UP:
            case DOWN:
                this.disc(v, targetBlock);
                break;

            default:
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v)
    {
        this.pre(v, this.getTargetBlock().getFace(this.getLastBlock()), this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v)
    {
        this.pre(v, this.getTargetBlock().getFace(this.getLastBlock()), this.getLastBlock());
    }

    @Override
    public final void info(final Message vm)
    {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final int getTimesUsed()
    {
        return VoxelDiscFaceBrush.timesUsed;
    }

    @Override
    public final void setTimesUsed(final int tUsed)
    {
        VoxelDiscFaceBrush.timesUsed = tUsed;
    }
}
