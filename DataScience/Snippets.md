#### Code snippets
```python
import math
def fixzip(row):
    if math.isnan(row['contbr_zip']):
        return 0
    else:
        return int(row['contbr_zip']) if row['contbr_zip'] < 100000. else int(row['contbr_zip']/1000.)

donations['zip'] = donations.apply(fixzip, axis=1)
dz60610 = donations[donations['zip']==60610]

```

##### Various ways to search by strings
```python
da1 = donations[donations['contbr_nm'].str.contains('A')]
da2 = donations[donations['contbr_nm'].apply(lambda x: x[0:5]=='SMITH')]
da3 = donations[donations['contbr_nm'].apply(lambda x: x.find('SMITH') > -1 and x.index('SMITH') == 0 )]

```

