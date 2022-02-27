import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDisplayer extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;

    public void setBoundsAtMiddle(int width, int height){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();



        int newX = (screenWidth - width)/2;
        int newY = (screenHeight - height)/2;

        setBounds(newX,newY,width,height);

    }

    public ImageDisplayer(){

        final int[] index = {0};


        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("E:\\kodlama hakkinda\\java\\Java convolution deneme\\ConvolutionDeneme\\images\\lena.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        //panel1.add(picLabel);

        add(panel1);

        setTitle("Yeni fotoğrafın önizlemesi");

        setBoundsAtMiddle(600,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // açılan form kapatıldığında, formu RAM üzerinden silmeye yarayan komut

        button1.setText("Kaydet");
        button2.setText("İptal");


    }

}
