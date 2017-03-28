### Setup

```python
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from statsmodels.formula.api import ols

%matplotlib inline
```

#### Read in data

```python
dataset = pd.read_csv("C:/Users/bob/Desktop/Week3.csv", index_col=False)
```

#### Explore data
```python
dataset
dataset.columns
```

Questions:
1. How many rows, how many columns?
2. What are the columns, what are the rows?
3. Who is the oldest person
4. What is the total number of fingers across the group? Min, max number?
5. What is the average number of fingers.
6. Draw a histogram:
```python
dataset['# of fingers'].hist()
```
7. What is the average 'Hours of sleep'
8. Draw a bar chart of 'Hours of sleep'
```python
dataset['HoursOfSleep'].hist()
```
9. It also has an average of 10, but very different distribution! How do we tell them apart?
https://en.wikipedia.org/wiki/Standard_deviation#/media/File:Comparison_standard_deviations.svg

10.
```python
print dataset['HoursOfSleep'].std(),dataset['# of fingers'].std()
```

11. Now look at IQ.
```python
dataset['IQ'].hist()
```

12. Long tail, lognormal, skewness, moments.
Long tail:    
http://neilpatel.com/wp-content/uploads/2015/12/image051.jpg   
http://www.webdesignstuff.co.uk/ta006/files/2011/03/true-long-tail.jpg   

Lognormal:   
https://en.wikipedia.org/wiki/Log-normal_distribution   

Moments: http://3.bp.blogspot.com/-FxqClGaImco/U9UkOh503AI/AAAAAAAAGpo/1fgnbXwgVxU/s1600/Understanding-Tolerance-Analysis2.png

13. Summary stats:
dataset.describe()

14. 
```python
dataset['Workout Heartrate'].hist()

# What drives workout heartrate?   
# Is it IQ?
dataset.plot.scatter(x='IQ', y='Workout Heartrate')

# Is it Age?
dataset.plot.scatter(x='Age', y='Workout Heartrate')

# Can we quantify this more?
ols("WorkoutHeartrate ~ Age", data=dataset).fit().summary()
ols("IQ ~ Age", data=dataset).fit().summary()

```

R squared: https://en.wikipedia.org/wiki/Coefficient_of_determination
