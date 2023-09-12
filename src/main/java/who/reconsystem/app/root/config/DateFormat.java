package who.reconsystem.app.root.config;

import lombok.Getter;

@Getter
public enum DateFormat {
    DATE_BY_BAR, DATE_BY_SLASH, DATE_LONG, DATE_BY_BAR_FR, TIME, DATETIME_BY_BAR, DATETIME_BY_SLASH,
    DATETIME_BY_SLASH_A, DATETIME_LONG, DATETIME_BY_BAR_FR, DATETIME_LONG_AT, DATETIME_LONG_A;

    private String iso;

    static {
        DATE_BY_BAR.iso = "yyyy-MM-dd";
        DATE_BY_BAR_FR.iso = "dd-MM-yyyy";
        DATE_BY_SLASH.iso = "dd/MM/yyyy";
        DATE_LONG.iso = "EEEE, dd MMMM yyyy";
        TIME.iso = "HH:mm:ss";
        DATETIME_BY_BAR.iso = "yyyy-MM-dd HH:mm:ss";
        DATETIME_BY_BAR_FR.iso = "dd-MM-yyyy HH:mm:ss";
        DATETIME_BY_SLASH.iso = "dd/MM/YYYY HH:mm:ss";
        DATETIME_BY_SLASH_A.iso = "dd/MM/YYYY à HH:mm:ss";
        DATETIME_LONG.iso = "dd/MM/YYYY HH:mm:ss";
        DATETIME_LONG_AT.iso = "EEEE, dd MMMM yyyy at HH:mm:ss";
        DATETIME_LONG_A.iso = "EEEE, dd MMMM yyyy à HH:mm:ss";
    }
}
