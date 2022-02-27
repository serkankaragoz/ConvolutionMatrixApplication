import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    /*
        String path = System.getProperty("user.dir");
        JFileChooser jf = new JFileChooser();
        jf.setCurrentDirectory(new File(path));
        jf.setSelectedFile(new File("Ser.txt"));
        jf.setDialogTitle("Filtre uygulanacak resim dosyasini seciniz:");
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.showSaveDialog(null);
        File f = jf.getSelectedFile();
     */

    public static void openBeginningScreen(){

    }

    public static JFileChooser getFilechooser(File initialPath, File selectedFile, String dialogTitle, int fileSelectionMode){
        JFileChooser jf = new JFileChooser();
        if(initialPath != null){
            jf.setCurrentDirectory(initialPath);
        }
        if(selectedFile != null){
            jf.setSelectedFile(selectedFile);
        }
        if(dialogTitle != null){
            jf.setDialogTitle(dialogTitle);
        }

        jf.setFileSelectionMode(fileSelectionMode);
        //jf.showSaveDialog(null);
        return jf;
    }



    public static void main(String[] args) {

        // write your code here
        /*
        Object [] options1 = {"Go Back", "Accept"};
        JOptionPane jop = new JOptionPane("Mean arterial pressure restored.\nReassess all vitals STAT.", JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options1, options1[0]);
        JDialog dialog = jop.createDialog(null, "Title");
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
// In real code, you should invoke this from AWT-EventQueue using invokeAndWait() or something
        dialog.setVisible(true);
// and would cast in a safe manner
        String a3 = (String) jop.getValue();
        if (a3.equals("Accept")) {

        } else if (a3.equals("Go Back")) {

        }
// don't forget to dispose of the dialog
        dialog.dispose();
        */


        /*
        JFileChooser example = new JFileChooser(){
            @Override
            public void approveSelection(){
                File f = getSelectedFile();
                if(f.exists() && getDialogType() == SAVE_DIALOG){
                    int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                    switch(result){
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
         */


        {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                //javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            } catch (ClassNotFoundException ex) {
                //Handle Exception
            } catch (InstantiationException ex) {
                //Handle Exception
            } catch (IllegalAccessException ex) {
                //Handle Exception
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                //Handle Exception
            }
        }



        String path = System.getProperty("user.dir");
        JFileChooser jf = getFilechooser(new File(System.getProperty("user.dir")), new File(""), "Filtre uygulanacak resim dosyasini seciniz:", JFileChooser.FILES_ONLY);
        int selection = jf.showSaveDialog(null);
        System.out.println(selection);

        File f = jf.getSelectedFile();
        String imagePath = f.getAbsolutePath();
        String fileName = f.getName();

        try{
            System.out.println(f.getAbsolutePath());
        }
        catch (NullPointerException npe){
            System.out.println("ERROR! You haven't selected any directory");
        }

        BufferedImage firstImage = ImageCommands.readFromFile(imagePath);

        //int[][] firstArray = ImageCommands.imageToArray(firstImage);
        int[][] firstArray = ImageCommands.rgbToGray(ImageCommands.imageToArray(firstImage));

        int[][][] RGBArray = ImageCommands.arrayToRGBArray(firstArray);


        float[][] kernelMatrix = ImageCommands.sharpKernel;
        //float[][] kernelMatrix = ImageCommands.getEqualWeightedArray(9);
        for(int i = 1;i < 4;i++){
            ImageCommands.getConvolutionedImage(RGBArray[i], kernelMatrix);
        }

        int[][] finalArray = ImageCommands.RGBArrayToArray(RGBArray);

        jf = getFilechooser(new File(System.getProperty("user.dir")), new File("changed_" + fileName), "Kaydedilecek dosya konumunu seçiniz:", JFileChooser.FILES_ONLY);
        jf.showOpenDialog(null);
        f = jf.getSelectedFile();

        System.out.println(f.getAbsolutePath());
        if(f.exists()){
            //int dialogResult = JOptionPane.showConfirmDialog (null, f.getName() + " zaten var.\nVarolan dosya ile değiştirilsin mi?","Farklı Kaydetmeyi Onayla",JOptionPane.YES_NO_OPTION);
            JFileChooser example = new JFileChooser(){
                @Override
                public void approveSelection(){
                    File f = getSelectedFile();
                    if(f.exists() && getDialogType() == SAVE_DIALOG){
                        int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                        switch(result){
                            case JOptionPane.YES_OPTION:
                                super.approveSelection();
                                return;
                            case JOptionPane.NO_OPTION:
                                return;
                            case JOptionPane.CLOSED_OPTION:
                                return;
                            case JOptionPane.CANCEL_OPTION:
                                cancelSelection();
                                return;
                        }
                    }
                    super.approveSelection();
                }
            };
            example.showOpenDialog(null);


        }
        else{
            //ImageCommands.writeImage("images\\DSC_0103." + ImageCommands.FILE_FORMAT, finalArray);
            ImageCommands.writeImage(f.getAbsolutePath(), finalArray);
        }





        System.out.println("OVER");

    }
}
