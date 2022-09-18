# MechaCar_Statistical_Analysis
## Linear Regression to Predict MPG

![D1](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/Deliverable_1.png)


The only __variables that provided statistically significant p-values__ are vehicle length and vehicle clearance. The rest of the variables’ p-values lie above 5%. 

Some of the independent variables had a significant effect on the dependent variables. Therefore, __the linear model is not considered to be zero.__

The r-squared value is at 71.49%. This means that this model would approximately predict the mpg of a MechaCar with 71.49% accuracy. __This is relatively efficient.__

 ## Summary Statistics on Suspension Coil

•	The design specifications for the MechaCar suspension coils dictate that the variance of the suspension coils must not exceed 100 pounds per square inch. 


![D2](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/Deliverable_2.png)
![d3](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/D2.png)

The total summary does meet the variance requirements at a variance of 62.29. However, upon further examination only the first and second lots meet the variance parameter, while lot thee reaches a variance over 170. 



## T-Tests on Suspension Coils


![D3](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/d3_overall.png)

The p-value is greater than 5%. At a significance level of 95%, this is considered __statistically insignificant__, and therefore the null is not rejected based on this data.

![D3_1](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/d3_lot1.png)

The p-value is greater than 5%. At a significance level of 95%, this is considered __statistically insignificant__, and therefore the null is not rejected based on this data.


![D3_2](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/d3_lot2.png)

The p-value is greater than 5%. At a significance level of 95%, this is considered __statistically insignificant__, and therefore the null is not rejected based on this data.


![D3_3](https://raw.githubusercontent.com/SavannahPosner/MechaCar_Statistical_Analysis/main/R_Session/images/d3_lot3.png)

The p-value is less than 5%, at approximately 4%. At a significance level of 95%, this is considered __statistically significant__, and therefore the null is rejected based on this data.

## Study Design: MechaCar vs Competition
I would use an ANOVA test to test the maintenance cost of MechaCar against the competing cars of different companies. I would use the ANOVA test due to the nature of the hypothesis that I want. With this test I would be trying to find a statistical difference between distribution means from multiple samples. Because I am measuring equality, of a single dependent variable, I chose to use the ANOVA test. The null is that the maintenance costs are equal, and the alternative is that the maintenance cost are not equal. I would need enough maintenance cost data of different classes of cars to be able to compare it to that to the MechaCar’s maintenance cost. In other words,  I would need reliable sample data from each class of car that the MechaCar is being compared to.
