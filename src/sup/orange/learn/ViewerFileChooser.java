    package sup.orange.learn;

    import javax.swing.*;
    import javax.swing.filechooser.FileFilter;
    import java.io.File;

    /**
     * Created by Rex on 14-11-25.
     */
    public class ViewerFileChooser extends JFileChooser{

        // create file chooser by using default path
        public ViewerFileChooser() {
            super();

            addFilter();
        }

        // create file chooser by using pointed path
        public ViewerFileChooser(String dir) {
            super(dir);
            addFilter();
        }

        // add file filter
        public void addFilter() {
            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".bmp"},
                            "bmp (*.bmp)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".jpg", ".jpeg", ".jfif"},
                            "jpeg (*.jpg; *.jpeg; *.jfif)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".gif"},
                            "gif (*.gif)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".tif", ".tiff"},
                            "tiff (*.tif; *.tiff)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".png"},
                            "png (*.png)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(new String[] {".ico"},
                            "ico (*.ico)"));

            this.addChoosableFileFilter(
                    new MyFileFilter(
                            new String[] {".bmp", ".jpg", ".jpeg", ".jfif", ".gif", ".tif", ".png", ".ico"},
                            "All Pics"));

        }

        class MyFileFilter extends FileFilter {
            // suffix name array
            String[] suffixArr;
            // description
            String description;

            public MyFileFilter() {
                super();
            }

            public MyFileFilter(String[] suffixArr, String description) {
                super();
                this.suffixArr = suffixArr;
                this.description = description;
            }

            // rewrite accept
            public boolean accept(File f) {
                // if suffix is valid
                for (String s: suffixArr) {
                    if (f.getName().toString().toLowerCase().endsWith(s)) {
                        return true;
                    }
                }

                // if directory, return true, or else false.
                return false;
            }

            // get description
            public String getDescription() {
                return this.description;
            }
        }
    }
