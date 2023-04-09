package fr.m2_cyu_indexation.ui.sub_panels.results;

import fr.m2_cyu_indexation.engine.Engine;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class ImageDownloaderThread extends Thread {

    private final ImageGridPanel gridPanel;

    public ImageDownloaderThread(ImageGridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    @Override
    public void run() {
        try {
            Engine engine = gridPanel.getContext().getEngine();
            List<ImageResponse> imageResponses = engine.getResponses();
            System.out.println("Start downloading images");
            for (var imageResponse : imageResponses) {
                if (this.isInterrupted()) {
                    System.out.println("Stop downloader thread");
                    break;
                }
                String imageName = imageResponse.getImageName();
                System.out.println("Download " + imageName);
                var data = engine.downloadImageData(imageName);
                if (data.length == 0) {
                    System.out.println("Error when downloading the image");
                    break;
                }
                imageResponse.setData(data);
                gridPanel.updateGrid();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("End of the downloader thread");
        } catch (Exception e) {
        }
    }
}
