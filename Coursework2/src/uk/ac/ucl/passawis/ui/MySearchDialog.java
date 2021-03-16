package uk.ac.ucl.passawis.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MySearchDialog extends JDialog {
    private JTextField searchField;

    public MySearchDialog(String name, Consumer<String> callback){
        super();
        createLayout(callback);
        this.setTitle("Search " + name);
        mapEscapeKeyToClose(this.getRootPane());

    }

    public MySearchDialog(String name, String defaultValue, Consumer<String> callback){
        this(name, callback);
        this.searchField.setText(defaultValue);
    }

    private void mapEscapeKeyToClose(JComponent component){
        KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(k, "ESCAPE_KEY");

        component.getActionMap().put("ESCAPE_KEY", new AbstractAction() {
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

        searchInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    dispose();
                }
            }
        });

        this.searchField = searchInput;

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
