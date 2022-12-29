package com.ModernCrayfish.client.renderer.screen;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.init.PacketInit;
import com.ModernCrayfish.objects.container.MicrowaveContainer;
import com.ModernCrayfish.objects.packet.PacketStartMicrowave;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MicrowaveScreen extends ContainerScreen<MicrowaveContainer> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ModernCrayfish.MOD_ID, "textures/gui/microwave.png");
    private int startX,startY;

    public MicrowaveScreen(MicrowaveContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, new StringTextComponent(""));
        this.ySize = 256;
    }

    protected void init() {
        super.init();
        startX = (this.width - this.xSize) / 2;
        startY = (this.height - this.ySize) / 2;
        this.addButton(new Button(startX + 119,startY + 41,32,20, new TranslationTextComponent("modern_crayfish.microwave.start"), (p_onPress_1_) -> {
            PacketInit.instance.sendToServer(new PacketStartMicrowave());
        }));


    }
    
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        startX = (this.width - this.xSize) / 2;
        startY = (this.height - this.ySize) / 2;
        int progress = container.data.get(0);
        this.blit(matrixStack, startX, startY, 0, 0, this.xSize, this.ySize);
        this.blit(matrixStack,startX + 120, startY + 26,176,0, progress,5);
    }
}
