package infrastructure.csv;

import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
    private CsvUtils() {}

    public static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return fallback; }
    }

    public static List<String> splitCats(String field) {
        List<String> out = new ArrayList<>();
        if (field == null || field.isBlank()) return out;
        for (String c : field.split("\\|")) {
            String v = c.trim();
            if (!v.isBlank()) out.add(v);
        }
        return out;
    }
}