package com.inari.tttaddon.feature;

import com.inari.tttaddon.api.TttBestMoveTracker;
import com.inari.tttaddon.config.TttAddonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Skyblockerが計算した最善手のAABB(ボタンブロックの当たり判定)に向けて
 * 実際にレイトレースし、右クリック(ブロック使用)する。
 *
 * 参考: rsa-1_0_2.jar の AutoTTT クラス。対象はItemFrameエンティティではなく
 * ボタンブロックであることをそちらの実装から確認した。
 */
public final class TttAutoClicker {

    private static int cooldownTicks = 0;
    private static final int COOLDOWN_AFTER_CLICK = 10;

    private TttAutoClicker() {
    }

    /** Kept for the existing /tttaddon commands; now just flips the same field the GUI toggle uses. */
    public static void setEnabled(boolean value) {
        TttAddonConfig.INSTANCE.autoClickEnabled = value;
        cooldownTicks = 0;
    }

    public static boolean isEnabled() {
        return TttAddonConfig.INSTANCE.autoClickEnabled;
    }

    public static void register() {
        TttBestMoveTracker.addListener(TttAutoClicker::onBestMoveUpdate);
    }

    private static void onBestMoveUpdate(Minecraft client, AABB best) {
        if (!TttAddonConfig.INSTANCE.autoClickEnabled || best == null) {
            return;
        }
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return;
        }
        if (client.level == null || client.player == null) {
            return;
        }

        Vec3 eyePos = client.player.getEyePosition(1.0f);
        Vec3 targetCenter = best.getCenter();

        double maxRange = TttAddonConfig.INSTANCE.maxRange;
        if (eyePos.distanceToSqr(targetCenter) > maxRange * maxRange) {
            return; // 届かない距離なら何もしない
        }

        ClipContext ctx = new ClipContext(eyePos, targetCenter, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, client.player);
        HitResult hit = client.level.clip(ctx);
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        if (!(client.level.getBlockState(blockHit.getBlockPos()).getBlock() instanceof ButtonBlock)) {
            return; // RSAと同様、対象が本当にボタンかを確認してからクリック
        }

        client.gameMode.useItemOn(client.player, InteractionHand.MAIN_HAND, blockHit);
        cooldownTicks = COOLDOWN_AFTER_CLICK;
    }
}