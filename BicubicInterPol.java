package proteinresearch;


import java.io.File;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class BicubicInterPol {

    public static void main(String[] args) throws Exception {

    String outDir = "E:\\Thesis\\scopdataExperiment\\matdata";

    File camatDir = new File( "E:\\Thesis\\scopdataExperiment\\acmat255level-28" );
    File camat8Dir = new File( outDir+"\\acmat8" );
    File camat16Dir = new File( outDir+"\\acmat16" );
    File camat32Dir = new File(outDir+"\\acmat32" );
    File camat64Dir = new File(outDir+"\\acmat64");
    File camat128Dir = new File( outDir+"\\acmat128");
    File camat256Dir = new File( outDir+"\\acmat256");
    File camat512Dir = new File( outDir+"\\acmat512");
    File camat1024Dir = new File( outDir+"\\acmat1024");
    File camat2048Dir = new File( outDir+"\\acmat2048" );
    
    File camatfiles[]= camatDir.listFiles();

        int i,j,k;
        File camat8;
        File camat16;
        File camat32;
        File camat64;
        File camat128;
        File camat256;
        File camat512;
        File camat1024;
        File camat2048;
        
        int newdim = 0;
        double normy;
        int xl;
        int xu;
        double normx;
        int yl;
        int yu;
        int eqnno;
        double[][] bicubicarr = new double[16][17];
        int y;
        int x;
        double newf;

        PrintWriter pw;
        int quantizedval;
        double zigmin;
   
        int maxCaCount=3000;
   /*
        List<String> dataset1=new ArrayList<String>();                 
   File dataset1f=new File("E:\\Thesis\\scopdataExperiment\\dataset_1.txt");
   Scanner scd1=new Scanner(dataset1f);
   String ln=null;
   
   while(scd1.hasNext()){
    ln=scd1.nextLine().toString();
   if(ln==null||ln.length()<7||!ln.startsWith("d"))continue;
   StringTokenizer strTok=new StringTokenizer(ln, " ");
   
   while(strTok.hasMoreTokens()){
   String tok = strTok.nextToken().toString();
   
   if(tok.length()==7&& tok.startsWith("d")){dataset1.add(tok);
       //System.out.println(tok);
   }
   
   }
   
   }
        System.out.println(dataset1.size()+"");
        */
int count=0;
    for ( i = 0; i <camatfiles.length ; i++) {
        count++;
        
        if(count%200==0)
            System.out.println("Completed "+":"+"\t"+count);
            File camatfile=camatfiles[i];
            String fname=camatfile.getName();
               if((fname.endsWith(".pdb")||fname.endsWith(".ent") ) ){
                  // System.out.println("Found matrix:  "+fname);
                   //System.exit(1);
                   double camatrix[][],newcamatrix[][];
                    camatrix = new double[ maxCaCount][ maxCaCount];
                    newcamatrix = new double[ maxCaCount][ maxCaCount];
                    int dim=0;
                    Scanner sc = new Scanner( camatfile );

                    int lineNo=0;

                    ///////////////
          //          System.out.println("");
        //             System.out.print(" file: ");
                   // System.out.print(camatfile.getName());
                    /////////////////////
                    StringTokenizer strTok;
                    
                    
                    
                    int pos=0;
                     lineNo=0;
                     int jj;
                     
                     while( sc.hasNext() )
                            {
                                String line = sc.nextLine();
                                strTok=new StringTokenizer(line, "\t");
                                jj=0;
                                while(strTok.hasMoreTokens()){
                                if(lineNo==0)dim++;
                                double vv=Double.parseDouble(strTok.nextToken().toString());
                                    camatrix[lineNo][jj++]=vv;
                               }
                                lineNo++;
                                
                            }
                     
                    // System.out.println("dim is:  "+dim);

                     /*
                     for (int l = 0; l < dim; l++) {
                         for (int m = 0; m < dim; m++) {
                             System.out.print("\t"+camatrix[l][m]);
                         }
                         System.out.println("");
                   }
                     */
                   //  System.exit(1);
////////////////////write interpolation code here/////////////////////////////////////

    //                System.out.print("    Dim : ");
      //                System.out.print(dim);
        //            System.out.println("");

                     

                    if(dim==4||dim==8 || dim ==16 || dim ==32 || dim ==64 || dim ==128 || dim ==256 || dim ==512 || dim ==1024  ){

                        
                     //   System.out.println(fname+"\t"+":"+dim);
                   //     System.out.println("matrix is of size in power of 2.  "+fname);
                   
                    }
                    
                    
                    
                    if(dim<4){
                            System.out.println(""+fname+ "\t"+"is ...");
                           System.out.println("too small matrix , less than 6x6");
                          
                           File small= new File(outDir+"\\acmatsmall\\"+fname);
                           pw=new PrintWriter(small);
                           
                           for (int l = 0; l < dim; l++) {
                               for (int m = 0; m < dim; m++) {
                                   pw.write((int)camatrix[l][m]+"\t");
                                   pw.flush();
                               }
                            pw.println();
                            pw.flush();
                        }
                           pw.checkError();
                           pw.close();
                           continue;
                        }                    
                       else  if (dim >= 4 && dim < 12) {
                        camat8 =new File( outDir+"\\acmat8\\"+fname );
                        pw = new PrintWriter( camat8 );
                        newdim=8;

                    }
                    else if(dim>=12 && dim <22){

                        newdim=16;
                        camat16 =new File( outDir+"\\acmat16\\"+fname );
                        pw = new PrintWriter( camat16 );

                    }else if(dim>=22 && dim <40){

                        newdim=32;
                        camat32 =new File( outDir+"\\acmat32\\"+fname );
                        pw = new PrintWriter( camat32 );

                    }else if(dim>=40 && dim <96){


                        newdim=64;
                        camat64 =new File( outDir+"\\acmat64\\"+fname );
                        pw = new PrintWriter( camat64 );

                    }else if(dim>=96 && dim <192){


                        newdim=128;
                        camat128 =new File( outDir+"\\acmat128\\"+fname );
                        pw = new PrintWriter( camat128 );


                    }else if(dim>=192 && dim <384){


                    newdim=256;
                    camat256 =new File( outDir+"\\acmat256\\"+fname );
                    pw = new PrintWriter( camat256 );

                    }else if(dim>=384 && dim <768){


                    newdim=512;
                    camat512 =new File( outDir+"\\acmat512\\"+fname );
                    pw = new PrintWriter( camat512 );

                    }else {

                    newdim=1024;
                    camat1024 =new File(outDir+"\\acmat1024\\"+fname );
                    pw = new PrintWriter( camat1024 );
                    }
                   // System.out.println(fname);
                  // System.out.println("new dim is:   "+newdim);
                    
                    for (int newx= 0; newx < newdim; newx++) {
                        for (int newy= 0; newy <newdim; newy++) {
                           // if(newx==newy){newcamatrix[newx][newy]=0.0; continue; }
                            normx= (double)(newx*(dim-1))/(newdim-1);
                            normy= (double)(newy*(dim-1))/(newdim-1);
                            if((int)Math.floor(normx)==0){
                                xl=0;xu=3;
                            } else if((int)Math.floor(normx)== (dim-1)){
                                xu=dim-1;
                                xl=xu-3;
                            }else{
                            xl= (int) (Math.floor(normx) - 1);xu=xl+3;
                            }
                            if((int)Math.floor(normy)==0){
                                yl=0;yu=3;
                            } else if((int)Math.floor(normy)== dim-1){
                            yl=dim-4;yu=dim-1;
                            }else{
                            yl= (int) (Math.floor(normy) - 1);yu=yl+3;
                            }

                            eqnno=0;
                            for ( x= xl; x <=xu; x++) {
                                for (y= yl; y <= yu; y++) {
                                    bicubicarr[eqnno][0]=1.0;
                                    bicubicarr[eqnno][1]=(double)y;
                                    bicubicarr[eqnno][2]=(double)y*y;
                                    bicubicarr[eqnno][3]=(double)y*y*y;
                                    for (int m = 4; m < 16; m++) {
                                        bicubicarr[eqnno][m]=bicubicarr[eqnno][m-4]*(double)x;
                                    }
                                   bicubicarr[eqnno][16]=(double)camatrix[x][y];
                                    eqnno++;
                                }
                            }

                            GaussJordan gaussJordan = new GaussJordan() ;
                            double []solution= gaussJordan.solve(bicubicarr);
                            newf=0.0;
                            newf+=solution[0]+solution[1]*normy+solution[2]*normy*normy+solution[3]*normy*normy*normy;
                            newf+=solution[4]*normx+solution[5]*normy*normx+solution[6]*normy*normy*normx+solution[7]*normy*normy*normy*normx;
                            newf+=solution[8]*normx*normx+solution[9]*normy*normx*normx+solution[10]*normy*normy*normx*normx+solution[11]*normy*normy*normy*normx*normx;
                            newf+=solution[12]*normx*normx*normx+solution[13]*normy*normx*normx*normx+solution[14]*normy*normy*normx*normx*normx+solution[15]*normy*normy*normy*normx*normx*normx;
                           
                            
                            if(newf<0)
                                newcamatrix[newx][newy]=0;
                            else if(newf>255)
                                newcamatrix[newx][newy]=255;
                            else
                                newcamatrix[newx][newy]=newf;
                            
                            
                        }


                    }

                    int val;
                    for (int xxnew= 0; xxnew < newdim; xxnew++) {
                        for (int yynew= 0; yynew < newdim; yynew++) {
                            
                             val=(int)newcamatrix[xxnew][yynew];
                            pw.write(val+"\t");
                            pw.flush();
                        }
                        pw.println();
                        pw.flush();
                    }
                    pw.checkError();
                    pw.close();
                }
        }
    }
}
