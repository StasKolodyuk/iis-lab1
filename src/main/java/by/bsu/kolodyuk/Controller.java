package by.bsu.kolodyuk;

import by.bsu.kolodyuk.model.Node;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jkee.gtree.Tree;
import org.jkee.gtree.builder.KeyTreeBuilder;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller implements Initializable {

    private KeyTreeBuilder.Funnel<String, Node> funnel = new KeyTreeBuilder.Funnel<String, Node>() {
        @Override
        public String getKey(Node node) {
            return node.getOption();
        }

        @Override
        public String getParentKey(Node node) {
            return node.getQuestion();
        }
    };

    private KeyTreeBuilder<String, Node> builder = new KeyTreeBuilder<>(funnel);
    private Tree<Node> tree;

    private String REGEX = "\\(([\\w\\s]+) = ([\\w\\s]+)\\)";

    @FXML
    private VBox vBox;

    private Node currentNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pattern pattern = Pattern.compile(REGEX);
        Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("rules.txt"));
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(null, "Target"));

        while(scanner.hasNextLine()) {
            List<String> groups = new ArrayList<>();
            Matcher matcher = pattern.matcher(scanner.nextLine());
            while (matcher.find()) {
                groups.add(matcher.group(1));
                groups.add(matcher.group(2));
            }
            for (int i = 0; i < groups.size() - 1; i++) {
                nodes.add(new Node(groups.get(i), groups.get(i + 1)));
            }
        }
        tree = builder.buildTree(nodes);
        currentNode = tree.getValue();
        showCurrentNode();
    }

    public void showAll() {
        List<Node> leaves = new ArrayList<>();
        List<Node> opt = new ArrayList<>();
        tree.treeIterator().forEachRemaining(tree -> {
            if(tree.getChildren() == null || tree.getChildren().isEmpty()) {
                leaves.add(tree.getValue());
            } else {
                opt.add(tree.getValue());
            }
        });

        opt.forEach(n -> vBox.getChildren().add(createMenuButton(n)));
    }

    public void showCurrentNode() {
        vBox.getChildren().clear();
        List<Node> options = tree.find(n -> currentNode.getOption().equals(n.getQuestion()));
        if(options.size() == 1) {
            Node next = tree.findOne(n -> options.get(0).getOption().equals(n.getQuestion()));
            if(next == null) { //is leaf
                vBox.getChildren().add(new Label(options.get(0).getOption()));
                vBox.getChildren().add(createBackButton());
            } else {
                vBox.getChildren().add(new Label(next.getOption() + '?'));
                tree.find(n -> next.getOption().equals(n.getQuestion())).forEach(n -> vBox.getChildren().add(createButton(n.getOption())));
            }
        } else {
            vBox.getChildren().add(new Label(currentNode.getOption() + '?'));
            options.forEach(n -> vBox.getChildren().add(createButton(n.getOption())));
        }
    }

    public Button createButton(String option) {
        Button button = new Button(option);
        button.setMinSize(200, 30);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(e -> {
            currentNode = tree.findOne(n -> option.equals(n.getQuestion()));
            showCurrentNode();
        });
        return button;
    }

    public Button createMenuButton(Node node) {
        Button button = new Button(node.getOption());
        button.setMinSize(200, 30);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(e -> {
            currentNode = tree.findOne(n -> node.getOption().equals(n.getQuestion()));
            showCurrentNode();
        });
        return button;
    }

    public Button createBackButton() {
        Button button = new Button("Back to Menu");
        button.setMinSize(200, 30);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(i -> showAll());
        currentNode = tree.getValue();
        return button;
    }
}