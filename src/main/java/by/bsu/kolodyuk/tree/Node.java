package by.bsu.kolodyuk.tree;


import java.util.ArrayList;
import java.util.List;

public class Node
{
    private Node parent;
    private String message;
    private List<Node> children;

    public Node()
    {
        this.children = new ArrayList<>();
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<Node> getChildren()
    {
        return children;
    }

    public void setChildren(List<Node> children)
    {
        this.children = children;
    }

    public boolean isLeaf()
    {
        return children.isEmpty();
    }

    public void addChild(Node node)
    {
        children.add(node);
    }
}
