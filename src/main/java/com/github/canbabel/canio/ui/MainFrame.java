/**
 *  CANBabel - Translator for Controller Area Network description formats
 *  Copyright (C) 2011-2013 julietkilo and Jan-Niklas Meier
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package com.github.canbabel.canio.ui;
// TODO Versionnumber(major.minor.build)
import com.github.canbabel.canio.dbc.DbcReader;
import com.github.canbabel.canio.kcd.NetworkDefinition;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.*;
import java.util.zip.GZIPInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * User interface
 * 
 * @author Jan-Niklas Meier < dschanoeh@googlemail.com >
 * @author julietkilo
 * 
 */
public class MainFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = -6772467633506915053L;
    //private JFileChooser fc = new JFileChooser("");
    private JFileChooser fc;
    private FileList list = new FileList();
    private Thread convertThread;
    public Preferences prefs = Preferences.userNodeForPackage(this.getClass());  
    private FileFilter directoryFilter = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory();
        }
    };
    private FileFilter dbcFilter = new FileFilter() {

        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".dbc");
        }
    };

    /** Creates new form MainFrame */
    public MainFrame() {
        initComponents();
        //Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        fc = new JFileChooser(prefs.get("user.dir", "."));
        

        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".dbc")) {
                    return true;
                } else if (f.isDirectory()) {
                    return true;
                }

                return false;
            }

            @Override
            public String getDescription() {
                return ".dbc files and directories";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
    }

    private List<File> filesForDirectory(File directory) {
        ArrayList<File> files = new ArrayList<File>();

        File[] dbcFiles = directory.listFiles(dbcFilter);
        files.addAll(Arrays.asList(dbcFiles));

        File[] dirs = directory.listFiles(directoryFilter);

        for (File dir : dirs) {
            files.addAll(filesForDirectory(dir));
        }



        return files;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        convertButton = new javax.swing.JButton();
        settingsPanel = new javax.swing.JPanel();
        gzippedCheckbox = new javax.swing.JCheckBox();
        prettyprintCheckbox = new javax.swing.JCheckBox();
        OverwriteCheckbox = new javax.swing.JCheckBox();
        informationPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        debugInfoArea = new javax.swing.JTextArea();
        inputPanel = new javax.swing.JPanel();
        addFilesOrFoldersButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList<File>();
        removeButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CANBabel");
        setMinimumSize(new java.awt.Dimension(640, 480));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        convertButton.setText("Convert");
        convertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        getContentPane().add(convertButton, gridBagConstraints);

        settingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        settingsPanel.setLayout(new java.awt.GridBagLayout());

        gzippedCheckbox.setText("Gzipped Output");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        settingsPanel.add(gzippedCheckbox, gridBagConstraints);

        prettyprintCheckbox.setSelected(true);
        prettyprintCheckbox.setText("Pretty print XML");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        settingsPanel.add(prettyprintCheckbox, gridBagConstraints);

        OverwriteCheckbox.setText("Overwrite all");
        settingsPanel.add(OverwriteCheckbox, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        getContentPane().add(settingsPanel, gridBagConstraints);

        informationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));
        informationPanel.setLayout(new java.awt.GridBagLayout());

        debugInfoArea.setEditable(false);
        debugInfoArea.setColumns(20);
        debugInfoArea.setRows(5);
        jScrollPane2.setViewportView(debugInfoArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        informationPanel.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(informationPanel, gridBagConstraints);

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));
        inputPanel.setLayout(new java.awt.GridBagLayout());

        addFilesOrFoldersButton.setText("Add files or folders");
        addFilesOrFoldersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFilesOrFoldersButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(addFilesOrFoldersButton, gridBagConstraints);

        fileList.setModel(list);
        jScrollPane1.setViewportView(fileList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        inputPanel.add(jScrollPane1, gridBagConstraints);

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(removeButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(inputPanel, gridBagConstraints);

        progressBar.setEnabled(false);
        progressBar.setMinimumSize(new java.awt.Dimension(410, 23));
        progressBar.setPreferredSize(new java.awt.Dimension(190, 23));
        progressBar.setRequestFocusEnabled(false);
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        getContentPane().add(progressBar, gridBagConstraints);

        closeButton.setText("Close");
        closeButton.setToolTipText("Close application");
        closeButton.setAlignmentX(0.5F);
        closeButton.setFocusTraversalPolicyProvider(true);
        closeButton.setMaximumSize(new java.awt.Dimension(68, 30));
        closeButton.setMinimumSize(new java.awt.Dimension(68, 30));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        getContentPane().add(closeButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    void outputDirectoryButton_actionPerformed(ActionEvent e)
//    {
//      int status = getDirectoryFromUser(jFileChooser,mostRecentOutputDirectory);
//      if ( status == JFileChooser.APPROVE_OPTION )
//      {
//        mostRecentOutputDirectory = jFileChooser.getSelectedFile();
//        jspOutputDirectoryTextField.setText(mostRecentOutputDirectory.getAbsolutePath());
//        prefs.put("LAST_OUTPUT_DIR", mostRecentOutputDirectory.getAbsolutePath());
//      }
//    }    

    private void addFilesOrFoldersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilesOrFoldersButtonActionPerformed
        int returnVal = fc.showOpenDialog(this);
        /** new code */
        String path =   fc.getCurrentDirectory().getAbsolutePath();
        System.out.println(path);
        prefs.put("user.dir",path);
        /** end new code */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
       
            File[] files = fc.getSelectedFiles();

            for (File f : files) {
                if (f.isDirectory()) {
                    List<File> dirFiles = filesForDirectory(f);

                    for (File fi : dirFiles) {
                        list.addFile(fi);
                    }

                } else {
                    list.addFile(f);
                }
            }
        }
    }//GEN-LAST:event_addFilesOrFoldersButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int[] selection = fileList.getSelectedIndices();

        Arrays.sort(selection);

        for (int i = 0; i < selection.length; i++) {
            list.remove(selection[i] - i);
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
        if (convertThread != null && convertThread.isAlive()) {
            convertThread.interrupt();
            try {
                convertThread.join();
            } catch (InterruptedException ex) {
            }
        } else if (list.getSize() > 0) {
            convertThread = new Thread(convertRunnable);
            convertThread.start();
        }
    }//GEN-LAST:event_convertButtonActionPerformed
 
private void closeButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonHandler
    System.exit(0);
}//GEN-LAST:event_closeButtonHandler

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length == 0){
            /* GUI mode */
            startGUI();
        } else if (args.length == 2){
            startCmdLine(args[0],args[1]);
            
        } else {
            System.out.println("Usage: CANBabel.jar [dbc-in  kcd-out]");
        }

    }
    
    private static void startGUI(){
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });        
    }
    
    private static void startCmdLine(String dbc, String kcd){
        
        File dbcfile = new File(dbc);
        File kcdfile = new File(kcd);
        
        if (dbcfile.canRead()) {
            DbcReader reader = new DbcReader();
            if (reader.parseFile(dbcfile, System.out)) {
                reader.writeKcdFile(kcdfile, true, false);
            }            
        }       
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox OverwriteCheckbox;
    private javax.swing.JButton addFilesOrFoldersButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton convertButton;
    private javax.swing.JTextArea debugInfoArea;
    private javax.swing.JList<File> fileList;
    private javax.swing.JCheckBox gzippedCheckbox;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox prettyprintCheckbox;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeButton;
    private javax.swing.JPanel settingsPanel;
    // End of variables declaration//GEN-END:variables
    OutputStream logOutput = new OutputStream() {

        private StringBuilder string = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            this.string.append((char) b);
        }

        @Override
        public String toString() {
            return this.string.toString();
        }

        @Override
        public void flush() throws IOException {
            debugInfoArea.setText(string.toString());
        }
    };

    PrintWriter logWriter = new PrintWriter(logOutput);
    private Runnable convertRunnable = new Runnable() {

        public void run() {
            addFilesOrFoldersButton.setEnabled(false);
            removeButton.setEnabled(false);
            convertButton.setText("Abort");

            progressBar.setEnabled(true);
            progressBar.setMinimum(0);
            progressBar.setMaximum(list.getSize());
            progressBar.setValue(0);

            gzippedCheckbox.setEnabled(false);
            prettyprintCheckbox.setEnabled(false);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream resourceAsStream = NetworkDefinition.class.getResourceAsStream("Definition.xsd");
            Validator val = null;

            if (resourceAsStream != null) {

                Source s = new StreamSource(resourceAsStream);
                Schema schema;

                try {
                    schema = schemaFactory.newSchema(s);
                    val = schema.newValidator();
                } catch (SAXException ex) {
                    ex.printStackTrace(logWriter);
                }

                ErrorHandler handler = new ErrorHandler() {

                    public void warning(SAXParseException exception) throws SAXException {
                        exception.printStackTrace(logWriter);
                    }

                    public void error(SAXParseException exception) throws SAXException {
                        exception.printStackTrace(logWriter);
                    }

                    public void fatalError(SAXParseException exception) throws SAXException {
                        exception.printStackTrace(logWriter);
                    }
                };
                try {
                    val.setErrorHandler(handler);
                } catch (Exception e) {
                    e.printStackTrace(logWriter);
                }

            } else {
                // if schema can't be found skip validation part
                logWriter.print("Network definition schema can't be found in jar. Started from commandline?\n");
            }

            for (File f : list.getFiles()) {

                String filename = f.getPath();

                if (gzippedCheckbox.isSelected()) {
                    filename = filename.substring(0, filename.length() - 4) + ".kcd.gz";
                } else {
                    filename = filename.substring(0, filename.length() - 4) + ".kcd";
                }

                File newFile = new File(filename);

                if (newFile.exists() && !OverwriteCheckbox.isSelected()) {
                    int answer = JOptionPane.showConfirmDialog(addFilesOrFoldersButton, "File " + filename + " already exists. Overwrite?");

                    if (answer == JOptionPane.NO_OPTION) {
                        progressBar.setValue(progressBar.getValue() + 1);
                        continue;
                    } else if (answer == JOptionPane.CANCEL_OPTION) {
                        progressBar.setValue(0);
                        progressBar.setString("");
                        return;
                    }
                }

                if (Thread.interrupted()) {
                    break;
                }

                progressBar.setString("Converting " + (progressBar.getValue() + 1) + " of " + progressBar.getMaximum() + ": " + f.getName());
                logWriter.write("### Converting " + f.getName() + " ###\n");
                logWriter.flush();
                try {
                    DbcReader reader = new DbcReader();
                    if (reader.parseFile(f, logOutput)) {
                        reader.writeKcdFile(newFile, prettyprintCheckbox.isSelected(), gzippedCheckbox.isSelected());

                        /* Validate the result */
                        StreamSource source;

                        if (gzippedCheckbox.isSelected()) {
                            source = new StreamSource(new GZIPInputStream(new FileInputStream(newFile)));
                        } else {
                            source = new StreamSource(newFile);
                        }
                        if (val != null) {
                            val.validate(source);
                        }

                    }

                    if (Thread.interrupted()) {
                        break;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(logWriter);
                } catch (SAXException ex) {
                    ex.printStackTrace(logWriter);
                }

                logWriter.flush();
                progressBar.setValue(progressBar.getValue() + 1);
            }

            list.clear();

            addFilesOrFoldersButton.setEnabled(true);
            removeButton.setEnabled(true);
            convertButton.setText("Convert");

            progressBar.setValue(0);
            progressBar.setString("");
            progressBar.setEnabled(false);

            gzippedCheckbox.setEnabled(true);
            prettyprintCheckbox.setEnabled(true);
        }
    };
}
