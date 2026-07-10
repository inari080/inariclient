package com.inari.tttaddon;

import com.example.themedgui.client.api.AddonRegistration;
import com.example.themedgui.client.api.ThemedGuiAddon;
import com.inari.tttaddon.config.TttAddonConfig;

/**
 * Registered via the "themedgui:addon" entrypoint in fabric.mod.json.
 * Shows up as "TicTacToe Addon" in ThemedGuiMod's hub screen (press O).
 */
public class TttThemedGuiAddon implements ThemedGuiAddon {

    @Override
    public void register(AddonRegistration registration) {
        registration.registerMod(TttAddonMod.MOD_ID, "TicTacToe Addon", TttAddonConfig.INSTANCE, null);
    }
}
