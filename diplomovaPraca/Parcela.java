package diplomovaPraca;

import java.util.ArrayList;
import java.util.List;

public class Parcela {

    private List<String> parcelneCislo = new ArrayList<>();
    private List<String> vymera = new ArrayList<>();
    private List<String> druhPozemku = new ArrayList<>();
    private List<String> sposobVyuzitia = new ArrayList<>();
    private List<String> druhCHN = new ArrayList<>();
    private List<String> spolocnaNehnutelnost = new ArrayList<>();
    private List<String> umiestnenie = new ArrayList<>();
    private List<String> druhPV = new ArrayList<>();


    public List<String> getParcelneCislo() {
        for (int i=0; i<parcelneCislo.size();i++)
            System.out.println(parcelneCislo.get(i));
        return parcelneCislo;
    }

    public List<String> getVymera() {
        for (int i=0; i<vymera.size();i++)
            System.out.println(vymera.get(i));
        return vymera;
    }

    public List<String> getDruhPozemku() {
            for (int i=0; i<druhPozemku.size();i++)
                System.out.println(druhPozemku.get(i));
        return druhPozemku;
    }

    public List<String> getSposobVyuzitia() {
        for (int i=0; i<sposobVyuzitia.size();i++)
            System.out.println(sposobVyuzitia.get(i));
        return sposobVyuzitia;
    }

    public List<String> getDruhCHN() {
        for (int i=0; i<druhCHN.size();i++)
            System.out.println(druhCHN.get(i));
        return druhCHN;
    }

    public List<String> getSpolocnaNehnutelnost() {
        for (int i=0; i<spolocnaNehnutelnost.size();i++)
            System.out.println(spolocnaNehnutelnost.get(i));
        return spolocnaNehnutelnost;
    }

    public List<String> getUmiestnenie() {
        for (int i=0; i<umiestnenie.size();i++)
            System.out.println(umiestnenie.get(i));
        return umiestnenie;
    }

    public List<String> getDruhPV() {
        for (int i=0; i<druhPV.size();i++)
            System.out.println(druhPV.get(i));
        return druhPV;
    }


    public void setParcelneCislo(List<String> parcelneCislo) {
        this.parcelneCislo = parcelneCislo;
    }

    public void setVymera(List<String> vymera) {
        this.vymera = vymera;
    }

    public void setDruhPozemku(List<String> druhPozemku) {
        this.druhPozemku = druhPozemku;
    }

    public void setSposobVyuzitia(List<String> sposobVyuzitia) {
        this.sposobVyuzitia = sposobVyuzitia;
    }

    public void setDruhCHN(List<String> druhCHN) {
        this.druhCHN = druhCHN;
    }

    public void setSpolocnaNehnutelnost(List<String> spolocnaNehnutelnost) {
        this.spolocnaNehnutelnost = spolocnaNehnutelnost;
    }

    public void setUmiestnenie(List<String> umiestnenie) {
        this.umiestnenie = umiestnenie;
    }

    public void setDruhPV(List<String> druhPV) {
        this.druhPV = druhPV;
    }
}