/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proteinresearch;

/**
 *
 * @author rezaul karim
 */
public class GaussJordan
{
    void swap( double a[],double b[] )
    {
        for (int i = 0; i < a.length; i++) {
            double t=a[i];
            a[i]=b[i];
            b[i]=t;
        }
    }

    void print( double mat[][] )
    {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(" "+mat[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    double [] solve(double mat[][])
    {
        int n=mat.length;
        double []ret = new double[mat.length];

        int i=0;
        double a,b,c;
        for (int var= 0; var < n; var++) {



            for (int j= i; j < n; j++) {
                if(Math.abs(mat[j][var])>.000006)
                {
                    swap( mat[i],mat[j] );
                    break;
                }
            }

            if(Math.abs(mat[i][var])>.000006)
            {
                a=mat[i][var];

                for (int j = var; j <=n; j++) {
                    mat[i][j]=mat[i][j]/a;
                }

                a=1;

                for (int j = i+1; j < n; j++) {
                    if(Math.abs(mat[j][var])>.000006)
                    {
                        b=mat[j][var];
                        for (int k = 0; k <= n; k++) {
                            mat[j][k]=mat[j][k]*a-mat[i][k]*b;
                        }
                    }
                }

                i++;

            }

  //          print( mat );

        }



        for (int j = n-1; j >= 0; j--) {
            for (int k = j-1; k >= 0; k--) {
                a=mat[k][j];
                for (int l = j; l <= n; l++) {
                    mat[k][l]-=mat[j][l]*a;
                }
            }
//            print(mat);
        }

        for (int j = 0; j  < n; j ++) {
            ret[j]=mat[j][n];
        }


        return ret;
    }
}


