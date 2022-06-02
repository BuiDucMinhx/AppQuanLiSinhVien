package ASM1;

import Model.Grade;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;

public class QLDiem extends javax.swing.JFrame {

    private ArrayList<Grade> list = new ArrayList<Grade>();
    private int currentIndex = -1;

    public QLDiem() {
        initComponents();
        setLocationRelativeTo(null);
        txtHoTen.setEditable(false);
        txtMaSV.setEditable(false);
        tblTop3.setCellSelectionEnabled(false);
        showTable();
        loadData();
        info();

    }

    String url = "jdbc:sqlserver://localhost:1433;databaseName=asmjava3";

    public void info() {
        lblUser1.setText(Share.USER.username);
        lblRole1.setText(Share.USER.role);
        lblEmail1.setText(Share.USER.email);
    }

    
    public void loadData() {
        Connection con = null;
        Statement stm = null;
        try {
            con = DriverManager.getConnection(url, "sa", "123");

            stm = con.createStatement();
            String sql = "";
            sql = "select Id, GRADE.MaSV as MaSV, HoTen, Photoshop, Java,"
                    + "Web from GRADE, sinhvien where GRADE.MASV = sinhvien.MASV ";
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int ID = rs.getInt("Id");
                String MASV = rs.getString("MaSV");
                String HoTen = rs.getString("HoTen");
                double Photoshop = rs.getDouble("Photoshop");
                double Java = rs.getDouble("Java");
                double Web = rs.getDouble("Web");
                double DiemTB = rs.getDouble("Web");
                Grade grade = new Grade(ID, MASV, HoTen, Photoshop, Java, Web, DiemTB);
                list.add(grade);
            }
            currentIndex = 0;
            DisplayDiemSV();
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    private void DisplayDiemSV() {
        Grade diemsv = list.get(currentIndex);
        txtMaSV.setText(diemsv.getMaSV() + "");
        txtHoTen.setText(diemsv.getHoTenSV() + "");
        txtPhotoshop.setText(diemsv.getPhotoshop() + "");
        txtJava.setText(diemsv.getJava() + "");
        txtWeb.setText(diemsv.getWeb() + "");
        UpdateDiemTB();
    }

    public void UpdateDiemTB() {

        float photoshop = Float.parseFloat(txtPhotoshop.getText());
        float java = Float.parseFloat(txtJava.getText());
        float web = Float.parseFloat(txtWeb.getText());
        double tb = (photoshop + java + web) / 3;
        lblDiemTB.setText(df.format(tb) + "");

    }

    public ArrayList<Grade> getGradeList3() {
        ArrayList<Grade> grades = new ArrayList<Grade>();
        String query = "SELECT TOP 3 * FROM grade ORDER BY DiemTB desc";
        try {
            con = DriverManager.getConnection(url, "sa", "123");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                grade = new Grade(rs.getInt("Id"), rs.getString("MaSV"), rs.getString("HoTenSV"),
                        Double.parseDouble(rs.getString("Photoshop")), Double.parseDouble(rs.getString("Java")),
                        Double.parseDouble(rs.getString("Web")), Double.parseDouble(rs.getString("DiemTB")));
                grades.add(grade);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return grades;
    }

    public ArrayList<Grade> getGradeList() {
        ArrayList<Grade> grades = new ArrayList<Grade>();
        String query = "SELECT * FROM grade ";
        try {
            con = DriverManager.getConnection(url, "sa", "123");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                grade = new Grade(rs.getInt("Id"), rs.getString("MaSV"), rs.getString("HoTenSV"),
                        Double.parseDouble(rs.getString("Photoshop")), Double.parseDouble(rs.getString("Java")),
                        Double.parseDouble(rs.getString("Web")), Double.parseDouble(rs.getString("DiemTB")));
                grades.add(grade);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return grades;
    }

    public void showTable() {
        ArrayList<Grade> list = getGradeList3();
        DefaultTableModel model = (DefaultTableModel) tblTop3.getModel();
        //clear khi chạy không chồng dữ liệu lên nhau
        model.setRowCount(0);
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getMaSV();
            row[1] = list.get(i).getHoTenSV();
            row[2] = list.get(i).getPhotoshop();
            row[3] = list.get(i).getJava();
            row[4] = list.get(i).getWeb();
            row[5] = list.get(i).getDiemTB();
            model.addRow(row);
        }
        System.out.println(list);
    }

    public void ShowItem(int index) {
        txtMaSV.setText(getGradeList3().get(index).getMaSV());
        txtHoTen.setText(getGradeList3().get(index).getHoTenSV());
        txtPhotoshop.setText(Double.toString(getGradeList3().get(index).getPhotoshop()));
        txtJava.setText(Double.toString(getGradeList3().get(index).getJava()));
        txtWeb.setText(Double.toString(getGradeList3().get(index).getWeb()));
        lblDiemTB.setText(Double.toString(getGradeList3().get(index).getDiemTB()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSearchMaSV = new javax.swing.JTextField();
        bntsearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPhotoshop = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJava = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblDiemTB = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        bntfirst = new javax.swing.JButton();
        bntnext = new javax.swing.JButton();
        bntprevious = new javax.swing.JButton();
        bntlast = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTop3 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        bntNew = new javax.swing.JButton();
        bntSave = new javax.swing.JButton();
        bntdelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbl2 = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        lblRole1 = new javax.swing.JLabel();
        lblUser1 = new javax.swing.JLabel();
        lblEmail1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Vitinh TT2\\Desktop\\Lab_java\\BuiDucMinh\\src\\Slide4\\images\\LOGO QLDIEM.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 0, 500, 70));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("Mã SV:");

        bntsearch.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        bntsearch.setForeground(new java.awt.Color(255, 102, 0));
        bntsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_search.png"))); // NOI18N
        bntsearch.setText("Search");
        bntsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearchMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bntsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSearchMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bntsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 470, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setBackground(new java.awt.Color(255, 0, 51));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("Họ Tên SV:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("Mã SV:");

        txtMaSV.setForeground(new java.awt.Color(51, 0, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Photoshop:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("Java");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Web");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Điểm TB:");

        lblDiemTB.setBackground(new java.awt.Color(51, 0, 255));
        lblDiemTB.setFont(new java.awt.Font("UTM Aristote", 1, 18)); // NOI18N
        lblDiemTB.setForeground(new java.awt.Color(51, 153, 0));
        lblDiemTB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiemTB.setText("00");
        lblDiemTB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 255, 0));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Vitinh TT2\\Desktop\\Lab_java\\BuiDucMinh\\icon\\email-send-icon.png")); // NOI18N
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtJava, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(txtPhotoshop, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWeb)
                    .addComponent(txtHoTen))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDiemTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPhotoshop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtJava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel9)
                        .addGap(23, 23, 23)
                        .addComponent(lblDiemTB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 390, 250));

        bntfirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icon10.png"))); // NOI18N
        bntfirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntfirstActionPerformed(evt);
            }
        });
        getContentPane().add(bntfirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 50, 32));

        bntnext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icon7.png"))); // NOI18N
        bntnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntnextActionPerformed(evt);
            }
        });
        getContentPane().add(bntnext, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, 50, 32));

        bntprevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icon8.png"))); // NOI18N
        bntprevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntpreviousActionPerformed(evt);
            }
        });
        getContentPane().add(bntprevious, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 480, 50, 32));

        bntlast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icon9.png"))); // NOI18N
        bntlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntlastActionPerformed(evt);
            }
        });
        getContentPane().add(bntlast, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 480, 50, 32));

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 0, 153));
        jLabel10.setText("Top 3 Sinh Viên Có Điểm Cao Nhất:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, -1, -1));

        tblTop3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SV", "Họ Tên", "Photoshop", "Java", "Web", "Điểm TB"
            }
        ));
        tblTop3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTop3MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTop3);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, 580, 90));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        bntNew.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_add.png"))); // NOI18N
        bntNew.setText("New");
        bntNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNewActionPerformed(evt);
            }
        });

        bntSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_save.png"))); // NOI18N
        bntSave.setText("Save");
        bntSave.setEnabled(false);
        bntSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSaveActionPerformed(evt);
            }
        });

        bntdelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_delete.png"))); // NOI18N
        bntdelete.setText("Delete");
        bntdelete.setEnabled(false);
        bntdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntdeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/edit.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/edit.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bntdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(bntNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntSave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 130, 250));

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 102, 102), null, null));
        jPanel5.setForeground(new java.awt.Color(102, 102, 255));

        lbl2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl2.setText("Role :");

        lbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl.setText("User :");

        lblRole1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRole1.setForeground(new java.awt.Color(255, 102, 0));

        lblUser1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUser1.setForeground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblUser1, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblRole1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(lblUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRole1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 190, 90));

        lblEmail1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblEmail1.setForeground(new java.awt.Color(255, 102, 0));
        getContentPane().add(lblEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 210, 27));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Email :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, 26));

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\Vitinh TT2\\Desktop\\Lab_java\\BuiDucMinh\\src\\Slide4\\images\\hinh-nen-background-dep-14 (1).jpg")); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    DecimalFormat df = new DecimalFormat("#0.##");
    private void bntSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSaveActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
    }//GEN-LAST:event_bntSaveActionPerformed

    private void bntdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntdeleteActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_bntdeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        String sql = "update grade set Photoshop = ?, Java = ?, Web = ?, DiemTB = ? where MaSV = ?;";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(url, "sa", "123");
            ps = con.prepareStatement(sql);

            ps.setFloat(1, Float.parseFloat(txtPhotoshop.getText()));
            ps.setFloat(2, Float.parseFloat(txtJava.getText()));
            ps.setFloat(3, Float.parseFloat(txtWeb.getText()));
            UpdateDiemTB();
            ps.setFloat(4, Float.parseFloat(lblDiemTB.getText()));
            ps.setString(5, txtMaSV.getText());

            ps.execute();
            JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!");

            ArrayList<Grade> list = getGradeList();

            con.close();
            ps.close();
            showTable();

            setVisible(false);
            new QLDiem().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(this, "Lưu thông tin thất bại");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }//GEN-LAST:event_btnUpdateActionPerformed
    Connection con;
    ResultSet rs;
    Statement st;
    Grade grade;


    private void bntsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntsearchActionPerformed
        int i;
        String masv;
        masv = txtSearchMaSV.getText();
        for (i = 0; i < list.size(); i++) {
            Grade diemsv = list.get(i);
            if (diemsv.maSV.equals(masv)) {
                JOptionPane.showMessageDialog(this, "Đã tìm thấy", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                currentIndex = i;
                DisplayDiemSV();
                break;
            }
        }
        if (i == list.size()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            txtSearchMaSV.requestFocus();
        }
    }//GEN-LAST:event_bntsearchActionPerformed

    private void bntNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNewActionPerformed
        // TODO add your handling code here:
        reset();
        txtHoTen.requestFocus();

    }//GEN-LAST:event_bntNewActionPerformed
    public void reset() {

        txtJava.setText("");
        txtPhotoshop.setText("");
        txtWeb.setText("");

        lblDiemTB.setText("");
        txtMaSV.requestFocus();
    }
    private void bntfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntfirstActionPerformed
        // TODO add your handling code here:

        currentIndex = 0;
        DisplayDiemSV();

    }//GEN-LAST:event_bntfirstActionPerformed

    private void send(String x, String email, String pass) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            String accountName = email;
            String accountPassword = pass;
            Session s = Session.getInstance(p,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(accountName, accountPassword);
                }
            });
            String from = email;

            String to = x;
            String subject = "Điểm tổng kết môn học Trường Cao Đằng FPT Polytecnic";
            String body = "";
            body += "\nThân gửi sinh viên: " + txtHoTen.getText() + "\n";
            body += "Điểm học kì của bạn vừa qua là:\n";
            body += "Photoshop : " + txtPhotoshop.getText() + "\n";
            body += "Java : " + txtJava.getText() + "\n";
            body += "Web : " + txtWeb.getText() + "\n";
            body += "Điểm trung bình : " + lblDiemTB.getText();
            body += "\nChúc bạn thành công trong những học kì tiếp theo :v";

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "Mail was sent successfully.", "Message",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException mex) {
            JOptionPane.showMessageDialog(this, "Sai mật khẩu");
        }
    }
    boolean flag = false;
    private void bntnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntnextActionPerformed
        // TODO add your handling code here:

        currentIndex++;
        if (currentIndex >= list.size()) {
            currentIndex = list.size() - 1;
            JOptionPane.showMessageDialog(this, "Bạn đang ở cuối!!");
        }

        DisplayDiemSV();


    }//GEN-LAST:event_bntnextActionPerformed

    private void bntpreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntpreviousActionPerformed
        // TODO add your handling code here:

        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
            JOptionPane.showMessageDialog(this, "Bạn đang ở đầu!!");
        }

        DisplayDiemSV();

    }//GEN-LAST:event_bntpreviousActionPerformed

    private void bntlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntlastActionPerformed
        // TODO add your handling code here:

        currentIndex = list.size() - 1;

        DisplayDiemSV();

    }//GEN-LAST:event_bntlastActionPerformed

    private void tblTop3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTop3MouseClicked
        // TODO add your handling code here:
        int index = tblTop3.getSelectedRow();

    }//GEN-LAST:event_tblTop3MouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        new Login1().setVisible(true);

        setVisible(false);

    }//GEN-LAST:event_btnLogoutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String email = lblEmail1.getText();

        String pass = "";
        JPasswordField pf = new JPasswordField();
        int ok = showConfirmDialog(null, pf, "Nhập mật khẩu cho email quản lí:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            pass = new String(pf.getPassword());

        }
        if (pass == null || pass.equals("")) {
            JOptionPane.showMessageDialog(this, "Gửi thất bại");
            return;
        }

        String sql = "select Email from sinhvien where MaSV = ?;";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(url, "sa", "123");
            ps = con.prepareStatement(sql);

            ps.setString(1, txtMaSV.getText());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String x = rs.getString("Email");
                System.out.println("email = " + x);
                send(x, email, pass);

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(this, "Lưu thông tin thất bại");
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLDiem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntNew;
    private javax.swing.JButton bntSave;
    private javax.swing.JButton bntdelete;
    private javax.swing.JButton bntfirst;
    private javax.swing.JButton bntlast;
    private javax.swing.JButton bntnext;
    private javax.swing.JButton bntprevious;
    private javax.swing.JButton bntsearch;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lblDiemTB;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblRole1;
    private javax.swing.JLabel lblUser1;
    private javax.swing.JTable tblTop3;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtJava;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtPhotoshop;
    private javax.swing.JTextField txtSearchMaSV;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables
}
