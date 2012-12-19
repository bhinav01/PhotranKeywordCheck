package keywordcheck;

import java.util.Arrays;
import java.util.HashSet;

public final class PhotranKeywords {
	
	private static HashSet<String> keywords = new HashSet<String>();
	
	static{
		keywords.addAll(Arrays.asList("ACCESS", "ACTION", "ADVANCE", "ALLOCATABLE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "ALLOCATE", "ASSIGN", "ASSIGNMENT", "ASSOCIATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "ASYNCHRONOUS", "BACKSPACE", "BIND", "BLANK", "BLOCK", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "BLOCKDATA", "CALL", "CASE", "CLOSE", "CLASS", "COMMON", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
            "CONTAINS", "CONTINUE", "CYCLE", "DATA", "DEALLOCATE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "DEFAULT", "DELIM", "DIMENSION", "DIRECT", "DO", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "DOUBLE", "DOUBLECOMPLEX", "DOUBLEPRECISION", "ELEMENTAL", "ELSE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "ELSEIF", "ELSEWHERE", "END", "ENDBLOCK", "ENDBLOCKDATA", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "ENDDO", "ENDFILE", "ENDIF", "ENTRY", "EOR", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "EQUIVALENCE", "ERR", "EXIST", "EXIT", "EXTENDS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "EXTENSIBLE", "EXTERNAL", "FILE", "FMT", "FLUSH", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "FORALL", "FORM", "FORMAT", "FORMATTED", "FUNCTION", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "GO", "GOTO", "IF", "IMPLICIT", "IN", "INOUT", "INCLUDE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
            "INQUIRE", "INTENT", "INTERFACE", "INTRINSIC", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "IOLENGTH", "IOSTAT", "INSTRINSIC", "KIND", "LEN", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "MODULE", "NAME", "NAMED", "NAMELIST", "NEXTREC", "NML", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
            "NONE", "NON_OVERRIDABLE", "NOPASS", "NULLIFY", "NUMBER", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "ONLY", "OPEN", "OPENED", "OPERATOR", "OPTIONAL", "OUT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
            "PAD", "PARAMETER", "PASS", "PAUSE", "POINTER", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "POSITION", "PRECISION", "PRINT", "PRIVATE", "PROCEDURE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "PROGRAM", "PROTECTED", "PUBLIC", "PURE", "READ", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "READWRITE", "REC", "RECL", "RECURSIVE", "RESULT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "RETURN", "REWIND", "SAVE", "SELECT", "SEQUENCE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "SEQUENTIAL", "SIZE", "STAT", "STATUS", "STOP", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "SUBROUTINE", "TARGET", "THEN", "TO", "TYPE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "UNFORMATTED", //$NON-NLS-1$
            "UNIT", //$NON-NLS-1$
            "USE", //$NON-NLS-1$
            "VOLATILE", //$NON-NLS-1$
            "WHERE", //$NON-NLS-1$
            "WHILE", //$NON-NLS-1$
            "WRITE", //$NON-NLS-1$
            // Fortran 2003 keywords to highlight
            "EXTENDS", "ABSTRACT", "BIND", "GENERIC", "PASS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "NOPASS", "NON_OVERRIDABLE", "DEFERRED", "FINAL", "ENUM", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "ENUMERATOR", "CLASS", "VALUE", "ASSOCIATE", "IS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "WAIT", //$NON-NLS-1$
            "NON_INTRINSIC", //$NON-NLS-1$
            "IMPORT", //$NON-NLS-1$
            // Fortran 2008 keywords to highlight
            "SUBMODULE", "ENDSUBMODULE", "ENDPROCEDURE", "IMPURE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "CODIMENSION", "CONTIGUOUS", "CRITICAL", "ENDCRITICAL", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "ALL", "ALLSTOP", "SYNC", "SYNCALL", "SYNCIMAGES", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "IMAGES", "SYNCMEMORY", "MEMORY", "LOCK", "UNLOCK", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"AND", "EQ", "EQV", "FALSE", "GE", "GT", "LE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
            "LT", "NE", "NEQV", "NOT", "OR", "TRUE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
            "REAL", "INTEGER", "CHARACTER", "LOGICAL", "COMPLEX")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	public static boolean isFortranKeyword(String wordToCheck) {
		return keywords.contains(wordToCheck.trim().toUpperCase());
	}
	
	
}
