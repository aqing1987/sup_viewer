package sup.orange.learn;

import javax.swing.*;
import java.awt.event.ActionEvent;

import sup.orange.learn.action.Action;

/**
 * Created by Rex on 14-11-25.
 */
public class ToolbarAction extends AbstractAction{
    private String actionName = "";
    private ViewerFrame frame = null;

    // one action instance
    private Action action = null;

    public ToolbarAction() {
        super();
    }

    public ToolbarAction(ImageIcon icon, String actionName, ViewerFrame frame) {
        super("", icon);
        this.actionName = actionName;
        this.frame = frame;
    }

    // re-write actionPerformed
    public void actionPerformed(ActionEvent e) {
        ViewerService service = ViewerService.getInstance();
        Action action = getAction(this.actionName);
        action.execute(service, frame);
    }

    // get action instance by using actionName
    private Action getAction(String actionName) {

        try {
            if (this.action == null) {
                Action action = (Action) Class.forName(actionName).newInstance();
                this.action = action;
            }

            return action;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
