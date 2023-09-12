package who.reconsystem.app.root.config;

import who.reconsystem.app.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Functions {
    private static final String _DIR = File.separator;

    private static final String DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "\\Downloads\\";

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

    public static String automaticFileName(String subNameToSearch) {
        Set<String> fileNameOccurrences = new HashSet<>();
        Path folder = Paths.get(DOWNLOAD_DIRECTORY);
        if (Files.exists(folder) && Files.isDirectory(folder)) {
            FileUtils fileUtils = new FileUtils(folder);
            List<String> fileNames = fileUtils.getAllFiles();
            for (String fileName: fileNames) {
                if (fileName.contains(subNameToSearch)) {
                    fileNameOccurrences.add(fileName);
                }
            }
        }
        return !fileNameOccurrences.isEmpty() ? subNameToSearch + "(" + fileNameOccurrences.size() + ")" : subNameToSearch;
    }

    public static List<String[]> stringToJaggedArray(String value) {
        String[] parts = value.split("[\\s\n]+");
        List<String[]> resultList = new ArrayList<>();
        for (String part: parts) {
            String[] subParts = part.split(",");
            for (int i = 0; i < subParts.length; i++) {
                subParts[i] = subParts[i].trim();
            }
            resultList.add(subParts);
        }
        return resultList;
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

    public static String instantToDatetimeString(Instant instant, DateFormat format){
        LocalDateTime dateTime = instant.atZone(congoTime()).toLocalDateTime();
        return dateTime.format(format != null ? DateTimeFormatter.ofPattern(format.getIso()) : DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static String yesterday() {
        return instantToDatetimeString(instantFromDay(1), null);
    }

    public static LocalDate date(String dateToConvert) {
        return isValidLocaleDate(dateToConvert)
                ? LocalDate.parse(dateToConvert, DateTimeFormatter.ofPattern(DateFormat.DATE_BY_BAR.getIso()))
                : null;
    }

    public static LocalDateTime dateTime(String dateToConvert) {
        return isValidLocaleDateTime(dateToConvert)
                ? LocalDateTime.parse(dateToConvert, DateTimeFormatter.ofPattern(DateFormat.DATETIME_BY_BAR.getIso()))
                : null;
    }

    /**
     * To get the instant date.
     * @param format String format of the date.
     * @return String the formatted date
     */
    public static String instantDate(DateFormat format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format.getIso()));
    }

    /**
     * Getting the datetime with the date of Congo.
     * @param format String the datetime format.
     * @return String the formatted datetime.
     */
    public static String instantDatetime(DateFormat format) {
        return LocalDateTime.now(congoTime()).format(DateTimeFormatter.ofPattern(format.getIso()));
    }

    public static boolean isValidLocaleDate(String date) {
        boolean isValid;
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DateFormat.DATE_BY_BAR.getIso()));
            isValid = true;
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidLocaleDateTime(String date) {
        boolean isValid;
        try {
            LocalDateTime localDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DateFormat.DATETIME_BY_BAR.getIso()));
            isValid = true;
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidDecimalNumber(String validDoubleStr) {
        boolean isValid;
        try {
            double validDouble = Double.parseDouble(validDoubleStr);
            isValid = true;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidNumber(String validIntStr) {
        boolean isValid;
        try {
            int validInt = Integer.parseInt(validIntStr);
            isValid = true;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Getting the time of Brazzaville.
     * @return ZoneId of Brazzaville.
     */
    private static ZoneId congoTime() {
        return ZoneId.of("Africa/Brazzaville");
    }
}
