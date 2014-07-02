disp('dwt2 started');
wavename='db2';
p0='E:\Thesis\scopdataExperiment\matdata\';
workingdir='acmat64';
p1=strcat(p0,workingdir);

p3=strcat(p0,'gray128');
%mkdir(p3);


p5=strcat(p0,'gray32');
%mkdir(p5);

Mdir = dir(p1);
nbentries = size(Mdir,1);

Mfiles = [];


numOfFilesTransformed=0;
for entry_i = 1 : nbentries 
    if Mdir(entry_i).isdir == false

            filename = Mdir(entry_i).name;
        if filename(1) ~= '.'
                    [p, n, ext] = fileparts(filename);
                    if strcmpi(ext, '.ent')
                    
                    %if strcmpi(ext, '.jpg')
                       
                       
                    infname=strcat(n,ext);   
                    
                    [fid ,msg ]= fopen(filename); 
                     
                    
                       if fid>1
                       line1chars=fgets(fid);
                       line2chars=fgets(fid);
                       
                       %y=sscanf(x,'%f')
                       if line2chars ~= -1 
                       line2nums=sscanf(line2chars ,'%f');
                       sizeline2=size(line2nums);
                       
                       inputdim=sizeline2(1);
                       else
                           inputdim=0;
                       end
                        fclose(fid);
                       
                        
                       

                       [fid ,msg ]= fopen(filename); 
                       
                       if fid > 1 && inputdim > 1
                          Mat =zeros(inputdim,inputdim);
                           szzz=size(Mat);         
                           for i= 1 : 1 : inputdim
                            for j= 1 : 1 : inputdim
                                number = fscanf(fid, '%f', 1);
                                    Mat(i,j)=number; 
                            end
                            end
                            
                         sz=size(Mat);   
                            
                    I= mat2gray(Mat);
                    
                    Im128=imresize(I,[128 128]);
                    
                    [CA,CH,CV,CD] = dwt2(Im64,wavename);
                    
                    Im32=imresize(mat2gray(CA),[64 64]);
                    
                    
                    f128=strcat(p3,'/');
                    ff128=strcat(f128,inff);
                    imwrite(Im128,ff128,'jpg');
                    
                    
                    f32=strcat(p5,'/');
                    ff32=strcat(f32,inff);
                    imwrite(Im32,ff32,'jpg');
                    
                       end
                       
                       
                       
                       end
                       
                       fid= fclose(fid);

                       numOfFilesTransformed=numOfFilesTransformed+1;    
                       if  500*uint32(numOfFilesTransformed/500)==numOfFilesTransformed
                           disp(numOfFilesTransformed);
                       end
                       
                       %if exist(inff,'file')==2
                        %   delete(inff);
                       %end
                       
                       
                       %%%%%%%%%%%
                       end 
                    end
    end

        
end


disp('dwt2 finished');
disp(numOfFilesTransformed);