%% Initialization
clear ; close all; clc


% Load Training Data
fprintf('Loading and Visualizing Data ...\n')

load('ex3data1.mat');
m = size(X, 1);


newX=zeros(size(X));
for i=1:size(X)    
    %currentImage=reshape(X(i,:),20,20);
    level = graythresh(X(i,:));
    bw = im2bw(X(i,:),level);
    newX(i,:)=bw;
    %imshow(bw)
    %imshow(currentData);
    %pause
end
[Theta1,Theta2]=trainNeuralNetwork(newX,y);

I=imread('expiryDate.jpg');
[s,allCharectesObjects]=getImageObjects(I);

X2=zeros(size(allCharectesObjects,1) ,400)
  for i=1:size(allCharectesObjects,1)    
      X2(i,:)=allCharectesObjects(i,:);
      %imshow(currentImage)
  end
predict(Theta1,Theta2,X2)
