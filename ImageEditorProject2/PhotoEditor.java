import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

/**
 * 
 * Citations and Notes:
 * 1. Learning resources and references from:
 *    - Oracle Java Tutorials (https://docs.oracle.com/javase/tutorial/) 
 *      for concepts such as BufferedImage, ImageIO, and custom image operations.
 *    - Stack Overflow threads for basic image manipulations like subimage 
 *      and scaling: 
 *      e.g., https://stackoverflow.com/questions/9417356/bufferedimage-resize 
 *    - Personal tutor sessions for clarifying my questions about advanced image manipulation 
 *      concepts like (e.g., breakARGB, buildARGB, saturate logic).
 * 2. For the Zoom feature, used getSubimage(...) I got from online resources like (Oracle Docs).
 * 3. For the Blur feature, the idea of sampling neighboring pixels I learnt from my in person tutor session, 
 * referencing a typical "box blur" technique, the 5 points blur is really well found on google, when i look up for blur algorithms, most of the websites they talk about 5 points blur.
 * 4. The rotate 180° logic is a common example in many image manipulation tutorials on youtube 
 *    (just reversing rows and columns).
 * 
 * Author: Brian Vu
 */
public class PhotoEditor {
    // Main application frame
    private JFrame mainFrame;
    private JLabel headerLabel;
    private ImageIcon displayedIcon;   
    private JLabel statusLabel;         
    
    
    private BufferedImage currentImage, backupImage; 
    
    
    private JButton showImageBtn, pixelReadBtn;
    private JButton makeRedBtn, darkLightBtn, keepRedBtn, resetBtn;
    private JButton blurBtn, rotateBtn, zoomBtn, saturateBtn;
    
    
    private JTextField xInput, yInput;
    
    
    private JFrame imageFrame;
    private JPanel imagePanel;
    private JLabel imageLabel;

    public PhotoEditor() {
        
        mainFrame = new JFrame("Photo Editor");
        mainFrame.setSize(400, 450);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        
        headerLabel = new JLabel("Photo Editor Filters", JLabel.CENTER);
        mainFrame.add(headerLabel);

        
        showImageBtn = new JButton("Show Image");
        showImageBtn.setActionCommand("SHOW_IMAGE");
        showImageBtn.addActionListener(new EditorActions());
        mainFrame.add(showImageBtn);

        
        pixelReadBtn = new JButton("Check Pixel");
        pixelReadBtn.setActionCommand("CHECK_PIXEL");
        pixelReadBtn.addActionListener(new EditorActions());
        mainFrame.add(pixelReadBtn);

        
        statusLabel = new JLabel("");
        mainFrame.add(statusLabel);

        
        xInput = new JTextField(3);
        yInput = new JTextField(3);
        mainFrame.add(new JLabel("X:"));
        mainFrame.add(xInput);
        mainFrame.add(new JLabel("Y:"));
        mainFrame.add(yInput);

        
        makeRedBtn = new JButton("Make Red");
        makeRedBtn.setActionCommand("MAKE_RED");
        makeRedBtn.addActionListener(new EditorActions());
        mainFrame.add(makeRedBtn);

        
        darkLightBtn = new JButton("Dark/Light");
        darkLightBtn.setActionCommand("DARK_LIGHT");
        darkLightBtn.addActionListener(new EditorActions());
        mainFrame.add(darkLightBtn);

        
        keepRedBtn = new JButton("Keep Red");
        keepRedBtn.setActionCommand("KEEP_RED");
        keepRedBtn.addActionListener(new EditorActions());
        mainFrame.add(keepRedBtn);

        
        resetBtn = new JButton("Restore");
        resetBtn.setActionCommand("RESTORE");
        resetBtn.addActionListener(new EditorActions());
        mainFrame.add(resetBtn);

        
        blurBtn = new JButton("Blur");
        blurBtn.setActionCommand("BLUR");
        blurBtn.addActionListener(new EditorActions());
        mainFrame.add(blurBtn);

        
        rotateBtn = new JButton("Rotate 180");
        rotateBtn.setActionCommand("TURN_180");
        rotateBtn.addActionListener(new EditorActions());
        mainFrame.add(rotateBtn);

        
        zoomBtn = new JButton("Zoom");
        zoomBtn.setActionCommand("ZOOM");
        zoomBtn.addActionListener(new EditorActions());
        mainFrame.add(zoomBtn);

        
        saturateBtn = new JButton("Saturate");
        saturateBtn.setActionCommand("SATURATE");
        saturateBtn.addActionListener(new EditorActions());
        mainFrame.add(saturateBtn);

        
        try {
            currentImage = ImageIO.read(new File("Shake Head GIF by PAPER.gif"));
            backupImage = ImageIO.read(new File("Shake Head GIF by PAPER.gif"));
            displayedIcon = new ImageIcon(currentImage);
        } catch (IOException e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }

        
        imageFrame = new JFrame("Preview");
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imagePanel = new JPanel();
        imageLabel = new JLabel(displayedIcon);
        imagePanel.add(imageLabel);
        imageFrame.add(imagePanel);
        imageFrame.pack();

        
        mainFrame.setVisible(true);
    }

   
    public static void main(String[] args) {
        new PhotoEditor();
    }

   
    private class EditorActions implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();

            // Show image window
            if (cmd.equals("SHOW_IMAGE")) {
                imageFrame.setVisible(true);
                imageFrame.setLocationRelativeTo(null);
            }
            // Turn the entire image red
            else if (cmd.equals("MAKE_RED")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        // Construct a red pixel with full alpha, red=255, green=0, blue=0
                        int redColor = (0xFF << 24) | (255 << 16) | (0 << 8) | 0;
                        currentImage.setRGB(x, y, redColor);
                    }
                }
                refreshImage();
            }
            // Restore the original (backup) image
            else if (cmd.equals("RESTORE")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        // Copy each pixel from backup to current
                        currentImage.setRGB(x, y, backupImage.getRGB(x, y));
                    }
                }
                refreshImage();
            }
            /
            else if (cmd.equals("BLUR")) {
                // Temporary image to store blurred pixels
                BufferedImage temp = new BufferedImage(currentImage.getWidth(), currentImage.getHeight(), currentImage.getType());
                
                // Loop through each pixel (excluding edges because i don't the edges does count and do much so)
                for (int i = 1; i < currentImage.getWidth() - 1; i++) {
                    for (int j = 1; j < currentImage.getHeight() - 1; j++) {
                        // Get the pixel and its neighbors
                        int center = currentImage.getRGB(i, j);
                        int left   = currentImage.getRGB(i - 1, j);
                        int right  = currentImage.getRGB(i + 1, j);
                        int up     = currentImage.getRGB(i, j - 1);
                        int down   = currentImage.getRGB(i, j + 1);
                        // and now after i look back at my code i just realize that i should include "cross"
                        // Break each pixel into ARGB components
                        int[] c = breakARGB(center);
                        int[] l = breakARGB(left);
                        int[] r = breakARGB(right);
                        int[] u = breakARGB(up);
                        int[] d = breakARGB(down);

                        // Sum up each channel
                        int sumA = c[0] + l[0] + r[0] + u[0] + d[0];
                        int sumR = c[1] + l[1] + r[1] + u[1] + d[1];
                        int sumG = c[2] + l[2] + r[2] + u[2] + d[2];
                        int sumB = c[3] + l[3] + r[3] + u[3] + d[3];

                        // Average each channel
                        int newA = sumA / 5;
                        int newR = sumR / 5;
                        int newG = sumG / 5;
                        int newB = sumB / 5;

                        // Build a new ARGB pixel
                        temp.setRGB(i, j, buildARGB(newA, newR, newG, newB));
                    }
                }
                // Replace current image with blurred version
                currentImage = temp;
                refreshImage();
            }
            // Darken/Lighten based on average brightness
            else if (cmd.equals("DARK_LIGHT")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        int pixel = currentImage.getRGB(x, y);
                        int a = (pixel >> 24) & 0xFF;
                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = pixel & 0xFF;

                        // Calculate brightness as average of R,G,B
                        int brightness = (r + g + b) / 3;

                        // If it's dark, make it darker. If it's bright, make it brighter.
                        // (Numbers 128 and 50 are pretty much random:)) choices for demonstration.)
                        if (brightness < 128) {
                            r = Math.max(0, r - 50);
                            g = Math.max(0, g - 50);
                            b = Math.max(0, b - 50);
                        } else {
                            r = Math.min(255, r + 50);
                            g = Math.min(255, g + 50);
                            b = Math.min(255, b + 50);
                        }
                        // Rebuild the pixel with new color
                        currentImage.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
                    }
                }
                refreshImage();
            }
            // Keep Red filter - only keep the most red parts, convert others to grayscale
            else if (cmd.equals("KEEP_RED")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        int rgb = currentImage.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;

                        // If red is not dominant, turn pixel to grayscale
                        if (!(r > g && r > b)) {
                            int gray = (r + g + b) / 3;
                            rgb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
                            currentImage.setRGB(x, y, rgb);
                        }
                    }
                }
                refreshImage();
            }
            // Rotate image by 180 degrees
            else if (cmd.equals("TURN_180")) {
                int width = currentImage.getWidth();
                int height = currentImage.getHeight();
                BufferedImage rotated = new BufferedImage(width, height, currentImage.getType());
                
                // Swap pixels from top-left with bottom-right
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        rotated.setRGB(width - i - 1, height - j - 1, currentImage.getRGB(i, j));
                    }
                }
                currentImage = rotated;
                refreshImage();
            }
            // Zoom in by taking the center part of the image and scaling it up
            else if (cmd.equals("ZOOM")) {
                int width = currentImage.getWidth();
                int height = currentImage.getHeight();
                
                // Get the middle sub-image (1/2 size in each dimension)
                BufferedImage sub = currentImage.getSubimage(width / 4, height / 4, width / 2, height / 2);

                // Scale the subimage back up to original dimensions
                // Citation for subimage and scaling:
                // "Java: BufferedImage getScaledInstance" usage from Oracle docs and 
                // Stack Overflow discussions (e.g., resizing a BufferedImage).
                Image scaled = sub.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                // Draw the scaled image into a new BufferedImage
                BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = newImg.getGraphics();
                g.drawImage(scaled, 0, 0, null);
                g.dispose();

                currentImage = newImg;
                refreshImage();
            }
            // Saturate colors by boosting each channel relative to the max channel
            else if (cmd.equals("SATURATE")) {
                for (int i = 0; i < currentImage.getWidth(); i++) {
                    for (int j = 0; j < currentImage.getHeight(); j++) {
                        int px = currentImage.getRGB(i, j);
                        int a = (px >> 24) & 0xFF;
                        int r = (px >> 16) & 0xFF;
                        int g = (px >> 8) & 0xFF;
                        int b = px & 0xFF;

                        // Identify the strongest color channel
                        int max = Math.max(r, Math.max(g, b));
                        // If max > 0, we scale each channel to 255 if it was the max
                        // This effectively pushes each color closer to fully saturated
                        if (max > 0) {
                            r = (r * 255) / max;
                            g = (g * 255) / max;
                            b = (b * 255) / max;
                        }

                        int satPX = (a << 24) | (r << 16) | (g << 8) | b;
                        currentImage.setRGB(i, j, satPX);
                    }
                }
                refreshImage();
            }
            // Checks the ARGB value of a pixel at user-entered coordinates
            else if (cmd.equals("CHECK_PIXEL")) {
                try {
                    int xVal = Integer.parseInt(xInput.getText());
                    int yVal = Integer.parseInt(yInput.getText());
                    int color = currentImage.getRGB(xVal, yVal);

                    // Extract ARGB components
                    int a = (color >> 24) & 0xFF;
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;

                    // Display these values in the status label
                    statusLabel.setText("ARGB = [" + a + ", " + r + ", " + g + ", " + b + "]");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid input");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    statusLabel.setText("Out of bounds");
                }
            }
        }
    }

    /**
     * Refresh the displayed image with the latest changes
     * so the user can see the modifications in the image frame.
     */
    private void refreshImage() {
        // Update the ImageIcon with the new image data
        displayedIcon.setImage(currentImage);
        // Repaint the label to visually reflect the changes
        imageLabel.repaint();
    }

    /**
     * Breaks a pixel integer into its ARGB components.
     *
     * @param pixel the integer pixel value
     * @return an array of integers [alpha, red, green, blue]
     * 
     *          This approach of shifting and masking to separate channels
     *          is standard practice, widely covered in Oracle docs and 
     *          in many online tutorials sources (e.g., Stack Overflow).
     */
    private int[] breakARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xFF;
        int red   = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue  = pixel & 0xFF;
        return new int[] { alpha, red, green, blue };
    }

    /**
     * Combines ARGB components back into a single integer pixel.
     * 
     * @param a alpha component (0-255)
     * @param r red component (0-255)
     * @param g green component (0-255)
     * @param b blue component (0-255)
     * @return combined integer pixel
     */
    private int buildARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
