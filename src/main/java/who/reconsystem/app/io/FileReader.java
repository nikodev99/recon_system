package who.reconsystem.app.io;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileReader {
    private static FileReader instance;
    private List<String> lines;
    private byte[] byteCodes;

    private FileReader(List<String> lines) {
        this.lines = lines;
    }

    public FileReader(byte[] byteCodes) {
        this.byteCodes = byteCodes;
    }

    public static synchronized FileReader getInstance(List<String> lines) {
        if(instance == null) {
            instance = new FileReader(lines);
        }
        return instance;
    }

    public static synchronized FileReader getInstance(byte[] byteCodes) {
        if(instance == null) {
            instance = new FileReader(byteCodes);
        }
        return instance;
    }

    public List<String[]> read() {
        List<String[]> fileContent = new ArrayList<>();
        if(lines instanceof ArrayList) {
            for (String line: lines) {
                fileContent.add(new String[]{ line });
            }
        }else {
            fileContent = convertBytesToUTF8(byteCodes);
        }
        return fileContent;
    }

    private List<String[]> convertBytesToUTF8(byte[] bytes) {
        List<String[]> fileContent = new ArrayList<>();
        byte[][] byte2D = SplitByteArray(bytes);
        for (byte[] byteArray: byte2D) {
            fileContent.add(new String[]{ new String(byteArray, StandardCharsets.ISO_8859_1) });
        }
        return fileContent;
    }

    private byte[][] SplitByteArray(byte[] inputBytes) {
        List<byte[]> resultList = new ArrayList<>();
        List<Byte> currentBytes = new ArrayList<>();

        for (byte b: inputBytes) {
            if (b == 10) {
                byte[] subArray = new byte[currentBytes.size()];
                for (int i = 0; i < currentBytes.size(); i++) {
                    subArray[i] = currentBytes.get(i);
                }
                resultList.add(subArray);
                currentBytes.clear();
            }else {
                currentBytes.add(b);
            }
        }

        if (!currentBytes.isEmpty()) {
            byte[] subArray = new byte[currentBytes.size()];
            for (int i = 0; i < currentBytes.size(); i++) {
                subArray[i] = currentBytes.get(i);
            }
            resultList.add(subArray);
        }

        return resultList.toArray(new byte[0][]);
    }

}
