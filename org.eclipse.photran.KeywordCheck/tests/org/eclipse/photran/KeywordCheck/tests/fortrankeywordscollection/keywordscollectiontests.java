package org.eclipse.photran.KeywordCheck.tests.fortrankeywordscollection;

import org.junit.Test;
import keywordcheck.PhotranKeywords;

import junit.framework.TestCase;

public class keywordscollectiontests extends TestCase {

	@Test
	public static void testWordIsKeyword() {
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
	public static void testWordIsKeywordWhitespace() {
		String word1 = " integer";
		String word2 = "REAL ";
		String word3 = "heLLo";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		assertTrue(PhotranKeywords.isFortranKeyword(word2));
		assertFalse(PhotranKeywords.isFortranKeyword(word3));
	}
}
