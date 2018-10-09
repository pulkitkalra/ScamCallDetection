# ScamCallDetection
This repository contains the source code for our Part 4 Project: Project #56: Scam Call Detection.

**Contributors**: Pulkit Kalra (pkal608) and Darius Au (dau782)

**Supervisor**: Prof. Gill Dobbie

## Overview
In an era of increased interconnectivity with the emergence of the smartphone, phone scam is unfortunately rampant. With the frequency of phone scam predicted to soar over the coming years, we are faced with a crisis that could result in increased financial fraud and the pollution of our phone lines. The existing methods of preventing scam include blacklisting of fraudulent phone numbers and increasingly aggressive public awareness campaigns. These methods have failed to curb the effectiveness and outreach of phone scams. To address this problem, we propose a real-time scam detection system called Scaminator. In contrast to existing methods, we our system detects scam based on the semantic content of the dialogue. Our novel approach leverages advanced natural language processing and machine learning to provide real-time feedback about the likelihood of scam. The results of our work are promising and validate our approach to solving this problem. Our research is a first step towards eradicating the scam call problem. Additional work will need to be carried out to execute our system in the real-world.

## Building and Running the Project
1. Clone this repository
2. Open your IDE (e.g. Eclipse) and import the project as a Maven Project
3. Build the project by running a Maven `install` goal.
4. Set up the environmnent variable that contains the key for Dialogflow agent.
   a. Download the json key from here: https://files.fm/u/kxkgxez6
   b. Create a new Environment Variable in Eclipse Run Configurations. The name of the variable should be: GOOGLE_APPLICATION_CREDENTIALS,
      and the value is the absolute path of the downloaded file. See image below:
      ![key](https://i.imgur.com/ICealBl.png)
      
5. The program can be executed by running the program from the main method in `view/MainApp.java`

Upon running the application you should be able to see this GUI. 
![Scaminator](https://i.imgur.com/NCYRfIB.png)

Click 'Start Recording' at the top and begin speaking. It is recommended to use a microphone to avoid background noise. 

