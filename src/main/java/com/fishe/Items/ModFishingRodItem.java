package com.fishe.Items;

import com.fishe.Utils.RodTiers.RodTier;
import com.fishe.enchantments.EnchantmentMaster;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModFishingRodItem extends FishingRodItem implements Vanishable {
    public ModFishingRodItem(Settings settings, RodTier tier) {
        super(settings);
        rodTier = tier;
    }

    public RodTier rodTier;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.fishHook != null) {
            if (!world.isClient) {
                int i = user.fishHook.use(itemStack);
                itemStack.damage(i, user, (p) -> p.sendToolBreakStatus(hand));
            }

            world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                int i = EnchantmentHelper.getLure(itemStack);
                int j = EnchantmentHelper.getLuckOfTheSea(itemStack);
                world.spawnEntity(new ModBobber(user, world, j, i,rodTier,getCalloftheNight(itemStack)));
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    private int getCalloftheNight(ItemStack itemstack){
        return EnchantmentHelper.getLevel(EnchantmentMaster.CALL_OF_THE_NIGHT,itemstack);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }


}
