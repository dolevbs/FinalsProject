function predictDateFromImage(fileName)
%% Initialization
close all; clc

I=imread(fileName);
[s,objects]=getImageObjects(I);
myX=objects;
myXrow=myX(:,:);

%% Setup the parameters you will use for this exercise
%input_layer_size  = 2116;  % 20x20 Input Images of Digits
%hidden_layer_size = 125;   % 25 hidden units
%num_labels = 11;          % 10 labels, from 1 to 10   
                          % (note that we have mapped "0" to label 10)

% Load the weights into variables Theta1 and Theta2
load('MyNetworkThetas.mat');

dateString='';
for i = 1:size(myXrow,1)
    % Display each element in picture with prediction
    %fprintf('\nDisplaying Example Image\n');
    %displayData(myXrow((i), :));

    pred = predict(Theta1, Theta2, myXrow((i), :));
    %if mod(i,3)==0
    %    dateString=strcat(dateString,'/');
    if pred==10
        dateString=strcat(dateString,'0');
    elseif pred==11
        dateString=strcat(dateString,'/');
    else
        dateString=strcat(dateString,num2str(pred));
    end
    %fprintf('\nNeural Network Prediction: %d (digit %d)\n', pred, mod(pred, 10));
       
end

disp(dateString);

end

