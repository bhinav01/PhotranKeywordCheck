package org.eclipse.photran.KeywordCheck.tests.illegalkeywords;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.Test;
import keywordcheck.Activator;
import keywordcheck.KeywordCheckAction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.photran.internal.core.lexer.Token;
import org.eclipse.photran.internal.tests.PhotranWorkspaceTestCase;
import org.eclipse.rephraserengine.testing.junit3.GeneralTestSuiteFromMarkers;

@SuppressWarnings("restriction")
public class IllegalVariableName extends GeneralTestSuiteFromMarkers{

	//protected File file = null;
	// protected boolean isFixedForm = false;
	//protected String fileDescription = null;
	private static final String DIR = "illegal-keyword-test-code";
	public static Test suite() throws Throwable
	{
		return new IllegalVariableName();
	}
	public IllegalVariableName() throws Throwable
	{
		super("Constructing variable access marking for",
				PhotranWorkspaceTestCase.MARKER,
				new File(DIR),
				PhotranWorkspaceTestCase.FORTRAN_FILE_FILTER);
		System.out.println("Here 1");
	}


	@Override
	protected Test createTestFor(File fileContainingMarker, int markerOffset,
			String markerText) throws Exception {
		// TODO Auto-generated method stub
		return new IllegalVariableTestCase(fileContainingMarker, markerText) {};
	}

	public static abstract class IllegalVariableTestCase extends PhotranWorkspaceTestCase
	{
		private File javaFile;
		private IFile file;
		private String[] markerText;

		public IllegalVariableTestCase(File file, String markerText) throws Exception{
			super("test");
			this.javaFile = file;
			this.markerText = parseMarker(markerText);
			System.out.println("constructor " + file.getName());
		}

		@Override public void setUp() throws Exception
		{
			super.setUp();
			System.out.println("Set up for test " + javaFile.getName() + Activator.getDefault().getBundle());
			this.file = importFile(Activator.getDefault(), javaFileDirectory(), javaFile.getName());
			System.out.println(this.file.getName() + " " + this.file.exists());
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			System.out.println("Set up complete");
		}

		protected String javaFileDirectory()
		{
			String y = DIR;//+File.separator+javaFile.getParentFile().getName();
			System.out.println("Java file directory - " + y);
			return DIR+File.separator+javaFile.getParentFile().getName();
			//return DIR;//+File.separator+javaFile.getParentFile().getName();
		}

		public void test() throws Exception
		{
			KeywordCheckAction kwa = new KeywordCheckAction();
			HashSet<Token> warningInfo = kwa.tokensOfIllegalIdentifiers(file);

			for (int i = 0; i < markerText.length; i = i + 2) {
				int line = Integer.parseInt(markerText[i]);
				int col = Integer.parseInt(markerText[i+1]);
				Iterator<Token> iter = warningInfo.iterator();    
				boolean correct=false;
				while(iter.hasNext()) {
					Token thisToken = (Token) iter.next();
					if(thisToken.getLine()==line && thisToken.getCol()==col) {
						correct = true;
					}
				}
				assertTrue(correct);
			}
		}
		
		
	}
}

