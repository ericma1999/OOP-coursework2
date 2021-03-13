import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI extends JFrame
{

    private int page = 0;
    private JPanel rootPanel;
    private Model model = new Model();
    private final Color sidePanelColour = new Color(61,105,240);
    private JButton sidePanelSearchButton;
    private JButton sidePanelDashboardButton;
    private JButton sidePanelCloseButton;
    private JPanel sideButtonContainer;
    private JPanel sidePanel;
    private MyTable table;


    public GUI()
    {
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
//        center the JFrame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGUI(){
        setTitle("Application");
        createSidePanel();
        createTablePanel();
    }

    private void createTablePanel(){
        JPanel rightPanel = new JPanel(new GridLayout(0 , 1));

        MyTable table = new MyTable(new MyTableModel(this.model));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane);
        add(rightPanel);
        this.table = table;
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
        sidePanelSearchButton.addActionListener((ActionEvent e) -> updateSidePanel(1));
    }


    private void createSidePanelDashboardButton(){
        sidePanelDashboardButton = createSidePanelButton("Dashboard");
        sidePanelDashboardButton.addActionListener((ActionEvent e) -> updateSidePanel(2));
    }

    private void createSidePanelCloseButton(){
        sidePanelCloseButton = createSidePanelButton("Close");
        sidePanelCloseButton.addActionListener((ActionEvent e) -> updateSidePanel(0));
    }

    private JCheckBox createColumnCheckbox(String columnName){
        JCheckBox checkbox = new JCheckBox(columnName, true);
        checkbox.setFont(new Font("Arial", Font.BOLD, 12));
        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String columnName = ((JCheckBox) e.getItem()).getText();
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    table.unhideColumn(columnName);
                } else {
                    table.hideColumn(columnName);
                };
            }
        });
        checkbox.setForeground(Color.white);
        return checkbox;
    }

    private void createSearchControls(){
        JPanel container = new JPanel(new GridLayout(0, 1,0, 0));
        container.setBackground(sidePanelColour);
        JTextField searchInput = new JFormattedTextField();
        searchInput.setPreferredSize(new Dimension(200, 40));
        container.add(searchInput, BorderFactory.createEmptyBorder());
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
            sideButtonContainer.remove(sidePanelDashboardButton);
            sideButtonContainer.remove(sidePanelSearchButton);
            createSidePanelCloseButton();
            createSearchControls();
            sideButtonContainer.revalidate();
            sideButtonContainer.repaint();

        }else if (id == 0){
            sideButtonContainer.removeAll();
            sideButtonContainer.add(sidePanelSearchButton);
            sideButtonContainer.add(sidePanelDashboardButton);
            sideButtonContainer.revalidate();
            sideButtonContainer.repaint();
        }
    }

}