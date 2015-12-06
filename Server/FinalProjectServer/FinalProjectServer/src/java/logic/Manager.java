package logic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

    public boolean AddUserProduct(String user, String barcode, String imgBuffer) throws Exception {
        BufferedImage expirationDateImage = stringToImage(imgBuffer);
        int userId = Integer.parseInt(user);
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

    public String getUserProductList(String id) throws Exception {
        return dbManager.getUserProductList(Integer.parseInt(id));
    }

    public String imageToString(BufferedImage bImage, String path) {
        String imageString = null;

        String formatName = path.substring(path.lastIndexOf('.') + 1, path.length());

        //image to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, formatName, baos);
            baos.flush();
            byte[] imageAsRawBytes = baos.toByteArray();
            baos.close();

            //bytes to string
            BASE64Encoder b64enc = new BASE64Encoder();
            imageString = b64enc.encode(imageAsRawBytes);
            //don't do this!!!
            //imageString = new String(imageAsRawBytes);
        } catch (IOException ex) {
        }

        return imageString;
    }

    public BufferedImage stringToImage(String imageString) {
        //string to ByteArrayInputStream
        BufferedImage bImage = null;
        BASE64Decoder b64dec = new BASE64Decoder();
        try {
            byte[] output = b64dec.decodeBuffer(imageString);
            ByteArrayInputStream bais = new ByteArrayInputStream(output);
            bImage = ImageIO.read(bais);
        } catch (IOException e) {
            System.err.println("Error");
        }

        return bImage;
    }

}
