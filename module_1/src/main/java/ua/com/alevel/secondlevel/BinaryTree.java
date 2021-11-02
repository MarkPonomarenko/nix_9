package ua.com.alevel.secondlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

//класс ячеек дерева
class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
        left = right = null;
    }

    public int getVal() {
        return val;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode getLeft() {
        return left;
    }
}

//красивый более менее вывод дерева
class BinaryTreePrinter {

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

    private TreeNode addRecursive(TreeNode current, int value) {
        if (current == null) {
            return new TreeNode(value);
        }

        if (value < current.val) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.val) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    public static void printMenu() {
        System.out.println("\tБинарное дерево");
        System.out.println("1 - Добавить элемент");
        System.out.println("2 - Вывод дерева");
        System.out.println("3 - Найти макс высоту");
        System.out.println("0 - Выход");
        System.out.println("Ваш ввод:");
    }

    public static boolean inputCheck(String[] input) {
        for (String s : input) {
            for (int j = 0; j < s.length(); ++j) {
                if (!Character.isDigit(s.charAt(j))) {
                    System.out.println("Неверный ввод");
                    return false;
                }
            }
        }
        return true;
    }

    public static int safeInput() throws IOException {
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String inputRaw = input.readLine();
            String[] inputSplit = inputRaw.split(" ");
            if (inputSplit.length > 1) continue;
            if (!inputCheck(inputSplit)) continue;
            int x = Integer.parseInt(inputSplit[0]);
            return x;
        }
    }

    public static void findMaxHeight() throws IOException {
        Scanner scanner = new Scanner(System.in);
        BinaryTree tree = new BinaryTree();
        System.out.println("Введите значение корневого узла:");
        int x = safeInput();
        tree.add(x);
        while (true) {
            printMenu();
            String statement = scanner.nextLine();
            switch (statement) {
                case "1":
                    System.out.println("Введите значение нового эл.:");
                    int y = safeInput();
                    tree.add(y);
                    break;
                case "2":
                    new BinaryTreePrinter(tree.root).print(System.out);
                    break;
                case "3":
                    System.out.println("Высота дерева: " + tree.maxDepth(tree.root));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный ввод");
            }
        }
    }
}