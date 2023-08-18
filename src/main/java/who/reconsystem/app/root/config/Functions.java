package who.reconsystem.app.root.config;

import java.io.File;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        String path = codeSource.getLocation().getPath() + fileName;
        if (path.startsWith("/")) path = path.substring(1);
        return path;
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

    public static String arrayToString(String[] array) {
        return arrayToString(array, false, "", "");
    }

    public static String arrayToString(String[] array, boolean byLine, String prefix, String suffix) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String element : array) {
            stringBuilder.append(byLine ? prefix + element + suffix + "\n" : prefix + element + suffix);
        }

        return stringBuilder.toString();
    }

    public static Instant now() {
        return Instant.now();
    }

    public static Instant instantFromDay(int minusDays) {
        return now().minus(minusDays, ChronoUnit.DAYS);
    }

    public static Instant instantToDay(int plusDays) {
        return now().plus(plusDays, ChronoUnit.DAYS);
    }

    public static boolean daysComparison(Instant instantOfDay1, Instant instantOfDay2) {
        LocalDate date1 = instantOfDay1.atZone(congoTime()).toLocalDate();
        LocalDate date2 = instantOfDay2.atZone(congoTime()).toLocalDate();
        int comparison = date1.compareTo(date2);
        return comparison == 0;
    }

    public static String instantToDatetimeString(Instant instant, String format){
        LocalDateTime dateTime = instant.atZone(congoTime()).toLocalDateTime();
        return dateTime.format(format != null ? DateTimeFormatter.ofPattern(format) : DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static String yesterday() {
        return instantToDatetimeString(instantFromDay(1), null);
    }

    public static LocalDateTime dateTime(String dateToConvert) {
        return LocalDateTime.parse(dateToConvert, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
