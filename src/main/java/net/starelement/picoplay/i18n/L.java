package net.starelement.picoplay.i18n;

import cn.nukkit.Player;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class L {

    private static HashMap<String, Language> languages = new HashMap<>();
    private static Language defaultLang;
    private static Language list;
    private static String defaultCode = "eng";

    private L() {
        throw new RuntimeException();
    }

    public static void load() {
        list = new Language("list", L.class.getResourceAsStream("/languages/list.properties"));
        for (Map.Entry<String, String> entry : list.entrySet()) {
            try {
                Language language = new Language(entry.getValue(), L.class.getResourceAsStream("/languages/" + entry.getValue() + ".properties"));
                languages.put(entry.getValue(), language);
            } catch (Exception e) {
                languages.put(entry.getValue(), new Language(entry.getValue()));
            }
        }
    }

    public static void setDefault(Language language) {
        defaultLang = language;
    }

    public static void setDefaultCode(String code) {
        defaultCode = code;
    }

    public static String get(Player player, String key) {
        return get(player.getLoginChainData().getLanguageCode(), key);
    }

    public static String get(String code, String key) {
        if (list.contains(code)) code = list.get(code);
        Language language = languages.get(code);
        if (language == null) language = defaultLang;
        return language.get(key);
    }

    public static String get(String key) {
        return get(defaultCode, key);
    }

}
