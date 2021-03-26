import uk.ac.ucl.passawis.ui.MySearchDialog;
import uk.ac.ucl.passawis.ui.MyTable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;

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

    private boolean handleClearFilters() {
        this.currentFilters = new HashMap<>();
        sidePanel.setCurrentFilters(this.currentFilters);
        if (this.currentSearchDialog != null) {
            this.currentSearchDialog.dispose();
        }
        ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
        return true;
    }

    private boolean handleClose() {
        this.currentFilters = new HashMap<>();
        sidePanel.setCurrentFilters(this.currentFilters);
        ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
        return true;
    }

    private boolean handleOldest(){
        this.currentFilters = new HashMap<>();
        sidePanel.setCurrentFilters(this.currentFilters);
        ((MyTableModel) this.table.getModel()).setData(controller.findOldest());
        return true;
    }

    private boolean handleDashboard(){
        if (table != null){
            renderDashboard();
            return true;
        }
        createErrorDialog("No file has been loaded yet");
        return false;
    }

    private boolean handleSearch(){
        if (this.table != null) {
            rightPanel.removeAll();
            renderTable();
            this.rightPanel.revalidate();
            this.rightPanel.repaint();
            return true;
        }
        createErrorDialog("No file has been loaded yet");
        return false;
    }

    private void createSidePanel() {
        SidePanel sidePanel = new SidePanel();
        sidePanel.onSearchButtonClicked(this::handleSearch);

        sidePanel.onClearButtonClicked(this::handleClearFilters);

        sidePanel.onCloseButtonClicked(this::handleClose);

        sidePanel.onLoadButtonClick(this::handleLoadFile);

        sidePanel.onWriteJsonButtonClick(this::writeJSONFile);

        sidePanel.onOldestButtonClicked(this::handleOldest);

        sidePanel.onDashboardButtonClick(this::handleDashboard);

        this.sidePanel = sidePanel;

        add(sidePanel.getPanel(), BorderLayout.WEST);
    }

    private void createErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createRightPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton loadFileButton = new JButton("Load Data");
        loadFileButton.addActionListener(e -> handleLoadFile());
        panel.add(loadFileButton);
        this.rightPanel = panel;
        add(panel);

    }

    private boolean handleLoadFile() {
        String path = showFileDialog();
        if (path != null && controller.loadFile(path)) {
            loadFileSuccess();
        } else {
            createErrorDialog("Failed to read the file selected. Please try again");
        }
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        return true;
    }

    private String showFileDialog() {
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv", "csv");
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("json", "json");
        fc.addChoosableFileFilter(csvFilter);
        fc.addChoosableFileFilter(jsonFilter);
        fc.setCurrentDirectory(new File("./"));
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file.getPath();
        }
        return null;
    }

    private boolean writeJSONFile() {
        JFileChooser dialog = new JFileChooser();
        int userSelection = dialog.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = dialog.getSelectedFile();
//                validate that the file does not exist
            controller.writeToJSON(file.getPath());
        }
        return true;
    }


    private void loadFileSuccess() {
        rightPanel.removeAll();
        rightPanel.setLayout(new GridLayout(0, 1));
        renderTable();
    }


    private void handleTableHeaderClick(MouseEvent e) {
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
            if (sidePanel.getPageID() != 1) {
                sidePanel.goToPage(1);
            }

            sidePanel.setCurrentFilters(currentFilters);
            ((MyTableModel) table.getModel()).setData(controller.getDataWithFilters(currentFilters));
        });
        currentSearchDialog.setVisible(true);

    }

    private void renderTable() {

        List<String> columnNames = controller.getColumnNames();

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


    private void renderDashboard(){
        rightPanel.removeAll();
        Dashboard dashboard = new Dashboard();
        dashboard.onHandleLivingClick(() -> controller.getOccurences());
        rightPanel.add(dashboard, BorderLayout.NORTH);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}