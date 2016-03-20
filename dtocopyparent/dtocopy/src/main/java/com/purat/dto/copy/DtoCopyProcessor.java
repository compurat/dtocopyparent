package com.purat.dto.copy;

import com.purat.dto.copy.annotations.CopyDto;
import org.apache.commons.io.FileUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.purat.dto.copy.annotations.CopyDto")
public class DtoCopyProcessor extends AbstractProcessor {

    public static final String FOLDER_SEPERATOR = "/";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String folderName = null;
        for (Element element : roundEnv.getElementsAnnotatedWith(CopyDto.class)) {
                folderName = "src/main/java/" + element.getAnnotation(CopyDto.class).copyToPackage().replaceAll("\\.", "/");
                File folder = new File(folderName);
                folder.mkdirs();
                for (File toCopyFile : folder.listFiles()) {
                    String[] seperatedFolders = toCopyFile.getAbsolutePath().split(FOLDER_SEPERATOR);
                    String fileOnlyName = seperatedFolders[seperatedFolders.length];
                    try {
                        FileUtils.copyFile(toCopyFile, new File(folderName + fileOnlyName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }

        return true;
    }
}
