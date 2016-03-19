package test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.Modifier;
import java.util.Set;

public class PubicFinalProcessor  extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations)
        {
            Set<javax.lang.model.element.Modifier> modifiers = typeElement.getModifiers();

            if (!modifiers.contains(Modifier.FINAL)
                    || !modifiers.contains(Modifier.PUBLIC))
            {
                System.out.print("inside");
            }
        }
        return true;
    }
}
