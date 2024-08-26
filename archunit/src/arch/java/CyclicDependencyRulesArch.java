import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)

public class CyclicDependencyRulesArch {
	@ArchTest
	public static final ArchRule noCyclesDependencies = slices().matching("..(*)..")
																.should().beFreeOfCycles();
}
