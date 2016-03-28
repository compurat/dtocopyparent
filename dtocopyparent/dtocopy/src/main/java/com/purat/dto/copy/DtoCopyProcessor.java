package com.purat.dto.copy;

import org.apache.commons.io.IOUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.*;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.purat.dto.copy.annotations.CopyDto")
public class DtoCopyProcessor extends AbstractProcessor {
    private static final String FOLDER_SEPERATOR = "/";
    public static final String PACKAGE = "package ";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
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
                   IOUtils.closeQuietly(fileOutputStream);
                }
            }
        }
        return true;
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

                if  (line.endsWith("{") && !(line.contains(filename)) || (line.contains(filename) && line.contains("("))) {
                    methodComplete = false;
                }
                if (line.endsWith("}")) {
                    methodComplete = true;
                }

                if (methodComplete && !(line.endsWith("}")) && !(line.startsWith("@")) && !(line.equals(""))) {
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
}
