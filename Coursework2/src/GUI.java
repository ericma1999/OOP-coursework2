import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class GUI extends JFrame
{

    private int page = 0;
    private Model model;
    private final Color sidePanelColour = new Color(61,105,240);
    private JButton sidePanelSearchButton;
    private JButton sidePanelDashboardButton;
    private JButton sidePanelLoadButton;
    private JButton sidePanelCloseButton;
    private JPanel sideButtonContainer;
    private JPanel rightPanel;
    private JPanel sidePanel;
    private MySearchDialog currentSearchDialog;
    private MyTable table;


    public GUI()
    {
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(1280, 720));
//        center the JFrame on the screen
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void createGUI(){
        setTitle("Application");
        createSidePanel();
        createRightPanel();
    }

    private String showFileDialog(){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./"));
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file.getPath();
        }
        return null;
    }

    private void loadFile(){
        String path = showFileDialog();
        this.model = new Model(path);
        rightPanel.removeAll();
        rightPanel.setLayout(new GridLayout(0, 1));
        renderTable();

        if (this.sidePanelLoadButton == null){
            this.sidePanelLoadButton = createSidePanelButton("Load New File");
            this.sidePanelLoadButton.addActionListener(e -> loadFile());
            sideButtonContainer.add(this.sidePanelLoadButton);
        }

        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void renderTable(){
        MyTable table = new MyTable(new MyTableModel(this.model.getAllData(), this.model.getColumnNames()));


        // listener
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentSearchDialog != null){
                    currentSearchDialog.dispose();
                    currentSearchDialog = null;
                }
                    int col = table.columnAtPoint(e.getPoint());
                    currentSearchDialog = new MySearchDialog(table.getColumnName(col), (String value) -> {
                        ((MyTableModel) table.getModel()).setData(model.findName(value));
                    });
                    currentSearchDialog.show();
            }
        });



        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane);
        add(rightPanel);
        this.table = table;

    }

    private void createRightPanel(){
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JButton loadFileButton = new JButton("Load Data");
        loadFileButton.addActionListener(e -> loadFile());
        rightPanel.add(loadFileButton);
        this.rightPanel = rightPanel;
        add(rightPanel);

    }

    private void createSidePanel(){
        JPanel sidePanel = new JPanel();
        sidePanelButtonContainer();
        sidePanel.setBackground(sidePanelColour);
        sidePanel.add(this.sideButtonContainer, SwingConstants.CENTER);
        this.sidePanel = sidePanel;
        JScrollPane scrollContainer = new JScrollPane(sidePanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContainer.getViewport().setPreferredSize(new Dimension(250, 720));
        add(scrollContainer, BorderLayout.WEST);

    }

    private void sidePanelButtonContainer(){
        JPanel buttonContainer = new JPanel(new GridLayout(0, 1,0, 10));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        buttonContainer.setBackground(sidePanelColour);
        createSidePanelSearchButton();
        createSidePanelDashboardButton();
        buttonContainer.add(sidePanelDashboardButton, SwingConstants.CENTER);
        buttonContainer.add(sidePanelSearchButton, SwingConstants.CENTER);
        this.sideButtonContainer = buttonContainer;
    }

    private JButton createSidePanelButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private void createSidePanelSearchButton(){
        sidePanelSearchButton = createSidePanelButton("Search");
        sidePanelSearchButton.addActionListener((ActionEvent e) -> {
            if(this.table != null){
                updateSidePanel(1);
            }else {
                JOptionPane.showMessageDialog(this, "Please load a file before trying to search", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    private void createSidePanelDashboardButton(){
        sidePanelDashboardButton = createSidePanelButton("Dashboard");
        sidePanelDashboardButton.addActionListener((ActionEvent e) -> updateSidePanel(2));
    }

    private void createSidePanelCloseButton(){
        sidePanelCloseButton = createSidePanelButton("Close");
        sidePanelCloseButton.addActionListener((ActionEvent e) -> {
            updateSidePanel(0);
            ((MyTableModel) this.table.getModel()).setData(model.getAllData());
        });
    }

    private JCheckBox createColumnCheckbox(String columnName){
        JCheckBox checkbox = new JCheckBox(columnName, true);
        checkbox.setFont(new Font("Arial", Font.BOLD, 12));
        checkbox.addItemListener(e -> {
            String columnName1 = ((JCheckBox) e.getItem()).getText();
            if(e.getStateChange() == ItemEvent.SELECTED) {
                table.unhideColumn(columnName1);
            } else {
                table.hideColumn(columnName1);
            }
        });
        checkbox.setForeground(Color.white);
        return checkbox;
    }

    private void createAdvanceSearchButtons(JPanel panel){

        JButton oldestButton = new JButton("oldest living");
        oldestButton.addActionListener(e -> ((MyTableModel) this.table.getModel()).setData(model.findOldest()));
        JButton samePlace = new JButton("same place");
        samePlace.addActionListener(e -> model.peopleSamePlace());



        panel.add(oldestButton);
        panel.add(samePlace);
    }

    private void createSearchControls(){
        JPanel container = new JPanel(new GridLayout(0, 1,0, 0));
        container.setBackground(sidePanelColour);
        JTextField searchInput = new JFormattedTextField();
        searchInput.getDocument().addDocumentListener(new DocumentListener() {
            private MyTableModel tableModel = (MyTableModel) table.getModel();

            @Override
            public void insertUpdate(DocumentEvent e) {
                tableModel.setData(model.findName(searchInput.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tableModel.setData(model.findName(searchInput.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tableModel.setData(model.findName(searchInput.getText()));
            }
        });
        searchInput.setPreferredSize(new Dimension(200, 40));


        container.add(searchInput, BorderFactory.createEmptyBorder());


        createAdvanceSearchButtons(container);

        for (String columnName: model.getColumnNames()){
            JCheckBox checkbox = createColumnCheckbox(columnName);
            container.add(checkbox);
        }
        container.add(this.sidePanelCloseButton, BorderFactory.createEmptyBorder());


        sideButtonContainer.add(container);
    }

    private void updateSidePanel(int id){
        this.page = id;
        if (id == 1){
            sideButtonContainer.removeAll();
            createSidePanelCloseButton();
            createSearchControls();

        }else if (id == 0){
            sideButtonContainer.removeAll();
            sideButtonContainer.add(sidePanelSearchButton);
            sideButtonContainer.add(sidePanelDashboardButton);
            if (this.sidePanelLoadButton != null){
                sideButtonContainer.add(this.sidePanelLoadButton);
            }
        }
        sideButtonContainer.revalidate();
        sideButtonContainer.repaint();
    }

}