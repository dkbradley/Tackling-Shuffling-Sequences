package li.aron.shuffler;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Heatmap extends javax.swing.JFrame {
    private final int size;
    private final Object[][] data;
    private final boolean showChance;
    private int timesShuffled;
    
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    
    public Heatmap(int size, Object[][] data, boolean showPercentages) {
        this.size = size;
        this.data = data;
        this.showChance = showPercentages;
        for (int i = 0; i < 52; i++) {
            this.timesShuffled += (int) data[0][i];
        }
        
        initComponents();
    }
    
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        TableContent model = new TableContent(size, data);
        jTable1 = new javax.swing.JTable(model);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Heatmap");

        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer());
        jTable1.setTableHeader(null);
        jTable1.getColumnModel().getColumn(0).setMinWidth(30);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
    
    class TableCellRenderer extends DefaultTableCellRenderer {
        private final Color level_100 = new Color(255,85,0);
        private final Color level_95  = new Color(255,119,25);
        private final Color level_90  = new Color(255,153,51);
        private final Color level_85  = new Color(255,204,51);
        private final Color level_80  = new Color(255,255,51);
        private final Color level_75  = new Color(204,255,51);
        private final Color level_70  = new Color(153,255,51);
        private final Color level_65  = new Color(76,255,25);
        private final Color level_60  = new Color(0,255,0);
        private final Color level_55  = new Color(0,255,127);
        private final Color level_50  = new Color(0,255,255);
        private final Color level_45  = new Color(0,204,204);
        private final Color level_40  = new Color(0,153,153);
        private final Color level_35  = new Color(0,127,127);
        private final Color level_30  = new Color(0,102,102);
        private final Color level_25  = new Color(0,51,153);
        private final Color level_20  = new Color(0,0,204);
        private final Color level_15  = new Color(0,0,153);
        private final Color level_10  = new Color(0,0,102);
        private final Color level_5   = new Color(0,0,51);
        private final Color level_0   = new Color(0,0,0);
        
        private final Card c;
        
        public TableCellRenderer() {
            super();
            setOpaque(true);
            c = new Card(0,0);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == 0 || column == 0) {
                setBackground(Color.LIGHT_GRAY);
                if (column == 0 && row != 0) {
                    setText(c.getCardByNumber((int) value - 1));
                } else {
                    setText(value != null ? value.toString() : "");
                }
            } else {
                DecimalFormat df = new DecimalFormat("#,##0.0");

                double rowPercent = ((int) value * 1.0) / timesShuffled * 100.0;
                double chance = Double.parseDouble(df.format(rowPercent / 100.0 * size).replaceAll(",", "."));
                
                if (chance >= 26)
                    setBackground(level_100);
                else if (chance >= 20)
                    setBackground(level_95);
                else if (chance >= 16)
                    setBackground(level_90);
                else if (chance >= 12)
                    setBackground(level_85);
                else if (chance >= 8)
                    setBackground(level_80);
                else if (chance >= 4)
                    setBackground(level_75);
                else if (chance >= 3)
                    setBackground(level_70);
                else if (chance >= 2)
                    setBackground(level_65);
                else if (chance >= 1.6)
                    setBackground(level_60);
                else if (chance >= 1.3)
                    setBackground(level_55);
                else if (chance >= 1.0)
                    setBackground(level_50);
                else if (chance >= 0.9)
                    setBackground(level_45);
                else if (chance >= 0.8)
                    setBackground(level_40);
                else if (chance >= 0.7)
                    setBackground(level_35);
                else if (chance >= 0.6)
                    setBackground(level_30);
                else if (chance >= 0.5)
                    setBackground(level_25);
                else if (chance >= 0.4)
                    setBackground(level_20);
                else if (chance >= 0.3)
                    setBackground(level_15);
                else if (chance >= 0.2)
                    setBackground(level_10);
                else if (chance >= 0.1)
                    setBackground(level_5);
                else
                    setBackground(level_0);
                
                if (showChance) {
                    if (chance >= 10) {
                        setText("" + (int)chance);
                    } else {
                        setText("" + chance);
                    }
                } else {
                    setText("");
                }
            }
            return this;
        }
    }
    
    class TableContent extends AbstractTableModel {
        private final int columns;
        public final Object[][] data;
        
        public TableContent(int size, Object[][] data) {
            this.columns = size + 1;
            
            Object[][] o = new Object[columns][columns];
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < columns; j++) {
                    if (!(i == 0 && j == 0)) {
                        if (i == 0) {
                            o[i][j] = j;
                        } else if (j == 0) {
                            o[i][j] = i;
                        } else {
                            o[i][j] = data[i-1][j-1];
                        }
                    }
                }
            }
            
            this.data = o;
        }
        
        @Override
        public int getColumnCount() {
            return columns;
        }
        
        @Override
        public int getRowCount() {
            return data.length;
        }
        
        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        
        @Override
        public void setValueAt(Object val, int row, int col) {
            data[row][col] = val;
        }
        
        @Override
        public String getColumnName(int col) {
            return "";
        }
        
        @Override
        public Class getColumnClass(int column) {
            return Object.class;
        }
    }
}
