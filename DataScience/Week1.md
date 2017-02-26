# Data Science with Python/Jupyter/Pandas

## Installation

This is very easy.

1. Download the Python 2.7 version from here: https://www.continuum.io/downloads
2. Then type:
    `jupyter notebook`
    
3. This will open a browser and put you at the start page for Jupyter. Click on 'New' and then 'Python 2'.

## Week 1.

Learn Python! Using: http://nbviewer.jupyter.org/github/ehmatthes/intro_programming/blob/master/notebooks/index.ipynb

## Week 2.

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
%matplotlib inline
```

