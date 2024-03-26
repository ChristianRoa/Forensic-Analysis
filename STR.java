package forensic;

/**
 * This class represents a Short Tandem Repeat (STR).
 * STRs are sequences of DNA that repeat themselves in our genetic code.
 * The STR class stores a string and number of times this string is repeated.
 * 
 * DO NOT EDIT THIS CODE
 * 
 * @author Kal Pandit
 */

public class STR {

    // Stores the STR string
    private String strString;
    // Number of times this sequence is repeated
    private int occurrences;

    /**
     * Initializes a new STR object with 0 occurrences, given a string.
     * @param strString the new STR string
     */
    public STR(String strString) {
        this.strString = strString;
        this.occurrences = 0;
    }

    /**
     * Initializes a new STR object with string and occurrences passed through parameters.
     * @param strString the new STR string
     * @param occurrences the number of times this sequence is repeated
     */
    public STR(String strString, int occurrences) {
        this.strString = strString;
        this.occurrences = occurrences;
    }

    /**
     * Getter method for the STR string.
     * @return the STR string of this object
     */
    public String getStrString() {
        return this.strString;
    }

    /**
     * Setter method for the STR string.
     * Updates the instance variable strString to the string passed through parameters.
     * @param newStrString the new STR string
     */
    public void setStrString(String newStrString) {
        this.strString = newStrString;
    }

    /**
     * Getter method for the number of occurrences
     * @return the number of occurrences for this STR
     */
    public int getOccurrences() {
        return this.occurrences;
    }

    /**
     * Setter method for the number of occurrences.
     * Updates the instance variable occurrences to the number passed through parameters.
     * @param newStrString the new STR string
     */
    public void setOccurrences(int newOccurrences) {
        this.occurrences = newOccurrences;
    }

    /**
     * Returns the STR data in this object as a string.
     */
    public String toString() {
        return strString + ", occurrences = " + occurrences;
    }

    /**
     * Checks if two STRs equal each other
     */
    public boolean equals(Object other) {
        if (other instanceof STR) {
            STR s = (STR) other;
            if (strString.equals(s.getStrString()) && occurrences == s.getOccurrences()) {
                return true;
            }
            return false;
        }
        else return false;
    }
    
}
