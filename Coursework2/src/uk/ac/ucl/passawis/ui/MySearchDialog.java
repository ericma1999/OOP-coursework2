package uk.ac.ucl.passawis.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MySearchDialog extends JDialog {
    private String name;

    public MySearchDialog(String name, Consumer<String> callback){
        super();
        this.name = name;
        createLayout(callback);
        this.setTitle("Search " + name);
        mapEscapeKeyToClose();

    }

    private void mapEscapeKeyToClose(){
        KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        JRootPane rootPane = this.getRootPane();

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(k, "ESCAPE_KEY");

        rootPane.getActionMap().put("ESCAPE_KEY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private JTextField createInputField(Consumer<String> callback){
        JFormattedTextField searchInput = new JFormattedTextField();
        searchInput.setPreferredSize(new Dimension(200, 50));
        searchInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                callback.accept(searchInput.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                callback.accept(searchInput.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                callback.accept(searchInput.getText());
            }
        });
        return searchInput;
    }

    private void createLayout(Consumer<String> callback){
        JPanel container = new JPanel(new FlowLayout());
        JTextField searchInput = createInputField(callback);
        container.add(searchInput, SwingConstants.CENTER);

        this.add(container);
        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
    }


}
