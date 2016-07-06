package code_coach.engine;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

import cc.rules.api.RuleResult;
import cc.rules.api.RuleType;
import cc.rules.api.ruleargs.ForStatmentArgument;
import cc.rules.api.ruleargs.IfStatementArgument;
import cc.rules.api.ruleargs.MethodDeclarationArgument;
import cc.rules.api.ruleargs.RuleArgument;
import cc.rules.api.ruleargs.VariableDeclarationArgument;
import cc.rules.api.ruleargs.WhileStatementArgument;

public class CodeCoach {

	public void execute() {

		System.out.println("Welcome to CodeCoach!");
		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects, sending each one to printProjectInfo method
		for (IProject project : projects) {
			try {
				getProjectInfo(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	// Prints the project currently being worked on. Not necessary
	private void getProjectInfo(IProject project) throws CoreException, JavaModelException {
		// check if we have a Java project
		if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			IJavaProject javaProject = JavaCore.create(project);
			getPackageInfos(javaProject);
		}
	}

	// Prints the package information for the current project. Not necessary
	private void getPackageInfos(IJavaProject javaProject) throws JavaModelException {
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			// Package fragments include all packages in the
			// classpath
			// We will only look at the package from the source
			// folder
			// K_BINARY would include also included JARS, e.g.
			// rt.jar
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				getICompilationUnitInfo(mypackage);

			}

		}
	}

	// Prints the .java for the current package. Not necessary
	private void getICompilationUnitInfo(IPackageFragment mypackage) throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			parse(unit);
		}
	}

	// Prints general information about the file we're working on
	private void parse(ICompilationUnit unit) throws JavaModelException {
		IResource resource = unit.getResource();
		try {
			resource.deleteMarkers("Code_Coach.marker", true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			private int parentStart;

			public boolean visit(VariableDeclarationFragment node) {
				VariableDeclarationArgument arg = new VariableDeclarationArgument(node);
				runRules(RuleType.VariableDeclaration, arg, false, 0);
				return false;
			}

			public boolean visit(MethodDeclaration node) {
				MethodDeclarationArgument arg = new MethodDeclarationArgument(node);
				runRules(RuleType.MethodDeclaration, arg, false, 0);
				parentStart = node.getStartPosition();
				ASTParser methodParser = ASTParser.newParser(AST.JLS8);
				methodParser.setSource(node.getBody().toString().toCharArray());
				methodParser.setKind(ASTParser.K_STATEMENTS);
				Block block = (Block) methodParser.createAST(null);
				block.accept(this);
				return false;
			}

			public boolean visit(IfStatement node) {
				IfStatementArgument arg = new IfStatementArgument(node);
				runRules(RuleType.IfStatement, arg, true, parentStart);
				return false;
			}

			public boolean visit(WhileStatement node) {
				WhileStatementArgument arg = new WhileStatementArgument(node);
				runRules(RuleType.WhileStatement, arg, true, parentStart);
				return false;
			}

			public boolean visit(ForStatement node) {
				ForStatmentArgument arg = new ForStatmentArgument(node);
				runRules(RuleType.ForStatement, arg, true, parentStart);
				return false;
			}

			private void runRules(RuleType type, RuleArgument<? extends ASTNode> node, boolean isChild,
					int parentStart) {
				try {
					List<RuleResult> results = RuleRunner.getInstance().runRulesByType(type, node);
					RuleResult.printResults(results, System.out);
					setMarkers(results, node, isChild, parentStart);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void setMarkers(List<RuleResult> results, RuleArgument<? extends ASTNode> node, boolean isChild,
					int parentStart) throws CoreException {
				for (RuleResult rr : results) {
					if (!rr.passed()) {

						int startPos = 0;
						int endPos = 0;
						if (isChild) {
							startPos = parentStart;
							endPos = rr.getEndPosition() == 0 ? startPos + node.getRawArg().getLength()
									: rr.getEndPosition();

						} else {
							startPos = rr.getStartPosition() == 0 ? node.getRawArg().getStartPosition()
									: rr.getStartPosition();
							endPos = rr.getEndPosition() == 0 ? startPos + node.getRawArg().getLength()
									: rr.getEndPosition();
						}
						makeMarker(resource, rr.getRule().getName() + ".  \n" + rr.getRule().getHint(), startPos,
								endPos);
					}
				}
			}
		});
	}

	private void makeMarker(IResource resource, String msg, int startPos, int endPos) throws CoreException {
		IMarker marker = (IMarker) resource.createMarker("Code_Coach.marker");
		marker.setAttribute(IMarker.CHAR_START, startPos);
		marker.setAttribute(IMarker.CHAR_END, endPos);
		marker.setAttribute(IMarker.MESSAGE, msg);
	}
}
