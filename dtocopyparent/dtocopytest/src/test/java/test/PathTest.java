package test;

import lombok.Getter;
@Getter
public class PathTest {
    private static final String FOLDER_SEPERATOR = "/";
    public static final String PACKAGE = "package ";


/*
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
                    String[] splittedTargetName = toCopyFile.getCanonicalPath().split(FOLDER_SEPERATOR);
                    String fileName = splittedTargetName[splittedTargetName.length - 1];
                    String[] shortName = fileName.split("\\.");
                    String newContent = changeJavaFile(toCopyFile,"com.purat.copied", shortName[0]);
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
    private String changeJavaFile(File file, String newPackage, String filename) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(file));
        StringBuilder  stringBuilder = new StringBuilder();
        boolean methodComplete = true;
        try {
            String line = null;
            while( ( line = reader.readLine() ) != null ) {
                line = changePackage(newPackage, line);
                line = removeExtends(line);
                if(line.startsWith("@")) {
                    line = "";
                }

                if(line.contains("class")) {
                    line = "@Getter" + " \n " + line;
                }

                if  (line.endsWith("{") && !(line.contains(filename)) || (line.contains(filename) && line.contains("("))) {
                    methodComplete = false;
                }
                if (line.endsWith("}")) {
                    methodComplete = true;
                }

                if (methodComplete && !(line.endsWith("}")) && !(line.equals(""))) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private String removeExtends(String line) {
        if( line.contains("extends")) {
            int endPos = line.indexOf("extends");
            line = line.substring(0, endPos) + "{";
        }
        return line;
    }

    private String changePackage(String newPackage, String line) {
        if(line.startsWith(PACKAGE)) {
            line = line.replace(line, PACKAGE + newPackage + ";");
        }
        return line;
    }
*/
}
