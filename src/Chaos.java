import javax.swing.*;
import java.awt.*;

public class Chaos {

    // E:\kodlama hakkinda\java\Java convolution deneme\ConvolutionDeneme\images\lena.jpg
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//çalışılan işletim sisteminin arayüzünü kullanmamızı sağlayan komut


        /**
         * Şifreleyici, şifreleme yaparken Vigenere şifreleme mantığını kullanır.
         * Vigenere şifreleme, özet olarak şifrelenmesi istenen bir mesajın anahtar kullanılarak şifrelenmesidir.
         */



        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageDisplayer id = new ImageDisplayer();
                id.setVisible(true);
            }
        });
    }
}
