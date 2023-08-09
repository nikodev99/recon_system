package who.reconsystem.app.root.config;

import java.io.File;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Functions {
    private static final String _DIR = File.separator;

    /**
     * Dynamically retrieve the root route of the project.
     * @param fileName {String} The file name located i the source folder.
     * @return String The source folder of the project.
     */
    public static String getLocalePath(String fileName) {
        ProtectionDomain domain = Functions.class.getProtectionDomain();
        CodeSource codeSource = domain.getCodeSource();
        return codeSource.getLocation().getPath() + fileName;
    }

    /**
     * To trim all the element of a list.
     * @param listToTrim List<String> the list of element to trim.
     * @return list<String> the list of element trimmed.
     */
    public static List<String> trimArray(List<String> listToTrim) {
        if(!listToTrim.isEmpty()) {
            listToTrim.replaceAll(String::trim);
            return listToTrim;
        }
        return Collections.emptyList();
    }

    /**
     * To get the instant date.
     * @param format String format of the date.
     * @return String the formatted date
     */
    public static String instantDate(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static Instant now() {
        return Instant.now();
    }

    public static LocalDateTime dateTime(String dateToConvert) {
        return LocalDateTime.parse(dateToConvert, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Getting the datetime with the date of Congo.
     * @param format String the datetime format.
     * @return String the formatted datetime.
     */
    public static String instantDatetime(String format) {
        return LocalDateTime.now(congoTime()).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Getting the time of Brazzaville.
     * @return ZoneId of Brazzaville.
     */
    private static ZoneId congoTime() {
        return ZoneId.of("Africa/Brazzaville");
    }
}
