package util;

public class ReverseLinkedList {

	public static void main(String[] args) {
		int noOfItems = 10;
		
		Node head = buildList(noOfItems);
		System.out.println("--------------------Current List--------------------");
		printList(head);
		
		System.out.println("----------------Reversed List-------------------");
		Node reversedHead = reverse(head);
		printList(reversedHead);
	}

	
	private static Node reverse(Node head) {
		Node newHead = reverseRecursive(head);// This will reverse the list one by one from first to last
		head.next = null; // set the old head next as null
		return newHead;
	}
	
	private static Node reverseRecursive(Node node) {
		if (node == null) {
			return null;
		} else if (node.next == null) {
			return node;// return the last node which is the head for reversed list
		}
		Node tail = node.next;
		Node newHead = reverseRecursive(tail);// recursively call reverse for each node
		tail.next = node; //reverse the list at each node by pointing tail's next to current node
		return newHead;
	}

	private static void printList(Node node) {
	   while (node != null ) {
		   System.out.print(node.data);
		   node = node.next;
		   if (node != null) {
			   System.out.print("-->");
		   }
	   }
	   System.out.println();
	}

	private static Node buildList(int noOfNodes) {
		Node head = new Node();
		head.data = 1;
		Node current = head;
		for (int i = 2; i <= noOfNodes ; i++) {
			Node newNode = new Node();
			newNode.data = i;
			current.next = newNode;
			current = newNode;
		}
		return head;
	}

	private static class Node {
		int data;
		Node next;
	}

}
