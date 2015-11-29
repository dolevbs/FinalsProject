/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import logic.DbManager;
import logic.Manager;

/**
 *
 * @author avira
 */
public class FinalProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        DbManager db = new DbManager();
        db.getProductList();
        db.getUserProductList(1);
        //System.out.println(db.verifyUserAndPassword("test", "test"));
        Manager manager = new Manager();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Goodexpiry_(10).jpg"));
        } catch (IOException e) {
        }
        manager.AddUserProduct(1, "1234", img);
        manager.runDataReader();
    }
}
