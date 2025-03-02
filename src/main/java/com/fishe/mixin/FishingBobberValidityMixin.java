package com.fishe.mixin;

import com.fishe.Items.ModFishingRodItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberValidityMixin extends Entity {
    private FishingBobberValidityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * Patches {@link FishingBobberEntity#removeIfInvalid(PlayerEntity)} to work for all items of the type {@link FishingRodItem}.
     * Stolen from draylar go fish (sorry)
     *
     * @param cir  mixin callback info
     */
    @Inject(
            method = "removeIfInvalid",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void removeIfInvalid(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = playerEntity.getMainHandStack();
        ItemStack offHandStack = playerEntity.getOffHandStack();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof ModFishingRodItem;
        boolean offHandHasRod = offHandStack.getItem() instanceof ModFishingRodItem;

        if (!playerEntity.isRemoved() && playerEntity.isAlive() && (mainHandHasRod || offHandHasRod) && this.squaredDistanceTo(playerEntity) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }

}
