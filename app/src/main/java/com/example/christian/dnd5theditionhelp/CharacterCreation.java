package com.example.christian.dnd5theditionhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CharacterCreation extends AppCompatActivity{
    private String selectedRace;
    private String selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        Spinner racespinner = (Spinner) findViewById(R.id.raceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.races_array, android.R.layout.simple_spinner_dropdown_item);
        racespinner.setAdapter(adapter);

        racespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                System.out.println("##TEST: "+ parent.getItemAtPosition(pos));
                selectedRace = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("NOTHING SELECTED");
            }


        });

        Spinner classspinner = (Spinner) findViewById(R.id.classSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.classes_array, android.R.layout.simple_spinner_dropdown_item);
        classspinner.setAdapter(adapter2);

        classspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                System.out.println("##TEST: "+ parent.getItemAtPosition(pos));
                selectedClass = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("NOTHING SELECTED");
            }

        });

    }


    //TODO : ID problem lösen. Idee anhand der Position in der Liste aus MainActivity bestimmen
    private int characterID = 0;

    public void createCharacter(View view){
        String result = "";
        TextView tv = (TextView)findViewById(R.id.nameTextfield);
        String characterName = tv.getText().toString();

        try{
            //TODO : momentan wird die file form erstellen gelöscht und dann neu erstellt. Also kann nur 1 char drinne sein- fix it
            // --------
            deleteFile("characters.xml");
            FileOutputStream fileout=openFileOutput("characters.xml", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<characters>\n" +
                    "</characters>");
            outputWriter.close();
            // ---------

            //InputStream input = getResources().openRawResource(R.raw.characters_template);
            FileInputStream input2 = openFileInput("characters.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(input2);
            Node root = doc.getFirstChild();

            //Create Character
            Element characterElement = doc.createElement("character");
            characterElement.setAttribute("id",String.valueOf(characterID));
            //Create Name
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(characterName));
            characterElement.appendChild(nameElement);
            //Create race
            Element raceElement = doc.createElement("race");
            raceElement.appendChild(doc.createTextNode(selectedRace));
            characterElement.appendChild(raceElement);
            //Create class
            Element classElement = doc.createElement("class");
            classElement.appendChild(doc.createTextNode(selectedClass));
            Element levelElement = doc.createElement("level");
            levelElement.appendChild(doc.createTextNode("1"));
            classElement.appendChild(levelElement);
            characterElement.appendChild(classElement);

            root.appendChild(characterElement);




            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult sr = new StreamResult(openFileOutput("characters.xml", MODE_PRIVATE));
            transformer.transform(source, sr);

            Toast.makeText(getBaseContext(), "Character Created!",
                    Toast.LENGTH_SHORT).show();

            //In String umwandeln
            /*StringWriter writer = new StringWriter();
            transformer.transform( new DOMSource( doc ), new StreamResult( writer ) );
            result = writer.getBuffer().toString();*/

            characterID++;

            System.out.println("after Done result: ! "+ result);
        }
        catch(Exception e){
            System.out.println("ERROR: "+e);
        }

        /*try {
            FileOutputStream fileout=openFileOutput("characters.xml", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(result);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            FileInputStream fileIn=openFileInput("characters.xml");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            System.out.println("ERGEBNIS VOM LESEN: "+s);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

