### Setup

```python
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
%matplotlib inline
```

#### Read in data

```python
dataset = pd.read_csv("C:/Users/bob/Desktop/Workbook1.csv", index_col=False)
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
dataset['Hours of sleep'].hist()
```
9. It also has an average of 10, but very different distribution! How do we tell them apart?
https://en.wikipedia.org/wiki/Standard_deviation#/media/File:Comparison_standard_deviations.svg

10.
```python
print dataset['Hours of sleep'].std(),dataset['# of fingers'].std()
```

11. Now look at IQ.
```python
dataset['IQ'].hist()
```
