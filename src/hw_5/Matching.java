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
		input = input.substring(2); 
		
		if(commandSign.equals("<")) convertFile(filePath);
		else if(commandSign.equals("@")) printData(input);
		else if(commandSign.equals("?")) searchPattern(input);
	}
	
	private static final int TABEL_SIZE = 100;
	private static final int K = 6;
	private static Hashtable<Integer, AVLTree> ht = new Hashtable<Integer, AVLTree>(TABEL_SIZE);
	
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
		for(int j=1; j<=input.length()-K+1; j++) {
			String sixStr = input.substring(j-1, j+K-1);
			int key = 0;
			for(int k=0; k<sixStr.length(); k++) {
				key += sixStr.charAt(k);
			}
			key %= 100;
			if(ht.get(key) == null) {

				TreeNode treeNode = new TreeNode(sixStr, i, j);
				AVLTree hashTree = new AVLTree(treeNode); // new treeNode set as new AVLTree's root
				ht.put(key, hashTree);
			} else {
				TreeNode treeNode = new TreeNode(sixStr, i, j); // same tree, diff node
				ht.get(key).insert(treeNode);
			}
		}
	}
	
	public static void printData(String input) {
		if(Integer.parseInt(input) == 0) {
			System.out.println("EMPTY");
			return;
		}
		AVLTree<TreeNode> at = ht.get(Integer.parseInt(input));
		at.preorder(at.getRoot());
	}
		
	public static void searchPattern(String input) {
		int i = 1;
		String startStr = input.substring(0, 6);
		
		if(input.length() == 6) {
			TreeNode tn = searchItem(input);
			LinkedList<TreeNode> tnList = tn.getItemList();
			for(TreeNode subTn : tnList) {
				System.out.println("(" + subTn.getI() + ", " + subTn.getJ() + ")");	
			}
		}
		
		while(i < input.length()-K+1) {
			String prevStr = input.substring(i-1, i+K-1);
			String currStr = input.substring(i, i+K);
						
			TreeNode prevTn = searchItem(prevStr);
			TreeNode currTn = searchItem(currStr);
			
			if(prevTn == null || currTn == null) {
				System.out.println("(0, 0)"); 
				break;
			}
			
//			if(prevTn.getI() == currTn.getI() && prevTn.getJ() + 1 == currTn.getJ()) System.out.println("true");
			if(i == input.length()-K) {
				TreeNode tn = searchItem(startStr);
				LinkedList<TreeNode> tnList = tn.getItemList();
				for(TreeNode subTn : tnList) {
					System.out.println("(" + subTn.getI() + ", " + subTn.getJ() + ")");	
				}
			}
			
			i++;
		}
	}
	
	private static TreeNode searchItem(String input) {
		int key = 0;
		for(int k=0; k<input.length(); k++) {
			key += input.charAt(k);
		}
		key %= 100;
//		System.out.println(key);
		
		if(ht.get(key) != null) {
			AVLTree<TreeNode> at = ht.get(key);
			return at.search(at.getRoot(), input);			
		} else {
			return null;
		}		
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
	
	private TreeNode<T> insertItem(TreeNode<T> currNode, TreeNode<T> newTreeNode) {
		
		if(currNode == null) { // currNode refers to root at first
			return newTreeNode;
		} 

		if(newTreeNode.getValue().compareTo(currNode.getValue()) < 0) {
			currNode.setLeftChild(insertItem(currNode.getLeftChild(), newTreeNode));

			// rotate if needed
			if(currNode.getLeftHeight() > currNode.getRightHeight() + 1) {
				return rotateRight(currNode);
			} else {
				return currNode;
			}
			
		} else if(newTreeNode.getValue().compareTo(currNode.getValue()) > 0) { 
			currNode.setRightChild(insertItem(currNode.getRightChild(), newTreeNode));

			// rotate if needed
			if(root.getRightHeight() > root.getLeftHeight() + 1) {
				return rotateLeft(currNode);
				
			} else {
				return currNode;
			}
			
		} else { // if new item is equal to current node
			currNode.getItemList().add(newTreeNode);
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
	
	public void preorder(TreeNode<T> root) {
		if(root != null) {
			System.out.print(root.getValue() + " ");
			preorder(root.getLeftChild());
			preorder(root.getRightChild());
		}
	}
	
	public TreeNode<T> search(TreeNode<T> root, String searchKey) {
		if(root == null) return null;
		
		if(searchKey.equals(root.getValue())) return root;
		else if(searchKey.compareTo(root.getValue()) < 0) {
			return search(root.getLeftChild(), searchKey);
		} else {
			return search(root.getRightChild(), searchKey);
		}
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
		return leftHeight;
	}

	public int getRightHeight() {
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
