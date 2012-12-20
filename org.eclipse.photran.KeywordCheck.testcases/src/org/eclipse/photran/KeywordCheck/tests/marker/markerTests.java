package org.eclipse.photran.KeywordCheck.tests.marker;

import java.io.File;

import junit.framework.Test;
import keywordcheck.Activator;
import keywordcheck.KeywordCheckAction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.photran.core.IFortranAST;
import org.eclipse.photran.internal.core.lexer.Token;
import org.eclipse.photran.internal.core.vpg.PhotranVPG;
import org.eclipse.photran.internal.tests.PhotranWorkspaceTestCase;
import org.eclipse.rephraserengine.testing.junit3.GeneralTestSuiteFromMarkers;

@SuppressWarnings("restriction")
public class markerTests extends GeneralTestSuiteFromMarkers {

	private static final String DIR = "illegal-keyword-test-code";

	public static Test suite() throws Throwable {
		return new markerTests();
	}

	public markerTests() throws Throwable {
		super("Constructing variable access marking for", PhotranWorkspaceTestCase.MARKER, new File(DIR),
				PhotranWorkspaceTestCase.FORTRAN_FILE_FILTER);

	}

	@Override
	protected Test createTestFor(File fileContainingMarker, int markerOffset, String markerText) throws Exception {
		return new markerTestsTestCase(fileContainingMarker, markerText) {
		};
	}

	public static abstract class markerTestsTestCase extends PhotranWorkspaceTestCase {

		private File javaFile;
		private IFile file;
		static IMarker[] problems;
		static int depth = IResource.DEPTH_INFINITE;
		static KeywordCheckAction KeyWordCheckObj = new KeywordCheckAction();

		public markerTestsTestCase(File file, String markerText) throws Exception {
			super("test");
			this.javaFile = file;
		}

		@Override
		public void setUp() throws Exception {
			super.setUp();
			this.file = importFile(Activator.getDefault(), javaFileDirectory(), javaFile.getName());
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			// Deleting all the pre-existing markers
			KeyWordCheckObj.deleteMarkers(file);

		}

		protected String javaFileDirectory() {
			return DIR + File.separator + javaFile.getParentFile().getName();
		}

		public void test() {
			try {
				testSingleMarker();
				testNullMarker();
				testManyMarker();
			} catch (CoreException e) {
				System.out.println("Core Exception in Marker Tests");
				e.printStackTrace();
			}
		}

		// Generating just one marker
		public void testSingleMarker() throws CoreException {

			int linenum = 2;// Randomly choosing a line from the active file

			
			IFortranAST tempAST = PhotranVPG.getInstance().acquireTransientAST(file);

			Token token = tempAST.findFirstTokenOnLine(linenum);

			KeyWordCheckObj.generateMarker(token); 

			problems = file.findMarkers(IMarker.PROBLEM, true, depth);

			// Only 1 marker should be generated
			assertEquals(1, problems.length);

			// Checking marker's attributes
			assertEquals(token.getLine(), problems[0].getAttribute(IMarker.LINE_NUMBER));
			assertTrue(problems[0].getAttribute(IMarker.MESSAGE).toString().contains(token.getText()));
			assertEquals(IMarker.SEVERITY_WARNING, problems[0].getAttribute(IMarker.SEVERITY));
			KeyWordCheckObj.deleteMarkers(file);

		}

		public void testNullMarker() throws CoreException {

			// Passing a null token
			KeyWordCheckObj.generateMarker(null);

			problems = file.findMarkers(IMarker.PROBLEM, true, depth);
			// No marker should be generated
			assertEquals(0, problems.length);

		}

		public void testManyMarker() throws CoreException {

			int linenum = 2;// Randomly choosing a line from the active file

			// Generating multiple markers in the same line
			IFortranAST tempAST = PhotranVPG.getInstance().acquireTransientAST(file);

			Token token1 = tempAST.findFirstTokenOnLine(linenum);
			Token token2 = tempAST.findLastTokenOnLine(linenum);

			KeyWordCheckObj.generateMarker(token1); 
			KeyWordCheckObj.generateMarker(token2); 
			problems = file.findMarkers(IMarker.PROBLEM, true, depth);

			// 2 markers should be generated
			assertEquals(2, problems.length);

			// Checking marker's attributes
			assertEquals(linenum, problems[0].getAttribute(IMarker.LINE_NUMBER));
			assertEquals(linenum, problems[1].getAttribute(IMarker.LINE_NUMBER));

			// The marker in problem[0] can be marker for t1 or t2
			assertTrue((problems[0].getAttribute(IMarker.MESSAGE).toString().contains(token1.getText()) || problems[0]
					.getAttribute(IMarker.MESSAGE).toString().contains(token2.getText())));

			// The marker in problem[1] can be marker for t1 or t2
			assertTrue((problems[1].getAttribute(IMarker.MESSAGE).toString().contains(token1.getText()) || problems[1]
					.getAttribute(IMarker.MESSAGE).toString().contains(token2.getText())));
			assertEquals(IMarker.SEVERITY_WARNING, problems[0].getAttribute(IMarker.SEVERITY));
			assertEquals(IMarker.SEVERITY_WARNING, problems[1].getAttribute(IMarker.SEVERITY));
			KeyWordCheckObj.deleteMarkers(file);
		}

	}
}
