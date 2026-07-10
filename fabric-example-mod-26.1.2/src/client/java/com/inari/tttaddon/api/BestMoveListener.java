package com.inari.tttaddon.api;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;

/**
 * Tic-Tac-Toeの最善手が更新されるたびに呼ばれるリスナー。
 * best が null の場合は「今は提示すべき最善手がない」ことを意味する
 * (パズル対象外の部屋にいる、盤面が読み取れない、自分の手番でない等)。
 */
@FunctionalInterface
public interface BestMoveListener {
    void onBestMoveUpdate(Minecraft client, AABB best);
}