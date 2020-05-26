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

	private static void command(String input) {
		String commandSign = String.valueOf(input.charAt(0));
		String filePath = input.substring(2);
		
		if(commandSign.equals("<")) inputData(filePath);
		else if(commandSign.equals("@")) printData(input);
		else if(commandSign.equals("?")) searchPattern(input);
	}
	
	public static void convertFile(String fileName) throws IOException {
		
		File file = new File(fileName);
		FileReader fReader = new FileReader(file);
		if(file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line; 
			while((line = br.readLine()) != null) {
				inputData(line);
			}
		}
	}
	
	public static void inputData(String input)
	{
		// TODO : 아래 문장을 삭제하고 구현해라.
//		System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
		
		final int TABEL_SIZE = 100;
		Hashtable<Integer, AVLTree> h = new Hashtable<Integer, AVLTree>(TABEL_SIZE);
		
		// extract substring from input 
		int i = 0;
		final int K = 6;
		for(int j=0; j<=input.length()-K; j++) {
//			System.out.print(input.substring(j, j+k));
			i++;
			String sixStr = input.substring(j, j+K);
			int key = 0;
			for(int l=0; l<sixStr.length(); l++) {
				key += sixStr.charAt(l);
			}
			key %= 100;
//			System.out.println(key);
			if(h.get(key).isEmpty()) {
				TreeNode treeNode = new TreeNode(sixStr, i, j+1);
				AVLTree hashTree = new AVLTree(treeNode); // new treeNode set as new AVLTree's root
				h.put(key, hashTree);
				
			} else {
				TreeNode treeNode = new TreeNode(sixStr, i, j+1); // same tree, diff node
				ListNode listNode = new ListNode(i, j+1); 
				TreeNode currRoot = h.get(key).getRoot();
				h.get(key).insert(currRoot, treeNode, listNode);
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
	
//	public AVLTree() {
//		root = null;
//	}
	
	public AVLTree(TreeNode<T> root) {
//		super();
		this.root = root;
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}
	
	public void insert(TreeNode<T> currNode, TreeNode<T> newTreeNode, ListNode<T> newListNode) {
		if(currNode == null) { // currNode refers to root at first
			currNode = newTreeNode;
			return;
		}
		
		// java String: lesser when it comes earlier		
		if(newTreeNode.getValue().compareTo(currNode.getValue()) == -1) { // if new item is smaller than curr
			
			// insert left
			currNode = currNode.getLeftChild();
			insert(currNode, newTreeNode, newListNode);
			
			// rotate if needed
			if(currNode.getLeftHeight() > currNode.getRightHeight() + 1) {
				this.rotateRight(currNode);
			}
		} else if(newTreeNode.getValue().compareTo(currNode.getValue()) == 1) { // if new item is larger than curr
			
			// insert right
			currNode = currNode.getRightChild();
			insert(currNode, newTreeNode, newListNode);
			
			// rotate if needed
			if(currNode.getRightHeight() > currNode.getLeftHeight() + 1) {
				this.rotateLeft(currNode);
			}			
		} else { // if new item is equal to current node
			currNode.getItemList().add(newListNode);
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
	private LinkedList<ListNode> itemList;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;	
	private int leftHeight;
	private int rightHeight;
	
	public TreeNode(String value, int i, int j) {
		this.value = value;
		this.itemList = new LinkedList<ListNode>();
		ListNode nodeItem = new ListNode(i, j);
		
		// add to linkedlist right after tree node is created
		itemList.add(nodeItem);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LinkedList<ListNode> getItemList() {
		return itemList;	
	}

	public void setItemList(LinkedList<ListNode> itemList) {
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
		if (this == null) return -1;
		this.leftHeight = 1 + Math.max(this.getLeftChild().getLeftHeight(), this.getLeftChild().getRightHeight());
		return leftHeight;
	}

	public int getRightHeight() {
		if (this == null) return -1;
		this.rightHeight = 1 + Math.max(this.getRightChild().getLeftHeight(), this.getRightChild().getRightHeight());
		return rightHeight;
	}

}

class ListNode<T> {
//	private String item;
	private int i;
	private int j;
	
	public ListNode(int i, int j) {
//		this.item = item;
		this.i = i;
		this.j = j;
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