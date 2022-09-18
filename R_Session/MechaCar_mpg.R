
#Import needed library 
library(tidyverse)
#Import the data frame
dataFrame1 <- read.csv(file='MechaCar_mpg.csv',check.names=F,stringsAsFactors = F)

# find the summary of the linear regression model of all six variables
summary_values <- summary(lm(mpg ~ vehicle_length + vehicle_weight + spoiler_angle + ground_clearance +AWD,data=dataFrame1))

# load in the Suspension_Coil.csv as dataFrame2
dataFrame2 <- read.csv(file='Suspension_Coil.csv',check.names=F,stringsAsFactors = F)
# create the total summary of dataFrame2's PSI 
total_summary <-dataFrame2 %>%  summarize(Mean=mean(PSI), MedianI=median(PSI), Variance = var(PSI), SD = sd(PSI), .groups = 'keep')
# create a summary of the PSI seperated by manufacturing lot 
lot_summary <-dataFrame2 %>% group_by(Manufacturing_Lot) %>% summarize(Mean=mean(PSI), MedianI=median(PSI), Variance = var(PSI), SD = sd(PSI), .groups = 'keep')
# preform a t-test on the PSI value with a mean of 1,500 
t_test_PSI <- t.test(dataFrame2$PSI,mu=1500)

# use subsets to seperate the lot data and then test it 
# lot 1
lot1 <- subset(dataFrame2, Manufacturing_Lot=="Lot1")
test1 <- t.test(lot1$PSI,mu=1500)
# lot 2 
lot2 <- subset(dataFrame2, Manufacturing_Lot=="Lot2")
test2<- t.test(lot2$PSI,mu=1500)
# lot 3 
lot3 <- subset(dataFrame2, Manufacturing_Lot=="Lot3")
test3<-t.test(lot3$PSI,mu=1500)




