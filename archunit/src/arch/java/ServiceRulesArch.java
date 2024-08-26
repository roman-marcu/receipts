import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static common.CommonRules.beanMethodsAreNotAllowedRule;
import static common.CommonRules.componentAnnotationIsNotAllowedRule;
import static common.CommonRules.fieldsShouldNotBePublic;
import static common.CommonRules.privateMethodsAreNotAllowedRule;
import static common.CommonRules.publicConstructorsRule;
import static common.CommonRules.staticMethodsAreNotAllowedRule;
import static common.ConfigurationArchTest.ANNOTATED_EXPLANATION;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import org.springframework.stereotype.Service;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)

public class ServiceRulesArch {
	public static final String SERVICE_PACKAGE = DEFAULT_PACKAGE + ".services";
	protected static final String SERVICE_SUFFIX = "Service";
	// Classes
	@ArchTest
	static final ArchRule componentAnnotationIsNotAllowedInService = componentAnnotationIsNotAllowedRule(
			SERVICE_PACKAGE);
	// Fields
	@ArchTest
	static final ArchRule serviceFieldsShouldNotBePublic = fieldsShouldNotBePublic(SERVICE_PACKAGE);
	// Constructors
	@ArchTest
	static final ArchRule serviceConstructorsShouldNotBePrivate = publicConstructorsRule(SERVICE_PACKAGE);
	// Methods
	@ArchTest
	static final ArchRule beanMethodsAreNotAllowedInService = beanMethodsAreNotAllowedRule(SERVICE_PACKAGE);
	@ArchTest
	static final ArchRule privateMethodsAreNotAllowedInService = privateMethodsAreNotAllowedRule(SERVICE_PACKAGE);
	@ArchTest
	static final ArchRule staticMethodsAreNotAllowedInService = staticMethodsAreNotAllowedRule(SERVICE_PACKAGE);
	@ArchTest
	static final ArchRule serviceClassesShouldBeAnnotatedWithService = classes()
																			   .that().resideInAPackage(SERVICE_PACKAGE)
																			   .should()
																			   .beAnnotatedWith(Service.class)
																			   .because(String.format(
																					   ANNOTATED_EXPLANATION,
																					   SERVICE_SUFFIX, "@Service"));

}
