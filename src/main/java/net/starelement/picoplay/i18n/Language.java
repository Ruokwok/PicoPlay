package net.starelement.picoplay.i18n;

import cn.nukkit.Server;
import cn.nukkit.utils.Utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Language {

    private String name;
    private String code;
    private Map<String, String> map;

    public Language(String language, InputStream stream) {
        this.code = language;
        this.map = load(stream);
        this.name = map.get("language.name");
    }

    public String get(String key) {
        String str = map.get(key);
        if (str == null || str.isEmpty()) {
            return "{" + key + "}";
        } else {
            return str;
        }
    }

    public String getName() {
        return name;
    }

    private static Map<String, String> load(InputStream stream) {
        try {
            String content = Utils.readFile(stream);
            Map<String, String> d = new HashMap<>();
            for (String line : content.split("\n")) {
                line = line.trim();
                if (line.equals("") || line.charAt(0) == '#') {
                    continue;
                }
                String[] t = line.split("=");
                if (t.length < 2) {
                    continue;
                }
                String key = t[0];
                String value = "";
                for (int i = 1; i < t.length - 1; i++) {
                    value += t[i] + "=";
                }
                value += t[t.length - 1];
                if (value.equals("")) {
                    continue;
                }
                d.put(key, value);
            }
            return d;
        } catch (IOException e) {
            Server.getInstance().getLogger().logException(e);
            return null;
        }
    }
}
