package sup.orange.learn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rex on 14-11-25.
 */
public class ViewerFrame extends JFrame{

    // set image viewer zone width and height
    private int width = 800;
    private int height = 600;

    // JLabel used to show images
    JLabel label = new JLabel();

    // get service
    ViewerService service = ViewerService.getInstance();

    public ViewerFrame() {
        super();

        // init the frame
        init();
    }

    public void init() {
        // title config
        this.setTitle("vieWer");

        // set size
        this.setPreferredSize(new Dimension(width, height));

        // set icon
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage("pic/logo.png");
        this.setIconImage(image);

        // add menu bar
        createMenuBar();

        // add tool bar
        JPanel toolBar = createToolPanel();

        // place toolbar to north of the frame
        this.add(toolBar, BorderLayout.NORTH);

        // place image viewer zone to center of the frame
        this.add(new JScrollPane(label), BorderLayout.CENTER);

        // set as visible
        this.setVisible(true);
        this.pack();
    }

    public JLabel getLabel() {
        return this.label;
    }

    public void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // menu words array
        String[] menuArr = {"File", "Tool", "Help"};
        // menu item words array
        String[][] menuItemArr = {
                {"Open", "-","Exit"},
                {"ZoomIn", "ZoomOut", "-", "Prev", "Next"},
                {"About"}
        };

        // create menu bar
        for (int i = 0; i < menuArr.length; i++) {
            // create one menu
            JMenu menu = new JMenu(menuArr[i]);
            for (int j = 0; j < menuItemArr[i].length; j++) {
                if (menuItemArr[i][j].equals("-")) {
                    menu.addSeparator();
                }
                else {
                    // create one menuItem
                    JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
                    menuItem.addActionListener(menuListener);
                    // add menuItem to menu
                    menu.add(menuItem);
                }
            }

            // add menu to menu bar
            menuBar.add(menu);
        }

        // set menuBar to JFrame
        this.setJMenuBar(menuBar);
    }

    // add listener for menu
    ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            service.menuResponse(ViewerFrame.this, e.getActionCommand());
        }
    };

    public JPanel createToolPanel() {
        // create a JPanel
        JPanel panel = new JPanel();
        // create a toolbar
        JToolBar toolBar = new JToolBar("Image Tool");
        // drag disable
        toolBar.setFloatable(true);
        // set layout
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // toolbar icon name array
        String[] toolIconArr = {"prev", "next", "zoom_in", "zoom_out"};
        // toolbar action name array
        String[] toolActionArr = {
                "sup.orange.learn.action.PrevAction",
                "sup.orange.learn.action.NextAction",
                "sup.orange.learn.action.ZoomInAction",
                "sup.orange.learn.action.ZoomOutAction"
        };

        for (int i = 0; i < toolIconArr.length; i++) {

            ToolbarAction action = new ToolbarAction(new ImageIcon("pic/"
                    + toolIconArr[i] + ".png"), toolActionArr[i], this);
            // create a button by using new icon
            JButton button = new JButton(action);
            // add button to toolbar
            toolBar.add(button);
        }

        // add toolbar to panel
        panel.add(toolBar);

        return panel;
    }

}

