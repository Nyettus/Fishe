package com.fishe.mixin.client;

import com.fishe.ModFishingRodItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Copied from go-fish mod
@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {
    private PlayerEntity player;

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void storeContext(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        player = fishingBobberEntity.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHandSwingProgress(F)F"),
            index = 12
    )
    private int mod(int i) {
        ItemStack itemStack = player.getMainHandStack();

        // previous condition was hit
        if (itemStack.getItem() != Items.FISHING_ROD) {
            if(itemStack.getItem() instanceof ModFishingRodItem) {
                // undo change
                return -i;
            }
        }

        return i;
    }
}
