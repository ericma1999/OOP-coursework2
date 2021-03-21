import uk.ac.ucl.passawis.ui.MySearchDialog;
import uk.ac.ucl.passawis.ui.MyTable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

public class GUI extends JFrame {

    private int page = 0;
    private final Controller controller = new Controller();
    private final Color sidePanelColour = new Color(61, 105, 240);
    private JButton sidePanelSearchButton;
    private JButton sidePanelDashboardButton;
    private JButton sidePanelLoadButton;
    private JButton sidePanelCloseButton;
    private JButton writeJSONButton;
    private JPanel sideButtonContainer;
    private JPanel rightPanel;
    private JPanel currentFilterContainer;
    private MySearchDialog currentSearchDialog;
    private MyTable table;

    private HashMap<String, String> currentFilters = new HashMap<>();


    public GUI() {
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(1280, 720));
//        center the JFrame on the screen
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void createGUI() {
        setTitle("Application");
        createSidePanel();
        createRightPanel();
    }

    private void createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanelButtonContainer();
        sidePanel.setBackground(sidePanelColour);
        sidePanel.add(this.sideButtonContainer, SwingConstants.CENTER);
        JScrollPane scrollContainer = new JScrollPane(sidePanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContainer.getViewport().setPreferredSize(new Dimension(300, 720));
        add(scrollContainer, BorderLayout.WEST);

    }

    private void sidePanelButtonContainer() {
        JPanel buttonContainer = new JPanel(new GridLayout(0, 1, 0, 10));
        buttonContainer.setMaximumSize(new Dimension(300, 500));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        buttonContainer.setBackground(sidePanelColour);
        createSidePanelSearchButton();
        createSidePanelDashboardButton();
        buttonContainer.add(sidePanelDashboardButton, SwingConstants.CENTER);
        buttonContainer.add(sidePanelSearchButton, SwingConstants.CENTER);
        this.sideButtonContainer = buttonContainer;
    }

    private JButton createSidePanelButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private void createSidePanelDashboardButton() {
        sidePanelDashboardButton = createSidePanelButton("Dashboard");
        sidePanelDashboardButton.addActionListener((ActionEvent e) -> updateSidePanel(2));
    }

    private void createErrorDialog(String message){
        JOptionPane.showMessageDialog(this,
                message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createSidePanelSearchButton() {
        sidePanelSearchButton = createSidePanelButton("Search");
        sidePanelSearchButton.addActionListener((ActionEvent e) -> {
            if (this.table != null) {
                updateSidePanel(1);
            } else {
                createErrorDialog("Please load a file before trying to search");
            }
        });
    }

    private void createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JButton loadFileButton = new JButton("Load Data");
        loadFileButton.addActionListener(e -> handleLoadFile());
        rightPanel.add(loadFileButton);
        this.rightPanel = rightPanel;
        add(rightPanel);

    }

    private void handleLoadFile() {
        String path = showFileDialog();
        if (path != null && controller.loadFile(path)){
            loadFileSuccess();
        }else {
            createErrorDialog("Failed to read the file selected. Please try again");
        }
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private String showFileDialog() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./"));
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file.getPath();
        }
        return null;
    }

    private void writeJSONFile(){
        JFileChooser dialog = new JFileChooser();
        int userSelection = dialog.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = dialog.getSelectedFile();
//                validate that the file does not exist
            controller.writeToJSON(file.getPath());
        }
    }


    private void loadFileSuccess(){
        rightPanel.removeAll();
        rightPanel.setLayout(new GridLayout(0, 1));
        renderTable();

        if (this.sidePanelLoadButton == null && this.writeJSONButton == null) {
            createSidePanelLoadButton();
            createSidePanelWriteJSONButton();

        }
    }

    private void createSidePanelLoadButton(){
        this.sidePanelLoadButton = createSidePanelButton("Load New File");
        this.sidePanelLoadButton.addActionListener(e -> handleLoadFile());
        sideButtonContainer.add(this.sidePanelLoadButton);
    }

    private void createSidePanelWriteJSONButton(){
        this.writeJSONButton = createSidePanelButton("Write to JSON file");
        this.writeJSONButton.addActionListener(e -> writeJSONFile());
        sideButtonContainer.add(this.writeJSONButton);
    }

    private void handleHeaderClick(MouseEvent e){
        if (currentSearchDialog != null) {
            currentSearchDialog.dispose();
            currentSearchDialog = null;
        }
        int col = table.columnAtPoint(e.getPoint());

        String columnName = table.getColumnName(col);
        String previousValue = currentFilters.get(columnName);
        String defaultValue = previousValue == null ? "" : previousValue;

        currentSearchDialog = new MySearchDialog(columnName, defaultValue, (String value) -> {
            if (!value.equals("")) {
                currentFilters.put(columnName, value);
            } else {
                currentFilters.remove(columnName);
            }
            if(page != 1){
                updateSidePanel(1);
            }
            ((MyTableModel) table.getModel()).setData(controller.getDataWithFilters(currentFilters));
            updateCurrentFilterDisplay();
        });
        currentSearchDialog.setVisible(true);

    }

    private void renderTable() {
        MyTable table = new MyTable(new MyTableModel(controller.getAllData(), controller.getColumnNames()));
        table.setHeaderClickAction(this::handleHeaderClick);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane);
        add(rightPanel);
        this.table = table;

    }

    private void createSidePanelCloseButton() {
        sidePanelCloseButton = createSidePanelButton("Close");
        sidePanelCloseButton.addActionListener((ActionEvent e) -> {
            updateSidePanel(0);
            this.currentFilters = new HashMap<>();
            ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
        });
    }

    private JCheckBox createColumnCheckbox(String columnName) {
        JCheckBox checkbox = new JCheckBox(columnName, true);
        checkbox.setFont(new Font("Arial", Font.BOLD, 12));
        checkbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                table.unhideColumn(columnName);
            } else {
                table.hideColumn(columnName);
            }
        });
        checkbox.setForeground(Color.white);
        return checkbox;
    }

    private void createAdvanceSearchButtons(JPanel panel) {

        JButton clearFilters = new JButton("Clear filters");
        clearFilters.addActionListener(e -> {
            this.currentFilters = new HashMap<>();

            if (this.currentSearchDialog != null) {
                this.currentSearchDialog.dispose();
            }

            updateCurrentFilterDisplay();
            ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
        });

        JButton oldestButton = new JButton("Oldest Living");
        oldestButton.addActionListener(e -> ((MyTableModel) this.table.getModel()).setData(controller.findOldest()));

        panel.add(clearFilters);
        panel.add(oldestButton);
    }

    private void updateCurrentFilterDisplay() {
        this.currentFilterContainer.removeAll();

        if (currentFilters.isEmpty()) {
            JLabel label = new JLabel("No Search filters", SwingConstants.CENTER);
            label.setMaximumSize(new Dimension(80, 0));
            label.setForeground(Color.black);
            this.currentFilterContainer.add(label);
        }

        for (String name : currentFilters.keySet()) {
            this.currentFilterContainer.add(new JLabel(name + ": " + this.currentFilters.get(name)));
        }

        this.currentFilterContainer.revalidate();
        this.currentFilterContainer.repaint();

    }

    private JPanel renderCurrentFilters() {
        JPanel container = new JPanel(new GridLayout(0, 1));
        container.setBackground(sidePanelColour);
        if (currentFilters.isEmpty()) {
            JLabel label = new JLabel("No Search filters", SwingConstants.CENTER);
            label.setMaximumSize(new Dimension(80, 0));
            label.setForeground(Color.black);
            container.add(label);
        }
        this.currentFilterContainer = container;
        return container;
    }

    private void createSearchControls() {
        JPanel container = new JPanel(new GridLayout(0, 1, 0, 0));
        container.setBackground(sidePanelColour);
        container.add(renderCurrentFilters());

        createAdvanceSearchButtons(container);

        for (String columnName : controller.getColumnNames()) {
            JCheckBox checkbox = createColumnCheckbox(columnName);
            container.add(checkbox);
        }
        container.add(this.sidePanelCloseButton, BorderFactory.createEmptyBorder());


        sideButtonContainer.add(container);
    }

    private void toSearchPage() {
        createSidePanelCloseButton();
        createSearchControls();
    }

    private void toMainPage() {
        sideButtonContainer.add(sidePanelSearchButton);
        sideButtonContainer.add(sidePanelDashboardButton);
        if (this.sidePanelLoadButton != null) {
            sideButtonContainer.add(this.sidePanelLoadButton);
            sideButtonContainer.add(this.writeJSONButton);
        }
    }

    private void updateSidePanel(int id) {
        this.page = id;
        sideButtonContainer.removeAll();
        if (id == 1) {
            toSearchPage();
        } else if (id == 0) {
            toMainPage();
        }
        sideButtonContainer.revalidate();
        sideButtonContainer.repaint();
    }

}