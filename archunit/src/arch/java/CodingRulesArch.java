import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import common.ConfigurationArchTest;

@AnalyzeClasses(packages = ConfigurationArchTest.DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class CodingRulesArch {

	//Classes
	@ArchTest
	static final ArchRule noClassesShouldUseJodatime = NO_CLASSES_SHOULD_USE_JODATIME
															   .because("Use java.time objects instead");

	@ArchTest
	static final ArchRule noGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
														.because(
																"Throw expected exception or any child of this instead");

	//Fields
	@ArchTest
	static final ArchRule loggersShouldBePrivateStaticAndFinal = fields().that().haveRawType(Logger.class)
																		 .should().bePrivate()
																		 .andShould().beStatic()
																		 .andShould().beFinal()
																		 .andShould().haveName("LOG")
																		 .because(
																				 "Logger variables should be private, static and final, and it should be named as LOGGER");

	@ArchTest
	static final ArchRule finalStaticVariablesInUppercase = fields().that().areStatic().and().areFinal()
																	.and().doNotHaveName("serialVersionUID")
																	.and().doNotHaveModifier(JavaModifier.SYNTHETIC)
																	.should().haveNameMatching(".*^[A-Z].*")
																	.because(
																			"Variables with static and final modifiers should be named in uppercase");

	//Methods
	@ArchTest
	static final ArchRule beanMethodsShouldBePublic =
			ArchRuleDefinition.methods().that().areAnnotatedWith(Bean.class).should().bePublic()
					.because(
							"@Bean annotation does not work in non public methods");
}
