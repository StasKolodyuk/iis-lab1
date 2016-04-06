package by.bsu.kolodyuk.tree;

import java.util.ArrayList;
import java.util.List;


public class Tree
{
    private Node root;
    private List<Node> nodes;

    public Tree()
    {
        nodes = new ArrayList<>();
    }

    public void add(Node node) {
        nodes.add(node);
        node.getParent().addChild(node);
    }
}
