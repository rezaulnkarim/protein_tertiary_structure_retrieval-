
function MM=COMOG(Im,numOfLevels)

[L,C]=size(Im); 

%Mdim=int16(360/anglePrecision);
%M=zeros(Mdim,Mdim);

%m=sqrt(L/2);

anglePrecision=360/numOfLevels;
I=double(Im);
hx = [-1,0,1];
hy = -hx';
grad_xr = imfilter(I,hx);
grad_yu = imfilter(I,hy);
anglesrad=atan2(grad_yu,grad_xr);
anglesdeg=anglesrad*180/3.1416;
%magnit=((grad_yu.^2)+(grad_xr.^2)).^.5;


[La,Ca]=size(anglesdeg);
for ii=1:1:La 
    for jj=1:1:Ca 
   if(anglesdeg(ii,jj)<-(anglePrecision/2)) 
       anglesdeg(ii,jj)=anglesdeg(ii,jj)+360; 
   end 
    end 
end 

Ad=anglesdeg/anglePrecision;
Aint = uint16(Ad);
AIm=mat2gray(Aint);
M=graycomatrix(AIm,'NumLevels',numOfLevels);
Msum=sum(sum(M));

MM=M/Msum;
end
