package who.reconsystem.app.root.config;

import who.reconsystem.app.models.Operator;
import who.reconsystem.app.models.connect.DbConnect;

import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Functions {
    /**
     * Dynamically retrieve the root route of the project.
     * @param fileName {String} The file name located i the source folder.
     * @return String The source folder of the project.
     */
    public static String getLocalePath(String fileName) {
        ProtectionDomain domain = DbConnect.class.getProtectionDomain();
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
