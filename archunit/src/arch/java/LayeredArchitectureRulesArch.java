import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class LayeredArchitectureRulesArch {

	private static final String CONTROLLER = "Controller";
	private static final String MODEL = "Model";
	private static final String REPOSITORY = "Repository";
	;
	private static final String SERVICE = "Service";

	@ArchTest
	static final ArchRule layeredArchitectureRule =
			layeredArchitecture().consideringOnlyDependenciesInLayers()
					.layer(CONTROLLER).definedBy(
							ControllerRulesArch.CONTROLLER_PACKAGE)
					.layer(MODEL).definedBy(ModelRulesArch.MODEL_PACKAGE)
					.layer(REPOSITORY).definedBy(RepositoryRulesArch.REPOSITORY_PACKAGE)
					.layer(SERVICE).definedBy(ServiceRulesArch.SERVICE_PACKAGE)

					.whereLayer(CONTROLLER).mayNotBeAccessedByAnyLayer()
					.whereLayer(MODEL)
					.mayOnlyBeAccessedByLayers(REPOSITORY, SERVICE)
					.whereLayer(REPOSITORY).mayOnlyBeAccessedByLayers(SERVICE)
					.whereLayer(SERVICE)
					.mayOnlyBeAccessedByLayers(CONTROLLER, SERVICE);
}
