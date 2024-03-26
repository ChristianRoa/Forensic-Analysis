package forensic;

/**
 * This class tests each method in the Forensic Analysis class to interactively
 * display outputs.
 * 
 * @author Kal Pandit
 *
 */
public class Driver {
    public static void main(String[] args) {
        String[] methods = {
                "createSingleProfile", "buildTree", "flagProfilesOfInterest", "getMatchingProfileCount",
                "getUnmarkedPeople",
                "removePerson", "cleanupTree"
        };
        String[] options = { "Test new file", "Test new method on the same file", "Quit" };
        int repeatChoice = 0;
        do {
            System.err.print("Enter an input file name => ");
            String input = StdIn.readLine();
            System.err.println();
            boolean firstMethodRun = false;
            ForensicAnalysis studentSolution = new ForensicAnalysis();

            do {
                System.err.println("What method would you like to test?");
                System.err.println("Later methods depend on previous methods being implemented.");

                for (int i = 0; i < methods.length; i++) {
                    if (i == 0 && firstMethodRun) {
                        methods[1] = "buildTree (START OVER)";
                    }
                    System.err.printf("%d. %s\n", i + 1, methods[i]);
                }

                System.err.print("Enter a number => ");
                int choice = StdIn.readInt();
                StdIn.readLine();
                System.err.println();

                switch (choice) {
                    case 1:
                        StdIn.setFile(input);
                        StdIn.readLine(); // skips 1st unknown sequence
                        StdIn.readLine(); // skips 2nd unknown sequence
                        int numOfPeople = Integer.parseInt(StdIn.readLine()); // skips # of people
                        StdOut.println("Called createSingleProfile on each person from input file.");
                        for (int i = 0; i < numOfPeople; i++) {
                            // Skip names.
                            StdIn.readString();
                            StdIn.readString();
                            Profile profile = studentSolution.createSingleProfile();
                            String markedFeedback = profile.getMarkedStatus() ? " [Marked] " : " [UNmarked] ";
                            StdOut.print("" + markedFeedback + "-> ");

                            STR[] strs = profile.getStrs();
                            for (STR str : strs) {
                                StdOut.print("(" + str.getStrString() + ", " + str.getOccurrences() + ") ");
                            }
                            StdOut.println();
                        }
                        break;
                    case 2:
                        // Starts over.
                        studentSolution = new ForensicAnalysis();
                        studentSolution.buildTree(input);
                        StdOut.println("New tree:\n");
                        printTree(studentSolution.getTreeRoot());
                        StdOut.println("First unknown sequence: " + studentSolution.getFirstUnknownSequence());
                        StdOut.println("Second unknown sequence: " + studentSolution.getSecondUnknownSequence());
                        firstMethodRun = true;
                        break;
                    case 3:
                        studentSolution.flagProfilesOfInterest();
                        StdOut.println("New tree:\n");
                        printTree(studentSolution.getTreeRoot());
                        break;
                    case 4:
                        printFeedbackIfEmpty(studentSolution.getTreeRoot());
                        StdOut.println("Number of marked profiles (passes in true parameter): "
                                + studentSolution.getMatchingProfileCount(true));
                        StdOut.println("Number of UNmarked profiles (passes in false parameter): "
                                + studentSolution.getMatchingProfileCount(false));
                        break;
                    case 5:
                        String[] unmarkedPeople = studentSolution.getUnmarkedPeople();
                        if (unmarkedPeople == null) {
                            StdOut.println("Resulting array is null.");
                            break;
                        }
                        StdOut.println("All unmarked people.");
                        for (String person : unmarkedPeople) {
                            StdOut.println(person);
                        }
                        break;
                    case 6:
                        System.err.print("Enter FULL name of person to remove (Last, First - case-sensitive) => ");
                        String fname = StdIn.readLine();
                        studentSolution.removePerson(fname);
                        StdOut.println("New tree:\n");
                        printTree(studentSolution.getTreeRoot());
                        break;
                    case 7:
                        studentSolution.cleanupTree();
                        StdOut.println("New tree:\n");
                        printTree(studentSolution.getTreeRoot());
                        break;
                    default:
                        System.err.println("Not a valid method to test!");
                }

                StdIn.resync();
                System.err.println("\nWhat would you like to do now?");
                for (int i = 0; i < options.length; i++) {
                    System.err.printf("%d. %s\n", i + 1, options[i]);
                }

                System.err.print("Enter a number => ");
                repeatChoice = StdIn.readInt();
                StdIn.readLine();
                System.err.println();

            } while (repeatChoice == 2);

        } while (repeatChoice == 1);
    }

    private static void printFeedbackIfEmpty(TreeNode root) {
        if (root == null) {
            StdOut.println("The tree is empty. Did you create the BST?");
        }
    }

    private static void printTree(TreeNode root) {
        // Print a feedback message IF tree is empty
        if (root == null) {
            StdOut.println("EMPTY - the instance variable holding the tree root is null.");
            StdOut.println();
            return;
        }
        printTree(root, "", false, true);
        StdOut.println();
    }

    private static void printTree(TreeNode n, String indent, boolean isRight, boolean isRoot) {
        StdOut.print(indent);

        // Print out either a right connection or a left connection
        if (!isRoot)
            StdOut.print(isRight ? "|-R- " : "|-L- ");
        else
            StdOut.print("+--- ");

        if (n == null) {
            StdOut.println("null");
            return;
        }
        String fullName = n.getName();

        if (n.getProfile() != null) {
            Profile profile = n.getProfile();
            String markedFeedback = profile.getMarkedStatus() ? " [Marked] " : " [UNmarked] ";
            StdOut.print("" + fullName + markedFeedback + "-> ");

            STR[] strs = profile.getStrs();
            for (STR str : strs) {
                StdOut.print("(" + str.getStrString() + ", " + str.getOccurrences() + ") ");
            }

        } else {
            StdOut.print(n.getName() + " null profile");
        }

        StdOut.println();

        // If no more children we're done
        if (n.getLeft() == null && n.getRight() == null)
            return;

        // Add to the indent based on whether we're branching left or right
        indent += isRight ? "|    " : "     ";

        printTree(n.getLeft(), indent, false, false);
        printTree(n.getRight(), indent, true, false);
    }

}
