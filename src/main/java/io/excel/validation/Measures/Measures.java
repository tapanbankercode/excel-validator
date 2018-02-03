package io.excel.validation.Measures;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tapan N. Banker
 * @author Tapan N. Banker
 */
public class Measures {
    private String ptn;
    private String measure;
    private String standardizedMeasureName;
    private String measureType;
    private String improvementAreaGoal;
    private String nationalStandardDefinitionUsed;
    private String acronyms;
    private String identifiers;
    private String nqf;
    private String pqrs;
    private String cms;
    private String other;
    private String numeratorDefinition;
    private String denominatorDefinition;

    // Constructor
    public Measures() {
    }

    public String getPtn() {
        return ptn;
    }

    public void setPtn(String ptn) {
        this.ptn = ptn;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getStandardizedMeasureName() {
        return standardizedMeasureName;
    }

    public void setStandardizedMeasureName(String standardizedMeasureName) {
        this.standardizedMeasureName = standardizedMeasureName;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getImprovementAreaGoal() {
        return improvementAreaGoal;
    }

    public void setImprovementAreaGoal(String improvementAreaGoal) {

        this.improvementAreaGoal = improvementAreaGoal;
    }

    public String getNationalStandardDefinitionUsed() {
        return nationalStandardDefinitionUsed;
    }

    public void setNationalStandardDefinitionUsed(String nationalStandardDefinitionUsed) {
        this.nationalStandardDefinitionUsed = nationalStandardDefinitionUsed;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public String getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    public String getNqf() {
        return nqf;
    }

    public void setNqf(String nqf) {
        this.nqf = nqf;
    }

    public String getPqrs() {
        return pqrs;
    }

    public void setPqrs(String pqrs) {
        this.pqrs = pqrs;
    }

    public String getCms() {
        return cms;
    }

    public void setCms(String cms) {
        this.cms = cms;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getNumeratorDefinition() {
        return numeratorDefinition;
    }

    public void setNumeratorDefinition(String numeratorDefinition) {
        this.numeratorDefinition = numeratorDefinition;
    }

    public String getDenominatorDefinition() {
        return denominatorDefinition;
    }

    public void setDenominatorDefinition(String denominatorDefinition) {
        this.denominatorDefinition = denominatorDefinition;
    }

    /**
     * Constructor
     * @param ptn
     * @param measure
     * @param standardizedMeasureName
     * @param measureType
     * @param improvementAreaGoal
     * @param nationalStandardDefinitionUsed
     * @param acronyms
     * @param identifiers
     * @param nqf
     * @param pqrs
     * @param cms
     * @param other
     * @param numeratorDefinition
     * @param denominatorDefinition
     */
    public Measures(String ptn, String measure, String standardizedMeasureName, String measureType, String improvementAreaGoal,
                    String nationalStandardDefinitionUsed, String acronyms, String identifiers, String nqf,
                    String pqrs, String cms, String other, String numeratorDefinition, String denominatorDefinition) {

        this.ptn = ptn;
        this.measure = measure;
        this.acronyms = acronyms;
        this.nationalStandardDefinitionUsed = nationalStandardDefinitionUsed;
        this.improvementAreaGoal = improvementAreaGoal;
        this.measureType = measureType;
        this.standardizedMeasureName = standardizedMeasureName;
        this.identifiers = identifiers;
        this.nqf = nqf;
        this.pqrs = pqrs;
        this.cms = cms;
        this.other = other;
        this.numeratorDefinition = numeratorDefinition;
        this.denominatorDefinition = denominatorDefinition;

    }


    /**
     * the method return the custom string representation of the object
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(500);

        sb.append(ptn);
        sb.append(" | ");
        sb.append(measure);
        sb.append(" | ");
        sb.append(standardizedMeasureName);
        sb.append(" | ");
        sb.append(measureType);
        sb.append(" | ");
        sb.append(improvementAreaGoal);
        sb.append(" | ");
        sb.append(nationalStandardDefinitionUsed);
        sb.append(" | ");
        sb.append(acronyms);
        sb.append(" | ");
        sb.append(identifiers);
        sb.append(" | ");
        sb.append(nqf);
        sb.append(" | ");
        sb.append(pqrs);
        sb.append(" | ");
        sb.append(cms);
        sb.append(" | ");
        sb.append(other);
        sb.append(" | ");
        sb.append(numeratorDefinition);
        sb.append(" | ");
        sb.append(denominatorDefinition);
        sb.append("\n ");
        // Convert SB to String and return
        return sb.toString();
    }
}
