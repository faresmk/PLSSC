import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
//PLSSC : plus longue sous-chaîne commune
public class RecherchePLSSC {

    // Recherche d'une PLSSC de 2 chaînes, naif: algorithme de enumeration recursive
    static String PLSSC(String S1, String S2) {
        int len1 = S1.length();
        int len2 = S2.length();
        if (len1 == 0 || len2 == 0) {
            return "";
        } else if (S1.charAt(len1 - 1) == S2.charAt(len2 - 1)) {

            return  PLSSC(S1.substring(0, len1 - 1), S2.substring(0, len2 - 1)) + S1.charAt(len1 - 1);
        } else {
            // tester tous les cas possibles
            String s1 = PLSSC(S1, S2.substring(0, len2 - 1));
            String s2 = PLSSC(S1.substring(0, len1 - 1), S2);
            return (s1.length() > s2.length()) ? s1 : s2; // expression ternaire : si s1 > s2 alors s1 sinon s2
        } 
    }

    // Recherche d'une PLSSC de 2 chaînes, programmation dynamique.
    static String PLSSC_PD(String S1, String S2) {
        int len1 = S1.length();
        int len2 = S2.length();
        int[][] tab = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    tab[i][j] = 0;
                } else if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
                    tab[i][j] = tab[i - 1][j - 1] + 1;
                } else {
                    tab[i][j] = Math.max(tab[i - 1][j], tab[i][j - 1]);
                }
            }
        }
        String resultat = "";
        while (len1 > 0 && len2 > 0) {
            if (S1.charAt(len1 - 1) == S2.charAt(len2 - 1)) {
                resultat = S1.charAt(len1 - 1) + resultat;
                len1--;
                len2--;
            } else if (tab[len1 - 1][len2] > tab[len1][len2 - 1]) {
                len1--;
            } else {
                len2--;
            }
        }
        return resultat;
    }


    public static void main(String args[]) {

        String S1;
        String S2;

        FileInputStream input;
        BufferedReader reader;

        for (int i = 0; i < args.length; i++) {
            try {
                // Ouverture du fichier passé en argument
                input = new FileInputStream(args[i]);
                reader = new BufferedReader(new InputStreamReader(input));

                // Lecture de S1
                S1 = reader.readLine();
                // Lecture S2
                S2 = reader.readLine();

                // date de début
                long startTime = System.nanoTime();
                //String result1 = PLSSC(S1,S2);
                String result2 = PLSSC_PD(S1,S2);

                // date de fin pour le calcul du temps écoulé
                long endTime = System.nanoTime();

                //System.out.println("PLSSC: " + result1);
                System.out.println("PLSSC_PD: " + result2);

                // Impression de la longueur du S1 de S2 et du temps d'exécution
                System.out.println(S1.length() + "\t" + S2.length() + "\t" + ((endTime - startTime)/1.0E9));

            } catch (FileNotFoundException e) {
                System.err.println("Erreur lors de l'ouverture du fichier " + args[i]);
            } catch (IOException e) {
                System.err.println("Erreur de lecture dans le fichier");
            }
        }
    }
}



