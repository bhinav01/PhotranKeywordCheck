package ui;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

public class FortranKeywordTextHover implements ITextHover {
	
	/** Returns the information which should be presented when a hover popup is shown for the
	 *  specified hover region. The hover region has the same semantics as the region returned
	 *  by getHoverRegion. If the returned information is null or empty no hover popup will be shown.
	 *  
	 *  @param textViewer the viewer on which the hover popup should be shown
	 *  @param hoverRegion the text range in the viewer which is used to determine the hover
	 *   display information
	 * 	@return the hover popup display information, or null if none available	
	 */
	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return "Inside getHoverInfo";
	}
	
	/**
	 * Returns the text region which should serve as the source of information
	 * to compute the hover popup display information. The popup has been requested
	 * for the given offset.<p>
	 *
	 * @param textViewer the viewer on which the hover popup should be shown
	 * @param offset the offset for which the hover request has been issued
	 * @return the hover region used to compute the hover display information
	 */
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset,0);
	}

}