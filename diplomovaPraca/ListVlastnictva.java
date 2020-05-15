package diplomovaPraca;

import java.io.*;
//import java.text.DateFormat;
import java.text.DateFormat;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ListVlastnictva {
    private String cestaSuboru;
    private int ID;
    private String cisloLV;
    private List<String> CKcisloLV;
    private String IDParcela;
    private String datumVyhotovenia;
    private String casVyhotovenia;
    private String okresCislo;
    private String okresNazov;
    private String obecCislo;
    private String obecNazov;
    private String katastralneUzemieCislo;
    private String katastralneUzemieNazov;
    private String platnostLV;
    private List<String> typParcely = new ArrayList<>();
    private int pocetParciel;
    private String pocetVlastnikov;
    private String pocetSpravcov;
    private List<Date> datumNarodenia = new ArrayList<>();
    private List<String> titulNadobudnutia = new ArrayList<>();
    private List<String> ineUdaje = new ArrayList<>();
    private List<String> poznamky = new ArrayList<>();
    private List<String> plomba = new ArrayList<>();
    private List<Integer> podielCitatel = new ArrayList<>();
    private List<Integer> podielMenovatel = new ArrayList<>();
    private List<Integer> novyPodielCitatel = new ArrayList<>();
    private List<Integer> novyPodielMenovatel = new ArrayList<>();
    private List<String> akcia = new ArrayList<>();
    private String tarchy;

    public String getTarchy() {
        return tarchy;
    }


    public List<Integer> getPodielCitatel() {
        return podielCitatel;
    }

    public List<Integer> getPodielMenovatel() {
        return podielMenovatel;
    }

    public List<Integer> getNovyPodielCitatel() {
        return novyPodielCitatel;
    }

    public List<Integer> getNovyPodielMenovatel() {
        return novyPodielMenovatel;
    }

    public List<String> getAkcia() {
        return akcia;
    }

    private List<Integer> IDVLastnik = new ArrayList<>();
    private List<String> menoVlastnika = new ArrayList<>();
    private List<String> priezviskoVlastnika = new ArrayList<>();
    private List<String> rodnePriezviskoVlastnika = new ArrayList<>();
    private List<String> inyIdentifikator = new ArrayList<>();
    private List<String> adresaV = new ArrayList<>();
    private List<String> ICO = new ArrayList<>();
    private List<String> spravcaTitulNadobudnutia = new ArrayList<>();
    private List<String> spravcaIneUdaje = new ArrayList<>();
    private List<String> spravcaPoznamky = new ArrayList<>();
   // private String legendaSVP;
   // private String legendaSN;
   // private String legendaUP;
    private List<String> parcelneCislo = new ArrayList<>();
    private List<String> vymera = new ArrayList<>();
    private List<String> povodneKU = new ArrayList<>();
    private List<String> druhPozemku = new ArrayList<>();
    private List<String> sposobVyuzitia = new ArrayList<>();
    private List<String> druhCHN = new ArrayList<>();
    private List<String> spolocnaNehnutelnost = new ArrayList<>();
    private List<String> umiestneniePozemku = new ArrayList<>();
    private List<String> druhPV = new ArrayList<>();
    private String typSuboru;
    public List<String> getCKcisloLV() {
        return CKcisloLV;
    }

    public List<String> getParcelneCislo() {
        return parcelneCislo;
    }

    public List<String> getVymera() {
        return vymera;
    }

    public List<String> getDruhPozemku() {
        return druhPozemku;
    }

    public List<String> getSposobVyuzitia() {
        return sposobVyuzitia;
    }

    public List<String> getDruhCHN() {
        return druhCHN;
    }

    public List<String> getSpolocnaNehnutelnost() {
        return spolocnaNehnutelnost;
    }

    public List<String> getUmiestneniePozemku() {
        return umiestneniePozemku;
    }

    public List<String> getDruhPV() {
        return druhPV;
    }


    public List<String> getPovodneKU() {
        return povodneKU;
    }
    /*
        public String getLegendaSN() {
            return legendaSN;
        }

        public void setLegendaSN(String legendaSN) {
            this.legendaSN = legendaSN;
        }

        public String getLegendaUP() {           return legendaUP;
        }

      /*  public void setLegendaUP(String legendaUP) {
            this.legendaUP = legendaUP;
        }

        public String getLegendaSVP() {
            return legendaSVP;
        }

       public void setLegendaSVP(String legendaSVP) {
            this.legendaSVP = legendaSVP;
        }
    */
    public String getObecNazov() {
        return obecNazov;
    }

    public void setObecNazov(String obecNazov) {
        this.obecNazov = obecNazov;
    }

    public String getPocetSpravcov() {
        //     System.out.println(pocetSpravcov);
        return pocetSpravcov;
    }

    public List<String> getSpravcaPoznamky() {
        //   for (int i=0; i<spravcaPoznamky.size();i++)
        //       System.out.println(spravcaPoznamky.get(i));
        return spravcaPoznamky;
    }

    public List<String> getSpravcaIneUdaje() {
        //  for (int i=0; i<spravcaIneUdaje.size();i++)
        //  System.out.println(spravcaIneUdaje.get(i));
        return spravcaIneUdaje;
    }

    public List<String> getSpravcaTitulNadobudnutia() {
        //   for (int i=0; i<spravcaTitulNadobudnutia.size();i++)
        // System.out.println(spravcaTitulNadobudnutia.get(i));
        return spravcaTitulNadobudnutia;
    }

    public List<String> getAdresaV() {
        // for (int i=0; i<adresaV.size();i++)
        // System.out.println(adresaV.get(i));
        return adresaV;
    }

    public List<String> getICO() {
        //   for (int i=0; i<ICO.size();i++)
        //   System.out.println(ICO.get(i));
        return ICO;
    }


    public List<String> getInyIdentifikator() {
        //  for (int i=0; i<inyIdentifikator.size();i++)
        //  System.out.println(inyIdentifikator.get(i));
        return inyIdentifikator;
    }

    public List<String> getRodnePriezviskoVlastnika() {
        // for (int i=0; i<rodnePriezviskoVlastnika.size();i++)
        //     System.out.println(rodnePriezviskoVlastnika.get(i));
        return rodnePriezviskoVlastnika;
    }

    public List<String> getMenoVlastnika() {
        //  for (int i=0; i<menoVlastnika.size();i++)
        //     System.out.println(menoVlastnika.get(i));
        return menoVlastnika;
    }

    public List<String> getPriezviskoVlastnika() {
        //  for (int i=0; i<priezviskoVlastnika.size();i++)
        //  System.out.println(priezviskoVlastnika.get(i));
        return priezviskoVlastnika;
    }


    public List<Integer> getIDVlastnik() {
        //    for (int i=0; i<IDVLastnik.size();i++)
        //     System.out.println(IDVLastnik.get(i));
        return IDVLastnik;
    }



    public List<Date> getDatumNarodenia() {
        //    for (int i=0; i<datumNarodenia.size();i++)
        //       System.out.println(datumNarodenia.get(i));
        return datumNarodenia;
    }

    public List<String> getPlomba() {
        //    for (int i=0; i<plomba.size();i++)
        //       System.out.println(plomba.get(i));
        return plomba;
    }


    public List<String> getTitulNadobudnutia() {
        //   for (int i=0; i<titulNadobudnutia.size();i++)
        //      System.out.println(titulNadobudnutia.get(i));
        return titulNadobudnutia;
    }

    public List<String> getIneUdaje() {
        //   for (int i=0; i<ineUdaje.size();i++)
        //     System.out.println(ineUdaje.get(i));
        return ineUdaje;
    }

    public List<String> getPoznamky() {
        //   for (int i=0; i<poznamky.size();i++)
        //      System.out.println(poznamky.get(i));
        return poznamky;
    }


    public String getPocetVlastnikov() {
        return pocetVlastnikov;
    }

    public int getPocetParciel() {
        return pocetParciel;
    }

    public List<String> getTypParcely() {
        return typParcely;
    }

    public String getPlatnostLV() {
        return platnostLV;
    }

    public String getKatastralneUzemieCislo() {
        return katastralneUzemieCislo;
    }

    public String getKatastralneUzemieNazov() {
        return katastralneUzemieNazov;
    }

    public String getObecCislo() {
        return obecCislo;
    }

    public String getOkresCislo() {
        return okresCislo;
    }

    public String getOkresNazov() {
        return okresNazov;
    }
    public int getID() {
        return ID;
    }

    public String getCisloLV() {
        return cisloLV;
    }


    public String getIDParcela() {
        return IDParcela;
    }

    public String getCasVyhotovenia() {
        return casVyhotovenia;
    }

    public String getDatumVyhotovenia() {
        return datumVyhotovenia;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCisloLV(String cisloLV) {
        this.cisloLV = cisloLV;
    }

    public void setIDParcela(String IDParcela) {
        this.IDParcela = IDParcela;
    }

    public void setDatumVyhotovenia(String datumVyhotovenia) {
        this.datumVyhotovenia = datumVyhotovenia;
    }

    public void setCasVyhotovenia(String casVyhotovenia) {
        this.casVyhotovenia = casVyhotovenia;
    }

    public void setOkresCislo(String okresCislo) {
        this.okresCislo = okresCislo;
    }
    public void setOkresNazov(String okresNazov) {
        this.okresNazov = okresNazov;
    }

    public void setObecCislo(String obecCislo) {
        this.obecCislo = obecCislo;
    }

    public void setKatastralneUzemieCislo(String katastralneUzemieCislo) {
        this.katastralneUzemieCislo = katastralneUzemieCislo;
    }


    public void setKatastralneUzemieNazov(String katastralneUzemieNazov) {
        this.katastralneUzemieNazov = katastralneUzemieNazov;
    }

    public void setPlatnostLV(String platnostLV) {
        this.platnostLV = platnostLV;
    }



    public void setPocetParciel(int pocetParciel) {
        this.pocetParciel = pocetParciel;
    }

    public void setPocetVlastnikov(String pocetVlastnikov) {
        this.pocetVlastnikov = pocetVlastnikov;
    }

    public void setTitulNadobudnutia(List<String> titulNadobudnutia) {
        this.titulNadobudnutia = titulNadobudnutia;
    }

    public void setIneUdaje(List<String> ineUdaje) {
        this.ineUdaje = ineUdaje;
    }

    public void setPoznamky(List<String> poznamky) {
        this.poznamky = poznamky;
    }

    public void setPlomba(List<String> plomba) {
        this.plomba = plomba;
    }

    public void setDatumNarodenia(List<Date> datumNarodenia) {
        this.datumNarodenia = datumNarodenia;
    }



    public void setIDVLastnik(List<Integer> IDVLastnik) {

        this.IDVLastnik = IDVLastnik;
    }

    public void setMenoVlastnika(List<String> menoVlastnika) {

        this.menoVlastnika = menoVlastnika;
    }

    public void setPriezviskoVlastnika(List<String> priezviskoVlastnika) {

        this.priezviskoVlastnika = priezviskoVlastnika;
    }

    public void setRodnePriezviskoVlastnika(List<String> rodnePriezviskoVlastnika) {

        this.rodnePriezviskoVlastnika = rodnePriezviskoVlastnika;
    }


    public void setInyIdentifikator(List<String> inyIdentifikator) {

        this.inyIdentifikator = inyIdentifikator;
    }

    public void setAdresaV(List<String> adresaV) {

        this.adresaV = adresaV;
    }

    public void setICO(List<String> ICO) {

        this.ICO = ICO;
    }

    public void setSpravcaTitulNadobudnutia(List<String> spravcaTitulNadobudnutia) {

        this.spravcaTitulNadobudnutia = spravcaTitulNadobudnutia;
    }

    public void setSpravcaIneUdaje(List<String> spravcaIneUdaje) {

        this.spravcaIneUdaje = spravcaIneUdaje;
    }

    public void setSpravcaPoznamky(List<String> spravcaPoznamky) {

        this.spravcaPoznamky = spravcaPoznamky;
    }

    public void setPocetSpravcov(String pocetSpravcov) {
        this.pocetSpravcov = pocetSpravcov;
    }


    public  ListVlastnictva(String cestaSuboru, String typSuboru) throws TikaException, IOException, SAXException {
        this.typSuboru = typSuboru;

        this.cestaSuboru = cestaSuboru;

        nacitanieTxtSuboru();
        nacitanieVsetkychUdajov();


    }

    Parcela pa = new Parcela();

    private String nacitanieTxtSuboru() {
        BufferedReader br;
        StringBuilder obsah = new StringBuilder();

        try {
            if(typSuboru.equals("txt")) {
                 br = new BufferedReader(new FileReader(cestaSuboru));
            }
          else  if(typSuboru.equals("xlsx")) { br=nacitanieXLSXsuboru();}

            else {br = nacitaniePDFsuboru();}


            String aktualnyRiadok;

            while ((aktualnyRiadok = br.readLine()) != null) {

                //System.out.println("Aktualny riadok skusame "+aktualnyRiadok.trim());
                hladanieJednoduchychUdajov(aktualnyRiadok.trim());

                obsah.append(aktualnyRiadok.trim());
                obsah.append(System.lineSeparator());

            }

        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }
     //  String novyobsah=obsah.toString();
       System.out.println(obsah.toString());
        return obsah.toString();
    }

    private BufferedReader nacitanieXLSXsuboru() throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        File txtLV = new File("C:\\Users\\igoro\\OneDrive\\Desktop\\dp_definitiva\\lv.xlsx");
        FileWriter fw = new FileWriter(txtLV);
        FileInputStream inputstream = new FileInputStream(new File(cestaSuboru));
        ParseContext pcontext = new ParseContext();

        //OOXml parser
        OOXMLParser msofficeparser = new OOXMLParser();
        msofficeparser.parse(inputstream, handler, metadata, pcontext);

        String[] metadataNames = metadata.names();

        Reader inputString = new StringReader(handler.toString());
        BufferedReader reader = new BufferedReader(inputString);
        fw.write(handler.toString());   //zapisanie nacitaneho obsahu z pdf do stringu a nasledne do txt
        fw.close();
        return reader;

}

    private BufferedReader nacitaniePDFsuboru() throws IOException, TikaException, SAXException {

        FileInputStream inputstream = new FileInputStream(new File(cestaSuboru));

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        //parsovanie dokumentu s pouzitim PDF Parsera

        PDFParser pdfparser = new PDFParser();
        PDFParserConfig config = new PDFParserConfig();

        config.setSortByPosition(true); // potrebne na zoradenie textu v urcitom poradi
        pdfparser.setPDFParserConfig(config);
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        //ziskanie obsahu PDF dokumentu
        String pdfToString = handler.toString();
        pdfToString = pdfToString.replaceAll("\\n\\n", "\n");
        Reader inputString = new StringReader(pdfToString);
        BufferedReader reader = new BufferedReader(inputString);

        //ziskanie metadat
        String[] metadataNames = metadata.names();


        for (String name : metadataNames) {
            //  System.out.println(name+ " : " + metadata.get(name));
        }
        return reader;
    }




    private void nacitanieVsetkychUdajov() throws TikaException, IOException, SAXException {
        List<String> titulNadobudnutia;
        List<String> ineUdaje;
        List<String> poznamky;
        List<String> vlastnik;
        List<String> parcela;
        List<String> spravcaTitulNadobudnutia;
        List<String> spravcaIneUdaje;
        List<String> spravca;
        String pomocnaTarchy;
        List<String> spravcaPoznamky;
        String pocetSpravcov = "";
        tarchy ="";
       // List<String> legenda;


    /*    titulNadobudnutia = nacitajUdaje( "Spoluvlastnícky podiel", "Správca", Pattern.compile("Titul nadobudnutia"), Pattern.compile("Iné údaje"));
        ineUdaje = nacitajUdaje("Spoluvlastnícky podiel", "Správca", Pattern.compile("Iné údaje"), Pattern.compile("Poznámky"));
        poznamky = nacitajUdaje("Spoluvlastnícky podiel", "Nájomca", Pattern.compile("Poznámky"), Pattern.compile("(^\\d+\\s+)|(Správca)"));
        vlastnik = nacitajUdaje( "Spoluvlastnícky podiel", "Správca", Pattern.compile("(^\\d+\\s+)"), Pattern.compile("(Titul nadobudnutia)|(Plomba)"));
        spravcaTitulNadobudnutia = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("Titul nadobudnutia"), Pattern.compile("Iné údaje"));
        spravcaIneUdaje = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("Iné údaje"), Pattern.compile("Poznámky"));
        spravcaPoznamky = nacitajUdaje( "Správca", "Iná oprávnená osoba", Pattern.compile("Poznámky"), Pattern.compile("(^\\d+\\s+)|(Nájomca)"));
        //   spravca = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("(^\\d+\\s+)"), Pattern.compile("Titul nadobudnutia"));
        pocetSpravcov = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("(Počet správcov)"), Pattern.compile("Poradové číslo")).toString();

        */




       titulNadobudnutia = nacitajUdaje( "Spoluvlastnícky", "Správca", Pattern.compile("Titul nadobudnutia"), Pattern.compile("Iné údaje"));
       ineUdaje = nacitajUdaje("Spoluvlastnícky", "Správca", Pattern.compile("Iné údaje"), Pattern.compile("Poznámky"));
        poznamky = nacitajUdaje("Spoluvlastnícky", "Nájomca", Pattern.compile("Poznámky"), Pattern.compile("(^\\d+\\s+)|(Správca)"));
        vlastnik = nacitajUdaje( "Spoluvlastnícky", "Správca", Pattern.compile("(^\\d+\\s+)"), Pattern.compile("(Titul nadobudnutia)|(Plomba)"));
        spravcaTitulNadobudnutia = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("Titul nadobudnutia"), Pattern.compile("Iné údaje"));
        spravcaIneUdaje = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("Iné údaje"), Pattern.compile("Poznámky"));
        spravcaPoznamky = nacitajUdaje( "Správca", "Iná oprávnená osoba", Pattern.compile("Poznámky"), Pattern.compile("(^\\d+\\s+)|(Nájomca)"));
        //   spravca = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("(^\\d+\\s+)"), Pattern.compile("Titul nadobudnutia"));
        pocetSpravcov = nacitajUdaje( "Správca", "Nájomca", Pattern.compile("(Počet správcov)"), Pattern.compile("Poradové číslo")).toString();
        parcela = nacitajParcelu( "Parcelné číslo", "Legenda");
        // lv.setLegendaSVP(nacitajUdaje( "Legenda", "Počet vlastníkov", Pattern.compile("Spôsob využívania pozemku"), Pattern.compile("(Spoločná nehnutelnosť)|(Spoločná nehnuteľnosť)")).toString());
      //  legenda = nacitajParcelu( "Legenda", "ČASŤ");
        pomocnaTarchy = nacitajParcelu("ČASŤ C: ŤARCHY","Výpis je nepoužiteľný na právne úkony").toString();
        //  System.out.println(lv.getLegendaSVP());
        tarchyMetoda(pomocnaTarchy);
        datumNarodeniaVlastnika(vlastnik);
        plombaVlastnika(vlastnik);
        podielVlastnika(vlastnik);
        udajeVlastnika(vlastnik);
        adresaVlastnika(vlastnik);
        udajeParcely(parcela);


        //udajeVlastnika(spravca);
      //  udajeLegendy(legenda);

        pocetSpravcov = pocetSpravcov.replace("Počet správcov:", "");
        pocetSpravcov = pocetSpravcov.replace("[", "");
        pocetSpravcov = pocetSpravcov.replace("]", "");

        setPocetSpravcov(pocetSpravcov.trim());
        //  lv.getPocetSpravcov();

        setPoznamky(poznamky);
        // lv.getPoznamky();
        System.out.println("diplomovaPraca/Poznamky " +poznamky);

        setTitulNadobudnutia(titulNadobudnutia);
       // System.out.println("Titul "+titulNadobudnutia);
      //  System.out.println("pocet "+titulNadobudnutia.size());
        // lv.getTitulNadobudnutia();

        setSpravcaTitulNadobudnutia(spravcaTitulNadobudnutia);
        //  lv.getSpravcaTitulNadobudnutia();

      //  setSpravcaIneUdaje(spravcaIneUdaje);
        //  lv.getSpravcaIneUdaje();

        setSpravcaPoznamky(spravcaPoznamky);
        //   lv.getSpravcaPoznamky();


        setIneUdaje(ineUdaje);
        // lv.getIneUdaje();

    }


    private List<String> nacitajParcelu(String zaciatokCyklu, String koniecCyklu) throws IOException, TikaException, SAXException {
/*
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(
                "C:\\Users\\danie\\Desktop\\Škola\\5.rocnik\\diplomovka\\list_vlastnictva.pdf"));

        ParseContext pcontext = new ParseContext();

        //parsovanie dokumentu pouzitim PDF parsera
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        //ziskanie obsahu z dokumentu
        System.out.println("Obsah PDF :" + handler.toString());
        String pdfToString = handler.toString();
        Reader inputString = new StringReader(pdfToString);
        BufferedReader reader = new BufferedReader(inputString);

        //ziskanie metadat z dokumentu
        System.out.println("Metadata :");
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
        /*    String strCurrentLine;
            while ((strCurrentLine = reader.readLine()) != null) {

                System.out.println(strCurrentLine);
            }
            */
        List<String> najdenyZoznam = new ArrayList<>();
        StringBuilder obsah = new StringBuilder();

        BufferedReader br;


        try {
            if(typSuboru.equals("txt")) {
                br = new BufferedReader(new FileReader(cestaSuboru));
            }
            else  if(typSuboru.equals("xlsx"))  br=nacitanieXLSXsuboru();

            else br = nacitaniePDFsuboru();

            String aktualnyRiadok;



            boolean priznak1 = false;
            boolean priznak2 = false;

            while ((aktualnyRiadok = br.readLine()) != null) {
                System.out.println("Riadok "+aktualnyRiadok);
                if (aktualnyRiadok.contains(zaciatokCyklu)) priznak1 = true;
                if (aktualnyRiadok.contains(koniecCyklu)) priznak1 = false;


                if (priznak1) {
                    najdenyZoznam.add(aktualnyRiadok.trim());

                }

            }
       } catch (IOException e) {

           e.printStackTrace();
       }

        System.out.println(najdenyZoznam);

            return najdenyZoznam;


    }




    private List<String> nacitajUdaje(String zaciatokCyklu, String koniecCyklu, Pattern  hladanyVyraz, Pattern koniecVyrazu) {


        StringBuilder nazov = new StringBuilder();
        List<String> najdenyZoznam = new ArrayList<>();
        String novy;
        String pomocnaHladanyVyraz;


        BufferedReader br;
        StringBuilder obsah = new StringBuilder();

        try {
            if(typSuboru.equals("txt")) {
                br = new BufferedReader(new FileReader(cestaSuboru));
            }
            else  if(typSuboru.equals("xlsx"))  br=nacitanieXLSXsuboru();

            else br = nacitaniePDFsuboru();


            String aktualnyRiadok;
            //  novy=koniecVyrazu.toString();
            pomocnaHladanyVyraz = hladanyVyraz.toString();

            boolean priznak1 = false;
            boolean priznak2 = false;

            while ((aktualnyRiadok = br.readLine()) != null) {

                if (aktualnyRiadok.contains(zaciatokCyklu)) priznak1 = true;
                if (aktualnyRiadok.contains(koniecCyklu)) priznak1 = false;


                if (priznak1) {
                    Matcher matcher = hladanyVyraz.matcher(aktualnyRiadok.trim());
                    // if( aktualnyRiadok.contains(pomocnaHladanyVyraz)) {  nazov =  new StringBuilder();   priznak2=true; }
                    if (matcher.find()) {
                        nazov = new StringBuilder();
                        priznak2 = true;
                    }
                    matcher = koniecVyrazu.matcher(aktualnyRiadok.trim());
                    if ((priznak2)&&(matcher.find())) {
                        //  System.out.println("POZNAMKY SU " + matcher.group(0));
                        String pomocna;
                        priznak2 = false;
                        pomocna = nazov.toString().replace(pomocnaHladanyVyraz, "");
                        System.out.println("Pomocna "+pomocna);
                        pomocna = pomocna.trim();    //orezanie medzier pred a po retazci
                        System.out.println("Pomocna "+pomocna);
                        najdenyZoznam.add(pomocna);
                        System.out.println("najdeny zoznam "+najdenyZoznam);


                    }

                    if (priznak2) {

                        nazov.append(aktualnyRiadok.trim());
                        int ssss=111;
                    }

                }

            }


        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }


       System.out.println(najdenyZoznam);
      //  System.out.println("Pocet "+najdenyZoznam.size());

        return najdenyZoznam;
    }


/*
    private void udajeLegendy(List<String> legenda) {
        List<String> pomocna = new ArrayList<>();

        for (int i = 0; i < legenda.size(); i++) {

            Pattern pattern = Pattern.compile("(^\\d+)");

            Matcher matcher = pattern.matcher(legenda.get(i));
            while (matcher.find()) {
                  System.out.println("Celkovo je to " + legenda.get(i));
                pomocna.add(legenda.get(i).trim());

            }


        }

        for (int i = 0; i < pomocna.size(); i++) {

            setLegendaSVP(pomocna.get(0));
            setLegendaSN(pomocna.get(1).replace("Umiestnenie pozemku", ""));
            setLegendaUP(pomocna.get(2));
        }
        // System.out.println(lv.getLegendaSVP());
        //  System.out.println(lv.getLegendaSN());
        //  System.out.println(lv.getLegendaUP());

    }
*/
    private void udajeParcely(List<String> parcela) {
        CKcisloLV = new ArrayList<>();
         parcelneCislo = new ArrayList<>();
         vymera = new ArrayList<>();
         druhPozemku = new ArrayList<>();
         sposobVyuzitia = new ArrayList<>();
         druhCHN = new ArrayList<>();
         spolocnaNehnutelnost = new ArrayList<>();
         umiestneniePozemku = new ArrayList<>();
         druhPV = new ArrayList<>();
         povodneKU = new ArrayList<>();

        for (int i = 0; i < parcela.size(); i++) {
             System.out.println("Parcela je "+parcela.get(i));
            Pattern pattern;
            Matcher matcher;
           //   Pattern pattern = Pattern.compile("(^\\d+)(\\s+\\d+\\s+)(\\s+\\w+|\\s+\\w+\\s+\\w+|\\s+\\w+\\s+\\w+\\s+\\w+\\s+)(\\s+\\d+)(\\s+\\d+)(\\s+\\d+)(\\s+\\d+)(\\s+\\d+)");
            if(typParcely.get(0).equals("C")) {
                 if(typSuboru.equals("xlsx")||typSuboru.equals("txt"))  {  pattern = Pattern.compile("(^\\d+.*)(\\s+\\d+\\s+)(\\s+.+\\s+)(\\s+\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)");}

              else {
                    pattern = Pattern.compile("(^\\d+\\s+)(\\d+\\s+)(.+)");

                    }


                 matcher = pattern.matcher(parcela.get(i));
                while (matcher.find()) {
                    System.out.println("Celkovo je to " + matcher.group(0));

                    parcelneCislo.add(matcher.group(1).trim());
                    vymera.add(matcher.group(2).trim());
                    druhPozemku.add(matcher.group(3).trim());

                    if(typSuboru.equals("pdf")) {
                        sposobVyuzitia.add(null);
                        druhCHN.add(null);
                        spolocnaNehnutelnost.add(null);
                        umiestneniePozemku.add(null);
                        druhPV.add(null);
                        povodneKU.add(null);

                    }
                    else {
                    sposobVyuzitia.add(matcher.group(4).trim());
                    if (matcher.group(5) != null) druhCHN.add(matcher.group(5).trim());
                    spolocnaNehnutelnost.add(matcher.group(6).trim());
                    umiestneniePozemku.add(matcher.group(7).trim());
                    if (matcher.group(8) != null) druhPV.add(matcher.group(8).trim());
                    povodneKU.add(null);
                }}
                // System.out.println(CKcisloLV);
                CKcisloLV.add(cisloLV);

            }
            else if(typParcely.get(0).equals("E"))  {
                 pattern = Pattern.compile("(^\\d+.*)(\\s+\\d+\\s+)(\\s+.+\\s+)(.\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)(.\\d+|\\s+\\S+.)");
                if(typSuboru.equals("pdf"))  { pattern = Pattern.compile("(^\\d+.*)(\\s+\\d+\\s+)(\\s+.+\\s+)(.\\d+|\\s+\\S+.|\\s+)(.\\d+|\\s+\\S+.|\\s+)(.\\d+|\\s+\\S+.|\\s+)");}

                 matcher = pattern.matcher(parcela.get(i));
                while (matcher.find()) {
                    System.out.println("Celkovo je to " + matcher.group(0));

                    parcelneCislo.add(matcher.group(1).trim());
                    vymera.add(matcher.group(2).trim());
                    druhPozemku.add(matcher.group(3).trim());
                    povodneKU.add(matcher.group(4).trim());
                    spolocnaNehnutelnost.add(matcher.group(5).trim());
                    umiestneniePozemku.add(matcher.group(6).trim());
                    sposobVyuzitia.add(null);
                    druhCHN.add(null);
                    druhPV.add(null);
                }
                // System.out.println(CKcisloLV);


                CKcisloLV.add(cisloLV);


            }
        }
        for (int i = 0; i < parcela.size()-1; i++) {typParcely.add(typParcely.get(0));}
    }


    private void udajeVlastnika(List<String> vlastnik) {
        List<Integer> idVlastnik = new ArrayList<>();
        List<String> menoVlastnik = new ArrayList<>();
        List<String> priezviskoVlastnik = new ArrayList<>();
        List<String> rodnePriezvisko = new ArrayList<>();
        List<String> inyIdentifikator = new ArrayList<>();

        for (int i = 0; i < vlastnik.size(); i++) {

            Pattern pattern = Pattern.compile("(^\\d+.)(\\S+\\s+)(\\S+\\s+)(r\\.\\s+\\S+)?(\\(.+\\))?");

            Matcher matcher = pattern.matcher(vlastnik.get(i));
            while (matcher.find()) {
                idVlastnik.add(Integer.parseInt(matcher.group(1).trim()));
                priezviskoVlastnik.add(matcher.group(2).replace('\"', ' ').trim());
                menoVlastnik.add(matcher.group(3).trim());
                if (matcher.group(4) != null) rodnePriezvisko.add(matcher.group(4).replace(",", "").trim());
                else rodnePriezvisko.add(null);
                if (matcher.group(5) != null) inyIdentifikator.add(matcher.group(5));
                else inyIdentifikator.add(null);


                // if(matcher.group(5)!=null) plomba.add(matcher.group(5).trim());
                //   else  plomba.add("Bez plomby");
                // plomba.add(matcher.group(6).trim());
                // System.out.println("Celkovo je to "+matcher.group(0));
                // System.out.println("Group 1 is "+matcher.group(1)); //IDvlastnika
                //  System.out.println("Group 2 is "+matcher.group(2));  //priezvisko
                //  System.out.println("Group 3 is "+matcher.group(3));  //meno
                //  System.out.println("Group 4 is "+matcher.group(4));  //rodne priezvisko
                //System.out.println("Group 5 is "+matcher.group(5));  //iny identifikacny udaj
                //  System.out.println("Group 6 is "+matcher.group());  //
            }

        }

        setInyIdentifikator(inyIdentifikator);
        //lv.getInyIdentifikator();

        setRodnePriezviskoVlastnika(rodnePriezvisko);
        //lv.getRodnePriezviskoVlastnika();

        setMenoVlastnika(menoVlastnik);
        //  lv.getMenoVlastnika();

        setPriezviskoVlastnika(priezviskoVlastnik);
        //   lv.getPriezviskoVlastnika();

        setIDVLastnik(idVlastnik);
        //  lv.getIDVlastnik();
    }


    private void podielVlastnika(List<String> vlastnik) {
        List<String> podiel = new ArrayList<>();

        for (int i = 0; i < vlastnik.size(); i++) {

            //  System.out.println("diplomovaPraca.Vlastnik je "+vlastnik.get(i));
            // Pattern pattern = Pattern.compile("(\\s+\\d+/\\d+\\s+)");
           // Pattern pattern = Pattern.compile("(\\s+\\d+/\\d+$)|(\\s+\\d+/\\d+\\s+)");
             Pattern pattern = Pattern.compile("(\\s+\\d+)/(\\d+$)");

            Matcher matcher = pattern.matcher(vlastnik.get(i));
            while (matcher.find()) {

                //podiel.add(matcher.group(0).trim());
                podielCitatel.add(Integer.parseInt(matcher.group(1).trim()));
                novyPodielCitatel.add(Integer.parseInt(matcher.group(1).trim()));
                podielMenovatel.add(Integer.parseInt(matcher.group(2).trim()));
                novyPodielMenovatel.add(Integer.parseInt(matcher.group(2).trim()));
                akcia.add("0");
                // System.out.println(matcher.group(0));
            }

        }



/* NEFUNGUJE KOD NA ROZDELENIE PODIELOV NA CITATEL A MENOVATEL  ********************************************************

List<diplomovaPraca.Podiel> podiely = new ArrayList<>();
String citatel="";
String menovatel="";
    for(int i=0;i<podiel.size();i++){

        Pattern pattern = Pattern.compile("((^\\d+)/(\\d+$))");
        Matcher matcher = pattern.matcher(podiel.get(i));
        while (matcher.find()) {
            citatel=matcher.group(2).toString();
            menovatel=matcher.group(3).toString();
          //podiely.add(new diplomovaPraca.Podiel(Integer.parseInt(matcher.group(2)),Integer.parseInt(matcher.group(3))));
            podiely.add(new diplomovaPraca.Podiel(citatel,menovatel));

            // System.out.println(Integer.parseInt(matcher.group(2)));

          //   System.out.println("Celkovo je to "+matcher.group(0));
          //  System.out.println("Group 1 is "+matcher.group(1));
          //  System.out.println("Group 2 is "+matcher.group(2));  //citatel
          //  System.out.println("Group 3 is "+matcher.group(3));  //menovatel
        }

    }

   System.out.println(Arrays.toString(diplomovaPraca.Podiel[]podiely);
//lv.setPodiely(podiely);
//    lv.getPodiely();
************************************************************************************************************************/
    }

    private void plombaVlastnika(List<String> vlastnik) {
        List<String> plomba = new ArrayList<>();

        for (int i = 0; i < vlastnik.size(); i++) {

            // System.out.println("diplomovaPraca.Vlastnik je "+vlastnik.get(i));
            Pattern pattern = Pattern.compile("(Plomba\\s+vyznačená\\s+na\\s+základe\\s+.+Kúpna zmluva.+)");
            Matcher matcher = pattern.matcher(vlastnik.get(i));

                if (matcher.find()) {
                    // System.out.println(matcher.group(0));

                    plomba.add(matcher.group(0).trim());
                }
                else plomba.add(null);

        }

        setPlomba(plomba);
        // lv.getPlomba();

    }


    private void adresaVlastnika(List<String> vlastnik) {
        List<String> adresaV = new ArrayList<>();
        List<String> pomocna = new ArrayList<>();
        List<String> ICO = new ArrayList<>();

        for (int i = 0; i < vlastnik.size(); i++) {

            // System.out.println("Vlastnik je "+vlastnik.get(i));
            Pattern pattern = Pattern.compile("(,.+)(Dátum|IČO:\\s+\\d+)");


            Matcher matcher = pattern.matcher(vlastnik.get(i));

            if (matcher.find()) {
                // System.out.println(matcher.group(2));
                if (matcher.group(1) != null) {

                    pomocna.add(matcher.group(1).replaceAll("(^.+\\))", ""));

                } else adresaV.add(null);
                if (matcher.group(2).toString().contains("IČO"))
                    ICO.add(matcher.group(2).replace("IČO:", "").trim());
                else ICO.add(null);
            }


        }

        for (int i = 0; i < pomocna.size(); i++) {
            adresaV.add(pomocna.get(i).replace(",", "").trim());

        }
        setICO(ICO);
        //  lv.getICO();


        setAdresaV(adresaV);
        getAdresaV();

    }


    private void datumNarodeniaVlastnika(List<String> vlastnik) {
        List<Date> datumNarodenia = new ArrayList<>();
        String novy = "";
        for (int i = 0; i < vlastnik.size(); i++) {

            Pattern pattern = Pattern.compile("(Dátum narodenia:.+)" +
                    "([0-3][0-9]\\.[0-1][0-9]\\.(?:[0-9]{2})?[0-9]{2}.+?)");
            Matcher matcher = pattern.matcher(vlastnik.get(i));

            if (matcher.find()) {
            novy = matcher.group(2).trim();
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date date;
                try {
                    date = dateFormat.parse(novy);
                    datumNarodenia.add(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {

                datumNarodenia.add(null);
            }


          //  System.out.println("AAAAAAAAAAAAAAAAAAAADatum je "+datumNarodenia);
                setDatumNarodenia(datumNarodenia);
                // lv.getDatumNarodenia();
            }



    }

private void tarchyMetoda(String tarchyPomocna){


                Pattern pattern = Pattern.compile("(^.+Obsah,*\\s*-*\\s+)(\\S+.+)");
                Matcher matcher = pattern.matcher(tarchyPomocna);
                if (matcher.find()) {
                    tarchy = matcher.group(2);
                    tarchy= tarchy.trim();
                    tarchy=tarchy.replaceAll("\\]","");
                    tarchy=tarchy.replaceAll("\\[","");
                    System.out.println("OFICIALNE TARCHY "+tarchy);

                }
                else {
                     pattern = Pattern.compile("(Bez tiarch.)(.+)");
                     matcher = pattern.matcher(tarchyPomocna);
                    if (matcher.find()) {
                    tarchy = matcher.group(1);
                    tarchy= tarchy.trim();
                    System.out.println("OFICIALNE TARCHY "+tarchy);

    }}

        }




    private void hladanieJednoduchychUdajov(String Riadok) {


        if (Riadok.contains("Čas vyhotovenia")) {
            Pattern pattern = Pattern.compile("((?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d))");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                   System.out.println("Cas vyhotovenia "+matcher.group(0));
                setCasVyhotovenia(matcher.group(0));
                //  System.out.println("Getter čas vyhotovenia je ulozeny ako " + lv.getCasVyhotovenia());

            }
        }
        if (Riadok.contains("Dátum vyhotovenia")) {
            Pattern pattern = Pattern.compile("((?:\\d{1,}).(?:\\d{1,}).(?:\\d{4}))");
           // Pattern pattern = Pattern.compile("(.*\\d{1,})(.{1})(\\d{1,})(.{1})(d{4}.*)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                System.out.println("Datum vyhotovenia "+matcher.group(0));
                setDatumVyhotovenia(matcher.group(0));
                // System.out.println("Getter datum vyhotovenia je ulozeny ako " + lv.getDatumVyhotovenia());

            }
        }


        if (Riadok.contains("Okres")) {
            Pattern pattern = Pattern.compile("(Okres.+)(\\s+\\d+\\s+)(\\s*\\S+\\s+)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                System.out.println("Okres  "+matcher.group(0));
                setOkresCislo(matcher.group(2).trim());
                setOkresNazov(matcher.group(3).trim());
                //   System.out.println("Getter cislo obce je ulozeny ako " + lv.getObecNazov());

            }
        }



        if (Riadok.contains("Obec")) {
            Pattern pattern = Pattern.compile("(Obec.+)(\\s+\\d+\\s+)(\\s*\\S+\\s+)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                System.out.println("Obec vyhotovenia "+matcher.group(2));
                setObecCislo(matcher.group(2).trim());
                setObecNazov(matcher.group(3).trim());
                //   System.out.println("Getter cislo obce je ulozeny ako " + lv.getObecNazov());

            }
        }


        if (Riadok.contains("Katastrálne územie")) {

            Pattern pattern = Pattern.compile("(Katastrálne územie.+)(\\s+\\d+\\s*)(\\s+\\S+\\s+)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                System.out.println("Katastralne uzmenie "+matcher.group(0));
                setKatastralneUzemieCislo(matcher.group(2).trim());
                setKatastralneUzemieNazov(matcher.group(3).trim());
                // System.out.println("Getter cislo katastralneho uzemia je ulozeny ako " + lv.getKatastralneUzemieCislo());

            }
        }

        if (Riadok.contains("VÝPIS Z LISTU VLASTNÍCTVA č.")) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                //  System.out.println(matcher.group(0));
                setCisloLV(matcher.group(0));
                //  System.out.println("Getter cislo listu vlastnictva je ulozeny ako " + lv.getCisloLV());

            }
        }

        if (Riadok.contains("Údaje platné k")) {
            // Pattern pattern = Pattern.compile("((?:\\d{1,}).(?:\\d{1,}).(?:\\d{4}) (?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d))");
            Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d{4})(\\s+\\d+:\\d+:\\d+)");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                //   System.out.println(matcher.group(0).trim());
                setPlatnostLV(matcher.group(0).trim());
                //  System.out.println("Getter platnost listu vlastnictva je ulozeny ako " + lv.getPlatnostLV());

            }
        }


        if (Riadok.contains("Parcely registra")) {
            Pattern pattern = Pattern.compile("([C,E])");              //parcely su typu C alebo E
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                //  System.out.println(matcher.group(0));
                typParcely.add(matcher.group(0));
                // System.out.println("Getter typ parcely je ulozeny ako " + lv.getTypParcely());

            }
        }

        if (Riadok.contains("Počet parciel")) {
            Pattern pattern = Pattern.compile("(?:\\d{1,})");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                //  System.out.println(matcher.group(0));
                setPocetParciel(Integer.parseInt(matcher.group(0)));
                //    System.out.println("Getter pocet parciel na jednom liste vlastnictva je ulozeny ako " + lv.getPocetParciel());

            }
        }

        if (Riadok.contains("Počet vlastníkov")) {
            Pattern pattern = Pattern.compile("(?:\\d{1,})");
            Matcher matcher = pattern.matcher(Riadok);
            while (matcher.find()) {
                // System.out.println(matcher.group(0));
                setPocetVlastnikov(matcher.group(0));
                //   System.out.println("Getter pocet vlastnikov na jednom liste vlastnictva je ulozeny ako " + lv.getPocetVlastnikov());

            }
        }


    }

}





