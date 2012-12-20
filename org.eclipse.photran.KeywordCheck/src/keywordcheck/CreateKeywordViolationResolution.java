package keywordcheck;

import java.lang.reflect.Constructor;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.photran.core.IFortranAST;
import org.eclipse.photran.internal.core.lexer.Token;
import org.eclipse.photran.internal.core.refactoring.RenameRefactoring;
import org.eclipse.photran.internal.core.vpg.PhotranVPG;
import org.eclipse.photran.internal.ui.editor.FortranEditor;
import org.eclipse.photran.internal.ui.refactoring.RenameAction.FortranRenameRefactoringWizard;
import org.eclipse.rephraserengine.core.vpg.refactoring.VPGRefactoring;
import org.eclipse.rephraserengine.ui.UIUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

@SuppressWarnings("restriction")
public class CreateKeywordViolationResolution implements IMarkerResolution2 {

	@Override
	public String getLabel() {
		return "Run Rename Refactoring to fix the problem";
	}

	/** Adds the selected resorces by the marker to the file.
	 * 	Initializes and creates a Rename Refactoring wizard based on selected text.
	 * @param marker
	 */
	@Override
	public void run(IMarker marker) {
		IWorkbenchWindow wbwin = Workbench.getInstance().getActiveWorkbenchWindow();
		FortranEditor fteditor = (FortranEditor)wbwin.getActivePage().getActiveEditor();

		RenameRefactoring refactoring = new RenameRefactoring();
		try {
			Integer startpos = (Integer) marker.getAttribute(IMarker.CHAR_START);
			Integer tokenLength   = (Integer) marker.getAttribute(IMarker.CHAR_END) - startpos;
			
			HashSet<String> files = new HashSet<String>();
			files.add(marker.getResource().getName());
			refactoring.initialize((IFile)(marker.getResource()),new TextSelection(startpos,tokenLength));
			RefactoringWizard wizard = getRefactoringWizard(FortranRenameRefactoringWizard.class, refactoring);
			
			try
	        {
	            Shell shell = UIUtil.determineActiveShell();
	            if (shell == null) return;

	            String name = refactoring.getName();
	            RefactoringWizardOpenOperation wiz = new RefactoringWizardOpenOperation(wizard);
	            wiz.run(shell, name);

	            FortranEditor activeFortranEditor = fteditor;
	            if (activeFortranEditor != null)
	                activeFortranEditor.forceOutlineViewUpdate();
	        }
	        catch (InterruptedException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finally
	        {
	            PhotranVPG.getInstance().releaseAllASTs();
	        }
		}
        catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** Returns new instance of the Refactoring wizard (Dialog box)
	 * @param wizardClass
	 * @param refactoring
	 * @return 
	 */
	private RefactoringWizard getRefactoringWizard(Class<?> wizardClass, VPGRefactoring<IFortranAST, Token, PhotranVPG> refactoring)
    {
        try
        {
            Constructor<?> ctor = wizardClass.getConstructors()[0];
            return (RefactoringWizard)ctor.newInstance(new Object[] { refactoring });
        }
        catch (Exception e)
        {
            throw new Error(e);
        }
    }

	@Override
	public String getDescription() {
		return "use rename refactor";
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
