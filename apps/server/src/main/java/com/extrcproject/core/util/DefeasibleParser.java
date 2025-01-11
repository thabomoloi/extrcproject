package com.extrcproject.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.syntax.KnowledgeBase;

/**
 * Defines a parser for parsing formulas including defeasible implications.
 *
 * @author Thabo Vincent Moloi
 */
public class DefeasibleParser {

    private final PlParser parser;

    /**
     * Create new defeasible parser.
     */
    public DefeasibleParser() {
        this.parser = new PlParser();
    }

    /**
     * Parse a formula from string. Example: "p~>f".
     *
     * @param formula Formula to parse.
     * @return Propositional logic formula.
     * @throws Exception
     */
    public PlFormula parseFormula(String formula) throws Exception {
        PlFormula parsedFormula;
        try {
            // Check if formula is defeasible implication first
            boolean isDI = formula.contains(Symbol.DEFEASIBLE_IMPLICATION());
            // Reformula if defeasible implication
            formula = isDI ? reformatDefeasibleImplication(formula) : formula;
            // Parse formula
            parsedFormula = parser.parseFormula(formula);
            // Dematerialise parsedFormula if formula is defeasible implication
            return isDI ? KnowledgeBase.dematerialise(parsedFormula) : parsedFormula;
        } catch (IOException | ParserException e) {
            throw new Exception("Cannot parse formula: " + formula);
        }
    }

    /**
     * Parse formulas from a string of comma separated formulas.
     *
     * @param formulas
     * @return Knowledge base.
     * @throws Exception
     */
    public KnowledgeBase parseFormulas(String formulas) throws Exception {
        // Split formulas by comma
        String[] formulaStrings = formulas.split(",");
        KnowledgeBase kb = new KnowledgeBase();

        for (String formula : formulaStrings) {
            // Parse formula if not empty
            if (!formula.trim().isEmpty()) {
                try {
                    // Parse formula and add it to knowledge base
                    PlFormula parsedFormula = this.parseFormula(formula.trim());
                    kb.add(parsedFormula);
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return kb;

    }

    /**
     * Parse formulas from a file.
     *
     * @param filePath Path of a file containing formulas.
     * @return Knowledge base.
     * @throws Exception
     */
    public KnowledgeBase parseFormulasFromFile(String filePath) throws Exception {
        KnowledgeBase kb = new KnowledgeBase();
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Parse the formula and add it to knowledge base
                    PlFormula parsedFormula = this.parseFormula(line.trim());
                    kb.add(parsedFormula);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return kb;
    }

    /**
     * Parse formulas from an input stream.
     *
     * @param inputStream Input stream to parse.
     * @return Knowledge base.
     * @throws Exception
     */
    public KnowledgeBase parseInputStream(InputStream inputStream) throws Exception {
        KnowledgeBase kb = new KnowledgeBase();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    PlFormula parsedFormula = this.parseFormula(line.trim());
                    kb.add(parsedFormula);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return kb;
    }

    /**
     * Reformat defeasible implication to implication. E.g. "p~>f" to "p=>f".
     *
     * @param formula Defeasible implication to reformat.
     * @return Implication.
     */
    private String reformatDefeasibleImplication(String formula) {
        int index = formula.indexOf(Symbol.DEFEASIBLE_IMPLICATION());
        formula = "(" + formula.substring(0, index) + ")" + Symbol.IMPLICATION() + "("
                + formula.substring(index + 2, formula.length()) + ")";
        return formula;
    }

}
