import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MatriceCommands {

        public static float stringToFloat(String number) throws ArithmeticException{
            String[] numbers = number.split("/");
            switch(numbers.length){
                case 1:
                    return Float.valueOf(numbers[0]);
                case 2:
                    return (Float.valueOf(numbers[0]) / Float.valueOf(numbers[1]));
                default:
                    throw new ArithmeticException("String must not have more than one '/' sign");
            }
        }
        public static void printMatrix(float[][] matrix){
            for(int i = 0; i < matrix.length;i++){
                for(int j = 0;j < matrix[0].length; j++){
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.print("\n");
            }

        }


    public static float[][] getMatriceFromFile(String path) throws FileNotFoundException {
        FileReader fr = new FileReader(path);
        Scanner reader = new Scanner(fr);

        int size = reader.nextLine().split(" ").length;
        fr = new FileReader(path);
        reader = new Scanner(fr);

        float[][] matrice = new float[size][size];
        int i = 0;
        String[] words;

        while(reader.hasNextLine()){
            words = reader.nextLine().split(",");
            if(words.length != size){
                throw new IndexOutOfBoundsException("The matrice must be NxN in the given file path");
            }
            for(int j = 0; j < size; j++){
                matrice[i][j] = stringToFloat(words[j]);
            }
            i++;
        }

        return matrice;

    }
}
