package com.fishe.ScreenClient;

import com.fishe.Fishe;
import com.fishe.Screen.FishePasterScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FishePasterScreen extends HandledScreen<FishePasterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Fishe.MOD_ID,"textures/gui/fishe_paster_gui.png");

    public FishePasterScreen(FishePasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        //titleX/Y controls block name word
        //playerInventoryX/Y controls player inventory name placement

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;

        context.drawTexture(TEXTURE, x,y,0,0,backgroundWidth,backgroundHeight);

        renderProgressArrow(context,x,y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x+85,y+30,176,0,8,handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //renderBackground(context,mouseX,mouseY,delta);
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX, mouseY);
    }
}
