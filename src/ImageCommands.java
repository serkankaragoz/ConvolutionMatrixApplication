import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCommands{

    /*
    public static final float[][] blurKernel = {{1/9f, 1/9f, 1/9f},{1/9f, 1/9f, 1/9f},{1/9f, 1/9f, 1/9f}};
    public static final float[][] blurKernel2 = {{1/16f, 2/16f, 1/16f},{2/16f, 4/16f, 2/16f},{1/16f, 2/16f, 1/16f}};
    public static final float[][] blurKernel3 = {{1/256f, 4/256f, 6/256f, 4/256f, 1/256f},{4/256f, 16/256f, 24/256f, 16/256f, 4/256f},{6/256f, 24/256f, 36/256f, 24/256f, 3/256f},{4/256f, 16/256f, 24/256f, 16/256f, 4/256f},{1/256f, 4/256f, 6/256f, 4/256f, 1/256f}};
    public static final float[][] blurKernel4 = {{1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}, {1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f, 1/49f}};
    public static final float[][] edgeKernel = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}};
    public static final float[][] emboss = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}}; // bu matrisi denemedim


    public static final float[][] identityKernel = {{0f, 0f, 0f},{0f, 1f, 0f},{0f, 0f, 0f}};
    public static final float[][] denemeKernel = {{0f, -1f, 0f},{-1f, 5f, -1f},{0f, -1f, 0f}};
    */
    public static final float[][] sharpKernel = {{-1f, -1f, -1f},{-1f, 8f, -1f},{-1f, -1f, -1f}};

    public static final int BYTE = 256;
    public static final int ALPHA_DIVISOR = BYTE*BYTE*BYTE;
    public static final int RED_DIVISOR = BYTE*BYTE;
    public static final int GREEN_DIVISOR = BYTE;
    public static final int BLUE_DIVISOR = 1;

    public static final String FILE_FORMAT = "jpg";


    // converts a string path to image
    public static BufferedImage readFromFile(String path) {

        BufferedImage image;

        File file = new File(path);

        try {
            image = ImageIO.read(file);
            return image;
        } catch (IOException e) {
            System.out.println("resim hic olusturulmadi");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static int[][] imageToArray(BufferedImage img){

        int[][] arr = new int[img.getWidth()][img.getHeight()];

        for(int i = 0;i < arr.length;i++){
            for(int j = 0;j < arr[0].length;j++){
                arr[i][j] = img.getRGB(i, j);
            }
        }


        return arr;
    }


    public static void writeImage(String path, int[][] color) {
        BufferedImage image = new BufferedImage(color.length, color[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < color.length; x++) {
            for (int y = 0; y < color[0].length; y++) {
                image.setRGB(x, y, (int)color[x][y]);
            }
        }

        File ImageFile = new File(path);
        try {
            ImageIO.write(image, FILE_FORMAT, ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *   arr[0][][] for red
    *  arr[1][][] for green
    *   arr[2][][] for blue
    *
     */
    public static int[][][] arrayToRGBArray(int[][] arr){
        int[][][] RGB = new int[4][arr.length][arr[0].length];

        RGB[0] = getAlphaArray(arr);
        RGB[1] = getRedArray(arr);
        RGB[2] = getGreenArray(arr);
        RGB[3] = getBlueArray(arr);



        return RGB;
    }

    public static int[][] RGBArrayToArray(int[][][] RGB){
        int[][] arr = new int[RGB[0].length][RGB[0][0].length];

        for(int i = 0;i < arr.length;i++){
            for(int j = 0;j < arr[0].length;j++){
                arr[i][j] =  (RGB[0][i][j] * ALPHA_DIVISOR) + (RGB[1][i][j] * RED_DIVISOR + RGB[2][i][j] * GREEN_DIVISOR + RGB[3][i][j] * BLUE_DIVISOR);
            }
        }


        return arr;
    }


    private static int[][] divideArray(int[][] arr2, DivideAndModulo format){
        int[][] arr = new int[arr2.length][arr2[0].length];

        for(int i = 0;i < arr.length;i++){
            for(int j = 0;j < arr[0].length;j++){
                arr[i][j] = format.run(arr2[i][j]);
            }
        }
        return arr;
    }

    public static int[][] getAlphaArray(int[][] arr){
        return divideArray(arr, ImageCommands::getAlpha);
    }

    public static int[][] getRedArray(int[][] arr){
        return divideArray(arr, ImageCommands::getRed);
    }

    public static int[][] getGreenArray(int[][] arr){
        return divideArray(arr, ImageCommands::getGreen);
    }

    public static int[][] getBlueArray(int[][] arr){
        return divideArray(arr, ImageCommands::getBlue);
    }


    public static int getAlpha(int RGB){
        return (RGB & 0xff000000) >> 24;
    }

    public static int getRed(int RGB){
        return (RGB & 0xff0000) >> 16;
    }

    public static int getGreen(int RGB){
        return (RGB & 0xff00) >> 8;
    }

    public static int getBlue(int RGB){
        return RGB & 0xff;
    }

    public static int compressNumber(int number, int lowerBound, int upperBound){
        return (number > upperBound) ? upperBound : (number < lowerBound) ? lowerBound: number;
    }

    public static float[][] getEqualWeightedArray(int size){
        float coeff = (1f / (size * size));
        float[][] matrix = new float[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = coeff;
            }
        }
        return matrix;
    }

    public static void getConvolutionedImage(int[][] imageArray, float[][] convolutionMatrix){

        int[][] newArray = new int[imageArray.length][imageArray[0].length];


        int frameWidth = (convolutionMatrix.length - 1) / 2;
        int height = imageArray.length;
        int width = imageArray[0].length;


        for(int i = 0; i < frameWidth;i++){
            newArray[i] = imageArray[i].clone();
            newArray[height - i - 1] = imageArray[height - i - 1].clone();
        }

        for(int i = frameWidth;i < imageArray.length - frameWidth;i++){
            for(int j = 0; j < frameWidth;j++){
                newArray[i][j] = imageArray[i][j];
                newArray[i][width - j - 1] = imageArray[i][width - j - 1];
            }
        }



        try{
            if(convolutionMatrix.length != convolutionMatrix[0].length){
                throw new Exception("This matrice is not NxN");
            }
            float pixel;

            for(int i = frameWidth;i < imageArray.length - frameWidth;i++){
                for(int j = frameWidth;j < imageArray[0].length - frameWidth;j++){
                    pixel = 0f;
                    for(int k = i - frameWidth;k <= i + frameWidth;k++){
                        for(int l = j - frameWidth;l <= j + frameWidth;l++){
                            pixel += (float)imageArray[k][l] * convolutionMatrix[k - (i - frameWidth)][l - (j - frameWidth)];
                        }
                    }

                    newArray[i][j] = compressNumber((int)pixel,0,255);
                    //imageArray[i][j] = (int)pixel;
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }



        for(int i = frameWidth;i < imageArray.length - frameWidth;i++){
            imageArray[i] = newArray[i].clone();
        }












    }

    public static int[][] rgbToGray(int[][] arr){
        int pixel, avg;
        for(int i = 0;i < arr.length;i++){
            for(int j = 0;j < arr[0].length;j++){
                pixel = arr[i][j];
                avg = (getRed(pixel) + getGreen(pixel) + getBlue(pixel))/3;
                arr[i][j] = getAlpha(pixel) * ALPHA_DIVISOR + avg*(RED_DIVISOR + GREEN_DIVISOR + BLUE_DIVISOR);
            }
        }
        return arr;
    }


}
