package h05.ui;

import fopbot.World;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@DoNotTouch
public class InfoPopup extends JDialog {

    @DoNotTouch
    private final String[] columnNames = {"Name", "Mined"};

    @DoNotTouch
    private final Object[][] data;

    @DoNotTouch
    public InfoPopup(JFrame parent, List<Map.Entry<String, Integer>> data) {
        super(parent, "Mining info", true);
        this.data = data.stream().map(entry -> new Object[]{
                entry.getKey(),
                entry.getValue()
        }).toArray(Object[][]::new);

        DefaultTableModel tableModel = new DefaultTableModel(this.data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @DoNotTouch
    public static void showInfo(List<Map.Entry<String, Integer>> data) {
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
