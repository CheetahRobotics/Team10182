# Week 2.

1. Download the dataset we'll be using from [here](DataSet1.csv). (right-click and select 'Save Link As'.

#### Code

```python
import pandas as pd
donations = pd.read_csv("C:/Users/bob/Dropbox/jupyter/P00000001-IL.csv", index_col=False)
```

```python
donations.shape

donations.head(2)
```

#### Plots
```python
%matplotlib inline      # required before doing any plots.
tot["contb_receipt_amt"].plot(kind="bar")

avg_donations = donations.groupby("cand_nm").mean().sort_values(by="contb_receipt_amt")
avg_donations["contb_receipt_amt"].plot(kind="bar")
```

