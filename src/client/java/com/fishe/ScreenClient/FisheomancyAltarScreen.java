package com.fishe.ScreenClient;

import com.fishe.Fishe;
import com.fishe.Screen.FisheomancyAltarScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FisheomancyAltarScreen extends HandledScreen<FisheomancyAltarScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Fishe.MOD_ID,"textures/gui/fisheomancy_alter_gui.png");
    public FisheomancyAltarScreen(FisheomancyAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;

        context.drawTexture(TEXTURE, x,y,0,0,backgroundWidth,backgroundHeight);

        renderProgressBar(context,x,y);
    }

    private void renderProgressBar(DrawContext context, int x, int y) {
        int progress=handler.getSlopProgress();
        context.drawTexture(TEXTURE,x+11,y+72-progress,176,0,52,progress);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }
}
