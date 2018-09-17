# Week 2.

Review last week's material by watching this video: https://www.youtube.com/watch?v=fnl6N_F7TvI&feature=youtu.be

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

```python
print 'max contribution', main.contb_receipt_amt.max()
print 'avg contribution', main.contb_receipt_amt.mean()
print 'amount', main[main.contbr_zip==60610].contb_receipt_amt.max()
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

# another way to get a random sample
```python
from random import sample

sample_size = 3
rows        = sample(range(0, num_rows), sample_size)
sample = donations.ix[rows, ['contb_receipt_amt', 'contbr_nm', 'cand_nm']]

sample.columns = ['Amount contributed', 'Contributor Name', 'Candidate Contributed to']

sample
```

# adding party info
```python

parties = {"Bachmann, Michelle":"Republican",
           "Cain, Herman":"Republican",
           "Gingrich, Newt":"Republican",
           "Huntsman, Jon":"Republican",
           "Johnson, Gary, Earl":"Republican",
           "McCotter, Thaddeus G":"Republican",
           "Obama, Barack":"Democrat",
           "Paul, Ron":"Republican",
           "Pawlenty, Timothy":"Republican",
           "Perry, Rick":"Republican",
           "Roemer, Charles, E. 'Buddy' III":"Republican",
           "Romney, Mitt":"Republican",
           "Santorum, Rick":"Republican",
            "Sanders, Bernard":"Republican",
           "Stein, Jill":"Republican",
           "Trump, Donald J.": "Republican",
          "Clinton, Hillary Rodham": "Democrat",
           "O'Malley, Martin Joseph": "Democrat",
           "Cruz, Rafael Edward 'Ted'": "Republican",
           'Carson, Benjamin S.': "Republican",
           'Kasich, John R.': "Republican"
          }

print donations.cand_nm[800], parties[donations.cand_nm[800]]
```
#### test party info
```python
i = randint(0,num_rows)
print i, donations.cand_nm[i], parties[donations.cand_nm[i]]
```

#### add party as new column
```python
donations['contbr_pt'] = donations.cand_nm.map(parties)

# and count by party
main = donations[donations.cand_nm.isin(['Trump, Donald J.', 'Clinton, Hillary Rodham'])]

main.contbr_pt.value_counts()

```

#### Keep only donors to the 2 main candidates
```python
main = donations[donations.cand_nm.isin(['Trump, Donald J.', 'Clinton, Hillary Rodham'])]

main.contbr_pt.value_counts()
```

#### pivot table
```python
by_occupation = main.pivot_table(
    values=['contb_receipt_amt'],
    index=['contbr_occupation'],
    aggfunc=np.sum
    )

by_occupation

```

#### filter to main occupations
```python
occupations = main.contbr_occupation.value_counts()
occupations[:20]
# Stop and try the above, then:

over_2mm = by_occupation[by_occupation.sum(1) > 200000]
over_2mm

```

#### pivot table - breakout by party
```python
by_occupation = main.pivot_table(
    values=['contb_receipt_amt'],
    columns=['contbr_pt'],
    index=['contbr_occupation'],
    aggfunc=np.sum
    )

by_occupation

```

#### filter to main occupations
```python
occupations = main.contbr_occupation.value_counts()
occupations[:20]
# Stop and try the above, then:

over_2mm = by_occupation[by_occupation.sum(1) > 200000]
over_2mm

```


#### Plots
```python

over_2mm.plot(kind='barh');

avg_donations = donations.groupby("cand_nm").mean().sort_values(by="contb_receipt_amt")
avg_donations["contb_receipt_amt"].plot(kind="bar")
```

#### Other
```python
avg_donations = donations.groupby("contbr_pt").mean().sort_values(by="contb_receipt_amt")
avg_donations["contb_receipt_amt"].plot(kind="bar")

#####
donations.max()

bigdonors = donations[donations['contb_receipt_amt'] == 10000]
bigdonors

```

#### Exercises


#### links
http://benoitdherin.github.io/blackboard-scribbles/dataframes.html

