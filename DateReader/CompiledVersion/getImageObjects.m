%% function thet gets an date image and returnes vector of object
% each object contain one charecter;

function [ s,allObjects ] = getImageObjects( I )

%imshow(I)
I = mat2gray(I);
I=I(:,:,1);
%imshow(I)

%I = medfilt2(I,[3 3]);
%figure, imshow(I)
%  background = imopen(I,strel('disk',15));
%  I2 = I - background;
%  imshow(I2)
% 
% I3 = imadjust(I2);
% imshow(I3);


level = graythresh(I);
bw = ~im2bw(I,level);
%bw = bwareaopen(bw, 50);
%imshow(bw)

%bw = bwareaopen(bw,10);
%imshow(bw)

%se = strel('disk',10);
%bw = imopen(bw,se);
%imshow(bw)

se = strel('disk',4);
bw = imclose(bw,se);
%imshow(bw)

s = regionprops(bw,'centroid','BoundingBox');
centroids = cat(1, s.Centroid);
%hold on
%plot(centroids(:,1),centroids(:,2), 'b*')
%hold off


cc = bwconncomp(bw, 4);

charecter = false(size(bw));
i=1;

% while(true)
%     if i==size(cc.PixelIdxList,2)+1        
%         i=1;
%     end
% charecter(cc.PixelIdxList{i}) = true;
% imshow(charecter);
% pause(0.5);
% charecter(cc.PixelIdxList{i}) = false;
% i=i+1;
% end
objectLength=46;
objectwidth=46;
allObjects=zeros(size(s,1),objectLength,objectwidth);

for i=1:size(s)
    bbox=s(i).BoundingBox;
    subImage = imcrop(bw, bbox);
    size1OfSubImage=size(subImage,1);
    size2OfSubImage=size(subImage,2);
    
    if mod(size1OfSubImage-size2OfSubImage,2)==1
        size1OfSubImage=size1OfSubImage-1;
    end
    
    if size1OfSubImage>size2OfSubImage
        padSize=(size1OfSubImage-size2OfSubImage)/2;
        subImage=padarray(subImage,[0 padSize]);
%       imshow(subImage);
    else
        padSize=(size2OfSubImage-size1OfSubImage)/2;
        subImage=padarray(subImage,[padSize 0]);
%        imshow(subImage);
    end
    
    resizedImage = imresize(subImage,[40 40]);
    resizedImage= padarray(resizedImage,[3 3]);
%    imshow(resizedImage);
    allObjects(i,:,:)=resizedImage;    
end



end