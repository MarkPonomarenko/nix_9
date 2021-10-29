package ua.com.alevel.secondlevel;

import java.io.PrintStream;
import java.util.Scanner;

//класс ячеек дерева
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {
        val = x;
        left = right = null;
    };
    public int getVal(){
        return val;
    }
    public TreeNode getRight(){
        return right;
    }
    public TreeNode getLeft(){
        return left;
    }
}
//красивый более менее вывод дерева
class BinaryTreePrinter{
    TreeNode root;
    BinaryTreePrinter(TreeNode node) {
        root = node;
    }
    public void traversePreOrder(StringBuilder sb, String padding, String pointer, TreeNode node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getVal());
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("|  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "/--";
            String pointerForLeft = (node.getRight() != null) ? "+--" : "/--";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.getLeft());
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.getRight());
        }
    }
    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", this.root);
        os.print(sb.toString());
    }
}

public class BinaryTree {
    TreeNode root;

    int maxDepth(TreeNode node) {
        if (node == null)
            return 0;
        else {
            int leftDepth = maxDepth(node.left);
            int rightDepth = maxDepth(node.right);
            if (leftDepth > rightDepth)
                return (leftDepth + 1);
            else
                return (rightDepth + 1);
        }

    }
    public static void findMaxHeight() {
        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);
        tree.root.left.right.left = new TreeNode(6);
        System.out.println("Бинарное дерево:");
        new BinaryTreePrinter(tree.root).print(System.out);

        System.out.println("Высота дерева: " + tree.maxDepth(tree.root));
        System.out.println("Нажмите Enter для продолжения...");
        new Scanner(System.in).nextLine();
    }
}