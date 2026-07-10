package com.inari.tttaddon.mixin;

import de.hysky.skyblocker.skyblock.dungeon.puzzle.TicTacToe;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * TicTacToe#nextBestMoveToMake (private static, nullable) を読むためのAccessor。
 */
@Mixin(TicTacToe.class)
public interface TicTacToeAccessor {

    @Accessor("nextBestMoveToMake")
    static AABB tttaddon$getNextBestMoveToMake() {
        throw new AssertionError("Mixin not applied");
    }
}