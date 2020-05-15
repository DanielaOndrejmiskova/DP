package diplomovaPraca;

import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import diplomovaPraca.Databaza;
import org.apache.cxf.jaxrs.springmvc.SpringViewResolverProvider;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HeaderFooter;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.swing.*;


public class WordDoc {
    private XWPFParagraph paragraph;
    private XWPFRun run;
    private Databaza db;
    private int pocetKupujucich =0;
    private List<Object> cislaLvZmluvy = new ArrayList<>();
    private List<Integer> indexyNONE = new ArrayList<>();
    private HashMap<Integer,String> tarchyMapa = new HashMap<>();
    private String predavajuciMeno="";
    private String predavajuciPriezvisko="";
    private List<String> kupujuciMeno = new ArrayList<>();
    private List<String> kupujuciPriezvisko = new ArrayList<>();
    private String podielCitatel;
    private String podielMenovatel;
    private String podiel;
    private List<String> text = new ArrayList<>();
    CTAbstractNum cTAbstractNum;

    XWPFAbstractNum abstractNum;
    XWPFNumbering numbering;
    XWPFDocument document;
    BigInteger abstractNumID;
    BigInteger numID;
    Set<Object> set = new HashSet<>();
    String cestaSuboru="";
    String cislaLVPeciatka="";
    public WordDoc(Databaza db, List<Object> cislaLvZmluvy) throws SQLException {
        this.db = db;

        Set<Object> set = new HashSet<>(cislaLvZmluvy);
        cislaLvZmluvy.clear();
        cislaLvZmluvy.addAll(set);
        this.cislaLvZmluvy = cislaLvZmluvy;

        cislaLVPeciatka = getCislaLV(cislaLvZmluvy);

        vytvorenieDokumentu();

    }

    private void vytvorenieDokumentu() {
        //Blank Document
        document = new XWPFDocument();
        FileOutputStream out;

        try {

            String nazovZmluvy="kúpna zmluva";

            cestaSuboru = JOptionPane.showInputDialog("Zadajte miesto uloženia novej kúpnej zmluvy", "C:\\Users\\igoro\\OneDrive\\Desktop\\dp_definitiva\\exportWord\\");

            out = new FileOutputStream(new File(cestaSuboru + nazovZmluvy + " "+ cislaLVPeciatka+".docx"));
            paragraph = document.createParagraph();

            XWPFParagraph p1 = document.createParagraph();
            XWPFParagraph[] pars;
            p1.setFirstLineIndent(400);
            // justify alignment
            p1.setAlignment(ParagraphAlignment.RIGHT);
            // wrap words
            p1.setWordWrapped(true);
                CTP ctP = CTP.Factory.newInstance();

                // header text
                CTText t = ctP.addNewR().addNewT();
               // t.setStringValue("Sample Header Text");

                pars = new XWPFParagraph[1];
                p1 = new XWPFParagraph(ctP, document);
                pars[0] = p1;

                XWPFHeaderFooterPolicy hfPolicy = document.createHeaderFooterPolicy();
               // hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

                ctP = CTP.Factory.newInstance();
                t = ctP.addNewR().addNewT();

                // footer text
                t.setStringValue("Kúpna zmluva\t\t\t\t\t");

                CTR ctr = ctP.addNewR();
                CTText t3 = ctr.addNewT();

                t3.setStringValue("\t\t\t\t\t\t\t\t\t\t\tstr ");
                t3.setSpace(SpaceAttribute.Space.PRESERVE);
                 ctr = ctP.addNewR();
                ctr.addNewRPr();
                CTFldChar fch = ctr.addNewFldChar();
                fch.setFldCharType(STFldCharType.BEGIN);

                ctr = ctP.addNewR();
                ctr.addNewInstrText().setStringValue(" PAGE ");

                ctP.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
                ctP.addNewR().addNewT().setStringValue("1");
                ctP.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

                pars[0] = new XWPFParagraph(ctP, document);


                hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars);



            paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
            paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
            paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
            paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);


            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run = paragraph.createRun();
            run.addBreak();

            stylCalibriBold("KÚPNA ZMLUVA", 16);
            stylCalibriNormal("podľa ustanovenia § 588 a nasl.  zákona č. 40/1964 " +
                    "Zb. Občiansky zákonník v znení neskorších predpisov", 9);

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            predavajuciMeno=najdiUdajeVlastnik("2").get(0).get(0);
            predavajuciPriezvisko=najdiUdajeVlastnik("2").get(0).get(1);

            stylCalibriBoldUnderLine("Predávajúci:", 11);
            stylCalibriNormal("Meno a priezvisko:\t\t\t " + predavajuciMeno + "  " + predavajuciPriezvisko, 11);
            stylCalibriNormal("Dátum narodenia: " + najdiUdajeVlastnik("2").get(0).get(2), 11);
            stylCalibriNormal("Rodné číslo: ", 11);
            stylCalibriNormal("Bytom: " + najdiUdajeVlastnik("2").get(0).get(3), 11);
            stylCalibriNormal("Štátna príslušnosť:\t\t\tSR", 11);
            stylCalibriNormal("Bankové spojenie: ", 11);
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            stylCalibriNormal("(ďalej v texte tiež ako „predávajúci“) ", 11);
            run.addBreak();
             podielCitatel =najdiUdajeVlastnik("2").get(0).get(4);
             podielMenovatel =najdiUdajeVlastnik("2").get(0).get(5);
             podiel="";
            podiel = podiel.concat(podielCitatel+"/"+podielMenovatel);
            for (int i = 0; i < najdiUdajeVlastnik("1").size(); i++) {
                pocetKupujucich=najdiUdajeVlastnik("1").size();
                kupujuciMeno.add(najdiUdajeVlastnik("1").get(i).get(0));
                kupujuciPriezvisko.add(najdiUdajeVlastnik("1").get(i).get(1));

                paragraph = document.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                stylCalibriBoldUnderLine("Kupujúci: ", 11);
                stylCalibriNormal("Meno a priezvisko: " + kupujuciMeno.get(i) + "  " + kupujuciPriezvisko.get(i), 11);
                stylCalibriNormal("Trvale bytom: " + najdiUdajeVlastnik("1").get(i).get(3), 11);
                stylCalibriNormal("Dátum narodenia: " + najdiUdajeVlastnik("1").get(i).get(2), 11);
                stylCalibriNormal("Rodné číslo: ", 11);
                stylCalibriNormal("Štátna príslušnosť:\t\t\tslovenská", 11);
            }


            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            stylCalibriNormal("(ďalej v texte tiež ako „kupujúci“) ", 11);
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            stylCalibriNormal("sa dohodli na uzavretí tejto kúpnej zmluvy o prevode vlastníckeho práva k nehnuteľnostiam (ďalej len „zmluva“) za nižšie dohodnutých podmienok s nasledovným obsahom:", 11);
            run.addBreak();



            /*****Článok I. Predmet zmluvy*************************************************/


            stylCalibriBold("Článok I. Predmet zmluvy", 11);
            run.addBreak();

            stylCalibriNormal("1\tPredávajúci je podielovým spoluvlastníkom nasledovných nehnuteľností a to: ", 11);
            run.addBreak();
            // run.addCarriageReturn();


            String query = "";
            int cisloNehnutelnosti=0;
            for (int i = 0; i < cislaLvZmluvy.size(); i++) {

                paragraph = document.createParagraph();
                paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
                paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
                paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
                paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);

                paragraph.setAlignment(ParagraphAlignment.CENTER);
                run = paragraph.createRun();
                run.addBreak();

                stylCalibriBold("PARCELY registra \"C\" evidované na katastrálnej mape", 11);
                //  XWPFTable tabulka = document.createTable();
                //  table.setWidthType(TableWidthType.AUTO);

                // tabulka.setRowBandSize(1500);
                // riadok.setHeight(1);
                XWPFTable tabulka = document.createTable();

                XWPFTableRow riadok = tabulka.getRow(0); // prvy riadok
                //  riadok.getCell(0).setParagraph(paragraph);
                XWPFTableCell bunka = riadok.getCell(0);
                CTTblWidth cellWidth = bunka.getCTTc().addNewTcPr().addNewTcW();
                cellWidth.setW(BigInteger.valueOf(5000));
                bunka.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                query = "SELECT  lv.IDLV, lv.cisloLV, lv.tarchy, lv.okresNazov, lv.obecNazov,  lv.katastralneUzemieNazov, p.parcelneCislo, p.vymera, p.druhPozemku, p.sposobVyuzitia, p.spolocnaNehnutelnost, p.umiestneniePozemku, p.druhPV \n" +
                        " FROM parcela p, list_vlastnictva lv \n" +
                        " WHERE lv.IDLV=p.IDLV \n" +
                        " AND  lv.IDLV = " + "' " + cislaLvZmluvy.get(i) + "' ";


                try {
                    db.getData(query, indexyNONE);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                List<Integer> indexyTabulka = new ArrayList<>();
                indexyTabulka.add(najdiIndex("parcelneCislo",db));
                indexyTabulka.add(najdiIndex("vymera",db));
                indexyTabulka.add(najdiIndex("druhPozemku",db));
                indexyTabulka.add(najdiIndex("sposobVyuzitia",db));
                indexyTabulka.add(najdiIndex("spolocnaNehnutelnost",db));
                indexyTabulka.add(najdiIndex("umiestneniePozemku",db));
                indexyTabulka.add(najdiIndex("druhPV",db));


                // Tento kod len vytvori prazdnu tabulku
                for (int j = 1; j < indexyTabulka.size(); j++) {

                    bunka = riadok.addNewTableCell();
                    cellWidth = bunka.getCTTc().addNewTcPr().addNewTcW();
                    cellWidth.setW(BigInteger.valueOf(5000));    //ak sirka presahuje sirku strany tak urobi max moznu sirku buniek
                    bunka.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    //  riadok.getCell(0).setParagraph(paragraph);

                }


                //tento kod da do nulteho riadku nazvy stlpcov z hash mapy
                for (int j = 0; j < indexyTabulka.size(); j++) {

                    riadok.getCell(j).setText(db.getMenaStlpcov().get(db.getColumnNames().get(indexyTabulka.get(j))));   //rekord poctu getov jupi

                }


                for (int j = 0; j < db.getTabulkaData().size(); j++) {  //pre vsetky riadky
                    riadok = tabulka.createRow();
                    for (int k = 0; k < indexyTabulka.size(); k++) {   //pre vsetky stlpce

                        if (db.getTabulkaData().get(j).get(indexyTabulka.get(k)) == null) {
                            riadok.getCell(k).setText("0");
                        } else {
                            riadok.getCell(k).setText(db.getTabulkaData().get(j).get(indexyTabulka.get(k)).toString());
                        }

                    }


                }
                run.addBreak();

                paragraph = document.createParagraph();
                run = paragraph.createRun();
                run.addBreak();
                run.addBreak();

                String tarchy = db.getTabulkaData().get(0).get(najdiIndex("tarchy",db)).toString();
                if (!tarchy.contains("Bez tiarch")){
                tarchyMapa.put(i+1,tarchy);

                }
               String okres = db.getTabulkaData().get(0).get(najdiIndex("okresNazov",db)).toString();
               String cisloLV = db.getTabulkaData().get(0).get(najdiIndex("cisloLV",db)).toString();
               String obec = db.getTabulkaData().get(0).get(najdiIndex("obecNazov",db)).toString();
               String katastralneUzemie = db.getTabulkaData().get(0).get(najdiIndex("katastralneUzemieNazov",db)).toString();
               cisloNehnutelnosti = i+1;
                String pomocnyString="";
               if(i==0){pomocnyString=" , a nehnuteľností";}
               else {
                   pomocnyString=" ďalej ";
                   for(int l=1;l<=cislaLvZmluvy.size();l++){

                       pomocnyString=pomocnyString.concat(" nehnuteľnosť "+l +" a ");

                   }
                   pomocnyString = pomocnyString.substring(0,pomocnyString.length() - 1);
                    pomocnyString=pomocnyString.concat(" spoločne ako „nehnuteľnosť“ alebo „predmet prevodu“).");
               }
               stylCalibriNormal("všetko zapísané na LV č. "+cisloLV+ " , vedené Okresným úradom "+ okres + " , katastrálny odbor, nachádzajúce sa v okrese " + okres + " , v obci " + obec + " , v katastrálnom území " + katastralneUzemie + "  o veľkosti spoluvlastníckeho podielu " + podiel + " (ďalej len „nehnuteľnosť " + cisloNehnutelnosti + " “), " + pomocnyString , 11);

                run.addBreak();

            }
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            run = paragraph.createRun();

            for (Map.Entry me : tarchyMapa.entrySet()) {
                stylCalibriNormal("2	K nehnuteľnosti " + me.getKey() + " špecifikovanej v predošlom bode sú na príslušnom liste vlastníctva zapísané nasledovné ťarchy: " + me.getValue() +" \n", 11);


            }

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.NUM_TAB);
            run = paragraph.createRun();

            run.setText("3\tKupujúci si je vedomý tiarch viažucich sa k vyššie nehnuteľnosti podľa predošlých bodov tohto článku zmluvy.\n");


            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);

            int pocitadloK=4;

        for(int i=0;i<kupujuciPriezvisko.size();i++){

            run = paragraph.createRun();
            run.setBold(true);
            run.setText(pocitadloK+"\tPredávajúci " +" "+ predavajuciMeno +" "+predavajuciPriezvisko +" touto zmluvou predáva svoj spoluvlastnícky podiel k nehnuteľnosti špecifikovanej v bode 1.1 tohto článku zmluvy  "+
                    "vo veľkosti spoluvlastníckeho podielu " +" "+ podiel + " kupujúcemu s menom " + kupujuciMeno.get(i) +" "+kupujuciPriezvisko.get(i)+ " za   dohodnutú kúpnu cenu. Kupujúci kupuje a nadobúda podiel za túto cenu do svojho výlučného vlastníctva. \t\t\t\n" );



            run.addBreak();
            pocitadloK++;
        }

            /*****Článok I. Predmet zmluvy*************************************************/




            /*****Článok II. Popis a technický stav nehnuteľností*************************************************/

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            stylCalibriBold("Článok II. Popis a technický stav nehnuteľností",11);
            run.addBreak();
            stylCalibriNormal("Kupujúci vyhlasuje, že pred uzavretím zmluvy sa oboznámil s technickým stavom nehnuteľnosti jeho obhliadkou na mieste samom a v takomto stave predmet zmluvy kupuje.",11);
            run.addBreak();

            /*****Článok II. Popis a technický stav nehnuteľností*************************************************/




            /*****Článok III. Kúpna cena predmetu prevodu a splatnosť kúpnej ceny*************************************************/

            stylCalibriBold("Článok III. Kúpna cena predmetu prevodu a splatnosť kúpnej ceny",11);

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            run = paragraph.createRun();


            stylCalibriNormal("1\tZmluvné strany sa dohodli, že cena za predmet zmluvy je v zmysle zákona č. 18/1996 Z. z. o cenách v znení neskorších právnych predpisov stanovená nasledovne:\t\t\t\n",11);
            paragraph = document.createParagraph();
            paragraph.setIndentFromLeft(800);
            run = paragraph.createRun();
                run.setText("a.\tvo výške ....................... € (slovom: desať centov) za každý 1 meter štvorcový z nehnuteľnosti 1 špecifikovaných v bode 1.1 tejto zmluvy, t. j. ............. €, za nehnuteľnosti 1 špecifikované v bode 1.1 tejto zmluvy,\t\t\t\n");
                run.addCarriageReturn();
                run.setText("b.\tvo výške 22 € (slovom: dvadsaťdva eur) za každý 1 meter štvorcový z nehnuteľnosti 2 špecifikovaných v bode 1.1 tejto zmluvy, t. j. ................................. € za nehnuteľnosti 2 špecifikované v bode 1.1, tejto zmluvy,\t\t\t\n");

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            run = paragraph.createRun();
            stylCalibriBold("celkovo teda suma vo výške .................................... € (slovom: .........................................ť eur a päťdesiatsedem centov) (ďalej v texte tiež ako „kúpna cena“). \t\t\t\n",11);
            run.addBreak();
            stylCalibriNormal("2\tZmluvné strany sa dohodli, že kupujúci uhradí predávajúcim kúpnu cenu uvedenú v bode 3.1 tohto článku tejto zmluvy a to nasledovne:\t\t\t\n",11);

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setIndentFromLeft(800);
            run = paragraph.createRun();
            run.setText("a.\tkúpnu cenu vo výške .................................. € (slovom: ............................... eur a päťdesiatsedem centov) uhradí kupujúci poukázaním na bankový účet predávajúceho uvedený v záhlaví tejto zmluvy, s čím predávajúci výslovne súhlasí a to najneskôr do 5 kalendárnych dní odo dňa podpísania tejto zmluvy a to z vlastných zdrojov. V prípade, ak nedôjde k úhrade Kúpnej ceny v dohodnutej lehote, zmluvné strany sa dohodli, že ktorákoľvek zo zmluvných strán je oprávnená odstúpiť od tejto zmluvy, v takom prípade si zmluvné strany vzájomne vrátia čo si plnili do doby, keď došlo k odstúpeniu od tejto zmluvy\t\t\t\n");
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            stylCalibriNormal("3\tV uvedenej kúpnej cene nie sú zahrnuté správne poplatky a iné platby súvisiace s povolením vkladu vlastníckeho práva v prospech kupujúceho do katastra nehnuteľností. Poplatky spojené s podaním návrhu na vklad vlastníckeho práva k predmetu zmluvy do príslušného katastra nehnuteľností a poplatky spojené s vyhotovením tejto zmluvy bude znášať kupujúci a poplatky spojené s úradným osvedčením podpisu tejto zmluvy bude hradiť predávajúci.\t\t\t\n ",11);
            run.addBreak();

            /*****Článok III. Kúpna cena predmetu prevodu a splatnosť kúpnej ceny*************************************************/






            /***Článok IV. Nadobudnutie vlastníctva k predmetu zmluvy********************************************************/

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            stylCalibriBold("Článok IV. Nadobudnutie vlastníctva k predmetu zmluvy ",11);

            text.add("Kupujúci nadobudne vlastnícke právo k predmetu zmluvy dňom zápisu vlastníckeho práva do príslušného katastra nehnuteľností v prospech kupujúceho.\n");
            text.add("Zmluvné strany sa dohodli, že návrh na vklad vlastníckych práv v zmysle tejto zmluvy podá kupujúci prostredníctvom advokáta ......................................, zapísaného v SAK pod č............., IČO: .............................., so sídlom ..............................................., SR, ktorého za týmto účelom splnomocňuje. Kupujúci splnomocňuje advokáta na vyhotovenie a podpísanie návrhu na vklad do katastra nehnuteľností vrátane vyhotovenia a podpísania zamýšľaného návrhu na vklad do katastra nehnuteľností, mimo doručenia rozhodnutia okresného úradu, katastrálny odbor o povolenia vkladu do katastra nehnuteľností.\t\t\t\n");
            text.add("Zmluvné strany sa zaväzujú poskytnúť si vzájomne súčinnosť potrebnú v konaní o vklade vlastníckeho práva k nehnuteľnosti v prospech kupujúceho a to bezodkladne, keď o konkrétnu súčinnosť ktorákoľvek zo zmluvných strán požiada.\t\t\t\t\t\n");



            cislovanyText(text);
            text = new ArrayList<>();

            /***Článok IV. Nadobudnutie vlastníctva k predmetu zmluvy********************************************************/






            /***Článok V********************************************************/

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            stylCalibriBold("Článok V.",11);

            text.add("Predávajúci ručí za vlastníctvo predmetu zmluvy, za jeho právny stav a za to, že je úpln neobmedzený vo svojich dispozičných právach k predmetnému predmetu zmluvy. Ďalej predávajúci ručí za to, že v súčasnosti nie je vedený žiaden súdny spor alebo správne konanie, a že ani nehrozí začatie takéhoto konania vo vzťahu k predmetu zmluvy. Rovnako ručí za to, že na predmete zmluvy nie sú uzatvorené žiadne nájomné zmluvy. V prípade preukázania mopaku ako je uvedené v predošlých vetách tohto bodu zmluvy je kupujúci oprávnený od tejto zmluvy odstúpiť; nárok na náhradu škody tým nie je dotknutý.\t\t\t\n");
            text.add("Pokiaľ príslušný kataster nehnuteľností nepovolí vklad vlastníckeho práva k predmetu zmluvy v prospech kupujúceho, zmluvné strany do 7 kalendárnych dní po vzájomnej dohode odstránia nedostatky, pre ktoré kataster nehnuteľností nepovolil vklad vlastníckeho práva, a to formou dodatku k tejto zmluve alebo uskutočnením iných právnych úkonov potrebných na dosiahnutie úspešného vkladu vlastníckeho práva k dohodnutému pozemku do príslušného katastra nehnuteľností.\t\t\t\n");
            text.add("Predávajúci sa zaväzuje, že po podpísaní tejto zmluvy nedaruje, nepredá ani iným spôsobom neprevedie predmet zmluvy na inú osobu, nezaťaží ho záložným právom, vecným bremenom, alebo akýmkoľvek iným právom tretej osoby, neprenajme a ani ho nevloží do žiadnej obchodnej spoločnosti alebo družstva, ani nevykoná iný úkon, na základe ktorého môže byť do príslušnej evidencie nehnuteľností zapísaná tretia osoba / tretie osoby ako vlastník predmetu zmluvy.\t\t\t\t\t\n");


            cislovanyText(text);
            text = new ArrayList<>();

            /***Článok V********************************************************/






            /***Článok VI. Udelenie plnomocenstva***********************************************************/

            run.addBreak();
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            stylCalibriBold("Článok VI. Udelenie plnomocenstva",11);

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            run = paragraph.createRun();
            stylCalibriNormal("Predávajúci a kupujúci podpisom tejto zmluvy zároveň splnomocňujú advokáta – pána ...................................., zapísaného v zozname SAK pod č. ..................., so sídlom ...................................... Partizánske, SR, IČO: ......................................, ako splnomocnenca na všetky ",11);
            stylCalibriBold("právne úkony súvisiace s opravou prípadných chýb v písaní a počítaní resp. iných zrejmých nesprávností v tejto zmluve, a to vo forme písomného dodatku k tejto zmluve, ako aj na všetky právne úkony súvisiace s opravou prípadných chýb v písaní a počítaní resp. iných zrejmých nesprávností v návrhu na vklad vlastníckeho práva resp. vecného bremena do katastra nehnuteľností na základe zmluvy ako aj k podpisu písomných dodatkov k tejto zmluve za účelom opravy prípadných chýb v písaní a počítaní resp. iných zrejmých nesprávností resp. opravy v právnych skutočnostiach vyplývajúcich z tejto zmluvy resp. iných zmlúv a právnych skutočností, mimo doručenia rozhodnutia okresného úradu, katastrálny odbor o povolenia vkladu do katastra nehnuteľností.\t\t\t\t\n",11);
            run.addBreak();
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            run = paragraph.createRun();

            /***Článok VI. Udelenie plnomocenstva***********************************************************/





            /***Článok VII. Osobitné ustanovenia***********************************************************/

            paragraph = document.createParagraph();
            stylCalibriBold("Článok VII. Osobitné ustanovenia",11);

            text.add("Zmluvné strany sa zaväzujú, že za účelom výkonu práv a plnenia povinností podľa zmluvy si budú vzájomne poskytovať potrebnú súčinnosť v akejkoľvek forme a urobia všetky úkony potrebné k tomu, aby sa dosiahol účel sledovaný zmluvou.\t\t\t\n");
            text.add("Zmluvné strany sa zároveň zaväzujú vzájomne sa informovať o všetkých skutočnostiach potrebných pre plnenie ich záväzkov zo zmluvy a oznamovať si včas dôležité okolnosti a ich zmeny, ktoré môžu mať vplyv na ich práva a povinnosti vyplývajúce zo zmluvy.\t\t\n");
            text.add("Zmluvné strany sa dohodli, že všetky informácie, ktoré si navzájom poskytnú v súvislosti so zmluvou a označia ich ako dôverné alebo z ktorých povahy bude vyplývať, že ide o dôverné informácie, použijú len v súvislosti s plnením svojich záväzkov a výkonom svojich práv podľa zmluvy alebo pri zabezpečovaní ochrany svojich práv podľa zmluvy a nepoužijú ich v rozpore s ich účelom; za porušenie tejto povinnosti sa však nepovažuje poskytnutie informácií tretím osobám v prípadoch a v rozsahu stanovenom právnymi predpismi alebo tretím osobám, ktoré sú alebo budú (na základe zákona alebo na základe dohody so zmluvnou stranou, ktorá informácie poskytuje) viazané mlčanlivosťou. Tento záväzok mlčanlivosti a utajenia platí i po zániku zmluvy.\t\t\t\t\t\t\t\n");

            cislovanyText(text);
            text = new ArrayList<>();

            /***Článok VII. Osobitné ustanovenia***********************************************************/





            /***Cast VIII Zaverecne ustanovenia***********************************************************/

            paragraph = document.createParagraph();
            stylCalibriBold("Článok VIII. Záverečné ustanovenia",11);

            text.add("Táto zmluva nadobúda platnosť a účinnosť dňom jej podpisu zmluvnými stranami  a vlastnícke právo k nehnuteľnostiam nadobudne kupujúci v zmysle platných všeobecne záväzných právnych predpisov. Zmluvné strany vyhlasujú, že berú na vedomie, že podpísaním tejto zmluvy sú svojimi prejavmi viazané až do rozhodnutia o vklade vlastníckeho práva k nehnuteľnostiam do príslušného katastra nehnuteľností.\t\t\t\n");
            text.add("Zmena zmluvy je možná len písomnou dohodou oboch zmluvných strán, vo forme riadne očíslovaných písomných dodatkov. \t\t\t\n");
            text.add("Táto zmluva je vyhotovená v štyroch rovnopisoch, pričom každý z nich má platnosť originálu, z ktorých kupujúci obdržia 1 vyhotovenie, predávajúci obdrží 1 vyhotovenia, 2 vyhotovenia sú určené pre účely katastrálneho konania o vklad vlastníckeho práva.\t\t\t\n");
            text.add("Ak by niektoré z ustanovení tejto zmluvy bolo  neplatné alebo by sa takým stalo neskôr, nie je tým dotknutá platnosť ostatných jej ustanovení. V takom prípade zmluvné strany dohodnú náhradnú úpravu, ktorá najviac zodpovedá cieľu sledovanému neplatným ustanovením.\n");
            text.add("Písomnosti týkajúce sa záväzkov medzi zmluvnými stranami, ktoré vyplývajú z tejto zmluvy sa účastníci zaväzujú doručovať poštou vo forme doporučenej listovej zásielky. Poštou doručuje zmluvná strana - odosielateľ písomnosti druhej zmluvnej strane - adresátovi na adresu jeho sídla uvedeného v záhlaví tejto zmluvy, resp. adresu písomne oznámenú zmluvnou stranou ako korešpondenčnú adresu. Zmluvné strany sa výslovne dohodli, že písomnosť sa bude považovať za doručenú adresátovi aj v prípade, ak bude vrátená poštou ako písomnosť adresátom v úložnej dobe neprevzatá, písomnosť, ktorú adresát odmietol prevziať alebo adresát na uvedenej adrese neznámy. Zmluvné strany sa dohodli, že za deň doručenia písomnosti sa vtedy považuje 10. deň odo dňa uloženia písomnosti na pošte, deň, kedy adresát odmietol zásielku prevziať alebo deň, kedy bola písomnosť vrátená poštou odosielateľovis tým, že adresát je neznámy. \t\t\t\n");
            text.add("Zmluvné strany vyhlasujú, že si zmluvu prečítali, s jej obsahom sa riadne a podrobne oboznámili, pričom všetky ustanovenia zmluvy sú im zrozumiteľné a dostatočne určitým spôsobom vyjadrujú slobodnú a vážnu vôľu zmluvných strán, ktorá nebola prejavená ani v tiesni ani za nápadne nevýhodných podmienok, a že sú oprávnení s predmetom zmluvy nakladať a ich spôsobilosť nie je ničím obmedzená, čo zmluvné strany nižšie potvrdzujú svojimi podpismi.\t\t\t\n");


            cislovanyText(text);
            text = new ArrayList<>();

            /***Cast VIII averecne ustanovenia***********************************************************/

/*
                stylCalibriNormal("Táto zmluva nadobúda platnosť a účinnosť dňom jej podpisu zmluvnými stranami  a vlastnícke právo k nehnuteľnostiam nadobudne kupujúci v zmysle platných všeobecne záväzných právnych predpisov. Zmluvné strany vyhlasujú, že berú na vedomie, že podpísaním tejto zmluvy sú svojimi prejavmi viazané až do rozhodnutia o vklade vlastníckeho práva k nehnuteľnostiam do príslušného katastra nehnuteľností.\t\t\t\n", 11);
                stylCalibriNormal("Zmena zmluvy je možná len písomnou dohodou oboch zmluvných strán, vo forme riadne očíslovaných písomných dodatkov. \t\t\t\n", 11);
                stylCalibriNormal("Táto zmluva je vyhotovená v štyroch rovnopisoch, pričom každý z nich má platnosť originálu, z ktorých kupujúci obdržia 1 vyhotovenie, predávajúci obdrží 1 vyhotovenia, 2 vyhotovenia sú určené pre účely katastrálneho konania o vklad vlastníckeho práva.\t\t\t\n", 11);
                stylCalibriNormal("Ak by niektoré z ustanovení tejto zmluvy bolo  neplatné alebo by sa takým stalo neskôr, nie je tým dotknutá platnosť ostatných jej ustanovení. V takom prípade zmluvné strany dohodnú náhradnú úpravu, ktorá najviac zodpovedá cieľu sledovanému neplatným ustanovením.\n", 11);
                stylCalibriNormal("Písomnosti týkajúce sa záväzkov medzi zmluvnými stranami, ktoré vyplývajú z tejto zmluvy sa účastníci zaväzujú doručovať poštou vo forme doporučenej listovej zásielky. Poštou doručuje zmluvná strana - odosielateľ písomnosti druhej zmluvnej strane - adresátovi na adresu jeho sídla uvedeného v záhlaví tejto zmluvy, resp. adresu písomne oznámenú zmluvnou stranou ako korešpondenčnú adresu. Zmluvné strany sa výslovne dohodli, že písomnosť sa bude považovať za doručenú adresátovi aj v prípade, ak bude vrátená poštou ako písomnosť adresátom v úložnej dobe neprevzatá, písomnosť, ktorú adresát odmietol prevziať alebo adresát na uvedenej adrese neznámy. Zmluvné strany sa dohodli, že za deň doručenia písomnosti sa vtedy považuje 10. deň odo dňa uloženia písomnosti na pošte, deň, kedy adresát odmietol zásielku prevziať alebo deň, kedy bola písomnosť vrátená poštou odosielateľovis tým, že adresát je neznámy. \t\t\t\n", 11);
                stylCalibriBold("Zmluvné strany vyhlasujú, že si zmluvu prečítali, s jej obsahom sa riadne a podrobne oboznámili, pričom všetky ustanovenia zmluvy sú im zrozumiteľné a dostatočne určitým spôsobom vyjadrujú slobodnú a vážnu vôľu zmluvných strán, ktorá nebola prejavená ani v tiesni ani za nápadne nevýhodných podmienok, a že sú oprávnení s predmetom zmluvy nakladať a ich spôsobilosť nie je ničím obmedzená, čo zmluvné strany nižšie potvrdzujú svojimi podpismi.\t\t\t\n", 11);
*/


            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            run = paragraph.createRun();
            run.addBreak();
            run.addBreak();
            run.addBreak();
            run.addBreak();
            stylCalibriNormal("V ................................., dňa ................",11);
            run.addBreak();
            paragraph = document.createParagraph();
            run.addBreak();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

                run.addBreak(BreakType.PAGE);

                run = paragraph.createRun();
                run.setBold(true);
                run.setText("Predávajúci");
                run.addTab();
                run.addTab();
                run.addTab();
                run.addTab();
                run.addTab();
                run.addTab();

                run.setText("Kupujúci 1");
                run.addBreak();
                run.addBreak();
                run.addBreak();
                run.addBreak();
                run.addCarriageReturn();
                run.setText("___________________________");
                run.addTab();
                run.addTab();
                run.addTab();
                run.setText("___________________________");
                run.addBreak();
                run.addCarriageReturn();
                run.addBreak();
                run.setText("....................................");
                run.addTab();
                run.addTab();
                run.addTab();
                run.addTab();
                run.addTab();
                run = paragraph.createRun();
                run.setBold(false);
                run.setText(kupujuciMeno.get(0) +" "+kupujuciPriezvisko.get(0));
                run.addCarriageReturn();
                run = paragraph.createRun();
                run.setFontSize(9);
                run.setText("úradne osvedčený podpis");
                run.addBreak();
                run.addBreak();

                int pocitadlo =1;
                for(int i=1;i<kupujuciPriezvisko.size();i++){
                    pocitadlo++;
                    run = paragraph.createRun();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                   stylCalibriBold("Kupujúci "+pocitadlo,11);
                    run.addBreak();
                    run.addBreak();
                    run.addBreak();
                    run.addBreak();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    stylCalibriNormal("___________________________",11);
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    run.addTab();
                    stylCalibriNormal(kupujuciMeno.get(i)+" "+kupujuciPriezvisko.get(i),11);
                    run.addBreak();
                    run.addBreak();

                }


            document.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void cislovanyText(List<String> textCislo) {



        cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));
        CTLvl cTLvl;
        cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewLvlText().setVal("%1.");



        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));

        abstractNum = new XWPFAbstractNum(cTAbstractNum);

        numbering = document.createNumbering();

        abstractNumID = numbering.addAbstractNum(abstractNum);
         numID = numbering.addNum(abstractNumID);


        for (int i = 0; i < textCislo.size(); i++) {


            paragraph = document.createParagraph();
            // run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setNumID(numID);
            stylCalibriNormal(textCislo.get(i), 11);
            //  run.setText(text.get(i));
            //run.addCarriageReturn();


        }


            XWPFNumbering numbering = document.getNumbering();

            XWPFNum num = numbering.getNum(numID);
            CTNumLvl lvloverride = num.getCTNum().addNewLvlOverride();
            lvloverride.setIlvl(BigInteger.ZERO);
            CTDecimalNumber number = lvloverride.addNewStartOverride();
            number.setVal(BigInteger.ONE);



    }







    private void stylCalibriBold(String text, int fontsize) {
        run = paragraph.createRun();
        run.setFontSize(fontsize);
        run.setBold(true);
        run.setFontFamily("Calibri");
        run.setText(text);
        run.addBreak();


    }

    private void stylCalibriBoldUnderLine(String text, int fontsize) {
        run = paragraph.createRun();
        run.setFontSize(fontsize);
        run.setBold(true);
        run.setUnderline(UnderlinePatterns.SINGLE);
        run.setFontFamily("Calibri");
        run.setText(text);
        run.addBreak();


    }

    private void stylCalibriNormal(String text, int fontsize) {
        run = paragraph.createRun();
        run.setFontSize(fontsize);
        run.setBold(false);
        run.setFontFamily("Calibri");
        run.setText(text);
        run.addBreak();


    }

    private int najdiIndex(String nazovStlpca, Databaza db) {
        int j = -1;
        for (int i = 0; i < db.getColumnNames().size(); i++) {

            if (nazovStlpca.equals(db.getColumnNames().get(i))) {
                j = i;
                break;

            }

        }

        return j;
    }

private String getCislaLV(List<Object> cislaLVPeciatka) throws SQLException {
       String dataPeciatka="";
        Databaza db = new Databaza(true,"databaza_pozemky.list_vlastnictva");
        String query="";
        for(int i=0;i<cislaLVPeciatka.size();i++) {
            query = "SELECT  lv.cisloLV \n" +
                    " FROM  list_vlastnictva lv \n" +
                    " WHERE  lv.IDLV = " + "' " + cislaLVPeciatka.get(i) + "' ";

    try {
         dataPeciatka=dataPeciatka.concat(db.getData(query, indexyNONE).toString()+"_");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}       dataPeciatka=dataPeciatka.replace("[","");
    dataPeciatka=dataPeciatka.replace("]","");
return dataPeciatka;
}



    private List<List<String>> najdiUdajeVlastnik(String akciaTyp) {
        List<List<String>> udajeVlastnik = new ArrayList<>();
        List<String> pomocnyRiadok = new ArrayList<>();
        int i;

        for (i = 0; i < db.getTabulkaData().size(); i++) {

            if (db.getTabulkaData().get(i).get(najdiIndex("akcia",db)).equals(akciaTyp)) {
                if (db.getTabulkaData().get(i).get(najdiIndex("menoVlastnik",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {
                    String meno = db.getTabulkaData().get(i).get(najdiIndex("menoVlastnik",db)).toString();
                    String output = meno.substring(0, 1) + meno.substring(1).toLowerCase();

                    // pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("menoVlastnik")).toString()); }
                    pomocnyRiadok.add(output);
                }

                if (db.getTabulkaData().get(i).get(najdiIndex("priezviskoVlastnik",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {

                    String meno = db.getTabulkaData().get(i).get(najdiIndex("priezviskoVlastnik",db)).toString();
                    String output = meno.substring(0, 1) + meno.substring(1).toLowerCase();

                    pomocnyRiadok.add(output);
                }
                //  pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("priezviskoVlastnik")).toString());}

                if (db.getTabulkaData().get(i).get(najdiIndex("datumNarodeniaVlastnik",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {
                    // pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("datumNarodeniaVlastnik")).toString());
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    String strDate = dateFormat.format(db.getTabulkaData().get(i).get(najdiIndex("datumNarodeniaVlastnik",db)));
                    pomocnyRiadok.add(strDate);
                }
                if (db.getTabulkaData().get(i).get(najdiIndex("adresaVlastnik",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {
                    pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("adresaVlastnik",db)).toString());
                }

                if (db.getTabulkaData().get(i).get(najdiIndex("podielCitatel",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {
                    pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("podielCitatel",db)).toString());
                }
                if (db.getTabulkaData().get(i).get(najdiIndex("podielMenovatel",db)) == null) {
                    pomocnyRiadok.add(null);
                } else {
                    pomocnyRiadok.add(db.getTabulkaData().get(i).get(najdiIndex("podielMenovatel",db)).toString());
                }



                udajeVlastnik.add(pomocnyRiadok);
                System.out.println("pomocny " + pomocnyRiadok);
                pomocnyRiadok = new ArrayList<>();
            }

        }
        return udajeVlastnik;
    }
}

