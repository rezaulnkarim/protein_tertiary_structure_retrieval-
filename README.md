protein_tertiary_structure_retrieval-
=====================================

This folder contains the codes used in my research on protein tertiary structure retrieval algorithms. 
Codes are fragmented. So, needs to run step by step as mentioned. There are some matlab scripts few of which 
are collected from github and the rest are done by me.  

***********************************************************************************
Note: The repository provided here contains codes of our years of work and done by several of us. So its better to contact us via email to have proper guidelines in details to run them. As the whole process needs extensive memory and feature extraction itself may need months of processing time, we also provide a mysql databse with extracted features. With our extracted features, one can directly run queries to get the retrieval results. We are working on an web service which we plan to make open source.
***********************************************************************************
All protein chain coordinate data in pdb format are taken from http://scop.berkeley.edu/astral/pdbstyle/ver=2.03.
Please go to the link to download and to know in details about the format of the files.
  
**********************************************************************************
1.PDBParser.java is used to parse pdb format files to generate alpha carbon distance matrix from tar file downloaded from scop.berkely.edu.
 Each tar file contains multiple number of pdb files. To run properly directory path strings on the file need to be modified.  

2. BicubicInterPol is used to transform all the alpha carbon distance matrix to a dimension that is nearest power of 2.
BicubicInterPol uses GaussJordan.
**********************************************************************************


3. The next step is to transform all the matrices that are in dimension of power of 2 like 16x16,32x32 , 64x64, 128x128 ... 1024x1024 to a dimension of 128x128 for our methods. We have done this with matlab. The matlab script "create_image_from_mat_textfile_and_db2_and_imresize_on_mat64.m" is used to transform the 64x64 matrix from text file and output a gray scale image of sixe 128x128 and 32x32. This script is used with slight modification for matrices of other dimensions like 256x256, 512x512 and so on. As anyone with a little knowledge can do the modification accordingly, all the modified scripts are not given here. In the same way, with some modification of matlab scripts, we transform all the matrices to dimension 32x32 to implement the method used by MASASW.


4. Now we have 128x128 grayscale images. The task next is to extract our COMoGrad and PHOG features. To extract PHOG feature, we have taken help of the code from "https://github.com/jason2506/SimpleCBIR/blob/master/src/phog.m". The code to extract COMoGrad is done by us. The matlab script "create_comog_phog_mat.m" is used to extract COMoGrad feature on a directory, PHOG feature on a directory and both COMoGrad and PHOG feature on another directory. Please ahange inputdir to the directory of 128x128 grayscale images in your system. As there was 152,387 grayscale image files and matlab have problems in handling huge directories, I have fragmented them to 30 directories of 5000 files and have run the code 30 times changing the indir name. You also have to change the 3 out directory name according to your filesystem. The script "create_comog_phog_mat.m" have dependency on "comog.m" , "anna_phogDescriptor.m", "anna_phog.m", "anna_binMatrix.m".


5. Now we have COMoGrad features as an array 256 values in space delimited text files  and there are 152,487 such files. We also have COMoGrad+PHOG features as an array 256+765=1021 values in space delimited text files  and there are 152,487 such files in another directory. We are now ready to run queries with these features.









