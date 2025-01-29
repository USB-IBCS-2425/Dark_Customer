import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuseumApp {
    public static void main(String[] args) {
        
        JFrame museumFrame = new JFrame("Virtual Museum");
        museumFrame.setSize(1000, 800);
        museumFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        museumFrame.setLayout(new GridLayout(2, 3, 10, 10));
        museumFrame.setLocationRelativeTo(null);

        
        String[] imageFiles = {"image1.jpg", "image2.jpg", "image3.jpg", "image4.jpg", "image5.jpg", "image6.jpg", "image7.jpg"};
        String[] buttonLabels = {"Megalodon jaw reconstruction", "Tyrannosaurus rex (T. rex) skeleton", "Triceratops skeleton", "Stegosaurus skeleton", "Crocodile or Alligator skeleton", "2 steps ahead", "CITATION PAGE"};

        
        for (int i = 0; i < 7; i++) {
            JButton button = new JButton(buttonLabels[i]);
            String imageFile = imageFiles[i];

            button.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    showImage(imageFile);
                }
            });

            museumFrame.add(button);
        }

        museumFrame.setVisible(true);
    }

    private static void showImage(String imageFile) {
        JFrame imageFrame = new JFrame("Artwork Display");
        imageFrame.setSize(300, 300);
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ImageIcon imageIcon = new ImageIcon(imageFile);
        JLabel imageLabel = new JLabel(imageIcon);
        imageFrame.add(imageLabel);

        imageFrame.setVisible(true);
        imageFrame.pack();
        imageFrame.setLocationRelativeTo(null);

    }
}
