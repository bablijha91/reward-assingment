# reward-assingment
Problem Description
A retailer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50
in each transaction.
(e.g a $120 purchase = 2*$20 + 1*$50 = 90 points)

Given a record of every transaction during a three month period , calculate the reward points for each customer per month and total.
############################################
This is maven Base project

Build the application using
mvn clean install

############################################
Sample input 

id,name,amount,date
1,Babli,120,02-08-2021
1,Babli,140,25-08-2021
2,Rupesh,150,22-08-2021
3,Aarush,220,23-08-2021
1,Babli,190,25-09-2021
2,Rupesh,170,22-09-2021
2,Rupesh,230,24-09-2021
3,Aarush,230,23-08-2021
1,Babli,160,25-10-2021
2,Rupesh,90,22-10-2021
3,Aarush,70,23-10-2021

############################################
Endpoint to verify

http://localhost:8086/v1/bjha/reward/1

############################################
sample output

{"8":220.0,"9":230.0,"10":170.0}
