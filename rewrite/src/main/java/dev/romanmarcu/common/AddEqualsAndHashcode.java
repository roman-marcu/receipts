package dev.romanmarcu.common;

import java.util.Objects;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.lang.Nullable;

public class AddEqualsAndHashcode extends Recipe {
	@Option(displayName = "Package name",
			description = "The package name to scan.",
			example = "com.yourorg.foo")
	@Nullable
	String packageName;

	@JsonCreator
	public AddEqualsAndHashcode(@Nullable @JsonProperty("fullyQualifiedClassName") String packageName) {
		this.packageName = packageName;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		final AddEqualsAndHashcode that = (AddEqualsAndHashcode) o;
		return Objects.equals(packageName, that.packageName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), packageName);
	}

	@Override
	public String getDisplayName() {
		return "Add Equals and hashcode";
	}

	@Override
	public String getDescription() {
		return "Adds equals and hashcode to Java source files when missing. Does not add if at least 1 method exist.";
	}

	@Override
	public TreeVisitor<?, ExecutionContext> getVisitor() {
		return new AddEqualsAndHashcodeVisitor();
	}

	private static class AddEqualsAndHashcodeVisitor extends JavaVisitor<ExecutionContext> {
		private static final JavaType TYPE_OBJECT = JavaType.buildType("java.lang.Object");

		@Override
		public J visitMethodDeclaration(final J.MethodDeclaration method, final ExecutionContext executionContext) {
			return super.visitMethodDeclaration(method, executionContext);
		}
	}
}