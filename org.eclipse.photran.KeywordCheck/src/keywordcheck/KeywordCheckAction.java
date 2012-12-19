package keywordcheck;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;


import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.photran.internal.core.FortranAST;
import org.eclipse.photran.internal.core.SyntaxException;
import org.eclipse.photran.internal.core.lexer.ASTLexerFactory;
import org.eclipse.photran.internal.core.lexer.IAccumulatingLexer;
import org.eclipse.photran.internal.core.lexer.LexerException;
import org.eclipse.photran.internal.core.lexer.Token;
import org.eclipse.photran.internal.core.lexer.preprocessor.fortran_include.IncludeLoaderCallback;
import org.eclipse.photran.internal.core.parser.ASTExecutableProgramNode;
import org.eclipse.photran.internal.core.parser.Parser;
import org.eclipse.photran.internal.core.sourceform.ISourceForm;
import org.eclipse.photran.internal.core.sourceform.SourceForm;
import org.eclipse.photran.internal.core.util.IterableWrapper;
import org.eclipse.photran.internal.core.vpg.PhotranVPG;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
@SuppressWarnings("restriction")
public class KeywordCheckAction {
	private IWorkbenchWindow window;
	private IFile file;
	private Parser parser = new Parser();

	/**
	 * The constructor.
	 */
	public KeywordCheckAction() {

		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

	}

	/**
	 * Deletes the existing markers on the current open file
	 * @param file IFIle object of the current file
	 */
	public void deleteMarkers(IFile file) {
		IMarker[] problems = null;
		int depth = IResource.DEPTH_INFINITE;
		try {
			problems = file.findMarkers(IMarker.PROBLEM, true, depth);

			int i = 0;
			while (i < problems.length) {
				problems[i].delete();
				i++;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Point of entry for the plugin: 
	 * Retrieves the current file resource, deletes the already existing marker and identifies the illegal tokens
	 */
	public void run() {
		file = getFileResource();
		deleteMarkers(file);
		tokensOfIllegalIdentifiers(file);
	}

	/**
	 * Determines the current source form
	 * @param file  IFile object of the current open file
	 * @return Returns ISourceForm object of the current source form 
	 */
	private ISourceForm determineSourceForm(IFile file) {
		ISourceForm sourceForm = SourceForm.of(file);
		if (file == null || file.getProject() == null)
			return sourceForm.configuredWith(new IncludeLoaderCallback(getFileResource().getProject()));
		else
			return sourceForm;
	}

	/**
	 * Gets the most recent AST from the file in temporary buffer
	 * @param file IFile object of the current open file
	 * @return current AST object
	 */
	FortranAST getUpdatedAST(IFile file) {
		try {

			FortranAST ast = null;
			ITextFileBuffer buff = FileBuffers.getTextFileBufferManager().getTextFileBuffer(file.getFullPath(),
					LocationKind.IFILE);
			if (buff != null) {
				IDocument buffDoc = buff.getDocument();
				String tempBuf = buffDoc.get();
				ISourceForm sourceForm = determineSourceForm(file);
				IAccumulatingLexer lexer = new ASTLexerFactory().createLexer(new StringReader(tempBuf), file,
						file.getName(), sourceForm);
				ASTExecutableProgramNode astRootNode = parser.parse(lexer);
				ast = new FortranAST(file, astRootNode, lexer.getTokenList());
			} else {
				PhotranVPG vpg = PhotranVPG.getInstance();
				ast = (FortranAST) vpg.acquireTransientAST(file);
			}
			return ast;

		} catch (IOException e) {
			System.out.println("I/O exception in creating updated AST");
			e.printStackTrace();
		} catch (LexerException e) { /* Ignore syntax errors */
		} catch (SyntaxException e) { /* Ignore syntax errors */
		}

		return null;
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */

	public HashSet<Token> tokensOfIllegalIdentifiers(IFile file) {
		HashSet<Token> warningInfo = new HashSet<Token>();
		if (file != null) {

			FortranAST ast = getUpdatedAST(file);
			if (ast == null) {
				System.out.println("AST is NULL. Trying to acquire AST again");
				// Attempt to get the AST again, SPIN until acquired a non-null
				// AST
				while (ast == null)
					ast = getUpdatedAST(file);
			}
			for (Token token : new IterableWrapper<Token>(ast)) {
				if (token.isIdentifier()) {
					if (PhotranKeywords.isFortranKeyword(token.getText())) {
						warningInfo.add(token);
						generateMarker(token);
					}
				}

			}
		}
		return warningInfo;
	}
	/**
	 * Fetches the current open file in the editor
	 * @return the current IFile object corresponding to the open file 
	 */
	public IFile getFileResource() {
		IFile file = null;
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			IEditorPart editor = page.getActiveEditor();

			if (editor != null) {
				IEditorInput input = editor.getEditorInput();
				if (input instanceof IFileEditorInput) {

					file = ((IFileEditorInput) input).getFile();
				}
			}
		}
		return file;
	}
	/**
	 * Generates the marker for a given token
	 * @param Token token: Takes in a token to generate a marker
	 * 
	 */
	public void generateMarker(Token token) {

		if (token == null)
			return;

		IResource resource = token.getLogicalFile();
		StringBuffer warningMsg = new StringBuffer();
		warningMsg.append(token.getText());
		warningMsg.append(" is a Fortran keyword");

		try {
			IMarker marker = resource.createMarker("org.eclipse.photran.KeywordCheck.photrankeywordmarker");
			if (marker.exists()) {
				marker.setAttribute(IMarker.MESSAGE, warningMsg.toString());
				marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				marker.setAttribute(IMarker.LINE_NUMBER, token.getLine());
				marker.setAttribute(IMarker.CHAR_START, token.getFileOffset());
				marker.setAttribute(IMarker.CHAR_END, token.getFileOffset() + token.getLength());
				marker.setAttribute("violation", 1);
				marker.setAttribute("keyword", token.getText());
			}

		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

}