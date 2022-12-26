package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.blocks.MirrorBlock;
import com.ModernCrayfish.objects.entity.MirrorEntity;
import com.ModernCrayfish.objects.tileEntity.CeilingFanTileEntity;
import com.ModernCrayfish.objects.tileEntity.MirrorTileEntity;
import com.ModernCrayfish.util.MirrorRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.javafx.geom.Vec3d;
import javafx.scene.Camera;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.system.MemoryUtil.NULL;

//TODO FIX this mess
public class MirrorTileEntityRenderer extends TileEntityRenderer<MirrorTileEntity> {

    public static final Minecraft mc = Minecraft.getInstance();
    public static WorldRenderer reflection = new MirrorRenderer(Minecraft.getInstance(),Minecraft.getInstance().getRenderTypeBuffers());
    private static Map<MirrorEntity, Integer> registerMirrors = new ConcurrentHashMap<>();
    private static List<Integer> pendingRemoval = Collections.synchronizedList(new ArrayList<Integer>());
    private static NativeImage image;
    private static int quality = 32;
    private static long renderEndNanoTime;




    public MirrorTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }


    @Override
    public void render(MirrorTileEntity tileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
      /*
        MirrorEntity entityMirror = tileEntity.getMirror();
        if(entityMirror == null)
            return;

        image = ScreenShotHelper.takeScreenshot(quality,quality,mc.getMainRenderTarget());
        if(!registerMirrors.containsKey(entityMirror))
        {
            int textureID = image.format().glFormat();
            GlStateManager._bindTexture(textureID);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, quality, quality, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(3 * quality * quality));
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            registerMirrors.put(tileEntity.getMirror(), textureID);
            return;
        }

        stack.pushPose();
        GL11.glPushMatrix();
        this.applyRegionalRenderOffset();
        BlockPos camPos = getCameraBlockPos();
        int regionX = (camPos.getX() >> 9) * 512;
        int regionZ = (camPos.getZ() >> 9) * 512;
        GL11.glTranslated(tileEntity.pos().getX() - regionX, tileEntity.pos().getY(), tileEntity.pos().getZ() - regionZ);
        GL11.glRotated(360,0,tileEntity.getBlockState().getValue(MirrorBlock.FACING).getOpposite().toYRot(),0);

        AxisAlignedBB bb =  new AxisAlignedBB(0.93,0.93,0.93    , 0.07,0.07,0.07);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
       // GL11.glBindTexture(GL11.GL_TEXTURE_2D, registerMirrors.get(entityMirror));

        if(image != null) {
            image.upload(0, 0, 0, false);
          //  ModernCrayfish.LOGGER.info(image.getPixelRGBA(0, 0) + "");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,image.format().glFormat());

        }



        GL11.glBegin(GL11.GL_QUADS);
        switch (tileEntity.getBlockState().getValue(MirrorBlock.FACING)) {
            case NORTH:
                GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
                GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
                GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
                break;
            case SOUTH:
                GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
                GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
                GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
                GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
                break;
            case WEST:
                GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
                GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
                GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
                GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                break;
            case EAST:
                GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
                GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
                GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
                GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
                break;
        }

        GL11.glTexCoord2d(0, 0);
        GL11.glTexCoord2d(1, 0);
        GL11.glTexCoord2d(0, 1);
        GL11.glTexCoord2d(1, 1);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        stack.popPose();

       */
    }

/*
    public static void tick(TickEvent.RenderTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            if(!pendingRemoval.isEmpty())
            {
                for(Integer integer : pendingRemoval)
                {
                    GlStateManager.deleteTexture(integer);
                }
                pendingRemoval.clear();
            }

            if(mc.isGameFocused()) {
                for (MirrorEntity entity : registerMirrors.keySet()) {
                    if (entity == null) {
                        registerMirrors.remove(entity);
                        continue;
                    }

                    if (!entity.rendering)
                        continue;

                    if (!mc.player.canEntityBeSeen(entity))
                        continue;

                   // GameRenderer entityRenderer = mc.gameRenderer;
                   // entityRenderer.renderLevel(event.renderTickTime, renderEndNanoTime + (1000000000 / 30),new MatrixStack());

                }
            }
        }
    }

    public static void removeRegisteredMirror(MirrorEntity entity)
    {
        pendingRemoval.add(registerMirrors.get(entity));
        registerMirrors.remove(entity);
    }

    public static void clearRegisteredMirrors() {
        registerMirrors.clear();
    }

    public void applyRegionalRenderOffset()
    {
        applyCameraRotationOnly();

        Vector3d camPos = getCameraPos();
        BlockPos blockPos = getCameraBlockPos();

        int regionX = (blockPos.getX() >> 9) * 512;
        int regionZ = (blockPos.getZ() >> 9) * 512;

        GL11.glTranslated(regionX - camPos.x, -camPos.y, regionZ - camPos.z);
    }


    public Vector3d getCameraPos()
    {
        ActiveRenderInfo camera = mc.getEntityRenderDispatcher().camera;
        if(camera == null)
            return Vector3d.ZERO;

        return camera.getPosition();
    }

    public BlockPos getCameraBlockPos()
    {
        ActiveRenderInfo camera = mc.getEntityRenderDispatcher().camera;
        if(camera == null)
            return BlockPos.ZERO;

        return camera.getBlockPosition();
    }

    public void applyCameraRotationOnly()
    {
        ActiveRenderInfo camera = mc.getEntityRenderDispatcher().camera;
        GL11.glRotated(MathHelper.wrapDegrees(camera.getXRot()), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(camera.getYRot() + 180.0), 0, 1, 0);
    }
    */
}


