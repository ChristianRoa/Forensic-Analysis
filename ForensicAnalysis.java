package forensic;

import java.util.jar.Attributes.Name;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {
        int numOfStr = StdIn.readInt();
        STR[] str = new STR[numOfStr];
        for (int i = 0; i < str.length; i++) {
            String strString = StdIn.readString();
            int occurrences = StdIn.readInt();
            str[i] = new STR(strString, occurrences);
        }
        Profile singleProfile = new Profile(str);
        singleProfile.setStrs(str);
        singleProfile.setInterestStatus(false);

        // WRITE YOUR CODE HERE
        
        return singleProfile; // update this line
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {
        TreeNode newPerson = new TreeNode(name, newProfile, null, null);
        TreeNode ptr = treeRoot;
        TreeNode prev = null;
        int cmp = 0;
        if (ptr == null){
            treeRoot = newPerson;
            return;
        }
        while ( ptr != null ) {
            cmp = name.compareTo(ptr.getName());
            if ( cmp == 0 ) {
                ptr.setProfile(newProfile);
                return;
            }
            prev = ptr;
            if ( cmp < 0 ) {
                ptr = ptr.getLeft();
            } else {
                ptr = ptr.getRight();
            }
        }
        if(cmp < 0){
            prev.setLeft(newPerson);
        }
        else{
            prev.setRight(newPerson);
        }

        // WRITE YOUR CODE HERE
    }
    private void postorder(TreeNode x, Queue<String> q){
        if (x==null) return;
        postorder(x.getLeft(), q);
        postorder(x.getRight(), q);
        q.enqueue(x.getName());
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        TreeNode ptr = treeRoot;
        int counter = 0;
        int cmp = 0;
        Queue<String> q= new Queue<String>();
        postorder(ptr, q);
        while(!q.isEmpty()){
            String nameHolder = q.dequeue();
            ptr = treeRoot;
            while(ptr != null){
                cmp = nameHolder.compareTo(ptr.getName());
                if ( cmp == 0 ) {
                    break;
                }
                if ( cmp < 0 ) {
                    ptr = ptr.getLeft();
                } else {
                    ptr = ptr.getRight();
                }
            }
            Profile testing = ptr.getProfile();
            boolean status = testing.getMarkedStatus();
            if (status == isOfInterest){
                counter++;
            }
        }
        // WRITE YOUR CODE HERE
        return counter; // update this line
    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
                        
        // DO NOT EDIT THIS CODE        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */
    public void flagProfilesOfInterest() {
        TreeNode ptr = treeRoot;
        int cmp = 0;
        Queue<String> q= new Queue<String>();
        postorder(ptr, q);
        while(!q.isEmpty()){
            String nameHolder = q.dequeue();
            ptr = treeRoot;
            while(ptr != null){
                cmp = nameHolder.compareTo(ptr.getName());
                if ( cmp == 0 ) {
                    break;
                }
                if ( cmp < 0 ) {
                    ptr = ptr.getLeft();
                } else {
                    ptr = ptr.getRight();
                }
            }
            Profile testing = ptr.getProfile();
            STR[] testing2 = testing.getStrs();
            String combinedSequence = firstUnknownSequence + secondUnknownSequence;
            int counter = 0;
            int interestCheck = 0;
            if(testing2.length % 2 == 0){
                interestCheck = testing2.length/2;
            }
            else{
                interestCheck = testing2.length/2 + 1;
            }
            for (int i = 0; i < testing2.length; i++) {
                String str = testing2[i].getStrString();
                int comparer = testing2[i].getOccurrences();
                int comparee = numberOfOccurrences(combinedSequence, str);
                if(comparer == comparee){
                    counter++;
                }
            }
            if(counter >= interestCheck){
                ptr.getProfile().setInterestStatus(true);
            }
            else{
                ptr.getProfile().setInterestStatus(false);

            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {
        int unmarkedCount = getMatchingProfileCount(false);
        int index = 0;
        String[] unmarkedPeople = new String[unmarkedCount];
        Queue<TreeNode> queue = new Queue<>();
        TreeNode ptr = treeRoot;
        if (treeRoot != null){
            queue.enqueue(ptr);
        }
        queue.peek();
        while(!queue.isEmpty()){
            TreeNode current = queue.dequeue();
            boolean status = current.getProfile().getMarkedStatus();
            if(current.getLeft() != null){
                queue.enqueue(current.getLeft());
                }
            if(current.getRight() != null){
                queue.enqueue(current.getRight());
                }
            if (status == false){
                unmarkedPeople[index] = current.getName();
                index++;
                }
        }
        // WRITE YOUR CODE HERE
        return unmarkedPeople; // update this line
    }
    private TreeNode deleteMin(TreeNode x){
        if (x.getLeft() == null) return x.getRight();
        x.setLeft(deleteMin(x.getLeft()));
        return x;
    }
    private TreeNode findMin(TreeNode x){
        if(x.getLeft() == null){
            return x;
        }
        return findMin(x.getLeft());
    }
    private TreeNode delete(TreeNode x, String fullname){
        if (x ==null) return null;
        int cmp = fullname.compareTo(x.getName());
        if ( cmp < 0) {
            x.setLeft(delete(x.getLeft(), fullname));
        }
        else if ( cmp > 0) {
            x.setRight(delete(x.getRight(), fullname));
        }
        else{
            if (x.getRight() == null) return x.getLeft();
            if (x.getLeft() == null) return x.getRight();
        
        TreeNode Successor = x;
        x = findMin(Successor.getRight());
        x.setRight(deleteMin(Successor.getRight())); 
        x.setLeft(Successor.getLeft());
        }
        return x;

    }
    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {
        TreeNode ptr = treeRoot;
        int cmp = 0;
        TreeNode prev = null;
        while ( ptr != null ) {
            cmp = fullName.compareTo(ptr.getName());
            if ( cmp == 0 ) {
                if(prev == null){
                    treeRoot = delete(ptr, fullName);
                }
                else if (ptr == prev.getLeft()){
                    prev.setLeft(delete(ptr, fullName));
                }
                else {
                    prev.setRight(delete(ptr, fullName));
                }
                break;
            }
            prev = ptr;
            if ( cmp < 0 ) {
                ptr = ptr.getLeft();
            } else {
                ptr = ptr.getRight();
            }
        }
      // WRITE YOUR CODE HERE
    }

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        String[] unmarkedPeople = getUnmarkedPeople();
        for (int i = 0; i < unmarkedPeople.length; i++) {
            String fullName = unmarkedPeople[i];
            removePerson(fullName);
        }

    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
