package test;

import org.junit.Test;

import java.io.*;

public class PathTest {
    private static final String FOLDER_SEPERATOR = "/";
    private static final String PACKAGE = "package ";
    public static final String START = "{";
    public static final String END = "}";

    private StringBuilder  stringBuilder = new StringBuilder();
    int getterOrSetterContent = 0;

    @Test
    public void test() throws IOException {
        String targetFolderName = "src/main/java/" + "com.purat.copied".replaceAll("\\.", "/") + FOLDER_SEPERATOR ;
        File targetFolder = new File(targetFolderName);
        targetFolder.mkdirs();


        String sourceFolderName = "src/main/java/" + "com.purat".replaceAll("\\.", "/");
        File sourceFolder = new File(sourceFolderName);
        FileOutputStream fileOutputStream = null;
        for (File toCopyFile : sourceFolder.listFiles()) {
            if (!(toCopyFile.isDirectory())) {
                try {
                    String newContent = changeJavaFile(toCopyFile,"com.purat.copied");
                    String[] splittedTargetName = toCopyFile.getCanonicalPath().split(FOLDER_SEPERATOR);
                    String fileName = splittedTargetName[splittedTargetName.length - 1];
                    File destFile = new File(targetFolderName + fileName);
                    fileOutputStream = new FileOutputStream(destFile);
                    fileOutputStream.write(newContent.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    fileOutputStream.close();
                }
            }
        }

    }
    private String changeJavaFile(File file, String newPackage) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(file));
        String         line = null;
        String         ls = System.getProperty("line.separator");

        try {
            while( ( line = reader.readLine() ) != null) {
                if(!(line.startsWith("@") && !(line.contains(START)) || !(line.contains(END)))) {
                    addingLineToFileContent(newPackage, line);
                } else if (line.contains(START) || line.contains(END)) {
                    removeGetterAndSetters(line);
                }
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private void addingLineToFileContent(String newPackage, String line) {
        if(line.startsWith(PACKAGE) ) {
            line = line.replace(line, PACKAGE + newPackage + ";");
            stringBuilder.append(line);
        } else {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
    }

    private boolean removeGetterAndSetters(String line) {
        boolean setterGetterComplete = false;
        if (line.contains(START)) {
            getterOrSetterContent += 1;
        } else if (line.contains(END)) {
            getterOrSetterContent -= 1;
        }

        if (getterOrSetterContent == 0) {
            setterGetterComplete = true;
        }

        return setterGetterComplete;
    }
}
