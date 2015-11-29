package logic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Manager {

    DbManager dbManager;

    public Manager() throws SQLException, ClassNotFoundException {
        dbManager = new DbManager();
    }

    public java.util.Date readExpirationDate(BufferedImage expirationDateImage) throws Exception {
        File outputfile = new File("tmp.jpg");
        ImageIO.write(expirationDateImage, "jpg", outputfile);
        String date = runDataReader();
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        java.util.Date expirationDate = format.parse(date);
        return expirationDate;
    }

    public int loginAuthentication(String user, String password) {
        int userId = -1;
        try {
            if (dbManager.verifyUserAndPassword(user, password)) {
                userId = dbManager.getUserIdByName(user);
            }
        } catch (Exception ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userId;
    }

    public boolean AddUserProduct(int userId, String barcode, BufferedImage expirationDateImage) throws Exception {
        if (dbManager.verifyUserExist(userId)) {
            java.util.Date expirationDate = readExpirationDate(expirationDateImage);
            int productId = dbManager.getProductIdByBarcode(barcode);
            dbManager.addProductUser(userId, productId, expirationDate);
            return true;
        }
        return false;
    }

    public String getProductName(String barcode) throws Exception {
        String productName = dbManager.getProductByBarcode(barcode);
        return (productName == null ? productName : "Unknown product");
    }

    public String runDataReader() {
        try {          
            Process p = Runtime.getRuntime().exec("DateReadExe\\for_redistribution_files_only\\DateReadExe.exe tmp.jpg");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            System.out.println(line);
            input.close();
            p.destroy();
            return line;

        } catch (Exception ex) {/*handle exception*/
            return null;
        }
    }
}
