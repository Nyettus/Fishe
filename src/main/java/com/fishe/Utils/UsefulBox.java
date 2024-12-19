package com.fishe.Utils;

import net.minecraft.util.math.BlockPos;

public class UsefulBox {
    private static BlockPos nullpos = new BlockPos(0,500,0);

    /**
     * Essentially BlockPos.asLong but with null protection
     * @param original
     * @return
     */
    public static long BlockPosToLong(BlockPos original) {
        if(original==null) return nullpos.asLong();
        return original.asLong();
    }

    /**
     * Essentially BlockPos.fromLong but with null protection
     * @param original
     * @return
     */
    public static BlockPos LongToBlockPos(long original){
        if(original==nullpos.asLong()) return null;
        return BlockPos.fromLong(original);
    }

}
