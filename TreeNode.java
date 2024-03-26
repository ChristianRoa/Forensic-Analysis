package forensic;

/**
 * This class represents a BST Node of a single DNA Profile.
 * 
 * Contains a Profile as its data.
 * 
 * DO NOT EDIT this code.
 * 
 * @author Kal Pandit
 */
public class TreeNode {
    private String fullName;
    private Profile profile;
    private TreeNode left;
    private TreeNode right;

    /**
     * 
     * Creates TreeNode with a profile and left and right children.
     * 
     * @param fullName the full name of this person
     * @param profile  of which this TreeNode should contain as its data
     * @param left     will be this TreeNode's left child
     * @param right    will be this TreeNode's right child
     */
    public TreeNode(String name, Profile profile, TreeNode left, TreeNode right) {
        this.fullName = name;
        this.profile = profile;
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a TreeNode with null data and children.
     */
    public TreeNode() {
        this(null, null, null, null);
    }

    // Getters and setters

    /**
     * Gets name (key) from this node
     * 
     * @return the key corresponding to this node
     */
    public String getName() {
        return fullName;
    }

    /**
     * Sets profile (data) from this node
     * 
     * @param name the name corresponding to this node
     */
    public void setName(String name) {
        this.fullName = name;
    }

    /**
     * Gets profile (data) from this node
     * 
     * @return the profile corresponding to this node
     */
    public Profile getProfile() {
        return profile;

    }

    /**
     * Sets this profile as provided by parameters.
     * 
     * @param profile will become this TreeNode's new profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Gets left child of this node
     * 
     * @return left node
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Sets left child of this node
     * 
     * @param node will become this node's new left child
     */
    public void setLeft(TreeNode node) {
        left = node;
    }

    /**
     * Gets right child of this node
     * 
     * @return the right node
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Sets right child of this node
     * 
     * @param node will become this node's new right child
     */
    public void setRight(TreeNode node) {
        right = node;
    }

}
