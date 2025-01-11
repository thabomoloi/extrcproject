package com.extrcproject.core.util;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tweetyproject.logics.pl.syntax.Conjunction;
import org.tweetyproject.logics.pl.syntax.Disjunction;
import org.tweetyproject.logics.pl.syntax.Equivalence;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.KnowledgeBase;

public class DefeasibleParserTest {

    private final DefeasibleParser defeasibleParser = new DefeasibleParser();

    @ParameterizedTest
    @MethodSource("provideValidFormulas")
    void testParseValidFormula(String formula, String expected) throws Exception {
        PlFormula parsedFormula = defeasibleParser.parseFormula(formula);
        assertNotNull(parsedFormula, "Parsed formula should not be null.");
        assertEquals(expected, parsedFormula.toString().replaceAll("[()]", ""), "Parsed formula does not match expected output.");
    }

    private static Stream<Arguments> provideValidFormulas() {
        return Stream.of(
                Arguments.of("p", "p"),
                Arguments.of("p || q", "p||q"),
                Arguments.of("p && q", "p&&q"),
                Arguments.of("p => q", "p=>q"),
                Arguments.of("p ~> q", "p~>q"),
                Arguments.of("p <=> q", "p<=>q"));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidFormulas")
    void testParseInvalidFormula(String formula) {
        Exception exception = assertThrows(Exception.class, () -> defeasibleParser.parseFormula(formula));
        assertTrue(exception.getMessage().contains("Cannot parse formula"));
    }

    private static Stream<Arguments> provideInvalidFormulas() {
        return Stream.of(
                Arguments.of("p &&"),
                Arguments.of("p =>"),
                Arguments.of("~> q"),
                Arguments.of(""));
    }

    @Test
    void testParseValidFormulas() throws Exception {
        String formulas = "p, q, p && q, p || q, p => q, p ~> q, p <=> q";
        KnowledgeBase expected = getValiKnowledgeBase();
        KnowledgeBase actual = defeasibleParser.parseFormulas(formulas);
        assertEquals(expected, actual);
    }

    @Test
    void testParseInvalidFormulas() {
        String formulas = "p, q, p && , p || q, p =>, ~> q, p <=> q";
        Exception exception = assertThrows(Exception.class, () -> defeasibleParser.parseFormulas(formulas));
        assertTrue(exception.getMessage().contains("Cannot parse formula"));
    }

    @Test
    void testParseValidFormulasFromFile() throws Exception {
        URL url = this.getClass().getClassLoader().getResource("test-valid-kb.txt");
        KnowledgeBase actual = defeasibleParser.parseFormulasFromFile(Paths.get(url.toURI()).toString());
        KnowledgeBase expected = getValiKnowledgeBase();
        assertEquals(expected, actual);
    }

    @Test
    void testParseInvalidFormulasFromFile() {
        URL url = this.getClass().getClassLoader().getResource("test-invalid-kb.txt");
        Exception exception = assertThrows(Exception.class, () -> defeasibleParser.parseFormulasFromFile(Paths.get(url.toURI()).toString()));
        assertTrue(exception.getMessage().contains("Cannot parse formula"));
    }

    @Test
    void testParseValidInputStream() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-valid-kb.txt");
        KnowledgeBase actual = defeasibleParser.parseInputStream(inputStream);
        KnowledgeBase expected = getValiKnowledgeBase();
        assertEquals(expected, actual);
    }

    @Test
    void testParseInvalidInputStream() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-invalid-kb.txt");
        Exception exception = assertThrows(Exception.class, () -> defeasibleParser.parseInputStream(inputStream));
        assertTrue(exception.getMessage().contains("Cannot parse formula"));
    }

    private KnowledgeBase getValiKnowledgeBase() {
        Proposition p = new Proposition("p");
        Proposition q = new Proposition("q");
        return new KnowledgeBase(Arrays.asList(
                p, q,
                new Conjunction(p, q),
                new Disjunction(p, q),
                new Implication(p, q),
                new DefeasibleImplication(p, q),
                new Equivalence(p, q)
        ));
    }

}
