package Control;

import java.util.ArrayList;
import java.util.Map;
import Entity.Product;

class Node { 
    int key; 
    Node left; 
    Node right; 
    int height; 
    ArrayList<Integer> productsIDs;
    
    Node(int k) { 
        key = k; 
        left = null; 
        right = null; 
        height = 1; 
        productsIDs = new ArrayList<>();
    }
    
    public void addProductID(int productID) {
        productsIDs.add(productID);
    }

    public ArrayList<Integer> getProductsIDs() {
        return productsIDs;
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

    // A utility function to print in-order traversal of the tree in descending order
    static void inOrderDescending(Node root, Map<Integer, Product> products) { 
        if (root != null) {
            inOrderDescending(root.right, products);
            for (int productId : root.getProductsIDs()) {
                Product product = products.get(productId);
                System.out.println(String.format("%-10d %-20s %-10d", 
                    productId, product.getProductName(), product.getQuantity()));
            }
            inOrderDescending(root.left, products);  
        } 
    }
}


public class Reports {

    public void getKBiggestOrders() {
        System.out.println("----------K biggest Orders Report----------");
    }

    public void getInventoryReport() {
        ProdManLogic prodMan = ProdManLogic.getInstance();
        Map<Integer, Product> products = prodMan.getProducts();

        System.out.println("----------Inventory Report----------");
        Node root = null;
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int  productId = entry.getKey();
            Product product = entry.getValue();
            root = AVLTree.insert(root, product.getQuantity(), productId);
        }
        
        // Print the header of the table
        System.out.println("-------------------------------------------------");
        System.out.println("Product ID  Product Name         Quantity  ");
        System.out.println("-------------------------------------------------");
        
        // Print the AVL tree in in-order traversal in descending order
        AVLTree.inOrderDescending(root, products);
        
        System.out.println("-------------------------------------------------");
    }
}
