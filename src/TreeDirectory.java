import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;

public class TreeDirectory extends JFrame implements TreeWillExpandListener
{
    private JTree jTree;
    private DefaultTreeModel treeModel;
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                TreeDirectory tree = new TreeDirectory();

            }
        });
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent arg0)
            throws ExpandVetoException {
    }
    @Override
    public void treeWillExpand(TreeExpansionEvent arg0)
            throws ExpandVetoException {
        final FileNode lazyNode =
                (FileNode) arg0.getPath().getLastPathComponent();
        if( lazyNode.isFullyLoaded() )
        {
            return;
        }
        new SwingWorker<List<MutableTreeNode>, Void>(){ //every time when a parent node happens after expanding, we load it's children
            @Override
            protected List<MutableTreeNode> doInBackground() throws Exception {
                // TODO Auto-generated method stub
                return lazyNode.loadChildren();
            }
            protected void done() {
                try {

                    for (MutableTreeNode node : get()) {
                        treeModel.insertNodeInto(node, lazyNode,
                                lazyNode.getChildCount());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    public TreeDirectory() {

            FileNode root = new FileNode(new File(""));
            treeModel = new DefaultTreeModel(root, true);
            jTree = new JTree(treeModel);
            jTree.setRootVisible(true);
            jTree.addTreeWillExpandListener(this);
            jTree.collapseRow(0); // collapse the root in order to implement TreeWillExpandListener after expanding in first level.

        JScrollPane scrollBar = new JScrollPane(jTree,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollBar);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("JTree Example");
            this.setSize(800,600);
            this.setVisible(true);


    }
}