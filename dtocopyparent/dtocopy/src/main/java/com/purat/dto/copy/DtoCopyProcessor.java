package com.purat.dto.copy;

import com.purat.dto.copy.annotations.CopyDto;
import org.apache.commons.io.IOUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.*;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.purat.dto.copy.annotations.CopyDto")
public class DtoCopyProcessor extends AbstractProcessor {
    private static final String FOLDER_SEPERATOR = "/";
    private static final String PACKAGE = "package ";
    private static final String SOURCE_FOLDER = "src/main/java/";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String copyFrom = null;
        String copyTo = null;
        String sourceFolderName = null;
        for (TypeElement typeElement: annotations) {
            //Get the members that are annotated with Option
            for (Element element: roundEnv.getElementsAnnotatedWith(typeElement)) {
                //Process the members. processAnnotation is our own method
                CopyDto copyDto = element.getAnnotation(CopyDto.class);
                copyFrom = copyDto.copyFromPackage();
                copyTo = copyDto.copyToPackage();
                sourceFolderName = copyDto.sourceFolder();

            }
            copyDto(copyFrom, copyTo, sourceFolderName);
        }
        return true;
    }

    private void copyDto(String copyFrom, String copyTo, String sourceFolderNaming) {
        String sources = null;
        if (!(sourceFolderNaming.isEmpty())) {
            sources = sourceFolderNaming;
        } else {
            sources = SOURCE_FOLDER;
        }

        String targetFolderName = sources + copyTo.replaceAll("\\.", FOLDER_SEPERATOR) + FOLDER_SEPERATOR ;
        File targetFolder = new File(targetFolderName);
        targetFolder.mkdirs();
        String sourceFolderName = sources + copyFrom.replaceAll("\\.", FOLDER_SEPERATOR);
        File sourceFolder = new File(sourceFolderName);
        FileOutputStream fileOutputStream = null;
        for (File toCopyFile : sourceFolder.listFiles()) {
            if (!(toCopyFile.isDirectory())) {
                try {
                    String[] splittedTargetName = toCopyFile.getCanonicalPath().split(FOLDER_SEPERATOR);
                    String fileName = splittedTargetName[splittedTargetName.length - 1];
                    String[] shortName = fileName.split("\\.");
                    String newContent = changeJavaFile(toCopyFile,copyTo, shortName[0]);
                    File destFile = new File(targetFolderName + fileName);
                    if(!(destFile.exists())) {
                        fileOutputStream = new FileOutputStream(destFile);
                        fileOutputStream.write(newContent.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                   IOUtils.closeQuietly(fileOutputStream);
                }
            }
        }
    }

    private String changeJavaFile(File file, String newPackage, String filename) throws IOException {
        ;
        StringBuilder  stringBuilder = new StringBuilder();
        boolean methodComplete = true;
        try (BufferedReader reader = new BufferedReader( new FileReader(file))) {
            String line = null;
            while( ( line = reader.readLine() ) != null ) {
                line = changePackage(newPackage, line);
                line = removeExtends(line);
                line = removeAnnotations(line);
                line = addGetters(line);
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
        }
    }

    private String addGetters(String line) {
        if(line.contains("class")) {
            line = "@Getter" + " \n " + line;
        }
        return line;
    }

    private String removeAnnotations(String line) {
        if(line.startsWith("@")) {
            line = "";
        }
        return line;
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
            line = line + "\n\nimport lombok.Getter;";
        }
        return line;
    }
}
