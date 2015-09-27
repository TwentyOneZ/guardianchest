package me.twentyonez.guardianchest.render.tile_entity;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import me.twentyonez.guardianchest.block.GCChest;
import me.twentyonez.guardianchest.tile_entity.TileEntityGCChest;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

@SideOnly(Side.CLIENT)
public class GCChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147505_d = new ResourceLocation("guardianchest:textures/blocks/GCChestSecure.png");
    private ModelChest field_147510_h = new ModelChest();
    private boolean field_147509_j;
    private static final String __OBFID = "CL_00000965";

    public GCChestRenderer()
    {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
            this.field_147509_j = true;
        }
    }

    public void renderTileEntityAt(TileEntityGCChest p_147502_1_, double p_147502_2_, double p_147502_4_, double p_147502_6_, float p_147502_8_)
    {
        int i;

        if (!p_147502_1_.hasWorldObj())
        {
            i = 0;
        }
        else
        {
            Block block = p_147502_1_.getBlockType();
            i = p_147502_1_.getBlockMetadata();

            if (block instanceof GCChest && i == 0)
            {
                try
                {
                ((GCChest)block).func_149954_e(p_147502_1_.getWorldObj(), p_147502_1_.xCoord, p_147502_1_.yCoord, p_147502_1_.zCoord);
                }
                catch (ClassCastException e)
                {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", p_147502_1_.xCoord, p_147502_1_.yCoord, p_147502_1_.zCoord);
                }
                i = p_147502_1_.getBlockMetadata();
            }

            p_147502_1_.checkForAdjacentChests();
        }

        if (p_147502_1_.adjacentChestZNeg == null && p_147502_1_.adjacentChestXNeg == null)
        {
            ModelChest modelchest;

            modelchest = this.field_147510_h;
            this.bindTexture(field_147505_d);

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)p_147502_2_, (float)p_147502_4_ + 1.0F, (float)p_147502_6_ + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            if (i == 2)
            {
                short1 = 180;
            }

            if (i == 3)
            {
                short1 = 0;
            }

            if (i == 4)
            {
                short1 = 90;
            }

            if (i == 5)
            {
                short1 = -90;
            }

            if (i == 2 && p_147502_1_.adjacentChestXPos != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (i == 5 && p_147502_1_.adjacentChestZPos != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f1 = p_147502_1_.prevLidAngle + (p_147502_1_.lidAngle - p_147502_1_.prevLidAngle) * p_147502_8_;
            float f2;

            if (p_147502_1_.adjacentChestZNeg != null)
            {
                f2 = p_147502_1_.adjacentChestZNeg.prevLidAngle + (p_147502_1_.adjacentChestZNeg.lidAngle - p_147502_1_.adjacentChestZNeg.prevLidAngle) * p_147502_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            if (p_147502_1_.adjacentChestXNeg != null)
            {
                f2 = p_147502_1_.adjacentChestXNeg.prevLidAngle + (p_147502_1_.adjacentChestXNeg.lidAngle - p_147502_1_.adjacentChestXNeg.prevLidAngle) * p_147502_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
            modelchest.renderAll();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        this.renderTileEntityAt((TileEntityGCChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

