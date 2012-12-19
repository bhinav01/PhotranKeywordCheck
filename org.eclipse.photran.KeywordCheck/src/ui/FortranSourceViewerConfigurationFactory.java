package ui;


import keywordcheck.KeywordCheckAction;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.photran.internal.ui.editor.FortranEditor;
import org.eclipse.photran.internal.ui.editor.FortranEditor.FortranSourceViewerConfiguration;
import org.eclipse.photran.internal.ui.editor.IFortranSourceViewerConfigurationFactory;

@SuppressWarnings("restriction")
public class FortranSourceViewerConfigurationFactory implements
IFortranSourceViewerConfigurationFactory {


	public SourceViewerConfiguration create(final FortranEditor editor)
	{

		return new FortranSourceViewerConfiguration(editor)
		{
			public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) 
			{ 
				return new MarkerHover(); 
			} 
			@Override
			public IReconciler getReconciler(ISourceViewer sourceViewer) {
				ReconcilerClass rc = new ReconcilerClass();
				MonoReconciler c = new MonoReconciler(rc, false);
				return c;
			}

			public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
				return new FortranKeywordTextHover();
			}
		};
	}


	public FortranSourceViewerConfigurationFactory() {

	}
}

class ReconcilerClass implements IReconcilingStrategy,
IReconcilingStrategyExtension 
{
	KeywordCheckAction keywordCheckObj = new KeywordCheckAction(); 
	@Override
	public void setDocument(IDocument document) {

		return;
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {

		keywordCheckObj.run();
	}

	public KeywordCheckAction getCurrentObject() {
		return keywordCheckObj;
	}

	@Override
	public void reconcile(IRegion partition) {

		keywordCheckObj.run();
	}


	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {

		return;
	}

	@Override
	public void initialReconcile() {

		keywordCheckObj.run();
	}
}

