package net.starelement.picoplay.i18n;

import cn.nukkit.Player;

import java.util.HashMap;

public class L {

    private static HashMap<String, Language> languages = new HashMap<>();
    private static Language defaultLang;

    private L() {
        throw new RuntimeException();
    }

    public static void load() {
    }

    public static void setDefault(Language language) {
        defaultLang = language;
    }

    public static String get(Player player, String key) {
        String lang = player.getLoginChainData().getLanguageCode();
        Language language = languages.get(lang);
        if (language == null) language = defaultLang;
        return language.get(key);
    }

}
