package dev.romanmarcu.common;

import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.PropertyPlaceholderHelper;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.MethodMatcher;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;
import org.openrewrite.java.tree.TextComment;
import org.openrewrite.jgit.annotations.NonNull;
import org.openrewrite.jgit.annotations.Nullable;
import org.openrewrite.marker.Markers;

public class AddLicenseHeader extends Recipe {
	/**
	 * A method pattern that is used to find matching method declarations/invocations.
	 * See {@link  MethodMatcher} for details on the expression's syntax.
	 */
	@Option(displayName = "License text",
			description = "The license header text without the block comment. May contain ${CURRENT_YEAR} property.",
			example = "Copyright ${CURRENT_YEAR} Your Name or Company ...")
	@NonNull
	String licenseText;

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		final AddLicenseHeader that = (AddLicenseHeader) o;
		return Objects.equals(licenseText, that.licenseText);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), licenseText);
	}

	public String getLicenseText() {
		return licenseText;
	}

	public AddLicenseHeader setLicenseText(final String licenseText) {
		this.licenseText = licenseText;
		return this;
	}

	@Override
	public String getDisplayName() {
		return "Add license header";
	}

	@Override
	public String getDescription() {
		return "Adds license headers to Java source files when missing. Does not override existing license headers.";
	}

	@Override
	public TreeVisitor<?, ExecutionContext> getVisitor() {
		return new JavaIsoVisitor<ExecutionContext>() {
			@Override
			public J visit(@Nullable Tree tree, ExecutionContext ctx) {
				if (tree instanceof JavaSourceFile) {
					JavaSourceFile cu = (JavaSourceFile) Objects.requireNonNull(tree);
					if (cu.getComments().isEmpty()) {
						PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}",
								null);
						String formattedLicenseText =
								"\n * " + propertyPlaceholderHelper.replacePlaceholders(licenseText,
										k -> {
											if ("CURRENT_YEAR".equals(k)) {
												return Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
											}
											return System.getProperty(k);
										}).replace("\n", "\n * ") + "\n ";

						cu = cu.withComments(Collections.singletonList(
								new TextComment(true, formattedLicenseText, "\n", Markers.EMPTY)
						));
					}
					return super.visit(cu, ctx);
				}
				return super.visit(tree, ctx);
			}

			@Override
			public J.Import visitImport(J.Import _import, ExecutionContext ctx) {
				// short circuit everything else
				return _import;
			}

			@Override
			public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl, ExecutionContext ctx) {
				// short circuit everything else
				return classDecl;
			}
		};
	}
}
