package com.fongmi.android.tv.theme;

/** Immutable Material 3 semantic color and player-control contract. */
public record ThemeTokens(
        int colorPrimary,
        int colorOnPrimary,
        int colorPrimaryContainer,
        int colorOnPrimaryContainer,
        int colorSecondary,
        int colorOnSecondary,
        int colorSecondaryContainer,
        int colorOnSecondaryContainer,
        int colorTertiary,
        int colorOnTertiary,
        int colorError,
        int colorOnError,
        int colorErrorContainer,
        int colorOnErrorContainer,
        int colorSuccess,
        int colorOnSuccess,
        int colorSuccessContainer,
        int colorOnSuccessContainer,
        int colorWarning,
        int colorOnWarning,
        int colorWarningContainer,
        int colorOnWarningContainer,
        int colorSurface,
        int colorSurfaceDim,
        int colorSurfaceBright,
        int colorSurfaceContainerLowest,
        int colorSurfaceContainerLow,
        int colorSurfaceContainer,
        int colorSurfaceContainerHigh,
        int colorSurfaceContainerHighest,
        int colorOnSurface,
        int colorOnSurfaceVariant,
        int colorOutline,
        int colorOutlineVariant,
        int colorInverseSurface,
        int colorInverseOnSurface,
        int colorInversePrimary,
        int colorScrim,
        int colorShadow,
        int colorFocus,
        float focusScale,
        /**
         * 播放页（视频层）焦点环色。由 FOCUS 槽派生并在固定玻璃面板上做了 ≥4.5:1 的可读性夹取，
         * 因为播放页控制条是叠在视频上的固定深珀玻璃：浅色表的 FOCUS 深琥珀在它上面只有 1.83:1。
         */
        int colorPlayerFocusRing,
        /** 播放页「当前播放/当前生效」环色。有独立的用户可编辑槽位（profile 的 playerCurrent）。 */
        int colorPlayerCurrentRing,
        float dialogOpacity,
        int colorPlayerControl,
        int colorPlayerControlMuted,
        int colorPlayerControlActive,
        int colorPlayerScrim,
        int colorHealthGood,
        int colorHealthWarn,
        int colorHealthBad,
        int colorOverlayLight,
        int colorOverlayDark,
        /**
         * 条目遮罩底色（已含 alpha）：默认 {@code #66000000}，即编译期 {@code @color/black_40}
         * 的取值，透明度由 profile 的 itemOverlayOpacity 槽缩放。
         *
         * <p>前缀 color 不是命名洁癖：{@code ThemeContractTest} 用组件名推断同族资源名
         * （{@code "webhtv_color_" + snakeCase(name.substring(5))}），组件名必须带 color 前缀
         * 才能推断出 {@code webhtv_color_item_overlay}。
         */
        int colorItemOverlay,
        /** 遮罩上的选中/聚焦文字色。默认等于本表的 {@link #colorOnPrimary}。 */
        int colorOnItemOverlay
) {

    public static ThemeTokens light() {
        return new ThemeTokens(
            0xFF8A5A12, // primary
            0xFFFFFFFF, // onPrimary
            0xFFF7DFB8, // primaryContainer
            0xFF2B1B00, // onPrimaryContainer
            0xFF5D574D, // secondary
            0xFFFFFFFF, // onSecondary
            0xFFEDE4D6, // secondaryContainer
            0xFF1D1B16, // onSecondaryContainer
            0xFF376257, // tertiary
            0xFFFFFFFF, // onTertiary
            0xFFA8261E, // error
            0xFFFFFFFF, // onError
            0xFFF9DEDC, // errorContainer
            0xFF410E0B, // onErrorContainer
            0xFF216B3C, // success
            0xFFFFFFFF, // onSuccess
            0xFFCDEBD5, // successContainer
            0xFF04210F, // onSuccessContainer
            0xFF7A5A00, // warning
            0xFFFFFFFF, // onWarning
            0xFFFFE3A6, // warningContainer
            0xFF261A00, // onWarningContainer
            0xFFFAF7F1, // surface
            0xFFDDD6C9, // surfaceDim
            0xFFFAF7F1, // surfaceBright
            0xFFFFFFFF, // surfaceContainerLowest
            0xFFF4F0E8, // surfaceContainerLow
            0xFFEDE6DB, // surfaceContainer
            0xFFE4DCCF, // surfaceContainerHigh
            0xFFDCD3C5, // surfaceContainerHighest
            0xFF1C1A16, // onSurface
            0xFF4E4941, // onSurfaceVariant
            0xFF7B756C, // outline
            0xFFCFC7B9, // outlineVariant
            0xFF2E2C28, // inverseSurface
            0xFFF5F1EA, // inverseOnSurface
            0xFFFFD79E, // inversePrimary
            0x52000000, // scrim
            0xFF000000, // shadow
            0xFF8A5A12, // focus
            1.1f, // focusScale
            0xFFF2B25C, // playerFocusRing
            0xFF2E9E68, // playerCurrentRing
            1.0f, // dialogOpacity
            0xFFFFFFFF, // playerControl
            0xCCFFFFFF, // playerControlMuted
            0xFFFFD08A, // playerControlActive
            0x7A000000, // playerScrim
            0xFF216B3C, // healthGood
            0xFF7A5A00, // healthWarn
            0xFFA8261E, // healthBad
            0x14FFFFFF, // overlayLight
            0x52000000, // overlayDark
            0x66000000, // itemOverlay
            0xFFFFFFFF // onItemOverlay
    );
    }

    public static ThemeTokens dark() {
        return new ThemeTokens(
            0xFFE9B872, // primary
            0xFF241703, // onPrimary
            0xFF4A3316, // primaryContainer
            0xFFFFDDA8, // onPrimaryContainer
            0xFFC9C3B8, // secondary
            0xFF26221C, // onSecondary
            0xFF39332C, // secondaryContainer
            0xFFEAE2D6, // onSecondaryContainer
            0xFF8FC7B6, // tertiary
            0xFF00251C, // onTertiary
            0xFFFFB4A9, // error
            0xFF5C1008, // onError
            0xFF8C1D18, // errorContainer
            0xFFFFDAD5, // onErrorContainer
            0xFF9BD7A6, // success
            0xFF00320F, // onSuccess
            0xFF1B5129, // successContainer
            0xFFC6F0CE, // onSuccessContainer
            0xFFF5C66B, // warning
            0xFF3D2A00, // onWarning
            0xFF5A4200, // warningContainer
            0xFFFFE0A8, // onWarningContainer
            0xFF0E1013, // surface
            0xFF08090B, // surfaceDim
            0xFF363A41, // surfaceBright
            0xFF08090B, // surfaceContainerLowest
            0xFF15171B, // surfaceContainerLow
            0xFF1B1E23, // surfaceContainer
            0xFF25282E, // surfaceContainerHigh
            0xFF2F333A, // surfaceContainerHighest
            0xFFEFEAE2, // onSurface
            0xFFBDB7AC, // onSurfaceVariant
            0xFF8F8A82, // outline
            0xFF454A51, // outlineVariant
            0xFFEFEAE2, // inverseSurface
            0xFF1B1E23, // inverseOnSurface
            0xFF845012, // inversePrimary
            0x8A000000, // scrim
            0xFF000000, // shadow
            0xFFFFD79E, // focus
            1.1f, // focusScale
            0xFFFFDEA8, // playerFocusRing
            0xFF7FD3A2, // playerCurrentRing
            1.0f, // dialogOpacity
            0xFFFFF9F0, // playerControl
            0xCCFFF9F0, // playerControlMuted
            0xFFF0BB69, // playerControlActive
            0x8F000000, // playerScrim
            0xFF9BD7A6, // healthGood
            0xFFF5C66B, // healthWarn
            0xFFFFB4A9, // healthBad
            0x1AFFEBD6, // overlayLight
            0x52000000, // overlayDark
            0x66000000, // itemOverlay
            0xFFEFEAE2 // onItemOverlay
    );
    }

    public ThemeTokens requireContrast() {
        ThemeContrast.require(this);
        return this;
    }
}
