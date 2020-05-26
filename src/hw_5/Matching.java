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

	private static void command(String input)
	{
		// TODO : 아래 문장을 삭제하고 구현해라.
//		System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
		Hashtable<Integer, AVLTree> h = new Hashtable<Integer, AVLTree>();
//		h.put(key, value);

		// extract substring from input 
		int i = 0;
		final int k = 6;
		for(int j=0; j<=input.length()-k; j++) {
//			System.out.print(input.substring(j, j+k));
			i++;
			String sixStr = input.substring(j, j+k);
			int key = 0;
			for(int l=0; l<sixStr.length(); l++) {
				key += sixStr.charAt(l);
			}
			key %= 100;
//			System.out.println(key);
			if(h.get(key).isEmpty()) {
				AVLTree hashTree = new AVLTree(new TreeNode(new LinkedList<ListNode>()));
				h.put(key, hashTree);
				
			} else {
				h.get(key).insert(new TreeNode(new LinkedList<ListNode>()));
			}
		}
	}
	
}

class AVLTree<T> {
	private TreeNode<T> root;
	
	public AVLTree(TreeNode<T> root) {
		super();
		this.root = root;
	}

	public void insert(TreeNode<T> item) {
		
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void traverse() {
		
	}
	
	public void rotateLeft() {
		
	}
	
	public void rotateRight() {
		
	}
}

class TreeNode<T> {	
//	private String key;
	private LinkedList<ListNode> itemList;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;
	private int leftHeight;
	private int rightHeight;
	
	public TreeNode(LinkedList<ListNode> itemList) {
		this.itemList = itemList;
	}
	
	public TreeNode(LinkedList<ListNode> itemList, TreeNode<T> leftChild, TreeNode<T> rightChild) {
		super();
		this.itemList = itemList;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
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

	public int getLeftHeight() {
		return leftHeight;
	}

	public void setLeftHeight(int leftHeight) {
		this.leftHeight = leftHeight;
	}

	public int getRightHeight() {
		return rightHeight;
	}

	public void setRightHeight(int rightHeight) {
		this.rightHeight = rightHeight;
	}
}

class ListNode<T> {
	private String item;
	private int i;
	private int j;
	
	public ListNode(String item, int i, int j) {
		this.item = item;
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