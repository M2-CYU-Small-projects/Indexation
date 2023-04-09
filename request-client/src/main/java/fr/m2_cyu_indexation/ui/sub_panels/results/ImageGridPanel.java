package fr.m2_cyu_indexation.ui.sub_panels.results;

import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * @author Aldric Vitali Silvestre
 */
public class ImageGridPanel extends AbstractSubPanel {

    public static final Dimension CENTER_DIMENSIONS = ResultPanel.CENTER_DIMENSIONS;
    public static final Dimension ITEM_DIMENSIONS = new Dimension(CENTER_DIMENSIONS.width / 3 - CENTER_DIMENSIONS.width / 20,
                                                                  CENTER_DIMENSIONS.height / 2
    );

    private final ResultPanel resultPanel;

    private ImageDownloaderThread downloaderThread;

    public ImageGridPanel(MainWindow context, ResultPanel resultPanel) {
        super(context);
        this.resultPanel = resultPanel;
        init();
    }

    private void init() {
        setLayout(new GridLayout(0, 3, CENTER_DIMENSIONS.width / 20, CENTER_DIMENSIONS.height / 10));
        updateGrid();
        downloaderThread = new ImageDownloaderThread(this);
        downloaderThread.start();
    }

    public void updateGrid() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> updateGrid());
            return;
        }
        System.out.println("Update");
        removeAll();
        List<ImageResponse> imageResponse = context.getImageResponse();
        imageResponse.stream()
                .map(createItem())
                .forEach(this::add);
        revalidate();
    }

    private Function<ImageResponse, ImgGridItem> createItem() {
        return resp -> {
            ImgGridItem imgGridItem = new ImgGridItem(context, resp);
            imgGridItem.setPreferredSize(ITEM_DIMENSIONS);
            return imgGridItem;
        };
    }

    public void onDestroy() {
        System.out.println("Stop downloader thread");
        downloaderThread.interrupt();
    }
}
