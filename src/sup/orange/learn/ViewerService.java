package sup.orange.learn;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rex on 14-11-25.
 */
public class ViewerService {
    private static ViewerService service = null;
    private ViewerFileChooser fileChooser = new ViewerFileChooser();
    // create zoom-in or zoom-out ratio
    private double ratio = 0.2;
    // current directory
    private File currentDirectory = null;
    // all pic files in current directory
    private List<File> currentFiles = null;
    // current pic file
    private File currentFile = null;

    ViewerService() {

    }

    // get static instance
    public static ViewerService getInstance() {
        if (service == null) {
            service = new ViewerService();
        }

        return service;
    }

    // open pic
    public void open(ViewerFrame frame) {
        // if select open
        if (fileChooser.showOpenDialog(frame) == ViewerFileChooser.APPROVE_OPTION) {

            // get file name
            this.currentFile = fileChooser.getSelectedFile();
            // get current path
            String name = this.currentFile.getPath();
            // get current directory
            File cd = fileChooser.getCurrentDirectory();

            // if directory changed
            if (cd != this.currentDirectory || this.currentDirectory == null) {
                // get all file filters
                FileFilter[] fileFilters = fileChooser.getChoosableFileFilters();
                File[] files = cd.listFiles();

                this.currentFiles = new ArrayList<File>();
                for (File file : files) {
                    // only file filter meets our need will be valid
                    for (FileFilter filter : fileFilters) {
                        if (filter.accept(file)) {
                            // add file to currentFiles
                            this.currentFiles.add(file);
                        }
                    }
                }
            }

            // show image now
            ImageIcon icon = new ImageIcon(name);

            // use thumbnail to avoid java.lang.OutOfMemoryError
            if (icon.getIconHeight() > 5000 || icon.getIconWidth() > 5000) {
                //java.awt.Image#SCALE_SMOOTH
                ImageIcon thumbIcon = new ImageIcon(icon.getImage()
                        .getScaledInstance(1024, 768, Image.SCALE_SMOOTH));
                frame.getLabel().setIcon(thumbIcon);
            }
            else {
                frame.getLabel().setIcon(icon);
            }
        }
    }

    // zoom in
    // there is damage to pic current now when zoom in
    public void zoomIn(ViewerFrame frame) {
        // get current pic
        ImageIcon icon = (ImageIcon) frame.getLabel().getIcon();

        double rationAdd = 1 + this.ratio;

        if (icon != null) {
            int width = (int) (icon.getIconWidth() * rationAdd);

            // get new size image
            ImageIcon newIcon = new ImageIcon(icon.getImage()
                    .getScaledInstance(width, -1, Image.SCALE_DEFAULT));
            // show the changed image
            frame.getLabel().setIcon(newIcon);
        }
    }

    // zoom out
    // there is damage to pic current now when zoom out
    public void zoomOut(ViewerFrame frame) {

        // get current pic
        ImageIcon icon = (ImageIcon) frame.getLabel().getIcon();

        double rationAdd = 1 - this.ratio;

        if (icon != null) {
            int width = (int) (icon.getIconWidth() * rationAdd);

            // get new size image
            ImageIcon newIcon = new ImageIcon(icon.getImage()
                    .getScaledInstance(width, -1, Image.SCALE_DEFAULT));
            // show the changed image
            frame.getLabel().setIcon(newIcon);
        }
    }

    // show previous
    public void prev(ViewerFrame frame) {
        // if directory have pics
        if ((this.currentFiles != null) && (!this.currentFiles.isEmpty())) {
            // get current file index
            int index = this.currentFiles.indexOf(this.currentFile);

            // open previous pic
            if (index > 0) {
                File file = (File) this.currentFiles.get(index - 1);
                ImageIcon icon = new ImageIcon(file.getPath());

                // use thumbnail to avoid java.lang.OutOfMemoryError
                if (icon.getIconHeight() > 5000 || icon.getIconWidth() > 5000) {
                    ImageIcon thumbIcon = new ImageIcon(icon.getImage()
                            .getScaledInstance(1024, 768, Image.SCALE_SMOOTH));
                    frame.getLabel().setIcon(thumbIcon);
                }
                else {
                    frame.getLabel().setIcon(icon);
                }

                frame.getLabel().setIcon(icon);
                this.currentFile = file;
            }
        }
    }

    // show next
    public void next(ViewerFrame frame) {
        // if directory have pics
        if ((this.currentFiles != null) && (!this.currentFiles.isEmpty())) {
            int index = this.currentFiles.indexOf(this.currentFile) + 1;

            // open next
            if (index + 1 < this.currentFiles.size()) {
                File file = (File) this.currentFiles.get(index+1);

                ImageIcon icon = new ImageIcon(file.getPath());
                // use thumbnail to avoid java.lang.OutOfMemoryError
                if (icon.getIconHeight() > 5000 || icon.getIconWidth() > 5000) {
                    ImageIcon thumbIcon = new ImageIcon(icon.getImage()
                            .getScaledInstance(1024, 768, Image.SCALE_SMOOTH));
                    frame.getLabel().setIcon(thumbIcon);
                }
                else {
                    frame.getLabel().setIcon(icon);
                }
                this.currentFile = file;
            }
        }
    }

    // menu response
    public void menuResponse(ViewerFrame frame, String cmd) {
        if (cmd.equals("Open")) {
            open(frame);
        }
        else if (cmd.equals("ZoomIn")) {
            zoomIn(frame);
        }
        else if (cmd.equals("ZoomOut")) {
            zoomOut(frame);
        }
        else if (cmd.equals("Prev")) {
            prev(frame);
        }
        else if (cmd.equals("Next")) {
            next(frame);
        }
        else if (cmd.equals("Exit")) {
            frame.dispose();
            System.exit(0);
        }
        else if (cmd.equals("About")) {
            JOptionPane.showMessageDialog(null, "One image display kit.", "About", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
