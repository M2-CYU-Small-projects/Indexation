package fr.m2_cyu_indexation.ui.sub_panels.results;

import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aldric Vitali Silvestre
 */
public class ImgGridItem extends AbstractSubPanel {

    public static final Dimension ITEM_DIMENSIONS = ImageGridPanel.ITEM_DIMENSIONS;
    public static final Dimension IMAGE_DIMENSIONS = new Dimension(ITEM_DIMENSIONS.width, 3 * ITEM_DIMENSIONS.height / 4);
    public static final Dimension NAME_DIMENSIONS = new Dimension(ITEM_DIMENSIONS.width, ITEM_DIMENSIONS.height / 4);
    public static final Font NAME_FONT = GuiPreferences.BASE_FONT.deriveFont((float) (NAME_DIMENSIONS.width / 30.0));

    private JLabel nameLabel;
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private BufferedImage image;

    private final ImageResponse response;

    public ImgGridItem(MainWindow context, ImageResponse response) {
        super(context);
        this.response = response;
        init();
        Component parent = this;
        nameLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 2));
                panel.setPreferredSize(ITEM_DIMENSIONS);

                panel.add(new JLabel("Average Color : "));
                Color color = new Color(response.getAverageColor());
                panel.add(new JLabel(color.getRed() + ", " + color.getGreen() + ", " + color.getBlue()));

                panel.add(new JLabel("Nb outline pixels : "));
                panel.add(new JLabel("" + response.getNbOutlinePixels()));

                JOptionPane.showMessageDialog(parent, panel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nameLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void init() {
        setLayout(new BorderLayout());
        if (response.getData().length != 0) {
            InputStream is = new ByteArrayInputStream(response.getData());
            try {
                image = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            image = new BufferedImage(IMAGE_DIMENSIONS.width, IMAGE_DIMENSIONS.height, BufferedImage.TYPE_3BYTE_BGR);
            Color color = randomColor();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }

        imageIcon = new ImageIcon(image);
        imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(IMAGE_DIMENSIONS);
        add(imageLabel, BorderLayout.CENTER);

        nameLabel = new JLabel(response.getImageName(), SwingConstants.CENTER);
        nameLabel.setFont(NAME_FONT);
        nameLabel.setPreferredSize(NAME_DIMENSIONS);
        add(nameLabel, BorderLayout.SOUTH);
    }

    private Color randomColor() {
        float hue = (float) Math.random();
        int rgb = Color.HSBtoRGB(hue, 0.5f, 0.5f);
        return new Color(rgb);
    }
}
