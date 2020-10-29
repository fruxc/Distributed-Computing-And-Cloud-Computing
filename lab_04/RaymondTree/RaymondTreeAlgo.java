package lab_04.RaymondTree;

import java.util.*;

class Tree {
	Node root = null;
}

class Node {
	int data;
	ArrayList<Node> reqlist;
	Node left = null;
	Node right = null;

	public Node() {
		reqlist = new ArrayList<Node>();
	}
}

public class RaymondTreeAlgo {

	Tree t;
	Node token;
	StringBuilder sb;

	public RaymondTreeAlgo() {
		t = new Tree();
		token = null;
		sb = new StringBuilder();
	}

	void createRoot(Node temp) {
		t.root = temp;
		token = temp;
	}

	Node createNode(int value) {

		Node temp = new Node();
		temp.data = value;
		return temp;
	}

	void leftChild(Node temp, Node left) {
		temp.left = left;
	}

	void rightChild(Node temp, Node right) {
		temp.right = right;
	}

	void updateRequestList(Node temp) {

	}

	int getTokenNode() {
		return token.data;
	}

	void makeRequest(Node temp) {
		Node tempRoot = t.root;
		Node parent = search(tempRoot, temp);
		if (parent != null && parent != temp) {
			temp.reqlist.add(temp);
			System.out.println("add request of " + temp.data + " in requestList of " + temp.data);
			System.out.println(temp.data + " make request for CS to" + parent.data);
			makeRequest(parent, temp);
		} else {
			System.out.println("It itself is a root node" + temp.data);
			token = temp;
			t.root = temp;
		}
	}

	void makeRequest(Node temp, Node reqNode) {
		Node tempRoot = t.root;
		if (tempRoot == temp) {
			System.out.println("Root node found /n process child request of " + reqNode.data);
			temp.reqlist.add(temp);
			processRequest(temp, reqNode);
		} else {
			Node parent = search(tempRoot, temp);
			if (parent != null) {
				System.out.println("add request of " + reqNode.data + " in requestList of " + temp.data);
				System.out.println(temp.data + " make request for CS to" + parent.data);
				temp.reqlist.add(reqNode);
				makeRequest(parent, temp);
			} else {
				System.out.println("Parent of " + temp.data + " null");
			}
		}
	}

	void processRequest(Node temp, Node reqNode) {

		temp.reqlist.remove(reqNode);
		System.out.println(reqNode.data + " Request removed from " + temp.data);
		token = reqNode;
		if (temp.left == reqNode) {
			temp.left = null;
			Node tempory = reqNode.left;
			reqNode.left = temp;
			temp.left = tempory;
			String childLeft = "null";
			if (tempory != null) {
				childLeft = "" + tempory.data;
			}
			System.out
					.println("new parent :" + reqNode.data + " " + " child :" + temp.data + " child Left " + childLeft);
		} else {
			temp.right = null;
			Node tempory = reqNode.right;
			reqNode.right = temp;
			temp.right = tempory;
			String childRight = "null";
			if (tempory != null) {
				childRight = "" + tempory.data;
			}
			System.out.println(
					"new parent :" + reqNode.data + " " + " child :" + temp.data + " child Left " + childRight);
		}
		if (temp == reqNode) {
			t.root = reqNode;
			token = reqNode;
			System.out.println("New Root it is :" + token.data);
		} else {
			System.out.println("ProcessRequest " + reqNode.data + " : " + reqNode.reqlist.get(0).data);
			processRequest(reqNode, reqNode.reqlist.get(0));
		}
	}

	Node search(Node r, Node s) {
		Node tempL = null, tempR = null;
		if (r == s) {
			return r;
		}
		if (r.left != null) {
			if (r.left == s) {
				return r;
			} else {
				// System.out.println("Compare "+r.left.data+" and "+s.data);
				tempL = search(r.left, s);

			}
		}
		if (r.right != null) {
			if (r.right == s) {
				return r;
			} else {
				// System.out.println("Compare "+r.right.data+" and "+s.data);
				tempR = search(r.right, s);
			}
		}

		if (tempL != null) {
			return tempL;
		} else if (tempR != null) {
			return tempR;
		} else {
			return null;
		}
	}

	void printTree() {
		Node temp = t.root;
		sb.delete(0, sb.length());
		System.out.println("R_Node:" + temp.data);
		sb.append("" + temp.data);
		printTree(temp);
		System.out.println("IN ORDER :" + sb.toString());
	}

	void printTree(Node n) {
		if (n.left != null) {
			System.out.println("L_Node" + n.left.data);
			sb.insert(0, "" + n.left.data);
			System.out.println(":" + sb.toString());
			printTree(n.left);
		}
		if (n.right != null) {
			System.out.println("R_Node" + n.right.data);
			sb.insert(sb.length(), "" + n.right.data);
			System.out.println(":" + sb.toString());
			printTree(n.right);
		}
	}

	public static void main(String str[]) {
		RaymondTreeAlgo ra = new RaymondTreeAlgo();

		Node n1 = ra.createNode(1);
		Node n2 = ra.createNode(2);
		Node n3 = ra.createNode(3);
		Node n4 = ra.createNode(4);
		Node n5 = ra.createNode(5);
		Node n6 = ra.createNode(6);
		Node n7 = ra.createNode(7);
		Node n8 = ra.createNode(8);

		ra.createRoot(n1);
		ra.leftChild(n1, n2);
		ra.rightChild(n1, n3);
		ra.leftChild(n2, n4);
		ra.rightChild(n2, n5);
		ra.leftChild(n3, n6);
		ra.rightChild(n3, n7);
		ra.leftChild(n4, n8);
		ra.printTree();
		ra.makeRequest(n6);
	}
}