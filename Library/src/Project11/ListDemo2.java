package Project11;

import OriginalCode.WelcomeToLibrary;
import OriginalCode.ui.Librarian;
import OriginalCode.ui.Library;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* ListDemo.java requires no other files. */
public class ListDemo2 extends JPanel
        implements ListSelectionListener {
    WelcomeToLibrary w=new WelcomeToLibrary();
    Librarian s;
    Library lib;
    JFrame m;
    private JList list;
    private DefaultListModel listModel;

    private static final String hireString = "Confirm";
    private static final String fireString = "Choose";
    private JButton fireButton;
    private JTextField employeeName;
    private JButton hireButton;
    public ListDemo2( Librarian s,Library l,JFrame m) {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("(1) Buy in new books");
        listModel.addElement("(2) Display all students' information");
        listModel.addElement("(3) Check single student's information");
        listModel.addElement("(4) Log Out");
        listModel.addElement("(5) Exit without logging out");
        this.s=s;
        this.lib=l;
        this.m=m;
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        hireButton = new JButton(hireString);
        HireListener hireListener = new HireListener(hireButton);
        hireButton.setActionCommand(hireString);
        hireButton.addActionListener(hireListener);
        hireButton.setEnabled(false);

        fireButton = new JButton(fireString);
        fireButton.setActionCommand(fireString);
        fireButton.addActionListener(new FireListener());

        employeeName = new JTextField(10);
        employeeName.addActionListener(hireListener);
        //employeeName.getDocument().addDocumentListener(hireListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(fireButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(employeeName);
        employeeName.enableInputMethods(false);
        buttonPane.add(hireButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    class FireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            employeeName.setText((String)listModel.getElementAt(index));
            hireButton.setEnabled(true);
//            try {
//                WelcomeToLibraryGUI.Exit();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            switch(index){
//                case 0: //lib.displayAvailableBooks();break;
//                case 1: //w.checkSingleBookHelper(lib);break;
//                case 2: //s.displayPrivateInformation();break;
//                case 3: //w.lendBookHelper(s,lib);break;
//                case 4: //w.returnBookHelper(s,lib);break;
//                case 5: //s.recommend(lib.getBookList());break;
//                case 6: // lib.checkLibrarianList(lib.getLibrarianList());break;
//                case 7:
////                    try {
////                        w.onlineLibHelper(lib);
////                    } catch (IOException e1) {
////                        e1.printStackTrace();
////                    }
////                    break;
//                case 8://return;
//            }
//            listModel.remove(index);
//
//            int size = listModel.getSize();
//
//            if (size == 0) { //Nobody's left, disable firing.
//                fireButton.setEnabled(false);
//
//            } else { //Select an index.
//                if (index == listModel.getSize()) {
//                    //removed item in last position
//                    index--;
//                }
//
//                list.setSelectedIndex(index);
//                list.ensureIndexIsVisible(index);
//            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class HireListener implements ActionListener, DocumentListener {

        private boolean alreadyEnabled = false;
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            m.setVisible(false);
            switch(list.getSelectedIndex()){
                case 0: w.mainl1(s,lib);break;
                case 1: w.mainl2(s,lib);break;
                case 2: w.mainl3(s,lib);break;
                case 3: w.mainl4(s,lib);break;
                case 4: w.mainl5(s,lib);break;
            }
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                fireButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                fireButton.setEnabled(true);
            }
        }
    }

}
