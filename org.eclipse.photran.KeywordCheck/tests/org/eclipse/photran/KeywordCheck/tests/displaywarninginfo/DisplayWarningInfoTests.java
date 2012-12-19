package org.eclipse.photran.KeywordCheck.tests.displaywarninginfo;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;
import org.eclipse.core.internal.resources.*;

import junit.framework.TestCase;
import keywordcheck.PhotranKeywords;

public class DisplayWarningInfoTests extends TestCase {
	
	@Test
	public static void testDumbTest() {
		String word1 = "integer";
		String word2 = "REAL";
		String word3 = "heLLo";
		String word4 = "cAlL";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		assertTrue(PhotranKeywords.isFortranKeyword(word2));
		assertFalse(PhotranKeywords.isFortranKeyword(word3));
		assertTrue(PhotranKeywords.isFortranKeyword(word4));
	}
	
	
	@Test
	public static void testCheckWarningInfo() throws Exception {
		IFile testfile = null;
		assertTrue(testfile == null);
		IWorkbenchWindow win = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
	
		if (page != null) {
			IEditorPart editor = page.getActiveEditor();
			if (editor != null) {
				IEditorInput input = editor.getEditorInput();
				if (input instanceof IFileEditorInput) {
					testfile = ((IFileEditorInput)input).getFile();
				}
			}
		}
		else {
			System.out.println("NO PAGE");
		}
		System.out.println("test");
		IMarker[] markers;
		markers = testfile.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		for(int i=0; i<markers.length; i++) {
			System.out.println("Marker1=" + markers[i].toString());
		}
		assertTrue(markers != null);
	}

}
