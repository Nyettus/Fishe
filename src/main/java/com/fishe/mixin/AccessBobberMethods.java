package com.fishe.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FishingBobberEntity.class)
public interface AccessBobberMethods {
    @Accessor
    Entity getHookedEntity();

    @Accessor
    int getHookCountdown();

    @Accessor
    int getLuckOfTheSeaLevel();

    @Invoker
    boolean invokeRemoveIfInvalid(PlayerEntity playerEntity);


}
