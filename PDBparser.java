package proteinresearch;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDBparser {

       public static void main(String[] args)  {
        // TODO code application logic here
// Can  handle files with multiple model , only considers first
// need to modify code to parse files with multiple model when to consider more than one model
        ////////////// Directories here/////////////////

        int numOfStructFound=0;
        int numOfStructParsed=0;
        int numOfStructSkipped=0;
        Date begin=new Date();
        
        System.out.println("starts on:   "+begin.toString());
        File logf=new File("E:\\Thesis\\scopdataExperiment\\log3.txt");
        PrintWriter pl=null;
        try {
            pl = new PrintWriter(logf);
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(PDBparser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        
        pl.println("starts on:   "+begin.toString());
        pl.flush();
        
        String path = "";

        File inf = new File("E:\\Thesis\\scopdataExperiment\\pdb files\\pdbstyle-2.03-3");
        File errf = new File("E:\\Thesis\\scopdataExperiment\\bigstructureList-3.txt");
        PrintWriter pe=null;
        try {
            pe = new PrintWriter(errf);
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(PDBparser.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("errur File error");
            System.out.println(ex.toString());
        }
        
        
        System.out.println(inf.canRead());

        double x[], y[], z[];

        Scanner sc=null;
        try {
            sc = new Scanner(inf);
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(PDBparser.class.getName()).log(Level.SEVERE, null, ex);
        
            
            System.out.println("INput File error");
            System.out.println(ex.toString());
        }
        String line = null;
        int limit = 0;
        int numOfCAatom = 0;
        int maxCAAtoms = 3000;
        int noQuantLevel = 255;
        x = new double[maxCAAtoms];
        y = new double[maxCAAtoms];
        z = new double[maxCAAtoms];
        int[][] mat = new int[maxCAAtoms][maxCAAtoms];
        double[][] matd = new double[maxCAAtoms][maxCAAtoms];
        String sid = null;
        int sunid = 0;
        int seqNo = 0;
        double maxDistance = -1;

        File sunidf = new File("E:\\Thesis\\scopdataExperiment\\sunid\\sunidList-3.ent");
        PrintWriter ps=null;

        
        try {
            ps = new PrintWriter(sunidf);
            //Scanner datasetsc=new Scanner(sunidf);
            //Scanner datasetsc=new Scanner(sunidf);
        } catch (FileNotFoundException ex) {
          //  Logger.getLogger(PDBparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        while (sc.hasNext()) {

            line = sc.nextLine().toString();
            StringTokenizer strTok = new StringTokenizer(line, " ");
            /*
            if (++limit < 10000) {
            System.out.println(line);
            } else {
            break;
            }
             */
            int numOfTokens = 0;
            numOfTokens = strTok.countTokens();
            String tokens[] = new String[numOfTokens];
            int tokenid = 0;
            while (strTok.hasMoreTokens()) {
                tokens[ tokenid++] = strTok.nextToken();
            }

            if ((numOfTokens > 0) && (tokens[0].toString().equalsIgnoreCase("END")||tokens[0].toString().equalsIgnoreCase("ENDMDL"))) {

                numOfStructFound++;
                numOfStructParsed++;
                ps.println(sid + "\t" + "\t" + sunid);
                ps.flush();
                
                //System.out.println("END FOUND");
                for (int i = 0; i < numOfCAatom; i++) {
                    for (int j = 0; j < numOfCAatom; j++) {
                        if (i == j) {
                            matd[i][i] = 0;
                        }
                        matd[i][j] = Math.sqrt((x[j] - x[i]) * (x[j] - x[i]) + (y[j] - y[i]) * (y[j] - y[i]) + (z[j] - z[i]) * (z[j] - z[i]));

                        maxDistance = Math.max(maxDistance, matd[i][j]);
                    }

                }


                for (int i = 0; i < numOfCAatom; i++) {
                    for (int j = 0; j < numOfCAatom; j++) {
                        if (maxDistance != 0) {
                            mat[i][j] = (int) (matd[i][j] * noQuantLevel / maxDistance);
                        } else {
                            System.out.println("Max distance is zero");
                        }
                    }
                }


                File outf = new File("E:\\Thesis\\scopdataExperiment\\acmat255level-3rest\\" + sid + ".ent");
                PrintWriter pw=null;
                try {
                    pw = new PrintWriter(outf);
                } catch (FileNotFoundException ex) {
                  //  Logger.getLogger(PDBparser.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                for (int i = 0; i < numOfCAatom; i++) {
                    for (int j = 0; j < numOfCAatom; j++) {
                        pw.write(mat[i][j] + "\t");
                        pw.flush();
                    }
                    pw.println();
                    pw.flush();
                }
                pw.checkError();
                pw.close();




                numOfCAatom = 0;
                seqNo = 0;
                maxDistance = -1;
                
                
            if ((numOfTokens > 0) && (tokens[0].toString().equalsIgnoreCase("ENDMDL"))) {

                while (sc.hasNext()) {
                            line = sc.nextLine().toString();
                            strTok = new StringTokenizer(line, " ");
                            numOfTokens = strTok.countTokens();
                            String tokenss[] = new String[numOfTokens];
                            int tokenidd = 0;
                            while (strTok.hasMoreTokens()) {
                                tokenss[ tokenidd++] = strTok.nextToken();
                            }

                            
                            if ((numOfTokens > 0) && (tokenss[0].toString().equalsIgnoreCase("END"))) 
                                break;
                        }
                
            }
                
                
                
                continue;
            }

            if ((numOfTokens > 4) && (tokens[0].toString().equalsIgnoreCase("REMARK") && tokens[3].toString().equalsIgnoreCase("SCOPe-sid:"))) {
            
                sid = tokens[4].toString(); //System.out.println(sid);
               
                File oldf=new File("E:\\Thesis\\scopdataExperiment\\acmat255level\\" + sid + ".ent");
                
                if(oldf.exists()){
                ////already exists, skip up to end
                
                    System.out.println(sid+"\t"+"already exists, so skipped");
                
                    
                    while (sc.hasNext()) {
                            line = sc.nextLine().toString();
                            strTok = new StringTokenizer(line, " ");
                            numOfTokens = strTok.countTokens();
                            String tokenss[] = new String[numOfTokens];
                            int tokenidd = 0;
                            while (strTok.hasMoreTokens()) {
                                tokenss[ tokenidd++] = strTok.nextToken();
                            }
                            if ((numOfTokens > 0) && (tokenss[0].toString().equalsIgnoreCase("END"))) 
                                break;
                            }
                   continue;
                }

                
            }

            if ((numOfTokens > 4) && (tokens[0].toString().equalsIgnoreCase("REMARK") && tokens[3].toString().equalsIgnoreCase("SCOPe-sun:"))) {
                sunid = Integer.parseInt(tokens[4]);
                
                //  System.out.println(sunid);
            }

                
            if (numOfTokens > 9 && tokens[0].toString().equalsIgnoreCase("ATOM") && tokens[2].toString().equalsIgnoreCase("CA")) {
                
                try{
                if (numOfTokens >= 12) {
                    x[seqNo] = Double.parseDouble(tokens[ 6]);
                    y[seqNo] = Double.parseDouble(tokens[ 7]);
                    z[seqNo] = Double.parseDouble(tokens[ 8]);
                }
                if (numOfTokens < 12) {
                    if (tokens[6].substring(1).contains("-")) {
                        int xendsAt = tokens[6].indexOf("-", 1);
                        x[seqNo] = Double.parseDouble(tokens[6].substring(0, xendsAt));

                        if (tokens[6].substring(xendsAt + 1).contains("-")) {
                            int yendsAt = tokens[6].indexOf("-", xendsAt + 1);
                            y[seqNo] = Double.parseDouble(tokens[6].substring(xendsAt + 1, yendsAt));
                            z[seqNo] = Double.parseDouble(tokens[6].substring(yendsAt + 1));
                        } else {
                            y[seqNo] = Double.parseDouble(tokens[6].substring(xendsAt + 1));
                            z[seqNo] = Double.parseDouble(tokens[7]);
                        }
                    } else {
                        x[seqNo] = Double.parseDouble(tokens[ 6]);

                        if (tokens[7].substring(1).contains("-")) {
                            int yendsAt = tokens[7].indexOf("-", 1);
                            y[seqNo] = Double.parseDouble(tokens[7].substring(0, yendsAt));
                            z[seqNo] = Double.parseDouble(tokens[7].substring(yendsAt + 1));
                        }
                    }
                }

                //  System.out.println("seq No   "+seqNo);
                seqNo++;
                numOfCAatom++;
                }catch(NumberFormatException nfe){
                
                    System.out.println(sid+"\t\t"+sunid);
                    System.out.println(nfe.toString());
                    
                    ps.println(sid + "\t" + "\t" + sunid);
                    ps.flush();
                    
                    pl.println(sid + "\t" + "\t" + sunid+":\t" +"skipped");
                    pl.flush();
                    pl.println(nfe.toString());
                    pl.flush();
                    pl.println();
                    pl.flush();
                    
                    seqNo=0;
                    numOfCAatom=0;
                    numOfStructFound++;
                    numOfStructSkipped++;
                    ////////////skip excepted ///////////////
                    
                    
                    while (sc.hasNext()) {
                            line = sc.nextLine().toString();
                            strTok = new StringTokenizer(line, " ");
                            numOfTokens = strTok.countTokens();
                            String tokenss[] = new String[numOfTokens];
                            int tokenidd = 0;
                            while (strTok.hasMoreTokens()) {
                                tokenss[ tokenidd++] = strTok.nextToken();
                            }
                            if ((numOfTokens > 0) && (tokenss[0].toString().equalsIgnoreCase("END"))) 
                                break;
                            }
                }
                
            }
            
            
                if (numOfCAatom >= maxCAAtoms - 1 || seqNo >= maxCAAtoms - 1) {
                        System.out.println("number of CA seq No     :   " + seqNo);
                        System.out.println(sid);
                        pe.write("number of CA seq No     :   " + seqNo + "\t" + sid + "\t" + sunid);
                        pe.flush();
                        pe.println();
                        pe.flush();
                        
                        System.out.println("will be skipped");
                        numOfStructSkipped++;
                        numOfStructFound++;

                        numOfCAatom=0;
                        seqNo=0;
                        
                        while (sc.hasNext()) {
                            line = sc.nextLine().toString();
                            strTok = new StringTokenizer(line, " ");
                            numOfTokens = strTok.countTokens();
                            String tokenss[] = new String[numOfTokens];
                            int tokenidd = 0;
                            while (strTok.hasMoreTokens()) {
                                tokenss[ tokenidd++] = strTok.nextToken();
                            }

                            
                            if ((numOfTokens > 0) && (tokenss[0].toString().equalsIgnoreCase("END"))) 
                                break;
                        }

                    }
                

        }
        ps.checkError();
        ps.close();
        pe.checkError();
        pe.close();
        
        Date end=new Date();
        
        System.out.println("ends on:   "+end.toString());
        pl.println("ends on:   "+end.toString());
        pl.flush();
       // pl.println("Time taken:"+"\t"+"\t"+"");
        
        pl.println("number of structure found:"+"\t"+"\t"+numOfStructFound);
        pl.flush();
        pl.println("number of structure parsed:"+"\t"+"\t"+numOfStructParsed);
        pl.flush();
        pl.println("number of structure skipped:"+"\t"+"\t"+numOfStructSkipped);
        pl.flush();
        
       pl.checkError();
       pl.close();
        
        
    }

}
