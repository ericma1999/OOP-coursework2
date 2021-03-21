import uk.ac.ucl.passawis.ui.MySearchDialog;
import uk.ac.ucl.passawis.ui.MyTable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;


import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

public class GUI extends JFrame {

    private final Controller controller = new Controller();
    private JPanel rightPanel;
    private SidePanel sidePanel;

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
         SidePanel sidePanel = new SidePanel();
         sidePanel.onSearchButtonClicked(() -> {
             if (this.table != null){
                 return true;
             }
             createErrorDialog("File not found");
             return false;

         });

         sidePanel.onClearButtonClicked(() -> {
             this.currentFilters = new HashMap<>();
             sidePanel.setCurrentFilters(this.currentFilters);
             if (this.currentSearchDialog != null) {
                 this.currentSearchDialog.dispose();
             }
            ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
             return true;
         });

         sidePanel.onCloseButtonClicked(() -> {
             this.currentFilters = new HashMap<>();
             sidePanel.setCurrentFilters(this.currentFilters);
             ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
             return true;
         });

         sidePanel.onLoadButtonClick(() -> {
             handleLoadFile();
             return true;
         });

         sidePanel.onWriteJsonButtonClick(() -> {
             writeJSONFile();
             return true;
         });

         sidePanel.onOldestButtonClicked(() -> {
             this.currentFilters = new HashMap<>();
             sidePanel.setCurrentFilters(this.currentFilters);
             ((MyTableModel) this.table.getModel()).setData(controller.findOldest());
             return true;
         });

         this.sidePanel = sidePanel;


        add(sidePanel.getPanel(), BorderLayout.WEST);
    }

    private void createErrorDialog(String message){
        JOptionPane.showMessageDialog(this,
                message,
                "Error", JOptionPane.ERROR_MESSAGE);
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
    }


    private void handleTableHeaderClick(MouseEvent e){
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
            if(sidePanel.getPageID() != 1){
                sidePanel.goToPage(1);
            }

            sidePanel.setCurrentFilters(currentFilters);
            ((MyTableModel) table.getModel()).setData(controller.getDataWithFilters(currentFilters));
        });
        currentSearchDialog.setVisible(true);

    }

    private void renderTable() {

        String[] columnNames = controller.getColumnNames();

        MyTable table = new MyTable(new MyTableModel(controller.getAllData(), columnNames));
        sidePanel.setTable(table);
        sidePanel.setColumnNames(columnNames);
        table.setHeaderClickAction(this::handleTableHeaderClick);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane);
        add(rightPanel);
        this.table = table;

    }
}