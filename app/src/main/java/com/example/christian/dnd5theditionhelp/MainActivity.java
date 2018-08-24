package com.example.christian.dnd5theditionhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. Scan Profile File welche klasse/Rasse erstellt wurde
        // 2. erstelle für jeden gefundenen charakter einen Listeneintrag
        // 3. (Beim klick auf den Listeneintrag)
        //    erstelle eine Liste mit jeder Fähigkeit als Listeneintrag TODO What about kleine Attributs Erhöhungen ? Auch ein Listeneintrag für jede ?
        // 4. Bei Multiple Choice Fähigkeiten muss es in der Profile-file noch ein Zusatz attribut geben.
        //    Falls dieses nicht gesetzt ist muss eine Auswahl statt finden Hinweis im Listeneintrag und bei draufklicken gibts ein AuswahlFenster.
        // 5. Jeder Listeneintrag ist farblich markiert (rot = schaden, grün = heilung, blau = support, orange = debuff, grau = sonstiges)
        //    Jeder Listeneintrag Zeigt Name, Stufe, sehr knappe beschreibung an. (+ bild ?)
        // 6. Beim Klicken auf ein Listeneintrag wird die Kurzbeschreibung und die volle Beschreibung darunter angezeigt.
        // 7. Todo Character bearbeiten ? bzw. die komplette Character erstellung



        // TESTINGS
        // TODO in onCreate schlechte idee Tutorial: https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
        try {
            parseXML(getResources().openRawResource(R.raw.human));
        }
        catch (Exception e){
            System.out.println("ERROR: "+e);
        }
    }

    private void parseXML(InputStream input) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);
        Element root = doc.getDocumentElement();

        NodeList abilities = doc.getElementsByTagName("ability");
        //List<Element> list = (Element) doc.getElementsByTagName("ability");

        StringBuilder sb = new StringBuilder();
        System.out.println("##### Start parsing");

        for(int n = 0; n < abilities.getLength(); n++){
            Node currentNode = abilities.item(n);

            if(currentNode.getNodeType() == currentNode.ELEMENT_NODE) {
                Element  e = (Element) currentNode;

                //System.out.println("####? "+e.getAttribute("choice"));
                if (e.getAttribute("choice").equals("true")) {
                    //LEERZEICHEN IM XML WERDEN MIRGEPARST
                    for(int i = 0; i < e.getElementsByTagName("variant").getLength();i++){
                        String s = e.getElementsByTagName("variant").item(i).getTextContent();
                        sb.append(s+"\n");
                    }
                }
                else{
                    sb.append(e.getTextContent()+"\n");
                }
            }

        }
        System.out.println(sb.toString());
    }

    private void parseXML(){
        System.out.println("##########2");
        XmlPullParserFactory parserFactory;
        InputStream is;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            is = getResources().openRawResource(R.raw.human);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is,null);
            processParsing(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        StringBuilder sb = new StringBuilder();

        int eventType = parser.getEventType();
        System.out.println("##########3");
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType==XmlPullParser.START_TAG){
                sb.append(parser.getName() + " "+ parser.getAttributeValue(null,"name"));

            }
            if(eventType==XmlPullParser.END_TAG){
                sb.append(parser.getName());
            }
            if(eventType==XmlPullParser.TEXT){
                sb.append(parser.getText());
            }
            //
            eventType = parser.next();
        }
        System.out.println(sb.toString());
    }
}
