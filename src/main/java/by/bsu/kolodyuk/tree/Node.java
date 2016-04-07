package by.bsu.kolodyuk.tree;


import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String message;
    private List<Node> children;

    public Node(String message) {
        this.message = message;
        this.children = new ArrayList<>();
    }

    public Node()
    {
        this.children = new ArrayList<>();
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

    @Override
    public String toString() {
        return message + " " + children.size();
    }
}
