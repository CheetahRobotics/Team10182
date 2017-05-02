Activity 1: McDonalds
Download data file (here)[menu.csv]

### Setup

```python
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from statsmodels.formula.api import ols

%matplotlib inline
```

### Read in data

```python
dataset = pd.read_csv("C:/Users/bob/Desktop/McDonalds.csv", index_col=False)
```

#### Questions:
1. How many rows, how many columns?
2. What are the columns, what are the rows?

#### Sodium:
It is recommended to consume between 1,500 milligrams to 2,300 milligrams of sodium daily. 
One teaspoon of table salt contains the maximum amount of daily sodium recommended, which is 2,300 milligrams.
The item with the highest sodium content is ?????.

#### Sugar:
High sugar intake leads to diabetic complications and causes cancer. 
In the plot above, the size of the points are indicative of the amount of sugar they contain. 
As expected, the the items that contain a large number of sugars are kids’ drinks and desserts.

#### Trans Fat:
Consuming low trans fat foods help in preventing heart related diseases. 
The items lowest in trans fat are kids’ drinks (which have high sugar). 
The item with the highest amount of trans fat content is ???.

#### Cholesterol:
At normal amounts, cholesterol in an important factor for the functioning of our body. High levels of cholestrol can cause harm to the well being of the heart. The food items that contain high levels of cholestrol are the Big Breakfast items with Large Biscuits.

The ideal healthy food would be have low sugars, low cholestrol, low trans fat content and low sodium content. These qualities occur seperately, not together.


#### Tools in our tool box:
- Mean, Standard Deviation, Kurtosis
- Histograms
- Scatterplot
- Regression
