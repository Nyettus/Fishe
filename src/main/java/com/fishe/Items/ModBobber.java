package com.fishe.Items;

import com.fishe.Utils.ModLootPool;
import com.fishe.Utils.RodTiers.RodTier;
import com.fishe.mixin.AccessBobberMethods;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ModBobber extends FishingBobberEntity {
    public ModBobber(PlayerEntity thrower, World world, int luckOfTheSeaLevel, int lureLevel, RodTier tier, int nightEnchantTier) {
        super(thrower, world, luckOfTheSeaLevel, lureLevel);
        rodTier = tier;
        this.nightEnchantTier = nightEnchantTier;
    }

    private final RodTier rodTier;
    private final int nightEnchantTier;

    @Override
    public int use(ItemStack usedItem) {
        PlayerEntity playerEntity = this.getPlayerOwner();
        AccessBobberMethods accessor = (AccessBobberMethods) this;
        if (!this.getWorld().isClient && playerEntity != null && !accessor.invokeRemoveIfInvalid(playerEntity)) {
            int i = 0;
            if (accessor.getHookedEntity() != null) {
                this.pullHookedEntity(accessor.getHookedEntity());
                Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity) playerEntity, usedItem, this, Collections.emptyList());
                this.getWorld().sendEntityStatus(this, EntityStatuses.PULL_HOOKED_ENTITY);
                i = accessor.getHookedEntity() instanceof ItemEntity ? 3 : 5;
            } else if (accessor.getHookCountdown() > 0) {
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder((ServerWorld) this.getWorld())).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.TOOL, usedItem).add(LootContextParameters.THIS_ENTITY, this).luck((float) accessor.getLuckOfTheSeaLevel() + playerEntity.getLuck()).build(LootContextTypes.FISHING);
                LootTable lootTable = this.getWorld().getServer().getLootManager().getLootTable(whichLootTable());
                List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);
                Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity) playerEntity, usedItem, this, list);
                Iterator var7 = list.iterator();

                while (var7.hasNext()) {
                    ItemStack itemStack = (ItemStack) var7.next();
                    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
                    double d = playerEntity.getX() - this.getX();
                    double e = playerEntity.getY() - this.getY();
                    double f = playerEntity.getZ() - this.getZ();
                    double g = 0.1;
                    itemEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08, f * 0.1);
                    this.getWorld().spawnEntity(itemEntity);
                    playerEntity.getWorld().spawnEntity(new ExperienceOrbEntity(playerEntity.getWorld(), playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, this.random.nextInt(6) + 1));
                    if (itemStack.isIn(ItemTags.FISHES)) {
                        playerEntity.increaseStat((Identifier) Stats.FISH_CAUGHT, 1);
                    }
                }

                i = 1;
            }

            if (this.isOnGround()) {
                i = 2;
            }

            this.discard();
            return i;
        } else {
            return 0;
        }
    }


    private Identifier whichLootTable() {
        Identifier returnIden = LootTables.FISHING_GAMEPLAY;

        //Call of the night enchantment also gives a chance for the night pool to be active at day
        //this is for servers where people instantly sleep at night.
        float randomValue = this.random.nextFloat();
        float threshold = 0;
        if (this.getWorld().isNight()) {
            threshold = 0.25f + (0.25f * nightEnchantTier);
        } else {
            threshold = 0.05f * nightEnchantTier;
        }
        if (randomValue < threshold) return ModLootPool.NIGHT_POOL;


        switch (rodTier) {
            case COPPER -> {
                returnIden = ModLootPool.COPPER_POOL;
            }
            case IRON -> {
                returnIden = ModLootPool.IRON_POOL;
            }
            case GOLD -> {
                returnIden = ModLootPool.GOLD_POOL;
            }
            case DIAMOND -> {
                returnIden = ModLootPool.DIAMOND_POOL;
            }

        }
        return returnIden;
    }


}
