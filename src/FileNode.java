import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends DefaultMutableTreeNode
{
    public FileNode(File file)
    {
        super(file);
        setAllowsChildren(file.isDirectory());
    }
    public List<MutableTreeNode> loadChildren() {
        List<MutableTreeNode> list = new ArrayList<>();
        File[] fileList = ((File) getUserObject()).listFiles();
        if (fileList == null) {
            fileList = new File[0];
        }
        for (File file : fileList) {
            list.add(new FileNode(file));
        }
        return list;
    }
    public boolean isFullyLoaded()
    {
        return getChildCount() != 0 && getAllowsChildren();
    }
}