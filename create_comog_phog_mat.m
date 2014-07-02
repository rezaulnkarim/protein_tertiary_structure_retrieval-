disp('Execution started create comog phog mat,current Time: ');
disp(datestr(now,'HH:MM:SS'));

indirname='E:\Thesis\scopdataExperiment\matdata\gray128\b3';

outdircomogphog='E:\Thesis\scopdataExperiment\matdata\feature\comog1b16phogL3b16'
outdircomog='E:\Thesis\scopdataExperiment\matdata\feature\comogb16'
outdirphog='E:\Thesis\scopdataExperiment\matdata\feature\phogL3b16'
%outdirname='E:\Thesis\scopdataExperiment\matdata\feature\comog1b9phogL3b9'


indir = dir(indirname);
nbentries = size(indir, 1);
dimx=128;
dimy=128;
bin = 16;
angle = 360;
L=3;
roi = [1;dimx;1;dimy];

numOfLevels=16;
%ap=360/numOfLevels;

numOfFilesCalculated=0;
for entry_i = 1: nbentries
    if indir(entry_i).isdir == false
        filename = indir(entry_i).name;
        if filename(1) ~= '.'
            [p, n, ext] = fileparts(filename);
             if strcmpi(ext, '.jpg')
                       
                        ofnameshort=strcat(n,'.ent');
                        ofpathcomogphog=strcat(outdircomogphog,'/');
                        ofpathcomogphogfullname=strcat(ofpathcomogphog,ofnameshort);
                        
                        ofpathcomog=strcat(outdircomog,'/');
                        ofcomogfullname=strcat(ofpathcomog,ofnameshort);
                        
                        ofpathphog=strcat(outdirphog,'/');
                        ofphogfullname=strcat(ofpathphog,ofnameshort);
                        
                        
                        fidcomog=fopen(ofcomogfullname,'w')
                        fidphog=fopen(ofphogfullname,'w')
                        fidcomogphog=fopen(ofpathcomogphogfullname,'w')

                        if fidcomog > 1 && fidphog > 1 && fidcomogphog > 1
                            
                        inputname=strcat(n,ext);
                        P = anna_phog(inputname,bin,angle,L,roi,dimx,dimy);
                        I=imread(filename);
                        M=COMOG(I,numOfLevels);
                        
                        Mint=int32(M*1000000000);
                        szM=size(M);
                        
                        szMx=szM(1);
                        szMy=szM(2);
                        
                        szP=size(P);
                        
                        for i=1:szMx;
                        for j=1:szMy;
                        fprintf(fidcomogphog,'%10d ',Mint(i,j));
                        fprintf(fidcomog ,'%10d ',Mint(i,j));
                        end
                        end
                        fprintf(fidcomogphog,'\n');
                        fprintf(fidcomogphog,'\n');
                        
                        sz=szMx*szMy;
                        Pint=int32(P*1000000000);
                        
                        for k=1:szP
                        fprintf(fidcomogphog,'%10d ',Pint(k));
                        fprintf(fidphog,'%10d ',Pint(k));
                        
                        if (int32(floor(k/sz)))*sz==k
                        fprintf(fidcomogphog,'\n');
                        fprintf(fidphog,'\n');
                        end
                        end
                        numOfFilesCalculated=numOfFilesCalculated+1;
                        if 1000*uint32(numOfFilesCalculated/1000)==numOfFilesCalculated
                           disp('numberOfFilesCalculated:');
						   disp(numOfFilesCalculated);
                        end
                        fclose(fidcomogphog);
                        fclose(fidcomog);
                        fclose(fidphog);
                        
                        end
             end
        end
    end
end
disp(numOfFilesCalculated);
disp('End Time:');
disp(datestr(now,'HH:MM:SS'));
