package core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import core.enums.JTableMode;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JTableAnno {

	JTableMode[] modes();
	
}
