import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static common.CommonRules.beanMethodsAreNotAllowedRule;
import static common.CommonRules.componentAnnotationIsNotAllowedRule;
import static common.CommonRules.fieldsShouldNotBePublic;
import static common.CommonRules.privateMethodsAreNotAllowedRule;
import static common.CommonRules.publicConstructorsRule;
import static common.CommonRules.staticMethodsAreNotAllowedRule;
import static common.ConfigurationArchTest.ANNOTATED_EXPLANATION;
import static common.ConfigurationArchTest.DEFAULT_PACKAGE;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = DEFAULT_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)

public class ControllerRulesArch {
	public static final String CONTROLLER_PACKAGE = DEFAULT_PACKAGE + ".controllers";
	protected static final String CONTROLLER_SUFFIX = "Controller";
	// Classes
	@ArchTest
	static final ArchRule componentAnnotationIsNotAllowedInController =
			componentAnnotationIsNotAllowedRule(CONTROLLER_PACKAGE);
	// Fields
	@ArchTest
	static final ArchRule controllerFieldsShouldNotBePublic = fieldsShouldNotBePublic(CONTROLLER_PACKAGE);
	// Constructors
	@ArchTest
	static final ArchRule controllerConstructorsShouldNotBePrivate = publicConstructorsRule(CONTROLLER_PACKAGE);
	// Methods
	@ArchTest
	static final ArchRule beanMethodsAreNotAllowedInController = beanMethodsAreNotAllowedRule(CONTROLLER_PACKAGE);
	@ArchTest
	static final ArchRule privateMethodsAreNotAllowedInController = privateMethodsAreNotAllowedRule(CONTROLLER_PACKAGE);
	@ArchTest
	static final ArchRule staticMethodsAreNotAllowedInController = staticMethodsAreNotAllowedRule(CONTROLLER_PACKAGE);
	@ArchTest
	static final ArchRule controllerMethodsShouldReturnResponseEntity = ArchRuleDefinition.methods()
																				.that().arePublic().and()
																				.areDeclaredInClassesThat()
																				.resideInAPackage(CONTROLLER_PACKAGE)
																				.should()
																				.haveRawReturnType(ResponseEntity.class)
																				.because(
																						"Controller endpoints should return a ResponseEntity object");
	@ArchTest
	static final ArchRule methodsShouldBeAnnotatedWithValidRestAnnotations =
			ArchRuleDefinition.methods()
					.that().arePublic().and()
					.areDeclaredInClassesThat()
					.resideInAPackage(CONTROLLER_PACKAGE)
					.should().beAnnotatedWith(PostMapping.class)
					.orShould().beAnnotatedWith(GetMapping.class)
					.orShould().beAnnotatedWith(DeleteMapping.class)
					.orShould().beAnnotatedWith(PatchMapping.class)
					.orShould().beAnnotatedWith(PutMapping.class)
					.because(
							"Controller methods should be annotated only with valid options of REST (POST, PUT, PATCH, GET, and DELETE)");
	@ArchTest
	static final ArchRule classesShouldBeAnnotatedWithRestController =
			classes()
					.that()
					.resideInAPackage(CONTROLLER_PACKAGE)
					.should()
					.beAnnotatedWith(RestController.class)
					.andShould()
					.notBeAnnotatedWith(Controller.class)
					.because(String.format(
							ANNOTATED_EXPLANATION,
							CONTROLLER_SUFFIX,
							"@RestController")
									 + ", and not with @Controller");

}
