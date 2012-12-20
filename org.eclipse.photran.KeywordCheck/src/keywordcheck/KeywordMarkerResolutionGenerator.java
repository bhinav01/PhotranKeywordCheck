package keywordcheck;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator2;


public class KeywordMarkerResolutionGenerator implements
		IMarkerResolutionGenerator2 {

	/** Returns resolutions for the given marker (may be empty).
	 * @param marker the marker
	 * @return resolutions for the given marker 
	 * 
	 */
	
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		List<IMarkerResolution2> resolutions = new ArrayList<IMarkerResolution2>();
		switch (getViolation(marker)) {
		case KeywordCheckAction.KEYWORD_VIOLATION :
			resolutions.add(new CreateKeywordViolationResolution());
			break;
		default :
			break;
		}

		return (IMarkerResolution[]) resolutions.toArray(
				new IMarkerResolution[resolutions.size()]);
	}
	
	/** Returns whether there are any resolutions for the given marker.
	 * @param marker the marker
	 * @return true if there are resolutions for the given marker, false if not
	 * 
	 */
	@Override
	public boolean hasResolutions(IMarker marker) {
		switch (getViolation(marker)) {
        case KeywordCheckAction.KEYWORD_VIOLATION :
           return true;
        default :
           return false;
		}
	}
	
	/** Returns the integer-valued attribute with the given name. 
	 * Returns the given default value if the attribute is undefined. or the marker
	 * does not exist or is not an integer value.
	 * 
	 * @param marker
	 * @return the value or the default value if no value was found.
	 */
	private int getViolation(IMarker marker) {
	     return marker.getAttribute(KeywordCheckAction.VIOLATION, 0);
	}

}
