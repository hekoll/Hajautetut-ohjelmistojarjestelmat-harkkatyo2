    // Kartankatseluohjelman graafinen kayttoliittyma

    // HAJAUTETUT OHJELMISTOJARJESTELMAT JA PILVIPALVELUT HARJOITUSTYO 2
    // TEKIJAT: Pinja Palm, Helena Ollila ja Satu Jantunen
     
    import javax.swing.*;
    import javax.swing.event.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.net.*;
     
    public class MapDialog extends JFrame {
     
      // Kayttoliittyman komponentit
      //Koordinaatit ja zoom/move muuttuja
      private int xmin;
      private int ymin;
      private int ymax;
      private int xmax;
      private final int zoom=20;
      private final int move=20;
     
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private JButton refreshB = new JButton("P�ivit�");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
     
      public MapDialog() throws Exception {
     
        // Valmistele ikkuna ja lisaa siihen komponentit
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
     
        // TESTIRIVI
        imageLabel.setIcon(new ImageIcon(new URL("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true")));
     
        add(imageLabel, BorderLayout.EAST);
     
        ButtonListener bl = new ButtonListener();
        refreshB.addActionListener(bl);  
        leftB.addActionListener(bl);
        rightB.addActionListener(bl);
        upB.addActionListener(bl);
        downB.addActionListener(bl);
        zoomInB.addActionListener(bl);
        zoomOutB.addActionListener(bl);
     
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        leftPanel.setMaximumSize(new Dimension(100, 600));
     
        // TODO:
        // ALLA OLEVIEN KOLMEN TESTIRIVIN TILALLE SILMUKKA JOKA LISAA KAYTTOLIITTYMAAN
        // KAIKKIEN XML-DATASTA HAETTUJEN KERROSTEN VALINTALAATIKOT MALLIN MUKAAN
        leftPanel.add(new LayerCheckBox("bluemarble", "Maapallo", true));
        leftPanel.add(new LayerCheckBox("cities", "Kaupungit", false));
     
        leftPanel.add(refreshB);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(leftB);
        leftPanel.add(rightB);
        leftPanel.add(upB);
        leftPanel.add(downB);
        leftPanel.add(zoomInB);
        leftPanel.add(zoomOutB);
     
        add(leftPanel, BorderLayout.WEST);
     
        pack();
        setVisible(true);
     
      }
     
      public static void main(String[] args) throws Exception {
        new MapDialog();
      }
     
      // Kontrollinappien kuuntelija
      // KAIKKIEN NAPPIEN YHTEYDESSA VOINEE HODYNTAA updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
          if(e.getSource() == refreshB) {
            //try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
          }
          if(e.getSource() == leftB) {
            // TODO:
            // VASEMMALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            xmin=xmin-move;
            xmax=xmax-move;
          }
          if(e.getSource() == rightB) {
            // TODO:
            // OIKEALLE SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            xmin=xmin+move;
            xmax=xmax+move;
          }
          if(e.getSource() == upB) {
            // TODO:
            // YLOSPAIN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            ymin=ymin+move;
            ymax=ymax+move;
          }
          if(e.getSource() == downB) {
            // TODO:
            // ALASPAIN SIIRTYMINEN KARTALLA
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            ymin=ymin-move;
            ymax=ymax-move;
          }
          if(e.getSource() == zoomInB) {
            // TODO:
            // ZOOM IN -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            xmin=xmin+zoom;
            xmax=xmax-zoom;
            ymin=ymin+zoom;
            ymax=ymax-zoom;
          }
          if(e.getSource() == zoomOutB) {
            // TODO:
            // ZOOM OUT -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            xmin=xmin-zoom;
            xmax=xmax+zoom;
            ymin=ymin-zoom;
            ymax=ymax+zoom;
          }
        }
      }
     
      // Valintalaatikko, joka muistaa karttakerroksen nimen
      private class LayerCheckBox extends JCheckBox {
        private String name = "";
        public LayerCheckBox(String name, String title, boolean selected) {
          super(title, null, selected);
          this.name = name;
        }
        public String getName() { return name; }
      }
     
      // Tarkastetaan mitka karttakerrokset on valittu,
      // tehdaan uudesta karttakuvasta pyynto palvelimelle ja paivitetaan kuva
      public void updateImage() throws Exception {
        String s = "";
     
        // Tutkitaan, mitka valintalaatikot on valittu, ja
        // ker�t��n s:��n pilkulla erotettu lista valittujen kerrosten
        // nimista (kaytetaan haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
     
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN PAIVITYS ERILLISESSA SAIKEESSA
        // imageLabel.setIcon(new ImageIcon(url));
      }
     
    } // MapDialog