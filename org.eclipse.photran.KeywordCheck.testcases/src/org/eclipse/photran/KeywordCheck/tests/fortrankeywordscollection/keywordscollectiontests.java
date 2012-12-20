package org.eclipse.photran.KeywordCheck.tests.fortrankeywordscollection;

import org.junit.Test;
import keywordcheck.PhotranKeywords;

import junit.framework.TestCase;

public class keywordscollectiontests extends TestCase {

	@Test
	public static void testLowerCaseWordIsKeyword() {
		String word1 = "integer";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testCamelCaseWordIsKeyword() {
		String word1 = "ReAl";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testUpperCaseWordIsKeyword() {
		String word1 = "INCLUDE";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testLowerCaseWordIsKeywordWhitespace() {
		String word1 = " interface";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}

	@Test
	public static void testCamelCaseWordIsKeywordWhitespace() {
		String word1 = "oPeraTor ";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testUpperCaseWordIsKeywordWhitespace() {
		String word1 = " PROTECTED ";
		
		assertTrue(PhotranKeywords.isFortranKeyword(word1));
		
	}

	@Test
	public static void testLowerCaseWordIsNotKeyword() {
		String word1 = "hello";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testCamelCaseWordIsNotKeyword() {
		String word1 = "wOrLd";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testUpperCaseWordIsNotKeyword() {
		String word1 = "GOODBYE";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testLowerCaseWordIsNotKeywordWhiteSpace() {
		String word1 = "hi ";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testCamelCaseWordIsNotKeywordWhiteSpace() {
		String word1 = " hElLo";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
	
	@Test
	public static void testUpperCaseWordIsNotKeywordWhiteSpace() {
		String word1 = " WORLD ";
		
		assertFalse(PhotranKeywords.isFortranKeyword(word1));
		
	}
}
