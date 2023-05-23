package who.reconsystem.app.root.config;

import who.reconsystem.app.models.Operator;
import who.reconsystem.app.models.connect.DbConnect;

import java.security.CodeSource;
import java.security.ProtectionDomain;
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
}
