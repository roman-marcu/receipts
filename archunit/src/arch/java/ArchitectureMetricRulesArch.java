import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import org.junit.jupiter.api.Assertions;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.ComponentDependencyMetrics;
import com.tngtech.archunit.library.metrics.LakosMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;
import com.tngtech.archunit.library.metrics.VisibilityMetrics;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureMetricRulesArch {

	/***
	 * Cumulative Dependency Metrics by John Lakos
	 */
	@ArchTest
	private void checkLakosMetrics(JavaClasses classes) {
		final LakosMetrics lakosMetrics = ArchitectureMetrics.lakosMetrics(
				MetricsComponents.fromPackages(classes.getDefaultPackage().getSubpackages()));
		Assertions.assertEquals(30, lakosMetrics.getCumulativeComponentDependency());
		Assertions.assertEquals(3.75, lakosMetrics.getAverageComponentDependency());
	}

	/**
	 * Component Dependency Metrics by Robert C. Martin
	 */
	@ArchTest
	private void checkComponentDependencyMetrics(JavaClasses classes) {
		final ComponentDependencyMetrics metrics = ArchitectureMetrics.componentDependencyMetrics(
				MetricsComponents.fromPackages(classes.getDefaultPackage().getSubpackages()));
		Assertions.assertEquals(0.2, metrics.getAbstractness(DEFAULT_PACKAGE));
		Assertions.assertEquals(1.0, metrics.getInstability(DEFAULT_PACKAGE));
	}

	/**
	 * Visibility Metrics by Herbert Dowalil
	 */
	@ArchTest
	private void checkVisibilityMetrics(JavaClasses classes) {
		final VisibilityMetrics metrics = ArchitectureMetrics.visibilityMetrics(
				MetricsComponents.fromPackages(classes.getDefaultPackage().getSubpackages()));
		Assertions.assertEquals(0.7142857142857143, metrics.getRelativeVisibility(DEFAULT_PACKAGE));
		Assertions.assertEquals(0.9495495495495495, metrics.getGlobalRelativeVisibility());
	}
}
