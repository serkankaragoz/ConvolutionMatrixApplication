import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main3 {

    public static final int BEGINNING_SCREEN = 0;
    public static final int FILE_SELECTION_SCREEN = 1;
    public static final int FILE_SAVING_SCREEN = 2;
    public static final int MATRICE_SELECTION_SCREEN = 4;
    public static File tempFile;


    public static void makeDirectory(String path, String requierdFolder){
        File file = new File(path+ "\\" + requierdFolder);
        if(! file.exists()){
            file.mkdir();
        }
    }

    public static String getExtension(String fileName){
        // https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
        int i = fileName.lastIndexOf('.');
        if(i > 0) {
            return fileName.substring(i + 1);
        }
        else{
            return null;
        }
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

            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                //javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            } catch (Exception e) {
                //Handle Exception
                e.printStackTrace();
                return;
            }


        int state, output;
        state = 0;
        String path; path = System.getProperty("user.dir");
        JFileChooser jf;
        File imageFile, matriceFile; imageFile = null;
        String imagePath, imageFileName; imageFileName = "";
        String kernelPath, kernelFileName; kernelFileName = "";
        BufferedImage firstImage;
        int[][] firstArray;
        int[][][] RGBArray = new int[4][1][1];
        float[][] kernelMatrix;
        int[][] finalArray = new int[5][5];
        boolean makeGray = false;

        Properties titles = new Properties();

        String englishLanguageFilePath = "languages/english.properties";
        String turkishLanguageFilePath = "languages/turkish.properties";

        try {
            titles.load(new FileInputStream(englishLanguageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }





        while(true){
            switch(state){

                case BEGINNING_SCREEN:{
                    output = JOptionPane.showConfirmDialog(null, titles.getProperty("BeginningScreenMessage"),titles.getProperty("BeginningScreenTitle"),JOptionPane.YES_NO_OPTION);
                    //System.out.println(output);
                    switch(output){

                        case 0:{
                            state = FILE_SELECTION_SCREEN;
                            break;
                        }
                        case -1:{
                        }
                        case 1:{
                            return;
                        }

                    }

                    break;
                }

                case FILE_SELECTION_SCREEN:{
                    ImageIcon frameIcon = new ImageIcon(path);

                    tempFile = new File(path + "\\images");
                    tempFile.mkdir();

                    jf = getFilechooser(tempFile, new File(""), titles.getProperty("FileSelectionScreenDialogTitle"), JFileChooser.FILES_ONLY);

                    output = jf.showSaveDialog(null);
                    //System.out.println(output);
                    //System.out.println(output);

                    switch(output){
                        case 1:{
                            state = BEGINNING_SCREEN;
                            break;
                        }
                        case 0:{
                            imageFile = jf.getSelectedFile();
                            imagePath = imageFile.getAbsolutePath();
                            imageFileName = imageFile.getName();

                            try{

                                System.out.println(imageFile.getAbsolutePath());
                                firstImage = ImageCommands.readFromFile(imagePath);

                                firstArray = ImageCommands.imageToArray(firstImage);
                                if(makeGray){
                                    firstArray = ImageCommands.rgbToGray(ImageCommands.imageToArray(firstImage));
                                }

                                RGBArray = ImageCommands.arrayToRGBArray(firstArray);
                                state = MATRICE_SELECTION_SCREEN;

                            }
                            catch (NullPointerException npe){
                                System.out.println(titles.getProperty("FileSelectionScreenWarning"));
                                state = FILE_SELECTION_SCREEN;
                                break;
                            }


                            break;
                        }

                    }

                    break;
                }


                case FILE_SAVING_SCREEN:{
                    tempFile = new File(System.getProperty("user.dir") + "\\images");
                    tempFile.mkdir();
                    final File[] file = {tempFile}; // might need a better solution
                    JFileChooser example = new JFileChooser(){
                        @Override
                        public void approveSelection(){
                            File f = getSelectedFile();
                            file[0] = f;
                            if(f.exists() && getDialogType() == SAVE_DIALOG){
                                int result = JOptionPane.showConfirmDialog(this, f.getName() + titles.getProperty("OverwriteConfirmationMessage"),titles.getProperty("OverwriteConfirmationTitle"),JOptionPane.YES_NO_OPTION);
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



                    tempFile = new File(System.getProperty("user.dir") + "\\images");
                    tempFile.mkdir();

                    example.setCurrentDirectory(tempFile);
                    example.setSelectedFile(new File("changed_" + imageFileName));
                    example.setDialogTitle(titles.getProperty("FileSavingDialogTitle"));
                    example.setFileSelectionMode(JFileChooser.FILES_ONLY);




                    int result = example.showSaveDialog(null);
                    System.out.println(result);
                    state = BEGINNING_SCREEN;
                    if(result == 0){
                        ImageCommands.writeImage(file[0].getPath(), finalArray);
                    }


                    break;
                }

                case MATRICE_SELECTION_SCREEN:{
                    tempFile = new File(System.getProperty("user.dir") + "\\matrices");
                    tempFile.mkdir();
                    jf = getFilechooser(tempFile, new File(""), titles.getProperty("MatrixFileDialogTitle"), JFileChooser.FILES_ONLY);
                    output = jf.showSaveDialog(null);


                    switch(output){
                        case 1:{
                            state = FILE_SELECTION_SCREEN;
                            break;
                        }
                        case 0:{
                            matriceFile = jf.getSelectedFile();
                            kernelPath = matriceFile.getAbsolutePath();

                            try{
                                kernelMatrix = MatriceCommands.getMatriceFromFile(kernelPath);
                                for(int i = 1;i < 4;i++){ ImageCommands.getConvolutionedImage(RGBArray[i], kernelMatrix); }
                                finalArray = ImageCommands.RGBArrayToArray(RGBArray);
                                state = FILE_SAVING_SCREEN;
                            }
                            catch (NullPointerException npe){
                                System.out.println(titles.getProperty("FileSelectionScreenWarning"));
                                state = FILE_SELECTION_SCREEN;
                                break;
                            }
                            catch (FileNotFoundException fnfe){
                                fnfe.printStackTrace();
                            }


                            break;
                        }

                    }

                    break;
                }


            }
        }

    }
}
