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
//			System.out.print(input.substring(j-1, j+K-1));
			String sixStr = input.substring(j-1, j+K-1);
			int key = 0;
			for(int k=0; k<sixStr.length(); k++) {
				key += sixStr.charAt(k);
			}
			key %= 100;
//			System.out.print(" / " + key + " / ");
			if(h.get(key) == null) {
//				System.out.println("new");
//				System.out.println("i: " + i + ", j: " + j);
				TreeNode treeNode = new TreeNode(sixStr, i, j);
				AVLTree hashTree = new AVLTree(treeNode); // new treeNode set as new AVLTree's root
				h.put(key, hashTree);
			} else {
//				System.out.println("already");
//				System.out.println("i: " + i + ", j: " + j);
				System.out.println("==========");
				TreeNode treeNode = new TreeNode(sixStr, i, j); // same tree, diff node
//				ListNode listNode = new ListNode(i, j); 
				TreeNode currRoot = h.get(key).getRoot();
				h.get(key).insert(currRoot, treeNode);
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
	
	public void insert(TreeNode<T> currNode, TreeNode<T> newTreeNode) {
//		if(root == null) { // currNode refers to root at first
//			root = newTreeNode;
//			return;
//		} 
		
		System.out.println("currNode: " + currNode.getValue());		
		System.out.println("newTreeNode: " + newTreeNode.getValue());

		// java String: lesser when it comes earlier		
		if(newTreeNode.getValue().compareTo(currNode.getValue()) < 0) { // if new item is smaller than curr
			
			System.out.print("insert left/ ");
			
			// insert left
			if(currNode.getLeftChild() == null) {
				currNode.setLeftChild(newTreeNode);
				System.out.println("works");
			} else {
//				currNode = 
				insert(currNode.getLeftChild(), newTreeNode);
			}
			
			System.out.print("left:" + currNode.getLeftHeight());
			System.out.println("/ right:" + currNode.getRightHeight());			
			
			// rotate if needed
			if(currNode.getLeftHeight() > currNode.getRightHeight() + 1) {
				System.out.println("rotate right");
				this.rotateRight(currNode);
			}
		} else if(newTreeNode.getValue().compareTo(currNode.getValue()) > 0) { // if new item is larger than curr
			System.out.print("insert right/ ");

			// insert right
			if(currNode.getRightChild() == null) {
				currNode.setRightChild(newTreeNode);
				System.out.println("works");
			} else {
//				currNode = 
				insert(currNode.getRightChild(), newTreeNode);
			}
			
			System.out.print("left:" + currNode.getLeftHeight());
			System.out.println("/ right:" + currNode.getRightHeight());
			
			// rotate if needed
			if(currNode.getRightHeight() > currNode.getLeftHeight() + 1) {
				System.out.println("rotate left");
				this.rotateLeft(currNode);
			}			
		} else { // if new item is equal to current node
			System.out.println("same value, add to list");
			currNode.getItemList().add(newTreeNode);
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

//	public TreeNode<T> search(TreeNode<T> currNode) {
//		
//	}
	
	public void rotateLeft(TreeNode<T> currNode) {
		TreeNode<T> tmpNode = this.root;
		this.root = this.root.getRightChild();
		this.root.setLeftChild(tmpNode);
	}
	
	public void rotateRight(TreeNode<T> currNode) {
		TreeNode<T> tmpNode = this.root;
		this.root = this.root.getLeftChild();
		this.root.setRightChild(tmpNode);
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
//		ListNode nodeItem = new ListNode(i, j);
		
		// add to linkedlist right after tree node is created
//		itemList.add(nodeItem);
		
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
	}

	public TreeNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(TreeNode<T> rightChild) {
		this.rightChild = rightChild;
	}

	// dunno
	public int getLeftHeight() {
		if (this.getLeftChild() == null) return 1;
		this.leftHeight = 1 + Math.max(this.getLeftChild().getLeftHeight(), this.getLeftChild().getRightHeight());
		return leftHeight;
	}

	public int getRightHeight() {
		if (this.getRightChild() == null) return 1;
		this.rightHeight = 1 + Math.max(this.getRightChild().getLeftHeight(), this.getRightChild().getRightHeight());
		return rightHeight;
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