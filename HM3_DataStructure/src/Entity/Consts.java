package Entity;

import java.net.URLDecoder;

public final class Consts {
    private Consts() {
        throw new AssertionError();
    }

    protected static final String DB_FILEPATH = getDBPath();
    public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
    
    /*----------------------------------------- Products QUERIES -----------------------------------------*/
    public static final String SQL_SEL_ALL_PROD = "SELECT * FROM Products";
    
    /*----------------------------------------- Orders QUERIES -----------------------------------------*/
    public static final String SQL_SEL_ALL_ORDERS = "SELECT * FROM Orders";
    
    /*----------------------------------------- ProductsInOrder QUERIES -----------------------------------------*/
    public static final String SQL_SEL_ALL_PRODUCTS_IN_ORDER = "SELECT * FROM ProductsInOrder";
    
    // Private method to get the database path
    private static String getDBPath() {
        try {
            // Get the path of the class file
            String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decoded = URLDecoder.decode(path, "UTF-8");
            if (decoded.contains(".jar")) {
                // If running from a JAR, get the directory of the JAR file
                decoded = decoded.substring(0, decoded.lastIndexOf('/'));
                return decoded + "/database/OrdersProductsDB.accdb";
            } else {
                // If running from a class file, get the directory of the class file
                decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
                return decoded + "src/Entity/OrdersProductsDB.accdb";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}