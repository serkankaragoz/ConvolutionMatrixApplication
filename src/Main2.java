import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

public class Main2 {

    public static final int BEGINNING_SCREEN = 0;
    public static final int FILE_SELECTION_SCREEN = 1;
    public static final int FILE_SAVING_SCREEN = 2;
    public static final int FILE_OVERWRITE_SCREEN = 3;
    public static final int MATRICE_SELECTION_SCREEN = 4;

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

        MatriceCommands.printMatrix(new float[][]{{1, 2},{3, 4}});
        try{
            float n = MatriceCommands.stringToFloat("1.5");
            float[][] matrice = MatriceCommands.getMatriceFromFile("E:\\kodlama hakkinda\\java\\Java convolution deneme\\ConvolutionDeneme\\matrices\\test_matrice.txt");
            MatriceCommands.printMatrix(matrice);
        }
        catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }

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
        File f;
        String imagePath = null, fileName; fileName = "";
        BufferedImage firstImage;
        int[][] firstArray;
        int[][][] RGBArray;
        float[][] kernelMatrix;
        int[][] finalArray = new int[5][5];

        String kernelPath;



        while(true){
            switch(state){

                case BEGINNING_SCREEN:{
                    output = JOptionPane.showConfirmDialog (null, " Lütfen gerçekleştirmek istediğiniz işlemi seçiniz:","Convolution matrix sihirbazı",JOptionPane.YES_NO_OPTION);
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

                    jf = getFilechooser(new File(path), new File(""), "Filtre uygulanacak resim dosyasini seciniz:", JFileChooser.FILES_ONLY);
                    output = jf.showSaveDialog(null);
                    //System.out.println(output);
                    //System.out.println(output);

                    switch(output){
                        case 1:{
                            state = BEGINNING_SCREEN;
                            break;
                        }
                        case 0:{
                            f = jf.getSelectedFile();
                            imagePath = f.getAbsolutePath();
                            fileName = f.getName();

                            try{
                                state = FILE_SAVING_SCREEN;
                                System.out.println(f.getAbsolutePath());
                                firstImage = ImageCommands.readFromFile(imagePath);

                                //firstArray = ImageCommands.imageToArray(firstImage);
                                firstArray = ImageCommands.rgbToGray(ImageCommands.imageToArray(firstImage));

                                RGBArray = ImageCommands.arrayToRGBArray(firstArray);

                                kernelMatrix = ImageCommands.sharpKernel;
                                //kernelMatrix = ImageCommands.getEqualWeightedArray(9);
                                for(int i = 1;i < 4;i++){ ImageCommands.getConvolutionedImage(RGBArray[i], kernelMatrix); }

                                finalArray = ImageCommands.RGBArrayToArray(RGBArray);


                            }
                            catch (NullPointerException npe){
                                System.out.println("ERROR! You haven't selected any directory");
                                state = FILE_SELECTION_SCREEN;
                                break;
                            }


                            break;
                        }

                    }

                    break;
                }


                case FILE_SAVING_SCREEN:{
                    final File[] file = {new File(System.getProperty("user.dir"))}; // might need a better solution
                    JFileChooser example = new JFileChooser(){
                        @Override
                        public void approveSelection(){
                            File f = getSelectedFile();
                            file[0] = f;
                            if(f.exists() && getDialogType() == SAVE_DIALOG){
                                int result = JOptionPane.showConfirmDialog(this, f.getName() + " zaten var.\nVarolan dosya ile değiştirilsin mi?","Farklı Kaydetmeyi Onayla",JOptionPane.YES_NO_OPTION);
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



                    example.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    example.setSelectedFile(new File("changed_" + fileName));
                    example.setDialogTitle("Kaydedilecek dosya konumunu seçiniz:");
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
                    jf = getFilechooser(new File(path), new File(""), "Matris dosyasini seciniz:", JFileChooser.FILES_ONLY);
                    output = jf.showSaveDialog(null);


                    switch(output){
                        case 1:{
                            state = FILE_SELECTION_SCREEN;
                            break;
                        }
                        case 0:{
                            f = jf.getSelectedFile();
                            kernelPath = f.getAbsolutePath();
                            fileName = f.getName();

                            try{
                                state = FILE_SAVING_SCREEN;
                                System.out.println(f.getAbsolutePath());
                                firstImage = ImageCommands.readFromFile(imagePath);

                                //firstArray = ImageCommands.imageToArray(firstImage);
                                firstArray = ImageCommands.rgbToGray(ImageCommands.imageToArray(firstImage));

                                RGBArray = ImageCommands.arrayToRGBArray(firstArray);

                                kernelMatrix = ImageCommands.sharpKernel;
                                //kernelMatrix = ImageCommands.getEqualWeightedArray(9);
                                for(int i = 1;i < 4;i++){ ImageCommands.getConvolutionedImage(RGBArray[i], kernelMatrix); }

                                finalArray = ImageCommands.RGBArrayToArray(RGBArray);


                            }
                            catch (NullPointerException npe){
                                System.out.println("ERROR! You haven't selected any directory");
                                state = FILE_SELECTION_SCREEN;
                                break;
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
