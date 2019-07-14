/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autosave.pkg;

import java.awt.event.KeyEvent;
import java.io.*;
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JRootPane;

public class EmployeeManager extends javax.swing.JFrame {

    String fileName = "employee.txt";

    EmployeeTableModel<Employee> model;
    EmployeeTableModel<Employee> show;
    EmployeeTableModel<Employee> result;
    boolean addNew = false;
    boolean changed = false;
    boolean isSearching = false;
    int currentPage, totalPage;
    Thread autoSave = new Thread() {
        @Override
        public void run() {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' hh:mm:ss");
                while (true) {
                    LocalDateTime dt = LocalDateTime.now();
                    dt = dt.plusSeconds(5);
                    txtAutoSave.setText("Data will be saved in:" + dt.format(dtf));
                    sleep(5000);
                    if (changed) {
                        btnSaveFileActionPerformed(null);
                    }
                }
            } catch (Exception e) {
            }
        }

    };

    public EmployeeManager() {

        initComponents();
        int[] index = {0, 1, 2};
        String[] header = {"Code", "Name", "Salary"};
        model = new EmployeeTableModel(header, index);
        show = new EmployeeTableModel(header, index);
        result = new EmployeeTableModel(header, index);
        this.tblEmployee.setModel(model);
        loadData();
        String totalPage = String.valueOf((int) Math.ceil((double) model.data.size() / 5));
        this.txtTotalPage.setEditable(false);
        int firstPage = 1;
        this.txtTotalPage.setText(totalPage);
        this.txtCurrentPage.setText(String.valueOf(firstPage));
        paging();
    }

    private void paging() {
        model.sort();
        if (!isSearching) {
            this.currentPage = Integer.parseInt(this.txtCurrentPage.getText());
            this.totalPage = (int) Math.ceil((double) model.data.size() / 5);
            show.data.removeAllElements();
            if (currentPage < totalPage) {
                for (int i = (currentPage * 5) - 5; i < (currentPage * 5); i++) {
                    show.data.add(model.data.get(i));
                }
            } else {
                for (int i = (currentPage * 5) - 5; i < model.data.size(); i++) {
                    show.data.add(model.data.get(i));
                }
            }
            this.tblEmployee.setModel(show);
            tblEmployee.updateUI();
        } else {
            this.currentPage = Integer.parseInt(this.txtCurrentPage.getText());
            this.totalPage = (int) Math.ceil((double) result.data.size() / 5);
            show.data.removeAllElements();

            if (currentPage < totalPage && totalPage != 1) {
                for (int i = (currentPage * 5) - 5; i < (currentPage * 5); i++) {
                    show.data.add(result.data.get(i));
                }
            } else {
                for (int i = (currentPage * 5) - 5; i < result.data.size(); i++) {
                    show.data.add(result.data.get(i));
                }
            }
            this.txtTotalPage.setText(String.valueOf(totalPage));
            this.tblEmployee.setModel(show);
            tblEmployee.updateUI();
            int currPage = Integer.parseInt(this.txtCurrentPage.getText());
            int totPage = Integer.parseInt(this.txtTotalPage.getText());
            if (currPage == 1) {
                this.btnPrev.setEnabled(false);
                this.btnNext.setEnabled(true);
            }
            if (currPage == totPage && totPage == 1) {
                this.btnNext.setEnabled(false);
                this.btnPrev.setEnabled(false);
            }
            if (currPage == totalPage && totPage != 1) {
                this.btnPrev.setEnabled(true);
                this.btnNext.setEnabled(false);
            } else {
                this.btnNext.setEnabled(true);
                this.btnPrev.setEnabled(true);
            }
        }

    }

    private void loadData() {
        try {
            FileReader f = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(f);
            String S;
            while ((S = bf.readLine()) != null) {
                S = S.trim();
                if (S.length() > 0) {
                    StringTokenizer stk = new StringTokenizer(S, ";");
                    String code = stk.nextToken();
                    String name = stk.nextToken();
                    int salary = Integer.parseInt(stk.nextToken());
                    model.getData().add(new Employee(code, name, salary));
                }
            }
            bf.close();
            f.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        btnNewActionPerformed(null);
        autoSave.start();
        model.sort();
    }

    private boolean validData() {
        String code = this.txtCode.getText().trim().toUpperCase();
        //SE|FB12345
        if (!code.matches("(SE|SB)\\d{5}")) {
            JOptionPane.showMessageDialog(this, "Invalid code [SE|SBxxxxx]");
            this.txtCode.requestFocus();
            return false;
        }

        for (int i = 0; i < model.data.size(); i++) {
            if (code.equalsIgnoreCase(model.data.get(i).getCode()) && addNew == true) {
                JOptionPane.showMessageDialog(this, "This code is existed!");
                this.txtCode.requestFocus();
                return false;

            }
        }

        String name = this.txtName.getText().trim().toUpperCase();
        if (!name.matches("[a-zA-Z ]{2,30}")) {
            JOptionPane.showMessageDialog(this, "Invalid name!");
            this.txtName.requestFocus();
            return false;
        }
        String salary = this.txtSalary.getText();
        if (!salary.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid salary!");
            this.txtSalary.requestFocus();
            return false;
        }
        return true;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();
        btnSaveFile = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtAutoSave = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        txtCurrentPage = new javax.swing.JTextField();
        txtTotalPage = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EmployeeManager");
        setBounds(new java.awt.Rectangle(250, 200, 0, 0));
        setPreferredSize(new java.awt.Dimension(760, 380));
        setResizable(false);
        setSize(new java.awt.Dimension(250, 380));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Employee List");

        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmployee);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel2.setText("Code");

        jLabel3.setText("Name");

        jLabel6.setText("Salary");

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnSaveFile.setText("Save File");
        btnSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveFileActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnSaveFile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnUpdate)
                    .addComponent(btnRemove))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveFile)
                    .addComponent(btnExit)))
        );

        txtAutoSave.setForeground(new java.awt.Color(255, 0, 0));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Search");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        txtCurrentPage.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCurrentPage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCurrentPageKeyPressed(evt);
            }
        });

        txtTotalPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPageActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel4.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(btnPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalPage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTotalPage)
                                .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCurrentPage)
                                .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        // TODO add your handling code here:
        int curPage = Integer.parseInt(this.txtCurrentPage.getText());
        int pos = tblEmployee.getSelectedRow() + ((curPage - 1) * 5);
        Employee curEmp = model.getData().get(pos);
        this.txtCode.setText(curEmp.getCode());
        this.txtName.setText(curEmp.getName());
        this.txtSalary.setText(curEmp.getSalary() + "");
        this.txtCode.setEditable(false);
        this.addNew = false;
    }//GEN-LAST:event_tblEmployeeMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        isSearching = false;
        addNew = true;
        this.txtCode.setEnabled(true);
        this.txtName.setEnabled(true);
        this.txtSalary.setEnabled(true);
        this.txtSearch.setText("");
        this.txtCode.setText("");
        this.txtName.setText("");
        this.txtSalary.setText("");
        this.txtCode.setEditable(true);
        this.txtCode.requestFocus();
        this.txtSearchKeyReleased(null);
    }//GEN-LAST:event_btnNewActionPerformed


    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (!validData()) {
            return;
        }
        String code = txtCode.getText().toUpperCase();
        String name = txtName.getText().toUpperCase();
        int salary = Integer.parseInt(txtSalary.getText());
        int curPage = Integer.parseInt(this.txtCurrentPage.getText());
        Employee emp = new Employee(code, name, salary);
        if (addNew) {
            model.getData().add(emp);
            addNew = true;
            changed = true;
        } else {
            int pos = tblEmployee.getSelectedRow() + ((curPage - 1) * 5);
            model.getData().set(pos, emp);
            addNew = false;
            changed = true;
        }
        this.txtSearch.setText("");
        this.txtSearchKeyReleased(null);


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        if (model.getData().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data is empty");
        } else {
            int curPage = Integer.parseInt(this.txtCurrentPage.getText());
            int pos = tblEmployee.getSelectedRow() + ((curPage - 1) * 5);
            if (pos >= 0 && pos < model.getData().size()) {
                model.getData().remove(pos);
                paging();
                changed = true;
            } else {
                int lastEl = model.getData().size() - 1;
                model.getData().remove(lastEl);
                paging();
                changed = true;
            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveFileActionPerformed
        // TODO add your handling code here:
        try {
            FileWriter fw = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fw);
            int n = model.getData().size();
            for (int i = 0; i < n; i++) {
                Employee emp = model.getData().get(i);
                String s = emp.getCode() + ";" + emp.getName() + ";" + emp.getSalary();
                pw.println(s);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        changed = false;
    }//GEN-LAST:event_btnSaveFileActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        if (changed) {
            int r = JOptionPane.showConfirmDialog(this, "Save data?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                btnSaveFileActionPerformed(evt);
                System.exit(0);
            } else if (r == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            int r = JOptionPane.showConfirmDialog(this, "Exit Application?", "Exit?", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        // TODO add your handling code here:
        if (this.txtSearch.getText().isEmpty()) {
            show.data.removeAllElements();
            isSearching = false;
            this.txtTotalPage.setText(String.valueOf((int) Math.ceil((double) model.data.size() / 5)));
            this.txtCurrentPage.setText(String.valueOf(1));
            this.tblEmployee.setModel(model);
            paging();
            return;
        } else {
            isSearching = true;
            result.data.removeAllElements();

            String name = this.txtSearch.getText().toUpperCase().trim();
            for (int i = 0; i < model.data.size(); i++) {
                if (model.data.get(i).getName().toUpperCase().contains(name)) {
                    result.data.add(model.data.get(i));
                }
            }
            paging();
            if (result.data.isEmpty()) {
                this.btnNext.setEnabled(false);
                this.btnPrev.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int currentPage = Integer.parseInt(this.txtCurrentPage.getText());
        int totalPage = Integer.parseInt(this.txtTotalPage.getText());

        if (1 <= currentPage && currentPage < totalPage) {
            currentPage++;
        }
        this.txtCurrentPage.setText(String.valueOf(currentPage));
        paging();


    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        int currentPage = Integer.parseInt(this.txtCurrentPage.getText());
        int totalPage = Integer.parseInt(this.txtTotalPage.getText());

        if (1 < currentPage && currentPage <= totalPage) {
            currentPage--;
        }
        this.txtCurrentPage.setText(String.valueOf(currentPage));
        paging();

    }//GEN-LAST:event_btnPrevActionPerformed

    private void txtTotalPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPageActionPerformed

    private void txtCurrentPageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurrentPageKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (!this.txtCurrentPage.getText().isEmpty()) {
                    int curPage = Integer.parseInt(this.txtCurrentPage.getText().trim());
                    if (curPage > Integer.parseInt(this.txtTotalPage.getText().trim())) {
                        this.txtCurrentPage.setText(this.txtTotalPage.getText());
                        paging();
                    }
                    if (curPage < 1) {
                        this.txtCurrentPage.setText("1");
                        paging();
                    }
                    paging();
                }

            } catch (Exception e) {
                this.txtCurrentPage.setText("1");
                paging();
            }

        }


    }//GEN-LAST:event_txtCurrentPageKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:

        if (this.txtSearch.getText().isEmpty()) {
            show.data.removeAllElements();
            isSearching = false;
            this.txtTotalPage.setText(String.valueOf((int) Math.ceil((double) model.data.size() / 5)));
            this.txtCurrentPage.setText(String.valueOf(1));
            this.tblEmployee.setModel(model);
            paging();
            return;
        } else {
            this.addNew = false;
            this.txtCode.setEnabled(false);
            this.txtName.setEnabled(false);
            this.txtSalary.setEnabled(false);

            isSearching = true;
            result.data.removeAllElements();

            String name = this.txtSearch.getText().toUpperCase().trim();
            for (int i = 0; i < model.data.size(); i++) {
                if (model.data.get(i).getName().toUpperCase().contains(name)) {
                    result.data.add(model.data.get(i));
                }
            }
            paging();
            if (result.data.isEmpty()) {
                this.btnNext.setEnabled(false);
                this.btnPrev.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

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
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveFile;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JLabel txtAutoSave;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCurrentPage;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalPage;
    // End of variables declaration//GEN-END:variables
}
