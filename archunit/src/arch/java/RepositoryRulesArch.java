import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static common.CommonRules.interfacesAreOnlyAllowedRule;
import static common.ConfigurationArchTest.ANNOTATED_EXPLANATION;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import org.springframework.stereotype.Repository;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryRulesArch {
	public static final String REPOSITORY_PACKAGE = DEFAULT_PACKAGE + ".repositories";
	protected static final String REPOSITORY_SUFFIX = "Repository";
	@ArchTest
	static final ArchRule classesShouldBeInterfaces = interfacesAreOnlyAllowedRule(REPOSITORY_PACKAGE);
	// Classes
	@ArchTest
	static final ArchRule repoClassesShouldBeAnnotatedWithRepository = classes()
																			   .that()
																			   .resideInAPackage(REPOSITORY_PACKAGE)
																			   .should()
																			   .beAnnotatedWith(Repository.class)
																			   .because(String.format(
																					   ANNOTATED_EXPLANATION,
																					   REPOSITORY_SUFFIX,
																					   "@Repository"));

}
