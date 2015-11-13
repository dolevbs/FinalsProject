%% Machine Learning Online Class - Exercise 3 | Part 2: Neural Networks

%  Instructions
%  ------------
% 
%  This file contains code that helps you get started on the
%  linear exercise. You will need to complete the following functions 
%  in this exericse:
%
%     lrCostFunction.m (logistic regression cost function)
%     oneVsAll.m
%     predictOneVsAll.m
%     predict.m
%
%  For this exercise, you will not need to change any code in this file,
%  or any other files other than those mentioned above.
%

%% Initialization
clear ; close all; clc
% load('test.mat');
% X=newX';
% y=vec2ind(newY)';

%load('myNumbers1.mat');
%load('myNumbers2.mat');
%load('myNumbers3.mat');
%load('myNumbers4.mat');
%myX=[allObjects1 ;allObjects2; allObjects3 ;allObjects4];


I=imread('Goodexpiry (3).jpg');
[s,objects]=getImageObjects(I);
myX=objects;
myXrow=myX(:,:);

%% Setup the parameters you will use for this exercise
input_layer_size  = 2116;  % 20x20 Input Images of Digits
hidden_layer_size = 125;   % 25 hidden units
num_labels = 11;          % 10 labels, from 1 to 10   
                          % (note that we have mapped "0" to label 10)

%% =========== Part 1: Loading and Visualizing Data =============
%  We start the exercise by first loading and visualizing the dataset. 
%  You will be working with a dataset that contains handwritten digits.
%

% Load Training Data
%fprintf('Loading and Visualizing Data ...\n')

%load('ex3data1.mat');
%m = size(X, 1);

% % Randomly select 100 data points to display
% sel = randperm(size(X, 1));
% sel = sel(1:100);
% 
% displayData(X(sel, :));
% 
% fprintf('Program paused. Press enter to continue.\n');
% %pause;

%% ================ Part 2: Loading Pameters ================
% In this part of the exercise, we load some pre-initialized 
% neural network parameters.

% fprintf('\nLoading Saved Neural Network Parameters ...\n')

% Load the weights into variables Theta1 and Theta2
load('MyNetworkThetas.mat');

%% ================= Part 3: Implement Predict =================
%  After training the neural network, we would like to use it to predict
%  the labels. You will now implement the "predict" function to use the
%  neural network to predict the labels of the training set. This lets
%  you compute the training set accuracy.

%pred = predict(Theta1, Theta2, X);

%fprintf('\nTraining Set Accuracy: %f\n', mean(double(pred == y)) * 100);



%  To give you an idea of the network's output, you can also run
%  through the examples one at the a time to see what it is predicting.

%  Randomly permute examples
%rp = randperm(m);
dateString='';
for i = 1:size(myXrow,1)
    % Display each element in picture with prediction
    fprintf('\nDisplaying Example Image\n');
    displayData(myXrow((i), :));

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
    fprintf('\nNeural Network Prediction: %d (digit %d)\n', pred, mod(pred, 10));
       
end



