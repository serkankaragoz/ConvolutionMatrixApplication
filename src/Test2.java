import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

// Resources:
// https://stackoverflow.com/questions/30314681/adding-image-to-jpanel-inside-jinternalframe
// https://stackoverflow.com/questions/276254/how-to-disable-or-hide-the-close-x-button-on-a-jframe
public class Test2 {

    public static boolean isFirst = true;

    public static final String exampleImagePath = "D:\\SERKAN\\AAA SERKAN LAPTOPTAN GELENLER\\Java convolution deneme\\ConvolutionDeneme\\images\\lena.jpg";
    //public static final String exampleImagePath = "E:\\kodlama hakkinda\\java\\Java convolution deneme\\ConvolutionDeneme\\images\\lena.jpg";

    public static void main(String[] args) {

        new Test2();
    }

    public Test2() {



        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }



                JDesktopPane dp = new JDesktopPane() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(1200, 1200);
                    }
                };



                JInternalFrame inFrm = new JInternalFrame("", false, false, false, false);
                inFrm.add(new ShowImagePane());

                inFrm.pack();
                inFrm.setVisible(true);
                dp.add(inFrm);
                dp.setVisible(true);

                JFrame frame = new JFrame("Yeni fotoğrafın önizlemesi");

                //frame.setUndecorated(true);
                //frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);


                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(inFrm);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);


                /*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                //frame.dispose();



            }
        });
    }

    public class ShowImagePane extends JPanel {

        private JFileChooser fc;

        public ShowImagePane() {


            setLayout(new BorderLayout());
            ImagePane imagePane = null;
            try {
                //imagePane = new ImagePane(new File("E:\\kodlama hakkinda\\java\\Java convolution deneme\\ConvolutionDeneme\\images\\lena.jpg"));
                imagePane = new ImagePane(new File(exampleImagePath));
                //imagePane = new ImagePane(new File("E:\\kodlama hakkinda\\java\\Java convolution deneme\\ConvolutionDeneme\\images\\49.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            add(imagePane);




            JButton open = new JButton("Farklı Kaydet");


            ImagePane finalImagePane = imagePane;
            open.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    if (fc == null) {
                        fc = new JFileChooser();

                        fc.addChoosableFileFilter(new FileFilter() {

                            @Override
                            public boolean accept(File f) {
                                String name = f.getName().toLowerCase();
                                return name.endsWith(".png")
                                        || name.endsWith(".jpg")
                                        || name.endsWith(".jpeg")
                                        || name.endsWith(".bmp");
                            }

                            @Override
                            public String getDescription() {
                                return "Images";
                            }
                        });
                        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                        fc.setSelectedFile(new File("new_lena.jpg"));
                        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        fc.setMultiSelectionEnabled(false);

                    }

                    switch (fc.showOpenDialog(ShowImagePane.this)) {
                        case JFileChooser.APPROVE_OPTION:
                            File selected = fc.getSelectedFile();
                            try {
                                //imagePane.setImageSource(selected);
                                //imagePane.setImageSource(new File(exampleImagePath));
                                finalImagePane.setImageSource(new File(exampleImagePath));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                    }
                }
            });



            try {
                imagePane.setImageSource(new File(exampleImagePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            add(open, BorderLayout.AFTER_LAST_LINE);

        }

    }

    public class ImagePane extends JPanel {

        private BufferedImage bgImage;
        private Image scaled;

        public ImagePane(File source) throws IOException {
            setImageSource(source);
        }

        public ImagePane() {
        }

        @Override
        public void invalidate() {
            super.invalidate();
            resizeImage();
        }

        public void setImageSource(File source) throws IOException {
            if (source != null) {
                //System.out.println("NULL değil");
                bgImage = ImageIO.read(source);
                resizeImage();
            } else {
                //System.out.println("NULL");
                bgImage = null;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            //return bgImage == null ? new Dimension(200, 200) : new Dimension(bgImage.getWidth(), bgImage.getHeight());
            return new Dimension(600, 600);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (scaled != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = (getWidth() - scaled.getWidth(this)) / 2;
                int y = (getHeight() - scaled.getHeight(this)) / 2;
                g2d.drawImage(scaled, x, y, this);
                g2d.dispose();
            }
        }


        protected void resizeImage() {
            if (bgImage != null) {

                if(getWidth() == 0 || getHeight() == 0){

                }
                else if (getWidth() < getHeight()) {
                    scaled = bgImage.getScaledInstance(this.getWidth(), -1, Image.SCALE_SMOOTH);
                }
                else if (getWidth() >= getHeight()) {
                    scaled = bgImage.getScaledInstance(-1, this.getHeight(), Image.SCALE_SMOOTH);
                }

                repaint();
            }
        }

    }

}
