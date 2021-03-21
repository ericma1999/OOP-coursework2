import uk.ac.ucl.passawis.ui.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class SidePanel extends JFrame{
    private JScrollPane panel;
    private JPanel sideButtonContainer;
    private JButton sidePanelCloseButton;
    private JButton sidePanelSearchButton;
    private JButton sidePanelDashboardButton;
    private JButton sidePanelLoadButton;
    private JButton writeJSONButton;
    private String[] columnNames;
    private MyTable table;
    private final Color sidePanelColour = new Color(61, 105, 240);

    private Supplier<Boolean> handleSearchClick;


    public SidePanel(){
        this.create();
    }
    public JScrollPane getPanel() {return this.panel;}


    public void setTable(MyTable table){
        this.table = table;
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

    private void create(){
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(this.sidePanelColour);
        createSideButtonContainer();
        sidePanel.add(this.sideButtonContainer, SwingConstants.CENTER);
        JScrollPane scrollContainer = new JScrollPane(sidePanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContainer.getViewport().setPreferredSize(new Dimension(300, 720));
        this.panel = scrollContainer;
    }

    private void createSideButtonContainer() {
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

    public void onSearchButtonClicked(Supplier<Boolean> callback){

        this.handleSearchClick = callback;
    }

    private void createSidePanelSearchButton(){
        sidePanelSearchButton = createSidePanelButton("Search");
        sidePanelSearchButton.addActionListener(e -> {
            if (handleSearchClick.get()){
                updateSidePanel(1);
            }
        });
    }

    private void createSidePanelDashboardButton() {
        sidePanelDashboardButton = createSidePanelButton("Dashboard");
//        sidePanelDashboardButton.addActionListener((ActionEvent e) -> updateSidePanel(2));
    }

    private void createSidePanelCloseButton() {
        sidePanelCloseButton = createSidePanelButton("Close");

        sidePanelCloseButton.addActionListener(e -> {
            updateSidePanel(0);
        });


//        sidePanelCloseButton.addActionListener((ActionEvent e) -> {
//            updateSidePanel(0);
//            this.currentFilters = new HashMap<>();
//            ((MyTableModel) this.table.getModel()).setData(controller.getAllData());
//        });
    }


    private void toSearchPage() {
        createSidePanelCloseButton();
        createSearchControls();
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

    public void setColumnNames(String[] columnNames){
        this.columnNames = columnNames;
    }

    private void createSearchControls() {
        JPanel container = new JPanel(new GridLayout(0, 1, 0, 0));
        container.setBackground(sidePanelColour);
//        container.add(renderCurrentFilters());

//        createAdvanceSearchButtons(container);

        for (String columnName : this.columnNames) {
            JCheckBox checkbox = createColumnCheckbox(columnName);
            container.add(checkbox);
        }
        container.add(this.sidePanelCloseButton, BorderFactory.createEmptyBorder());


        sideButtonContainer.add(container);
    }

    private void createSidePanelLoadButton(){
        this.sidePanelLoadButton = createSidePanelButton("Load New File");
//        this.sidePanelLoadButton.addActionListener(e -> handleLoadFile());
//        sideButtonContainer.add(this.sidePanelLoadButton);
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