protein_tertiary_structure_retrieval-
=====================================

Here i am going to share the codes used in my research on protein tertiary structure retrieval algorithms. 
Codes are fragmented. So, needs to run step by step as mentioned. There are some matlab scripts few of which 
are collected from github and the rest are done by me.  

***********************************************************************************
Note: The repository provided here contains codes of our years of work and done by several of us. So its better to contack us via email to have proper guidelines in details to run them. As the whole process needs extensive memory and feature extraction itself may need months of processing time, we also provide a mysql databse with extracted features. With our extracted features, one can directly run queries to get the retrieval results. We are working on an web service which we plan to make open source.
***********************************************************************************
All protein chain coordinate data in pdb format are taken from http://scop.berkeley.edu/astral/pdbstyle/ver=2.03.
Please go to the link to download and to know in details about the format of the files.
  
**********************************************************************************
1.PDBParser.java is used to parse pdb format files to generate alpha carbon distance matrix from tar file downloaded from scop.berkely.edu.
 Each tar file contains multiple number of pdb files. To run properly directory path strings on the file need to be modified.  

2. BicubicInterPol is used to transform all the alpha carbon distance matrix to a dimension that is nearest power of 2.
BicubicInterPol uses GaussJordan.
**********************************************************************************

3.

