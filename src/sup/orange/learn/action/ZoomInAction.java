package sup.orange.learn.action;

import sup.orange.learn.ViewerFrame;
import sup.orange.learn.ViewerService;

/**
 * Created by Rex on 14-11-25.
 */
public class ZoomInAction implements Action {

    @Override
    public void execute(ViewerService service, ViewerFrame frame) {
        service.zoomIn(frame);
    }
}
