package by.bsu.kolodyuk;

import by.bsu.kolodyuk.tree.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller implements Initializable {

    private Tree tree = new Tree();

    private String REGEX = "\\(([\\w\\s]+) = ([\\w\\s]+)\\)";

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pattern pattern = Pattern.compile(REGEX);
        Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("rules.txt"));

        Node prev = null;
        while(scanner.hasNextLine()) {
            Matcher matcher = pattern.matcher(scanner.nextLine());
            while (matcher.find()) {
                Node key = new Node(matcher.group(1));
                Node value = new Node(matcher.group(2));
                Optional<Node> foundKey = tree.findNodeByMessage(key.getMessage());
                if(foundKey.isPresent()) {
                    Optional<Node> foundValue = tree.findNodeByMessage(value.getMessage());
                    if(!foundValue.isPresent()) {
                        foundKey.get().addChild(value);
                        tree.add(value);
                    } else {
                        value = foundValue.get();
                    }
                } else {
                    key.addChild(value);
                    tree.add(key);
                    tree.add(value);
                    if(prev != null) {
                        prev.addChild(key);
                    }
                }
                prev = value;
            }
        }
        showAll();
    }

    public void showNode(Node node) {
        while(node.getChildren().size() == 1) {
            node = node.getChildren().get(0);
        }

        vBox.getChildren().clear();
        vBox.getChildren().add(new Label(node.getMessage() + (node.isLeaf() ? " !" : " ?")));
        node.getChildren().forEach(n -> vBox.getChildren().add(createButton(n)));
        if(node.isLeaf()) {
            vBox.getChildren().add(createBackButton());
        }
    }

    public void showAll() {
        vBox.getChildren().clear();
        tree.findAllQuestions().forEach(n -> vBox.getChildren().add(createButton(n)));
    }

    public Button createButton(Node node) {
        Button button = new Button(node.getMessage());
        button.setMinSize(200, 30);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(i -> showNode(node));
        return button;
    }

    public Button createBackButton() {
        Button button = new Button("Back to Menu");
        button.setMinSize(200, 30);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(i -> showAll());
        return button;
    }
}
