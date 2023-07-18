package who.reconsystem.app.root.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionsTest {

    @Test
    public void testGetLocalePath() {
        String localPath0 = Functions.getLocalePath("reconsystem.db");
        boolean exists = (new File(localPath0)).exists();

        assertEquals(localPath0, "/C:/Users/niamakoumban/IdeaProjects/ImprestReconSystem/build/classes/java/main/reconsystem.db");
        assertTrue(exists);
    }

    @Test
    public void testTrimArray() {
        List<String> arrayToTrim0 = Arrays.asList("a  ", "   b   ", "c");
        List<String> arrayToTrim1 = Collections.emptyList();

        List<String> trimmed0 = Functions.trimArray(arrayToTrim0);
        List<String> trimmed1 = Functions.trimArray(arrayToTrim1);

        assertEquals(trimmed0, Arrays.asList("a", "b", "c"));
        assertEquals(trimmed1, Collections.emptyList());
    }

}
