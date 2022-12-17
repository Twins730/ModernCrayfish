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
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//TODO FIX this mess
@Mod.EventBusSubscriber
public class MirrorTileEntityRenderer extends TileEntityRenderer<MirrorTileEntity> {

    public static final Minecraft mc = Minecraft.getInstance();
    public static WorldRenderer reflection = new MirrorRenderer(Minecraft.getInstance(),Minecraft.getInstance().renderBuffers());
    private static Map<MirrorEntity, Integer> registerMirrors = new ConcurrentHashMap<>();
    private static List<Integer> pendingRemoval = Collections.synchronizedList(new ArrayList<Integer>());
    private int quality = 32;
    private long renderEndNanoTime;


    public MirrorTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }


    @Override
    public void render(MirrorTileEntity tileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {

      //  if(!ConfigurationHandler.mirrorEnabled)
        //    return;

        if(TileEntityRendererDispatcher.instance.camera.getEntity() instanceof MirrorEntity)
            return;

        MirrorEntity entityMirror = tileEntity.getMirror();
        if(entityMirror == null)
            return;

        if(!registerMirrors.containsKey(entityMirror))
        {
            int newTextureId = GL11.glGenTextures();
            GlStateManager._bindTexture(newTextureId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, quality, quality, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(3 * quality * quality));
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            registerMirrors.put(tileEntity.getMirror(), newTextureId);
            return;
        }

        entityMirror.rendering = true;

        Direction facing = tileEntity.getBlockState().getValue(MirrorBlock.FACING);
        stack.pushPose();
        {
            GlStateManager._enableBlend();
            //OpenGlHelper.glBlendFunc(770, 771, 1, 0);

            GlStateManager._disableLighting();
            GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager._bindTexture(registerMirrors.get(entityMirror));

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);


           // stack.mulPose(new Quaternion(-90F * facing.toYRot() + 180F, 0, 1, 0));
           // stack.translate(-0.5F, 0, -0.43F);


            GlStateManager._enableRescaleNormal();

            // Render
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glTexCoord2d(1, 0);
                GL11.glVertex3d(0.0625, 0.0625, 0);
                GL11.glTexCoord2d(0, 0);
                GL11.glVertex3d(0.9375, 0.0625, 0);
                GL11.glTexCoord2d(0, 1);
                GL11.glVertex3d(0.9375, 0.9375, 0);
                GL11.glTexCoord2d(1, 1);
                GL11.glVertex3d(0.0625, 0.9375, 0);
                ModernCrayfish.LOGGER.info("Rendering!!!");
            }
            GL11.glEnd();

            GlStateManager._disableRescaleNormal();
            GlStateManager._disableBlend();
            GlStateManager._enableLighting();
        }


        stack.popPose();
    }


    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END))
            return;

        //if(!ConfigurationHandler.mirrorEnabled)
        //   return;

        if (!pendingRemoval.isEmpty()) {
            for (Integer integer : pendingRemoval) {
                GlStateManager._deleteTexture(integer);
            }
            pendingRemoval.clear();
        }

        if (mc.isWindowActive()) {
            for (MirrorEntity entity : registerMirrors.keySet()) {
                if (entity == null) {
                    registerMirrors.remove(entity);
                    continue;
                }

                if (!entity.rendering)
                    continue;

                if (!mc.player.canSee(entity))
                    continue;

                if(entity.distanceTo(mc.player) < 5) {
                    WorldRenderer renderBackup = mc.levelRenderer;
                    Entity entityBackup = mc.getCameraEntity();
                    GameSettings settings = mc.options;
                    PointOfView thirdPersonBackup = settings.getCameraType();
                    boolean hideGuiBackup = settings.hideGui;
                    int mipmapBackup = settings.mipmapLevels;
                    double fovBackup = settings.fov;
                    int widthBackup = mc.getMainRenderTarget().viewWidth;
                    int heightBackup = mc.getMainRenderTarget().viewHeight;

                    //mc.levelRenderer = reflection;
                    mc.setCameraEntity(entity);
                    settings.fov = 80;//ConfigurationHandler.mirrorFov;
                    settings.setCameraType(PointOfView.FIRST_PERSON);
                    settings.hideGui = true;
                    settings.mipmapLevels = 3;
                    mc.getMainRenderTarget().resize(quality, quality, false);

                    ModernCrayfish.rendering = true;
                    ModernCrayfish.renderEntity = mc.player;

                    int fps = Math.max(30, settings.framerateLimit);
                    GameRenderer entityRenderer = mc.gameRenderer;
                    entityRenderer.renderLevel(event.renderTickTime, renderEndNanoTime + (1000000000 / fps), new MatrixStack());
                    GlStateManager._bindTexture(registerMirrors.get(entity));
                    GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 0, 0, quality, quality, 0);

                    renderEndNanoTime = System.nanoTime();

                    ModernCrayfish.renderEntity = null;
                    ModernCrayfish.rendering = false;

                    //mc.levelRenderer = renderBackup;
                    mc.setCameraEntity(entityBackup);
                    settings.fov = fovBackup;
                    settings.setCameraType(thirdPersonBackup);
                    settings.hideGui = hideGuiBackup;
                    settings.mipmapLevels = mipmapBackup;
                    mc.getMainRenderTarget().resize(widthBackup, heightBackup, false);
                }

                entity.rendering = false;
            }
        }
    }




    public static void removeRegisteredMirror(MirrorEntity entity)
    {
        pendingRemoval.add(registerMirrors.get(entity));
        registerMirrors.remove(entity);
    }

    public static void clearRegisteredMirrors()
    {
        registerMirrors.clear();
    }
}


