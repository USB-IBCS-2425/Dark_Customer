import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class PhotoEditor {
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

            if (cmd.equals("SHOW_IMAGE")) {
                imageFrame.setVisible(true);
                imageFrame.setLocationRelativeTo(null);
            }
            else if (cmd.equals("MAKE_RED")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        int redColor = (0xFF << 24) | (255 << 16) | (0 << 8) | 0;
                        currentImage.setRGB(x, y, redColor);
                    }
                }
                refreshImage();
            }
            else if (cmd.equals("RESTORE")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        currentImage.setRGB(x, y, backupImage.getRGB(x, y));
                    }
                }
                refreshImage();
            }
            else if (cmd.equals("BLUR")) {
                BufferedImage temp = new BufferedImage(currentImage.getWidth(), currentImage.getHeight(), currentImage.getType());
                for (int i = 1; i < currentImage.getWidth() - 1; i++) {
                    for (int j = 1; j < currentImage.getHeight() - 1; j++) {
                        int center = currentImage.getRGB(i, j);
                        int left   = currentImage.getRGB(i - 1, j);
                        int right  = currentImage.getRGB(i + 1, j);
                        int up     = currentImage.getRGB(i, j - 1);
                        int down   = currentImage.getRGB(i, j + 1);

                        int[] c = breakARGB(center);
                        int[] l = breakARGB(left);
                        int[] r = breakARGB(right);
                        int[] u = breakARGB(up);
                        int[] d = breakARGB(down);

                        int sumA = c[0] + l[0] + r[0] + u[0] + d[0];
                        int sumR = c[1] + l[1] + r[1] + u[1] + d[1];
                        int sumG = c[2] + l[2] + r[2] + u[2] + d[2];
                        int sumB = c[3] + l[3] + r[3] + u[3] + d[3];

                        int newA = sumA / 5;
                        int newR = sumR / 5;
                        int newG = sumG / 5;
                        int newB = sumB / 5;

                        temp.setRGB(i, j, buildARGB(newA, newR, newG, newB));
                    }
                }
                currentImage = temp;
                refreshImage();
            }
            else if (cmd.equals("DARK_LIGHT")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        int pixel = currentImage.getRGB(x, y);
                        int a = (pixel >> 24) & 0xFF;
                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = pixel & 0xFF;
                        int brightness = (r + g + b) / 3;
                        if (brightness < 128) {
                            r = Math.max(0, r - 50);
                            g = Math.max(0, g - 50);
                            b = Math.max(0, b - 50);
                        } else {
                            r = Math.min(255, r + 50);
                            g = Math.min(255, g + 50);
                            b = Math.min(255, b + 50);
                        }
                        currentImage.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
                    }
                }
                refreshImage();
            }
            else if (cmd.equals("KEEP_RED")) {
                for (int x = 0; x < currentImage.getWidth(); x++) {
                    for (int y = 0; y < currentImage.getHeight(); y++) {
                        int rgb = currentImage.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;
                        if (!(r > g && r > b)) {
                            int gray = (r + g + b) / 3;
                            rgb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
                            currentImage.setRGB(x, y, rgb);
                        }
                    }
                }
                refreshImage();
            }
            else if (cmd.equals("TURN_180")) {
                int width = currentImage.getWidth();
                int height = currentImage.getHeight();
                BufferedImage rotated = new BufferedImage(width, height, currentImage.getType());
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        rotated.setRGB(width - i - 1, height - j - 1, currentImage.getRGB(i, j));
                    }
                }
                currentImage = rotated;
                refreshImage();
            }
            else if (cmd.equals("ZOOM")) {
                int width = currentImage.getWidth();
                int height = currentImage.getHeight();
                BufferedImage sub = currentImage.getSubimage(width / 4, height / 4, width / 2, height / 2);
                Image scaled = sub.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = newImg.getGraphics();
                g.drawImage(scaled, 0, 0, null);
                g.dispose();
                currentImage = newImg;
                refreshImage();
            }
            else if (cmd.equals("SATURATE")) {
                for (int i = 0; i < currentImage.getWidth(); i++) {
                    for (int j = 0; j < currentImage.getHeight(); j++) {
                        int px = currentImage.getRGB(i, j);
                        int a = (px >> 24) & 0xFF;
                        int r = (px >> 16) & 0xFF;
                        int g = (px >> 8) & 0xFF;
                        int b = px & 0xFF;
                        int max = Math.max(r, Math.max(g, b));
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
            else if (cmd.equals("CHECK_PIXEL")) {
                try {
                    int xVal = Integer.parseInt(xInput.getText());
                    int yVal = Integer.parseInt(yInput.getText());
                    int color = currentImage.getRGB(xVal, yVal);
                    int a = (color >> 24) & 0xFF;
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;
                    statusLabel.setText("ARGB = [" + a + ", " + r + ", " + g + ", " + b + "]");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid input");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    statusLabel.setText("Out of bounds");
                }
            }
        }
    }

    private void refreshImage() {
        displayedIcon.setImage(currentImage);
        imageLabel.repaint();
    }

    private int[] breakARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xFF;
        int red   = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue  = pixel & 0xFF;
        return new int[] { alpha, red, green, blue };
    }

    private int buildARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
