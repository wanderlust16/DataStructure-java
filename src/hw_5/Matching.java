package hw_5;
import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;

public class Matching
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws IOException {
		String commandSign = String.valueOf(input.charAt(0));
		String filePath = input.substring(2); // user later(file input)
//		input = input.substring(2); 
		
		if(commandSign.equals("<")) convertFile(filePath);
//		int i = 1;
//		if(commandSign.equals("<")) inputData(input, i);
		else if(commandSign.equals("@")) printData(input);
		else if(commandSign.equals("?")) searchPattern(input);
	}
	
	private static final int TABEL_SIZE = 100;
	private static final int K = 6;
	private static Hashtable<Integer, AVLTree> h = new Hashtable<Integer, AVLTree>(TABEL_SIZE);
	
//	private static int i = 1;
//	private static int j = 1;
	
	public static void convertFile(String fileName) throws IOException {
		File file = new File(fileName);
		if(file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int i = 1;
			String line = "";
			while((line = br.readLine()) != null) {
				System.out.println(line);
				inputData(line, i++);	
			}
			br.close();
		}
	}
	
	public static void inputData(String input, int i)
	{
		System.out.println("i: " + i);
		
		// extract substring from input 
		for(int j=1; j<=input.length()-K+1; j++) {
			String sixStr = input.substring(j-1, j+K-1);
			System.out.print(sixStr);
			int key = 0;
			for(int k=0; k<sixStr.length(); k++) {
				key += sixStr.charAt(k);
			}
			key %= 100;
			System.out.print("/" + key + "/");
			if(h.get(key) == null) {
				System.out.println("new");
				System.out.println("i: " + i + ", j: " + j);
				TreeNode treeNode = new TreeNode(sixStr, i, j);
				AVLTree hashTree = new AVLTree(treeNode); // new treeNode set as new AVLTree's root
				h.put(key, hashTree);
			} else {
				System.out.println("already");
				System.out.println("i: " + i + ", j: " + j);
				TreeNode treeNode = new TreeNode(sixStr, i, j); // same tree, diff node
//				TreeNode currRoot = h.get(key).getRoot();
				h.get(key).insert(treeNode);
			}
		}
	}
	
	public static void printData(String input) {
		
	}
	
	public static void searchPattern(String input) {
		
	}
}

class AVLTree<T> {
	private TreeNode<T> root;
	
	public AVLTree() {
		root = null;
	}
	
	public AVLTree(TreeNode<T> root) {
		this.root = root;
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}
	
	public void insert(TreeNode<T> newTreeNode) {
		root = insertItem(root, newTreeNode);
	}
	
	public TreeNode<T> insertItem(TreeNode<T> currNode, TreeNode<T> newTreeNode) {
		
		if(currNode == null) { // currNode refers to root at first
			return newTreeNode;
		} 

		if(newTreeNode.getValue().compareTo(currNode.getValue()) < 0) {
			currNode.setLeftChild(insertItem(currNode.getLeftChild(), newTreeNode));

			System.out.print(root.getValue());
			System.out.print("/ left:" + root.getLeftHeight());
			System.out.print("/ right:" + root.getRightHeight());		
			System.out.println("/ height:" + root.getHeight());			
			
			// rotate if needed
			if(currNode.getLeftHeight() > currNode.getRightHeight() + 1) {
				System.out.println("rotate right");
				return rotateRight(currNode);
			} else {
				return currNode;
			}
			
		} else if(newTreeNode.getValue().compareTo(currNode.getValue()) > 0) { 
			currNode.setRightChild(insertItem(currNode.getRightChild(), newTreeNode));

			System.out.print(root.getValue());
			System.out.print("/ left:" + root.getLeftHeight());
			System.out.print("/ right:" + root.getRightHeight());
			System.out.println("/ height:" + root.getHeight());			
			
			// rotate if needed
			if(root.getRightHeight() > root.getLeftHeight() + 1) {
				System.out.println("rotate left");
				return rotateLeft(currNode);
				
			} else {
				return currNode;
			}
			
		} else { // if new item is equal to current node
			System.out.println("same value, add to list");
			currNode.getItemList().add(newTreeNode);
			System.out.println(currNode.getItemList().size());	
			return currNode;
		}
	
	}

	public boolean isEmpty() {
		return root == null;
	}
	
	public TreeNode<T> rotateLeft(TreeNode<T> currNode) {
		TreeNode<T> tmpNode = currNode.getRightChild();
		currNode.setRightChild(tmpNode.getLeftChild());
		tmpNode.setLeftChild(currNode);
		return tmpNode;
	}
	
	public TreeNode<T> rotateRight(TreeNode<T> currNode) {
		TreeNode<T> tmpNode = currNode.getLeftChild();
		currNode.setLeftChild(tmpNode.getRightChild());
		tmpNode.setRightChild(currNode);
		return tmpNode;
	}
	
	public void traverse() {
		
	}
	
}

class TreeNode<T> {	
	private String value;
	private int i;
	private int j;
	private LinkedList<TreeNode> itemList;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;	
	private int leftHeight;
	private int rightHeight;
	
	public TreeNode(String value, int i, int j) {
		this.value = value;
		this.i = i;
		this.j = j;
		this.itemList = new LinkedList<TreeNode>();
		this.leftHeight = 0;
		this.rightHeight = 0;
		
		this.itemList.add(this); // wow it works! 
		
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LinkedList<TreeNode> getItemList() {
		return itemList;	
	}

	public void setItemList(LinkedList<TreeNode> itemList) {
		this.itemList = itemList;
	}

	public TreeNode<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(TreeNode<T> leftChild) {
		this.leftChild = leftChild;
		this.leftHeight = leftChild == null ? 0 : leftChild.getHeight();
	}

	public TreeNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(TreeNode<T> rightChild) {
		this.rightChild = rightChild;
		this.rightHeight = rightChild == null ? 0 : rightChild.getHeight();
	}

	public int getLeftHeight() {
//		if (leftChild == null) return 0;
//		this.leftHeight = 1 + Math.max(this.getLeftChild().getLeftHeight(), this.getLeftChild().getRightHeight());
//		return leftChild.getHeight();
		return leftHeight;
	}

	public int getRightHeight() {
//		if (rightChild == null) return 0;
//		this.rightHeight = 1 + Math.max(this.getRightChild().getLeftHeight(), this.getRightChild().getRightHeight());
//		return rightChild.getHeight();
		return rightHeight;
	}
	
	public int getHeight() {
		return Math.max(leftHeight, rightHeight)+1;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
}

//class ListNode<T> {
////	private String item;
//	private int i;
//	private int j;
//	
//	public ListNode(int i, int j) {
////		this.item = item;
//		this.i = i;
//		this.j = j;
//	}
//
//	public int getI() {
//		return i;
//	}
//
//	public void setI(int i) {
//		this.i = i;
//	}
//
//	public int getJ() {
//		return j;
//	}
//
//	public void setJ(int j) {
//		this.j = j;
//	}
//	
//}