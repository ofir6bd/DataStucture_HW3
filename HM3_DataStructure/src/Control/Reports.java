package Control;

import java.util.ArrayList;
import java.util.Map;
import Entity.Product;
import Entity.Order;

class Node { 
    int key; 
    Node left; 
    Node right; 
    int height; 
    ArrayList<Integer> arrayList;
    
    Node(int k) { 
        key = k; 
        left = null; 
        right = null; 
        height = 1; 
        arrayList = new ArrayList<>();
    }
    
    public void addProductID(int productID) {
        arrayList.add(productID);
    }

    public ArrayList<Integer> getarrayList() {
        return arrayList;
    }
    
    public int getKey() {
        return key;
    }
} 

class AVLTree {
    
    // A utility function to get the height of the tree 
    static int height(Node N) { 
        if (N == null) 
            return 0; 
        return N.height; 
    } 
    
    // A utility function to right rotate subtree rooted with y 
    static Node rightRotate(Node y) { 
        Node x = y.left; 
        Node T2 = x.right; 
    
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
    
        // Update heights 
        y.height = 1 + Math.max(height(y.left), height(y.right)); 
        x.height = 1 + Math.max(height(x.left), height(x.right)); 
    
        // Return new root 
        return x; 
    } 
    
    // A utility function to left rotate subtree rooted with x 
    static Node leftRotate(Node x) { 
        Node y = x.right; 
        Node T2 = y.left; 
    
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
    
        // Update heights 
        x.height = 1 + Math.max(height(x.left), height(x.right)); 
        y.height = 1 + Math.max(height(y.left), height(y.right)); 
    
        // Return new root 
        return y; 
    } 
    
    // Get balance factor of node N 
    static int getBalance(Node N) { 
        if (N == null) 
            return 0; 
        return height(N.left) - height(N.right); 
    } 
    
    // Recursive function to insert a key in the subtree rooted with node 
    static Node insert(Node node, int key, int productID) { 
        // Perform the normal BST insertion
        if (node == null) {
            Node newNode = new Node(key);
            newNode.addProductID(productID);
            return newNode;
        }

        if (key < node.key) {
            node.left = insert(node.left, key, productID);
        } else if (key > node.key) {
            node.right = insert(node.right, key, productID);
        } else { // Equal keys are allowed in AVL tree
            node.addProductID(productID);
            return node;
        }

        // Update height of this ancestor node 
        node.height = 1 + Math.max(height(node.left), height(node.right)); 

        // Get the balance factor of this ancestor node 
        int balance = getBalance(node); 

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case 
        if (balance > 1 && key < node.left.key) 
            return rightRotate(node); 

        // Right Right Case 
        if (balance < -1 && key > node.right.key) 
            return leftRotate(node); 

        // Left Right Case 
        if (balance > 1 && key > node.left.key) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 

        // Right Left Case 
        if (balance < -1 && key < node.right.key) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 

        // Return the (unchanged) node pointer 
        return node; 
    }

    // A utility function to print in-order traversal of the tree in ascending order
    static void inOrderAscendingProducts(Node root, Map<Integer, Product> products) { 
        if (root != null) {
        	inOrderAscendingProducts(root.left, products);
            for (int productId : root.getarrayList()) {
                Product product = products.get(productId);
                System.out.println(String.format("%-10d %-20s %-10d", 
                    productId, product.getProductName(), product.getQuantity()));
            }
            inOrderAscendingProducts(root.right, products);  
        } 
    }
    
 // A utility function to print in-order traversal of the tree in descending order
    static void inOrderDescendingOrders(Node root, Map<Integer, Order> orders) {
        if (root != null) {
            inOrderDescendingOrders(root.right, orders);
            for (int orderId : root.getarrayList()) {
                Order order = orders.get(orderId);
                System.out.printf("%-10d %-40s %-15d%n", 
                    orderId, order.getDestination(), order.getTotalItems());
            }
            inOrderDescendingOrders(root.left, orders);
        }
    }
    
}


public class Reports {

    
    public void getInventoryReport() {
        ProdManLogic prodMan = ProdManLogic.getInstance();
        Map<Integer, Product> products = prodMan.getProducts();

        Node root = null;
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int  productId = entry.getKey();
            Product product = entry.getValue();
            root = AVLTree.insert(root, product.getQuantity(), productId);
        }
        System.out.println();
        System.out.println("-------Inventory Report by Quantity (Asc)-------");
        // Print the header of the table
        System.out.println("-------------------------------------------------");
        System.out.println("Product ID  Product Name         Quantity  ");
        System.out.println("-------------------------------------------------");
        
        // Print the AVL tree in in-order traversal in ascending order
        AVLTree.inOrderAscendingProducts(root, products);
        
        System.out.println("-------------------------------------------------");
    }
    
    public void getKBiggestOrders() {
        System.out.println("----------K biggest Orders Report----------");
        OrdManLogic ordMan = OrdManLogic.getInstance();
        QueNode[] prioQueStart = ordMan.getPrioQueStart(); // Get the priority queues

        Node root = null;
        for (int priority = 0; priority < prioQueStart.length; priority++) {
            QueNode currentNode = prioQueStart[priority];
            while (currentNode != null) {
                int orderID = currentNode.getOrderID();
                Order order = currentNode.getOrder();
                root = AVLTree.insert(root, order.getTotalItems(), orderID);
                currentNode = currentNode.getNext();
            }
        }

        System.out.println();
        System.out.println("-------Orders Report by Quantity (Desc)-------");
        // Print the header of the table
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-15s%n", "Order ID", "Destination", "Total Items");
        System.out.println("----------------------------------------------------------------");

        // Print the AVL tree in in-order traversal in descending order
        AVLTree.inOrderDescendingOrders(root, ordMan.getOrders());

        System.out.println("----------------------------------------------------------------");
    }
}
