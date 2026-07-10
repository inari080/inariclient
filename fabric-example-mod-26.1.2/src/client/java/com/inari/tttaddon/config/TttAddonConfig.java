package com.inari.tttaddon.config;

import com.example.themedgui.client.config.Setting;

/**
 * Backing config for ThemedGuiMod's hub screen. TttAutoClicker reads these
 * fields directly (see the "Feature code reads the config directly" pattern
 * in ThemedGuiMod's README) instead of keeping its own duplicate state, so
 * the GUI toggle/slider and the existing /tttaddon commands always agree.
 */
public class TttAddonConfig {

    public static final TttAddonConfig INSTANCE = new TttAddonConfig();

    @Setting(category = "General", label = "Auto click", tooltip = "Skyblockerが検出した最善手のボタンを自動でクリックする")
    public boolean autoClickEnabled = false;

    @Setting(category = "General", label = "Max range", tooltip = "自動クリックが反応する最大距離 (ブロック)", min = 1.0, max = 6.0)
    public float maxRange = 3.0f;
}
