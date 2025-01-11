package Control;

import java.util.ArrayList;
import java.util.Map;
import Entity.Product;
import Entity.Order;

// Class representing a node in the AVL tree
class Node { 
    int key; 
    Node left; 
    Node right; 
    int height; 
    ArrayList<Integer> arrayList;
    
    // Constructor to initialize a node with a key
    Node(int k) { 
        key = k; 
        left = null; 
        right = null; 
        height = 1; 
        arrayList = new ArrayList<>();
    }
    
    // Method to add a product ID to the node's list
    public void addProductID(int productID) {
        arrayList.add(productID);
    }

    // Getter for the node's list
    public ArrayList<Integer> getarrayList() {
        return arrayList;
    }
    
    // Getter for the node's key
    public int getKey() {
        return key;
    }
} 

// Class representing an AVL tree
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
    static void inOrderDescendingOrders(Node root, Map<Integer, Order> orders, int k, int[] counter) {
        if (root != null && counter[0] < k) {
            inOrderDescendingOrders(root.right, orders, k, counter);
            if (counter[0] < k) {
                for (int orderId : root.getarrayList()) {
                    if (counter[0] >= k) break;
                    Order order = orders.get(orderId);
                    System.out.printf("%-10d %-40s %-15d%n", 
                        orderId, order.getDestination(), order.getTotalItems());
                    counter[0]++;
                }
            }
            inOrderDescendingOrders(root.left, orders, k, counter);
        }
    }
}

public class Reports {

    // Method to generate and print the inventory report
    public void getInventoryReport() {
        ProdManLogic prodMan = ProdManLogic.getInstance();
        Map<Integer, Product> products = prodMan.getProducts();

        Node root = null;
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int productId = entry.getKey();
            Product product = entry.getValue();
            root = AVLTree.insert(root, product.getQuantity(), productId);
        }
        System.out.println();
        System.out.println("\n---------------------------Inventory Report by Quantity (Asc)---------------------------------");
        // Print the header of the table
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("Product ID  Product Name         Quantity  ");
        System.out.println("----------------------------------------------------------------------------------------------");
        
        // Print the AVL tree in in-order traversal in ascending order
        AVLTree.inOrderAscendingProducts(root, products);

        System.out.println("----------------------------------------------------------------------------------------------");
    }
    
    // Method to generate and print the report of the k biggest orders
    public void getKBiggestOrders(int k) {
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
        System.out.println("\n----------------------------K biggest orders by Quantity report (Desc)-------------------------------");
        // Print the header of the table
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-15s%n", "Order ID", "Destination", "Total Items");
        System.out.println("----------------------------------------------------------------------------------------------");

        // Print the AVL tree in in-order traversal in descending order
        int[] counter = {0}; // Counter to keep track of the number of elements printed
        AVLTree.inOrderDescendingOrders(root, ordMan.getOrders(), k, counter);
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    
    // Method to generate and print the total orders report
    public void totalOrdersReport() {
        System.out.println("------------------------------Total Orders Report (that are in Q)-----------------------------");
        ProdManLogic prodMan = ProdManLogic.getInstance();
        OrdManLogic ordMan = OrdManLogic.getInstance();
        QueNode[] prioQueStart = ordMan.getPrioQueStart(); 
        ArrayList<Integer> unAbleToDeliver = new ArrayList<>();
        ArrayList<Integer> ableToDeliver = new ArrayList<>();
        Map<Integer, Order> orders = ordMan.getOrders();

        for (int priority = 0; priority < prioQueStart.length; priority++) {
            QueNode currentNode = prioQueStart[priority];
            while (currentNode != null) {
                int orderID = currentNode.getOrderID();
                Order order = currentNode.getOrder();
                if (prodMan.checkAvailability(order.getProductsInOrderMap())) {
                    ableToDeliver.add(orderID);
                } else {
                    unAbleToDeliver.add(orderID);
                }
                currentNode = currentNode.getNext();
            }
        }

        // Print the header of the table
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-15s %-10s %-10s%n", "Order ID", "Destination", "Total Items", "Priority", "Able to Deliver");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (int i = 0; i < unAbleToDeliver.size(); i++) {
            Order order = orders.get(unAbleToDeliver.get(i));
            System.out.printf("%-10d %-40s %-15d %-10d %-10s%n", 
                    unAbleToDeliver.get(i), order.getDestination(), order.getTotalItems(), order.getPriority(), "No");
        }
        for (int i = 0; i < ableToDeliver.size(); i++) {
            Order order = orders.get(ableToDeliver.get(i));
            System.out.printf("%-10d %-40s %-15d %-10d %-10s%n", 
                    ableToDeliver.get(i), order.getDestination(), order.getTotalItems(), order.getPriority(), "Yes");
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }
}
