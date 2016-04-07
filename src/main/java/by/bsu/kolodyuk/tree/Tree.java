package by.bsu.kolodyuk.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Tree
{
    private List<Node> nodes;

    public Tree()
    {
        nodes = new ArrayList<>();
    }

    public void add(Node node) {
        nodes.add(node);
    }

    public Optional<Node> findNodeByMessage(String message) {
        return nodes.stream().filter(n -> message.equals(n.getMessage())).findFirst();
    }

    public List<Node> findAllQuestions() {
        return nodes.stream().filter(n -> n.getChildren().size() > 1).collect(Collectors.toList());
    }

    public Node getRoot()
    {
        return nodes.get(0);
    }
}
