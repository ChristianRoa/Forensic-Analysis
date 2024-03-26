package forensic;

/**
 * Represents a DNA profile with associated information.
 * Includes first name, last name, DNA sequence, and Short Tandem Repeats (STRs).
 * 
 * DO NOT EDIT THIS CODE
 * 
 * @author Kal Pandit
 */
public class Profile {

    private STR[] strs;
    private boolean isMarked;

    /**
     * Constructs a DNA profile with the provided name and null STRs.
     *
     */
    public Profile() {
        this.strs = null;
        this.isMarked = false;
    }

    /**
     * Constructs a DNA profile with the provided name and given STRs.
     * By default, a profile is automatically unmarked.
     *
     * @param firstName the first name of the individual associated with the DNA profile.
     * @param lastName  the last name of the individual associated with the DNA profile.
     * @param strs      an array of Short Tandem Repeats (STRs) associated with the DNA profile.
     */
    public Profile(STR[] strs) {
        this.strs = strs;
        this.isMarked = false;
    }

    /**
     * Retrieves the array of Short Tandem Repeats (STRs) associated with the DNA profile.
     *
     * @return the array of STRs.
     */
    public STR[] getStrs() {
        return this.strs;
    }

    /**
     * Sets the array of Short Tandem Repeats (STRs) associated with the DNA profile.
     *
     * @param strs the new array of STRs to be set.
     */
    public void setStrs(STR[] strs) {
        this.strs = strs;
    }

    /**
     * Gets the marked status of this profile.
     * @return the marked status.
     */
    public boolean getMarkedStatus() {
        return this.isMarked;
    }

    /**
     * Sets interest status to parameter status.
     * @param status the new interest value, as a boolean.
     */
    public void setInterestStatus(boolean status) {
        this.isMarked = status;
    }

    /**
     * Returns a string representation of the DNA profile, including DNA sequence, and STRs.
     *
     * @return a string representation of the profile.
     */
    public String toString() {
        String strString = "";
        if (strs != null) {
            for (STR str : strs) {
                strString += "  " + str.toString() + "\n";
            }
        }
        return "STRs:\n" + strString;
    }

    /**
     * Checks if two profiles equal each other
     */
    public boolean equals(Object other) {
        if (other instanceof Profile) {
            Profile p = (Profile) other;
            // Marked statuses have to equal each other
            if (p.getMarkedStatus() != this.getMarkedStatus()) {
                return false;
            }
            // Lengths of STR arrays have to equal each other
            if (p.getStrs().length != this.getStrs().length) {
                return false;
            }
            for (int i = 0; i < this.getStrs().length; i++) {
                // STRs have to be the same in the same positions.
                if (!this.getStrs()[i].equals(p.getStrs()[i])) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
}
