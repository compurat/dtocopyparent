package com.purat.dto.copy;


import com.purat.dto.copy.annotations.CopyDto;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DtoCopyProcessorTest {

    public static final String FOLDER_SEPERATOR = "/";

    @Test
    public void testProcess() {
        RoundEnvironment roundEnvironment = Mockito.mock(RoundEnvironment.class);
        TypeElement typeElement = Mockito.mock(TypeElement.class);
        Set elements = new HashSet<>();
        elements.add(typeElement);
        Mockito.when(roundEnvironment.getElementsAnnotatedWith(Mockito.any(TypeElement.class))).thenReturn(elements);
        CopyDto copyDto = Mockito.mock(CopyDto.class);
        Mockito.when(typeElement.getAnnotation(CopyDto.class)).thenReturn(copyDto);
        Mockito.when(copyDto.copyFromPackage()).thenReturn("com.purat");
        Mockito.when(copyDto.copyToPackage()).thenReturn("com.purat.copied");
        Mockito.when(copyDto.sourceFolder()).thenReturn("src/test/java/");
        DtoCopyProcessor dtoCopyProcessor = new DtoCopyProcessor();
        dtoCopyProcessor.process(createAnnotations(), roundEnvironment);
        File copiedFile = new File("src/test/java/com/purat/copied/TestBean.java");
        Assert.assertTrue(copiedFile.exists());
        copiedFile.delete();
        copiedFile.getParentFile().delete();

    }


    private Set<TypeElement> createAnnotations() {
        Set<TypeElement> annotations = new HashSet<>();
        TypeElement element = Mockito.mock(TypeElement.class);
        annotations.add(element);
        return annotations;
    }


}