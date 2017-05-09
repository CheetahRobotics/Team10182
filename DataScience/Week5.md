Setup:
```python
%matplotlib inline
import matplotlib.pyplot as plt
import numpy as np
import scipy

#### 1. Graphing helper function
def setup_graph(title='', x_label='', y_label='', fig_size=None):
    fig = plt.figure()
    if fig_size != None:
        fig.set_size_inches(fig_size[0], fig_size[1])
    ax = fig.add_subplot(111)
    ax.set_title(title)
    ax.set_xlabel(x_label)
    ax.set_ylabel(y_label)
```

#### 2. What does np.linspace() do?
```python
t = np.linspace(0, 4, 5)
print t
```

#### 3. What does np.sin() do?
```python
t = np.linspace(0, 10, 15)

signal = [np.sin(i) for i in t] 

plt.plot(t,signal)
```

#### 4. What does the np prefix refer to?
####
