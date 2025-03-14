import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class CodingQuizGUI {

    public JFrame mainF;

    public CodingQuizGUI() {
        mainF = new JFrame("Coding Quiz");
        mainF.setSize(800, 600);
        mainF.setLayout(null);
        
        JButton colorMapButton = new JButton("Color Map");
        colorMapButton.setActionCommand("COLOR_MAP");
        colorMapButton.setBounds(50, 50, 150, 30);
        colorMapButton.addActionListener(new ButtonClickListener());
        mainF.add(colorMapButton);

        JButton printImageButton = new JButton("Print Image");
        printImageButton.setActionCommand("PRINT_IMAGE");
        printImageButton.setBounds(50, 100, 150, 30);
        printImageButton.addActionListener(new ButtonClickListener());
        mainF.add(printImageButton);

       

        
        mainF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainF.setVisible(true);
    }

    public static void colorMap() {
    JFrame frame = new JFrame("Color Map");
    frame.setSize(400, 400);
    frame.setLayout(new GridLayout(2, 3));
    
    
    Map<String, Color> colorMap = new HashMap<>();
    colorMap.put("Red", Color.RED);
    colorMap.put("Green", Color.GREEN);
    colorMap.put("Blue", Color.BLUE);
    colorMap.put("Yellow", Color.YELLOW);
    colorMap.put("Orange", Color.ORANGE);
    colorMap.put("Gray", Color.GRAY);
    
    for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
        JPanel panel = new JPanel();
        panel.setBackground(entry.getValue());
        panel.add(new JLabel(entry.getKey()));
        frame.add(panel);
    }
    
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

    public static void printImage() {
        JFrame frame = new JFrame("Print Image");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        
        int width = newImage.getWidth();
        int height = newImage.getHeight();

        long sumR = 0;
        long sumG = 0;
        long sumB = 0;
        int totalPixels = width * height;

        for (int x =0; x< width; x++){
        	for (int y =0; y<height; y++){
        		int rbg = newImage.getRGB(x,y);
        		int r = (rbg >> 16) & 0xFF;
				int g = (rbg >> 8) & 0xFF;
				int b = rbg & 0xFF;


        		
        	}
        }

        try {
            BufferedImage image = ImageIO.read(new File("leo.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label, BorderLayout.CENTER);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading image: " + e.getMessage());
            return;
        }
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void getWords() {
    }

    public static void editStory() {
             
      
    }

    public static void main(String[] args) {
        new CodingQuizGUI();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("COLOR_MAP")) {
                colorMap();
            } else if (action.equals("PRINT_IMAGE")) {
                printImage();
            } 
        }
    }
}
