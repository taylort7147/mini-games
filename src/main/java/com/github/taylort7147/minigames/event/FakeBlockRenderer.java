package com.github.taylort7147.minigames.event;

import static org.lwjgl.opengl.GL11.GL_QUADS;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.github.taylort7147.minigames.MiniGames;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class FakeBlockRenderer
{

    @SubscribeEvent
    public static void onRenderWorldLast(final RenderWorldLastEvent event)
    {
        MatrixStack matrixStack = event.getMatrixStack();

        // Not always the player, is sometimes the mob the player is spectating. Mods
        // that hijack the camera also often do it this way.
        final Minecraft minecraft = Minecraft.getInstance();
        final Entity renderViewEntity = minecraft.getRenderViewEntity();
        assert renderViewEntity != null;

        // Interpolating everything back to (0, 0, 0). You can find these transforms in
        // the RenderEntity class
//        final double d0 = renderViewEntity.lastTickPosX
//                + ((renderViewEntity.getPosition().getX() - renderViewEntity.lastTickPosX) * partialTicks);
//        final double d1 = renderViewEntity.lastTickPosY
//                + ((renderViewEntity.getPosition().getY() - renderViewEntity.lastTickPosY) * partialTicks);
//        final double d2 = renderViewEntity.lastTickPosZ
//                + ((renderViewEntity.getPosition().getZ() - renderViewEntity.lastTickPosZ) * partialTicks);

//        final Tessellator tessellator = Tessellator.getInstance();
//        final BufferBuilder bufferBuilder = tessellator.getBuffer();

        // Transform to BlockPos (0, 0, 0)
//        bufferBuilder.setTranslation(-d0, -d1, -d2);

        // Begin drawing quads in the block format
//        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.BLOCK);

        final BlockRendererDispatcher renderDispatcher = minecraft.getBlockRendererDispatcher();

        // Read-only access to the world
        final World world = minecraft.world;

        IChunk chunk = world.getChunk(renderViewEntity.getPosition());
        BlockPos playerPos = renderViewEntity.getPosition();
        Vec3d playerPosVec = renderViewEntity.getPositionVector();
        ChunkPos origin = chunk.getPos();

        List<BlockPos> positions = new ArrayList<BlockPos>();
        int y = 5; // playerPos.getY();

// Add blocks around the perimeter of the chunk        
//        for (int x = origin.getXStart(); x <= origin.getXEnd(); ++x)
//        {
//            tryAdd(positions, world, new BlockPos(x, y, origin.getZStart()));
//            tryAdd(positions, world, new BlockPos(x, y, origin.getZEnd()));
//        }
//        for (int z = origin.getZStart() + 1; z <= origin.getZEnd() - 1; ++z)
//        {
//            tryAdd(positions, world, new BlockPos(origin.getXStart(), y, z));
//            tryAdd(positions, world, new BlockPos(origin.getXEnd(), y, z));
//        }

////        // Add all blocks in a chunk
//        for (int x = origin.getXStart(); x <= origin.getXEnd(); ++x)
//        {
//            for (int z = origin.getZStart(); z <= origin.getZEnd(); ++z)
//            {
//                tryAdd(positions, world, new BlockPos(x, y, z));
//            }
//        }

        // All blocks in static range
        int minX = 0;
        int maxX = 64;
        int minZ = 0;
        int maxZ = 64;
        for (int x = minX; x < maxX; ++x)
        {
            for (int z = minZ; z < maxZ; ++z)
            {
                tryAdd(positions, world, new BlockPos(x, y, z));
            }
        }

        for (final BlockPos pos : positions)
        {
            final BlockState state = Blocks.IRON_ORE.getDefaultState();
//            final BlockState state = world.getBlockState(pos);
            renderBlockAt(world, pos, state, renderDispatcher, matrixStack);
            recolorBlockFace(world, pos, state, Direction.UP, renderDispatcher, matrixStack);
        }

        // Draw with the tessellator, not the bufferBuilder
//        tessellator.draw();

        // Reset translation
//        bufferBuilder.setTranslation(0, 0, 0);
    }

    private static void tryAdd(List<BlockPos> positions, World world, BlockPos pos)
    {
//        if (world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        positions.add(pos);
    }

    private static void recolorBlockFace(World world, BlockPos pos, BlockState state, Direction face,
            BlockRendererDispatcher renderDispatcher, MatrixStack matrixStack)
    {

    }

    @SuppressWarnings("unused")
    private static void renderBlockAt(World world, BlockPos pos, BlockState state,
            BlockRendererDispatcher renderDispatcher, MatrixStack matrixStack)
    {
        Minecraft minecraft = Minecraft.getInstance();
        ActiveRenderInfo renderInfo = minecraft.gameRenderer.getActiveRenderInfo();
        Vec3d offset = renderInfo.getProjectedView();
        IModelData model = renderDispatcher.getModelForState(state).getModelData(world, pos, state,
                ModelDataManager.getModelData(world, pos));

        matrixStack.func_227860_a_(); // push
        matrixStack.func_227861_a_(-offset.getX() + pos.getX(), -offset.getY() + pos.getY(),
                -offset.getZ() + pos.getZ()); // translate back to camera

        // Original:
        // Overlay t﻿exture = custom RGBA on top of texture, 0 -> red
        // func_228487_b_ -> display over everything else
        // func_228489_c_ -> display as part of chunk rendering
//        renderDispatcher.renderBlock(state, matrixStack, minecraft.func_228019_au_().func_228489_c_(), 15728880,
//                OverlayTexture.field_229196_a_, model);
//
//        

        // func_229201_a_(int p_229201_0_, int p_229201_1_)
        // - p_229201_0_
        // - Appears to set brightness of the block (0-15)
        // 0xFFFF, 0x0000 -> red tint
        // 0x0000, 0xFFFF -> red tint
        // 0x0000, 0x000A -> normal

        int p_229201_0_ = pos.getX();
        int p_229201_1_ = pos.getZ()+512;

        MiniGames.LOGGER.debug("p_229201_0_ = " + p_229201_0_ + ", p_229201_1_ = " + p_229201_1_);

        renderDispatcher.renderBlock(state, matrixStack, minecraft.func_228019_au_().func_228489_c_(), 15728880,
                OverlayTexture.func_229201_a_(p_229201_0_, p_229201_1_), model);

//        // Using tessellator
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.BLOCK);
//
//        float x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4;
//        x1 = x2 = 1.0F;
//        x3 = x4 = 0.0F;
//        z1 = z4 = 0.0F;
//        z2 = z3 = 1.0F;
//        y1 = y2 = y3 = y4 = 1.0F;
//
//        int packednormal = calculatePackedNormal(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
//
//        BakedQuad bakedQuad = new BakedQuad(new int[] { 0, 0, }, 0, Direction.UP, null, false);
//        IRenderTypeBuffer renderTypeBuffer = minecraft.func_228019_au_().func_228489_c_();
//
//        TextureAtlasSprite texture = null;
//        Direction face = Direction.UP;
//        boolean applyDiffuseLighting = true;
//        int tint = 1;
//        bakedQuad = new BakedQuad(
//                Ints.concat(vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16, packednormal),
//                        vertexToInts(x2, y2, z2, Color.WHITE.getRGB(), texture, 16, 0, packednormal),
//                        vertexToInts(x3, y3, z3, Color.WHITE.getRGB(), texture, 0, 0, packednormal),
//                        vertexToInts(x4, y4, z4, Color.WHITE.getRGB(), texture, 0, 16, packednormal)),
//                tint, face, texture, applyDiffuseLighting);
//
//        float f = 0.0F;
//        float f1 = 0.0F;
//        float f2 = 0.0F;
//
//        int d = 0;
//        int d1 = 0;
//        bufferBuilder.getVertexBuilder().func_227889_a_(matrixStack.func_227866_c_(), bakedQuad, f, f1, f2, d, d1);
//        tessellator.draw();
////        GL11.glPushMatrix();
////        GL11.glEnable(GL11.GL_BLEND);
////        GL11.glDepthMask(false);
////        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
////        GL11.glEnable(GL11.GL_ALPHA_TEST);
////        GL11.glBegin(GL11.GL_QUAD_STRIP);
////        GL11.glVertex2d(0, 0);
////        GL11.glColor4f(0.0F, 1.0F,  0.0F, 0.4F);
////        GL11.glVertex2d(128.0, 0.0);
////        GL11.glColor4f(0.0F, 1.0F,  0.0F, 0.4F);
////        GL11.glVertex2d(128.0, 128.0);
////        GL11.glColor4f(0.0F, 1.0F,  0.0F, 0.4F);
////        GL11.glVertex2d(0.0, 128.0);
////        GL11.glColor4f(0.0F, 1.0F,  0.0F, 0.4F);
////        GL11.glEnd();
////        GL11.glPopMatrix();
//
        matrixStack.func_227865_b_(); // pop
    }

    /**
     * Converts the vertex information to the int array format expected by
     * BakedQuads. Useful if you don't know in advance what it should be.
     * 
     * @param x       x coordinate
     * @param y       y coordinate
     * @param z       z coordinate
     * @param color   RGBA colour format - white for no effect, non-white to tint
     *                the face with the specified colour
     * @param texture the texture to use for the face
     * @param u       u-coordinate of the texture (0 - 16) corresponding to [x,y,z]
     * @param v       v-coordinate of the texture (0 - 16) corresponding to [x,y,z]
     * @param normal  the packed representation of the normal vector, see
     *                calculatePackedNormal(). Used for lighting items.
     * @return
     */
    private static int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u,
            float v, int normal)
    {
        return new int[] { Float.floatToRawIntBits(x), Float.floatToRawIntBits(y), Float.floatToRawIntBits(z), color,
                Float.floatToRawIntBits(texture.getInterpolatedU(u)),
                Float.floatToRawIntBits(texture.getInterpolatedV(v)), normal };
    }

    /**
     * Calculate the normal vector based on four input coordinates assumes that the
     * quad is coplanar but should produce a 'reasonable' answer even if not.
     * 
     * @return the packed normal, ZZYYXX
     */
    private static int calculatePackedNormal(float x1, float y1, float z1, float x2, float y2, float z2, float x3,
            float y3, float z3, float x4, float y4, float z4)
    {

        float xp = x4 - x2;
        float yp = y4 - y2;
        float zp = z4 - z2;

        float xq = x3 - x1;
        float yq = y3 - y1;
        float zq = z3 - z1;

        // Cross Product
        float xn = yq * zp - zq * yp;
        float yn = zq * xp - xq * zp;
        float zn = xq * yp - yq * xp;

        // Normalize
        float norm = (float) Math.sqrt(xn * xn + yn * yn + zn * zn);
        final float SMALL_LENGTH = 1.0E-4F; // Vec3d.normalise() uses this
        if (norm < SMALL_LENGTH)
            norm = 1.0F; // protect against degenerate quad

        norm = 1.0F / norm;
        xn *= norm;
        yn *= norm;
        zn *= norm;

        int x = ((byte) (xn * 127)) & 0xFF;
        int y = ((byte) (yn * 127)) & 0xFF;
        int z = ((byte) (zn * 127)) & 0xFF;
        return x | (y << 0x08) | (z << 0x10);
    }
}
