import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

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

        String[] columnNames = this.model.getColumnNames();
        JTable table = new JTable(new MyTableModel(this.model));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane);
        add(rightPanel);
    }

    private void createSidePanel(){
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(250, 720));
        sidePanelButtonContainer();
        sidePanel.setBackground(sidePanelColour);
        sidePanel.add(this.sideButtonContainer, SwingConstants.CENTER);
        add(sidePanel, BorderLayout.WEST);
        this.sidePanel = sidePanel;

    }

    private void sidePanelButtonContainer(){
        JPanel buttonContainer = new JPanel(new GridLayout(0, 1,0, 10));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
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

    private void updateSidePanel(int id){
        this.page = id;
        if (id == 1){
            sideButtonContainer.remove(sidePanelDashboardButton);
            sideButtonContainer.remove(sidePanelSearchButton);
            createSidePanelCloseButton();
            sideButtonContainer.add(this.sidePanelCloseButton);
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