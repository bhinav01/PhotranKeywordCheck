package keywordcheck;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class PropertiesFileAuditor extends IncrementalProjectBuilder {

	private static final String MARKER_ID = "photrankeywordmarker";
	public static final  String VIOLATION = "violation";
	public static final  int KEYWORD_VIOLATION = 1;
	
	public PropertiesFileAuditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args,
			IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

}
