package ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class MarkerHover implements IAnnotationHover {

	/**
	 * Returns all the messages corresponding to the given line number by
	 * calling getMessagesForLine
	 * 
	 * @param sourceViewer
	 *            Source viewer object of the current editor
	 * @param lineNumber
	 *            Line number for the marker
	 */
	@Override
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		String[] messages = getMessagesForLine(sourceViewer, lineNumber);

		StringBuffer buffer = new StringBuffer("");

		for (int i = 0; i < messages.length; i++) {
			buffer.append(messages[i]);
			if (i != messages.length - 1)
				buffer.append(System.getProperty("line.separator")); //$NON-NLS-1$ 
		}

		return buffer.toString();

	}

	/**
	 * Returns all the messages corresponding to the given line number
	 * 
	 * @param viewer
	 *            Current source viewer object
	 * @param line
	 *            Line number
	 * @return String array of all the messages
	 */
	private String[] getMessagesForLine(ISourceViewer viewer, int line) {
		IDocument document = viewer.getDocument();
		IAnnotationModel model = viewer.getAnnotationModel();

		if (model == null)
			return new String[0];

		ArrayList<String> messages = new ArrayList<String>();

		Iterator iter = model.getAnnotationIterator();
		while (iter.hasNext()) {
			Object object = iter.next();
			if (object instanceof MarkerAnnotation) {
				MarkerAnnotation annotation = (MarkerAnnotation) object;
				if (compareRulerLine(model.getPosition(annotation), document, line)) {
					IMarker marker = annotation.getMarker();
					String message = marker.getAttribute(IMarker.MESSAGE, (String) null);
					if (message != null && message.trim().length() > 0)
						messages.add(message);
				}
			}
		}
		return (String[]) messages.toArray(new String[messages.size()]);
	}

	/**
	 * This method makes sure that the annotation is on a particular like and
	 * not exceeding that line
	 * 
	 * @param position
	 *            The current position of the annotation
	 * @param document
	 *            The current IDocument object
	 * @param line
	 *            The current line number for the annotation
	 * @return
	 */
	private boolean compareRulerLine(Position position, IDocument document, int line) {

		try {
			if (position.getOffset() > -1 && position.getLength() > -1) {
				return document.getLineOfOffset(position.getOffset()) == line;
			}
		} catch (BadLocationException e) {
			System.out.println("Bad Location in compareRulerLine");
			e.printStackTrace();
		}
		return false;
	}

}
