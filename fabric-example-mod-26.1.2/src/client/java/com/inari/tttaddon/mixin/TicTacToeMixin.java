package com.inari.tttaddon.mixin;

import com.inari.tttaddon.api.TttBestMoveTracker;
import de.hysky.skyblocker.skyblock.dungeon.puzzle.TicTacToe;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TicTacToe.class)
public abstract class TicTacToeMixin {

    /**
     * Skyblocker側の tick() が nextBestMoveToMake を更新し終えた直後に発火。
     * (null = 更新なし/対象外 も含めてそのまま Tracker に流す)
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void tttaddon$onTickEnd(Minecraft client, CallbackInfo ci) {
        AABB best = TicTacToeAccessor.tttaddon$getNextBestMoveToMake();
        TttBestMoveTracker.update(client, best);
    }
}