    // Kartankatseluohjelman graafinen kayttoliittyma

    // HAJAUTETUT OHJELMISTOJARJESTELMAT JA PILVIPALVELUT HARJOITUSTYO 2
    // TEKIJAT: Pinja Palm, Helena Ollila ja Satu Jantunen
     
    import javax.swing.*;
    import javax.swing.ImageIcon;
    import javax.swing.event.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.net.*;
    
    // XML Parsimispaketteja: kaytettiin javax.xml.parsers
    import org.w3c.dom.Document;
    import org.xml.sax.SAXException;
    import org.w3c.dom.NodeList;
    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import javax.xml.parsers.ParserConfigurationException;
    import javax.xml.xpath.XPath;
    import javax.xml.xpath.XPathConstants;
    import javax.xml.xpath.XPathExpression;
    import javax.xml.xpath.XPathExpressionException;
    import javax.xml.xpath.XPathFactory;

    import java.io.IOException;
    import java.util.ArrayList;
     
    public class MapDialog extends JFrame {
     
      // Koordinaatit ja zoom/move muuttuja
        
      private int xmin;
      private int ymin;
      private int ymax;
      private int xmax;
      private final int zoom=20;
      private final int move=20;
     
      // Lista, johon tallennetaan layerit
      
      private ArrayList<LayerCheckBox> layerLista = new ArrayList<>();
      
      // Kayttoliittyman komponentit
      
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private JButton refreshB = new JButton("Paivita");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
     
      public MapDialog() throws Exception {
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
     
        // Haetaan alustava karttakuva
        imageLabel.setIcon(new ImageIcon(new URL("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true")));
        // Koordinaattien alkuarvot
        xmin=-180;
        ymin=-90;
        xmax=180;
        ymax=90;
        
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
       
        try{
            // Luodaan Document olio
            DocumentBuilderFactory docMuodostus = DocumentBuilderFactory.newInstance();
            DocumentBuilder muodostaja = docMuodostus.newDocumentBuilder();
            Document tieto = muodostaja.parse("http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities");
        
            // Parsimiseen tarvittavia olioita
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
        
            // Haetaan tiedot XML dodumentista
            ArrayList<String> lista = new ArrayList<>();
            XPathExpression expr = xpath.compile("/WMT_MS_Capabilities/Capability/Layer/Layer/Name/text()") ;
            NodeList nodes = (NodeList) expr.evaluate( tieto , XPathConstants.NODESET);
            
            //Tallennetaan tiedot listaan
            for(int i = 0; i<nodes.getLength(); i++){
                lista.add(nodes.item(i).getNodeValue());
            } // for
            
            //Kaydaan lista lapi ja luodaan valintalaatikot
            for(int i = 0; i<lista.size(); i++){
                String nimi = lista.get(i);
                LayerCheckBox box = new LayerCheckBox(nimi,nimi,false);
                layerLista.add(box);
                leftPanel.add(box);
            } // for            
        } // try
        
        catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e){
            e.printStackTrace();
        } // catch
        
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
     
      } // MapDialog
     
      public static void main(String[] args) throws Exception {
        new MapDialog();
      } // main
     
      // Kontrollinappien kuuntelija
      // KAIKKIEN NAPPIEN YHTEYDESSA VOINEE HODYNTAA updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
          if(e.getSource() == refreshB) {
           
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
            ymin=ymin+zoom/2;
            ymax=ymax-zoom/2;
            
          }
          if(e.getSource() == zoomOutB) {
            // TODO:
            // ZOOM OUT -TOIMINTO
            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PAIVITA KUVA
            xmin=xmin-zoom;
            xmax=xmax+zoom;
            ymin=ymin-zoom/2;
            ymax=ymax+zoom/2;
            
          }
          // Paivitetaan kuva
          try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
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
      
      public void updateImage() throws Exception{
        String s = "";
     
        // Tutkitaan, mitka valintalaatikot on valittu, ja
        // kerataan s:aan pilkulla erotettu lista valittujen kerrosten
        // nimista (kaytetaan haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
        
        // Kaynnistetaan saie, joka paivittaa kuvan
        new KartanPaivitys(s).run();
      } // updateImage
        
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN PAIVITYS ERILLISESSA SAIKEESSA
      
        private class KartanPaivitys extends Thread{ 
        
            private String s;
            
            KartanPaivitys(String s){
                this.s = s;
            } // konstruktori
            
            @Override
            public void run(){
                try{
                    String x1 = String.valueOf(xmin);
                    String x2 = String.valueOf(xmax);
                    String y1 = String.valueOf(ymin); 
                    String y2 = String.valueOf(ymax);
                    String url = "";
                    
                    // Varmistetaan, ettei layer ikina tyhja, jos kayttaja ei valitse oletuksena bluemarble,cities
                    if(s.isEmpty()){
                    url = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=" + x1 + "," + y1 + "," + x2 + ","+ y2
                                  + "&SRS=EPSG:4326" + "&WIDTH=953&HEIGHT=480&LAYERS=" + "bluemarble,cities" + "&STYLES=&FORMAT=image/png&TRANSPARENT=true";    
                    }else{
                    
                    url = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=" + x1 + "," + y1 + "," + x2 + ","+ y2
                                  + "&SRS=EPSG:4326" + "&WIDTH=953&HEIGHT=480&LAYERS=" + s + "&STYLES=&FORMAT=image/png&TRANSPARENT=true";
                    }
                    
                    // Varmistetaan etta url oikein
                    System.out.println(url);
                    URL url1 = new URL(url);
                    
                    imageLabel.setIcon(new ImageIcon(url1));
                    
                } // try
                
                catch(Exception a){
                    a.printStackTrace();
                } // catch
            } // run
        } // KartanPaivitys
    
} // MapDialog