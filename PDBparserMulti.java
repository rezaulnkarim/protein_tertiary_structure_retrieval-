/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proteinresearch;



import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;




/**
 *
 * @author rezaul karim
 */
public class PDBparserMulti {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        // TODO code application logic here
// Can  handle files with multiple model , only considers first
// need to modify code to parse files with multiple model when to consider more than one model
        ////////////// Directories here/////////////////

        String path = "";

        path="E:\\Thesis\\data\\pdb files";
        File pdbDir = new File( path );

        path="E:\\Thesis\\data\\ca coordinates";
        File cacoordDir = new File( path );


        path="E:\\Thesis\\data\\camat";
        File camatDir = new File( path );

        path="E:\\Thesis\\data\\camatq1";
        File camatq1Dir = new File( path );

        path="E:\\Thesis\\data\\camatq2";
        File camatq2Dir = new File( path );

        path="E:\\Thesis\\data\\camathistq1";
        File canatHistq1Dir = new File( path );

        path="E:\\Thesis\\data\\camathistq2";
        File canatHistq2Dir = new File( path );



        int maxCaCount=2000;

        double x[],y[],z[];

        x= new double[ maxCaCount];
        y= new double[ maxCaCount];
        z= new double[ maxCaCount];




        System.out.println(pdbDir.isDirectory());

        File pdbfiles[]= pdbDir.listFiles();



        /////////////prints to console names of all pdb files in directory//////////
/*
        for (int i = 0; i < pdbfiles.length; i++) {
            System.out.println(pdbfiles[i].getName());
            }
        */
/////////////prints to console names of all pdb files in directory//////////

        for (int i = 0; i < pdbfiles.length; i++) {
            File temp=pdbfiles[i];
            int numOfFileInList=0;
            File [] pdbfiles2=new File[1000];
            if(temp.isDirectory())
            {
             pdbfiles2=temp.listFiles();
             numOfFileInList=temp.listFiles().length;

            }
            else
            {
            //    File[] pdbfiles2= new File[1] ;
                pdbfiles2[0]= temp;
                numOfFileInList=1;
            }

           /////////////////////skip too large file/////////////////////////////

            for (int fileseqno = 0; fileseqno < numOfFileInList ; fileseqno++) {

                File pdbfile = pdbfiles2[fileseqno];

  /*              if(pdbfile.length()>500000){

                System.out.println("Current Directory: "+temp.getName().toString());
                System.out.println("Current file:  "+pdbfile.getName().toString());
                System.out.println(" file too long , may have multiple model ");

                System.out.println(pdbfile.length());

                continue;
                }
*/
                ////////////////////////skip too large file//////////////////////////////////

                System.out.println("Current Directory: "+temp.getName().toString());
                System.out.println("Current file:  "+pdbfile.getName().toString());


            if(pdbfile.getName().endsWith(".pdb")||pdbfile.getName().endsWith(".ent")  )
            {
                File cacoord,camat,camatq1,camatq2,camathistq1,camathistq2;


                path="E:\\Thesis\\data\\ca coordinates\\"+pdbfile.getName();
                cacoord=new File( path );

                path="E:\\Thesis\\data\\camat\\"+pdbfile.getName();
                camat=new File( path );

                path="E:\\Thesis\\data\\camatq1\\"+pdbfile.getName();
                camatq1=new File( path );

                path="E:\\Thesis\\data\\camatq2\\"+pdbfile.getName();
                camatq2=new File( path );

                path="E:\\Thesis\\data\\camathistq1\\"+pdbfile.getName();
                camathistq1=new File( path );

                path="E:\\Thesis\\data\\camathistq2\\"+pdbfile.getName();
                camathistq2=new File( path );



                PrintWriter cacoordpw = new PrintWriter( cacoord );
                PrintWriter camatpw = new PrintWriter( camat );
                PrintWriter camatq1pw = new PrintWriter( camatq1 );
                PrintWriter camatq2pw = new PrintWriter( camatq2 );
                PrintWriter camathistq1pw = new PrintWriter( camathistq1 );
                PrintWriter camathistq2pw = new PrintWriter( camathistq2 );



                Scanner sc = new Scanner( pdbfile );

                int seqNo;

                seqNo=0;

                while( sc.hasNext() )
                {
                    String line = sc.nextLine();
                    StringTokenizer strTok = new StringTokenizer( line," " );

                    int numOfTokens=0;
                    numOfTokens=strTok.countTokens();



                    String tokens[ ]= new String[numOfTokens];


                    int tokenid=0;

                    while( strTok.hasMoreTokens() )
                    {
                        tokens[ tokenid++ ] = strTok.nextToken();
                    }

                    if(tokens[0].equalsIgnoreCase("ENDMDL")){

                    System.out.println(" first model of multiple model is considered ");
                    break;
                    }

                    if(tokens[0].equalsIgnoreCase("END"))
                        break;

                        if( numOfTokens < 8 )
                        continue;


                    if(!tokens[0].equalsIgnoreCase("atom"))
                        continue;

                    if( tokens[0].equalsIgnoreCase("atom") )
                    {
                        if( tokens[2].equalsIgnoreCase("ca") )
                        {

                            if(numOfTokens>=12){
                            x[seqNo]=Double.parseDouble(tokens[ 6 ]);
                            y[seqNo]=Double.parseDouble(tokens[ 7 ]);
                            z[seqNo]=Double.parseDouble(tokens[ 8 ]);
                            }


                            if(numOfTokens<12){

                            if(tokens[6].substring(1).contains("-")){
                                int xendsAt=tokens[6].indexOf("-",1);
                                x[seqNo]= Double.parseDouble(tokens[6].substring(0,xendsAt));

                                if(tokens[6].substring(xendsAt+1).contains("-")){
                                        int yendsAt=tokens[6].indexOf("-",xendsAt+1);
                                        y[seqNo]= Double.parseDouble(tokens[6].substring(xendsAt+1,yendsAt));
                                        z[seqNo]=Double.parseDouble(tokens[6].substring(yendsAt+1));

                                      }else{

                                            y[seqNo]= Double.parseDouble(tokens[6].substring(xendsAt+1));
                                            z[seqNo]=Double.parseDouble(tokens[7]);
                                            }

                                      }
                            else
                            {
                            x[seqNo]=Double.parseDouble(tokens[ 6 ]);

                                if(tokens[7].substring(1).contains("-"))
                                    {
                                    int yendsAt = tokens[7].indexOf("-", 1);
                                    y[seqNo]= Double.parseDouble(tokens[7].substring(0,yendsAt));
                                    z[seqNo]= Double.parseDouble(tokens[7].substring(yendsAt+1));
                                    }
                            }





                            }


                            cacoordpw.print(tokens[0]+" "+seqNo);

                            for (int j = 2; j < tokens.length; j++) {
                                cacoordpw.print(" "+tokens[j]);
                            }

                            cacoordpw.println("");

                            cacoordpw.flush();
                            seqNo++;

                        }
                    }





                }

                cacoordpw.checkError();
/////////// all ca co-ordinates of pdb file are now in cacoord file and x[] , y[] , z[] array////////////
                int numOfCAatom=seqNo;
                double maxDistance=-1;
                int n=0;

                double totalDistData[]= new double[ numOfCAatom*numOfCAatom ] ;

                for (int j = 0; j < numOfCAatom; j++) {
                    for (int k = 0; k < numOfCAatom; k++) {

                        double dist= Math.sqrt((x[j]-x[k])*(x[j]-x[k])+(y[j]-y[k])*(y[j]-y[k])+(z[j]-z[k])*(z[j]-z[k]))  ;

                        //camatpw.print( " " + dist  );

                        camatpw.printf("%-3.2f  ", dist);
                        maxDistance=Math.max(maxDistance, dist);

                        totalDistData[ n++ ] = dist;
                        camatpw.flush();


                    }
                    camatpw.println();
                }

                System.out.print("Maximum Distance: ");
                System.out.println(maxDistance);

////// hist q1 is .5 quantization and q2 is 255 level quantization///////////
                int noQuantLevel= 255 ;
                int camatq1maxval= (int)(maxDistance*2);
                int histogramq1[] = new int[ camatq1maxval+1];
                int histogramq2[] = new int[ noQuantLevel+1 ];

                for (int j = 0; j < histogramq1.length; j++) {
                    histogramq1[j]=0;
                }

                for (int j = 0; j < histogramq2.length; j++) {
                    histogramq2[j]=0;
                }


                for (int j = 0; j < numOfCAatom; j++) {
                    for (int k = 0; k < numOfCAatom; k++) {

                    int valq1= (int)((totalDistData[j*numOfCAatom+k])*2);
                    int valq2 = ( int ) ((totalDistData[j*numOfCAatom+k])*noQuantLevel/maxDistance);

                    histogramq1[ valq1 ]++;
                    histogramq2[ valq2 ]++;


                    camatq1pw.printf(" %3d ",valq1);
                    camatq2pw.printf("%3d ",valq2);
                    camatq1pw.flush();
                    camatq2pw.flush();




                    }
                    camatq1pw.println();
                    camatq2pw.println();


                }




                for (int j = 0; j < histogramq1.length; j++) {

                    camathistq1pw.print(j);
                    camathistq1pw.print(" ");
                    camathistq1pw.print(histogramq1[j]);
                    camathistq1pw.println();
                    camathistq1pw.flush();
                    }
                camathistq1pw.checkError();



            for (int j = 0; j < histogramq2.length; j++) {
                    //System.out.println(j+" "+histogram[j]);

                    camathistq2pw.print(j);
                    camathistq2pw.print(" ");
                    camathistq2pw.print(histogramq2[j]);
                    camathistq2pw.println();
                    camathistq2pw.flush();
                    }
                camathistq2pw.checkError();








            }

            }
        }




    }
}
