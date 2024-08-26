import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static common.CommonConditions.HAVE_EQUALS_AND_HASH_CODE;
import static common.CommonRules.fieldsShouldHaveGetterRule;
import static common.CommonRules.methodsShouldBePublicRule;
import static common.CommonRules.publicAndFinalFieldsAreNotAllowedRule;
import static common.CommonRules.springAnnotationsClassesAreNotAllowedRule;
import static common.CommonRules.staticMethodsAreNotAllowedRule;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.thirdparty.com.google.common.collect.Maps;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)

public class ModelRulesArch {
	public static final String MODEL_PACKAGE = DEFAULT_PACKAGE + ".entities";
	@ArchTest
	static final ArchRule classesShouldOverrideEqualsAndHashCode = classes().that()
																		   .resideInAnyPackage(MODEL_PACKAGE)
																		   .and().areNotMemberClasses()
																		   .should(HAVE_EQUALS_AND_HASH_CODE)
																		   .because(
																				   "Model classes should override equals and hashCode methods");
	@ArchTest
	static final ArchRule springAnnotationsAreNotAllowed = springAnnotationsClassesAreNotAllowedRule(MODEL_PACKAGE);

	//Fields
	@ArchTest
	static final ArchRule fieldsShouldHaveGetter = fieldsShouldHaveGetterRule(
			Maps.newHashMap(), MODEL_PACKAGE);

	@ArchTest
	static final ArchRule publicAndFinalFieldsAreNotAllowed = publicAndFinalFieldsAreNotAllowedRule(MODEL_PACKAGE);

	//Methods
	@ArchTest
	static final ArchRule methodsShouldBePublic = methodsShouldBePublicRule(MODEL_PACKAGE);

	@ArchTest
	static final ArchRule staticMethodsAreNotAllowed = staticMethodsAreNotAllowedRule(MODEL_PACKAGE);

}
