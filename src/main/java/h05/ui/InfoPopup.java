package h05.ui;

import fopbot.World;
import h05.loot.Inventory;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@DoNotTouch
public class InfoPopup extends JDialog {

    @DoNotTouch
    private final String[] columnNames = {"Name", "Amount"};

    @DoNotTouch
    private final Object[][] data;

    @DoNotTouch
    public InfoPopup(JFrame parent, Object[][] data) {
        super(parent, "Mining info", true);
        this.data = data;
        DefaultTableModel tableModel = new DefaultTableModel(this.data, columnNames);
        JTable table = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int x = 0; x < columnNames.length; x++) {
            table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @DoNotTouch
    public static void showInfo(Inventory inventory) {
        Object[][] data = new Object[inventory.size()][2];
        String[] names = inventory.getNames();
        for (int i = 0; i < inventory.size(); i++) {
            data[i][0] = names[i];
            data[i][1] = inventory.getAmount(names[i]);
        }
        JFrame parent = World.getGlobalWorld().getGuiFrame();

        InfoPopup infoPopup = new InfoPopup(parent, data);
        infoPopup.setSize(300, 200);
        infoPopup.setLocationRelativeTo(parent);
        infoPopup.setVisible(true);
    }

    @DoNotTouch
    public String[] getColumnNames() {
        return columnNames;
    }

    @DoNotTouch
    public Object[][] getData() {
        return data;
    }
}
