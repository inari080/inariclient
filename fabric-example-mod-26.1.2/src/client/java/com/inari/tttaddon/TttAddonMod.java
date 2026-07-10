package com.inari.tttaddon;

import com.inari.tttaddon.feature.TttAutoClicker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;

public class TttAddonMod implements ClientModInitializer {

    public static final String MOD_ID = "ttt-addon";

    @Override
    public void onInitializeClient() {
        TttAutoClicker.register();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("tttaddon")
                        .then(ClientCommands.literal("autoclick")
                                .then(ClientCommands.literal("on").executes(ctx -> {
                                    TttAutoClicker.setEnabled(true);
                                    ctx.getSource().sendFeedback(Component.literal("§a[TttAddon] 自動クリックを有効にしました"));
                                    return 1;
                                }))
                                .then(ClientCommands.literal("off").executes(ctx -> {
                                    TttAutoClicker.setEnabled(false);
                                    ctx.getSource().sendFeedback(Component.literal("§c[TttAddon] 自動クリックを無効にしました"));
                                    return 1;
                                }))
                        )
                )
        );
    }
}