# Week 2.

1. Download the dataset we'll be using from [here](DataSet1.csv). (right-click and select 'Save Link As'.

#### Setup

```python
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
%matplotlib inline
```

#### Read in data

```python
donations = pd.read_csv("C:/Users/bob/Dropbox/jupyter/P00000001-IL.csv", index_col=False)
```

#### Examine data set.

```python
donations.shape
donations.ix[0:5, 0:5]
donations.head(2)
donations.columns
```

#### Print out candidates
```python
# this doesn't work so well, too many rows:
candidates = donations.cand_nm.unique()
candidates

# want unique names
candidates = donations.cand_nm.unique()
candidates
```


#### print out a random row. You can run this multiple times.
```python
from random import randint
num_rows = donations.shape[0]
i = randint(0,num_rows)

print donations.ix[i,:]
```




#### Plots
```python
tot["contb_receipt_amt"].plot(kind="bar")

avg_donations = donations.groupby("cand_nm").mean().sort_values(by="contb_receipt_amt")
avg_donations["contb_receipt_amt"].plot(kind="bar")
```

