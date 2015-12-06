package logic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public java.util.Date readExpirationDate(ByteArrayInputStream expirationDateImage) throws Exception {
        File outputfile = new File("tmp.jpg");
        //BufferedImage
        System.out.println(outputfile.getAbsolutePath());
        outputfile = null;
        InputStream in = expirationDateImage;
        OutputStream out = new FileOutputStream("tmp.jpg");

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
//        ImageIO.write(expirationDateImage, "jpg", outputfile);
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
        ByteArrayInputStream expirationDateImage = stringToImage(imgBuffer);
//        BufferedImage expirationDateImage =  ImageIO.read(new File("C:\\CodeBase\\FinalsProject\\Server\\FinalProjectServer\\FinalProjectServer\\DateReadExe\\for_redistribution_files_only\\finalpic2.jpg"));
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
        return (productName != null ? productName : "Unknown product");
    }

    public String runDataReader() {
        try {
            Process p = Runtime.getRuntime().exec("\"C:\\CodeBase\\FinalsProject\\Server\\FinalProjectServer\\FinalProjectServer\\DateReadExe\\for_redistribution_files_only\\DatereadExe.exe\" tmp.jpg");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            System.out.println(line);
            input.close();
            p.destroy();
            return line;

        } catch (Exception ex) {/*handle exception*/
            System.out.println(ex);
            ex.printStackTrace();
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

    public ByteArrayInputStream stringToImage(String imageString) {
        //string ByteArrayInputStream ByteArrayInputStream
        BufferedImage bImage = null;
        BASE64Decoder b64dec = new BASE64Decoder();      
        System.err.println("the img");
        System.out.println(imageString);
        //System.err.println(imageString);
        ByteArrayInputStream bais = null;
        try {
            byte[] output = b64dec.decodeBuffer(imageString);
            bais = new ByteArrayInputStream(output);
            //bImage = ImageIO.read(bais);
        } catch (IOException e) {
            System.err.println("Error");
            System.err.println(e);
            e.printStackTrace();
        }
        //System.out.println(bImage);
        return bais;
    }

}
