package com.inari.tttaddon.api;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

/**
 * Skyblockerの TicTacToe#tick() 末尾からMixin経由で呼ばれるレジストリ。
 * 今後機能を追加するときは、ここに addListener するだけでよい。
 *
 * 例:
 * TttBestMoveTracker.addListener((client, best) -> {
 *     if (best != null) {
 *         // ここに好きな機能を実装(自動クリック、通知、統計など)
 *     }
 * });
 */
public final class TttBestMoveTracker {

    private static final List<BestMoveListener> LISTENERS = new ArrayList<>();
    private static AABB lastBestMove = null;

    private TttBestMoveTracker() {
    }

    public static void addListener(BestMoveListener listener) {
        LISTENERS.add(listener);
    }

    public static void removeListener(BestMoveListener listener) {
        LISTENERS.remove(listener);
    }

    /** 直近でSkyblockerが検知した最善手(未検知/対象外なら null) */
    public static AABB getLastBestMove() {
        return lastBestMove;
    }

    // TicTacToeMixin から呼ばれる。ユーザーコードから直接呼ばないこと。
    public static void update(Minecraft client, AABB best) {
        lastBestMove = best;
        for (BestMoveListener listener : LISTENERS) {
            listener.onBestMoveUpdate(client, best);
        }
    }
}