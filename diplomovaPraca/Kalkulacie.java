package diplomovaPraca;

import java.util.ArrayList;
import java.util.List;

public class Kalkulacie {


    public List<Integer> sucetZlomkov(int citatelPrvy,int menovatelPrvy,int citatelDruhy,int menovatelDruhy){
        long citatelVysledok = (long)citatelPrvy*(long)menovatelDruhy + (long)citatelDruhy*(long)menovatelPrvy;
        long menovatelVysledok = (long)menovatelPrvy*(long)menovatelDruhy;

       return   zakladnyTvarZlomku( citatelVysledok,menovatelVysledok);
    }

    public List<Integer> rozdielZlomkov(int citatelPrvy,int menovatelPrvy,int citatelDruhy,int menovatelDruhy){
        long citatelVysledok = (long)citatelPrvy*(long)menovatelDruhy - (long)citatelDruhy*(long)menovatelPrvy;
        long menovatelVysledok = (long)menovatelPrvy*(long)menovatelDruhy;

        return   zakladnyTvarZlomku(citatelVysledok,menovatelVysledok);
    }

    public List<Integer> sucinZlomkov(int citatelPrvy,int menovatelPrvy,int citatelDruhy,int menovatelDruhy){
        long citatelVysledok =(long) citatelPrvy*(long)citatelDruhy;
        long menovatelVysledok = (long)menovatelPrvy*(long)menovatelDruhy;

        return  zakladnyTvarZlomku(citatelVysledok,menovatelVysledok);

    }

    public List<Integer> podielZlomkov(int citatelPrvy,int menovatelPrvy,int citatelDruhy,int menovatelDruhy){
        long citatelVysledok = (long)citatelPrvy*(long)menovatelDruhy;
        long menovatelVysledok = (long)menovatelPrvy*(long)citatelDruhy;

        return    zakladnyTvarZlomku(citatelVysledok,menovatelVysledok);
    }

    public List<Integer> zakladnyTvarZlomku(long citatelPrvy,long menovatelPrvy){
        List<Integer> zlomokZakladnyTvar = new ArrayList<>();
        long mensieCislo;
        long delitel=1;
        System.out.println("Zakladny tvar VSTUP CP "+citatelPrvy+" MP "+menovatelPrvy);
        if(citatelPrvy<menovatelPrvy) mensieCislo = citatelPrvy;
        else mensieCislo = menovatelPrvy;

        /*for(long i=1;i<=mensieCislo;i++){
            if((citatelPrvy%i==0) && (menovatelPrvy%i==0)) {delitel = i;}
        }
*/
        for(long i=mensieCislo;i>1;i--){
            if((citatelPrvy%i==0)) {if(menovatelPrvy%i==0){
                delitel = i;
                break;
            }
            }
        }
         long citatelVysledok =citatelPrvy/delitel;
         long menovatelVysledok =menovatelPrvy/delitel;
        zlomokZakladnyTvar.add((int)citatelVysledok);
        zlomokZakladnyTvar.add((int)menovatelVysledok);
        System.out.println("Zakladny tvar VYSTUP  CV "+citatelVysledok+" MV "+menovatelVysledok);
        return zlomokZakladnyTvar;
    }




}
