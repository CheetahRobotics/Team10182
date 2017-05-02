
#ds.Item.unique()
#ds.pivot_table(values=['Calories'],index=['Item'])
ds.groupby("Item").sum().sort_values(by="Calories")['Calories']


ds['CholesterolPerCalorie'] = ds.Cholesterol/ds.Calories
#ds.groupby("Item").sum().sort_values(by="CholesterolPerCalorie")['CholesterolPerCalorie']

grouped = ds[ds['CholesterolPerCalorie'] > .4].groupby("Item").mean().sort_values(by="CholesterolPerCalorie")
grouped["CholesterolPerCalorie"].plot(kind="bar")
# table version of same:
ds[ds['CholesterolPerCalorie'] > .4].sort_values(by="CholesterolPerCalorie").pivot_table(
    values=['CholesterolPerCalorie'],
    index=['Item'],
    aggfunc=np.sum
    )
    

ds.plot.scatter(x='Sugars', y='Calories')

sns.swarmplot(x="Sugars", y="Calories", data=ds, hue="Category", size=10)

ds.plot.scatter(x='Total Fat', y='Calories')

ds.plot.scatter(x='Sugars', y='Calories')

sns.swarmplot(x="Sugars", y="Calories", data=ds, hue="Category", size=10)

ds.plot.scatter(x='Total Fat', y='Calories')

ds1 = ds[[u'Category', u'Item', u'Calories',       u'Total Fat (% Daily Value)', \
                u'Cholesterol (% Daily Value)', \
       u'Sodium (% Daily Value)',        u'Carbohydrates (% Daily Value)', \
             u'Vitamin A (% Daily Value)', \
       u'Vitamin C (% Daily Value)',       u'Calcium (% Daily Value)', u'Iron (% Daily Value)','Sugars']]
       
sns.pairplot(ds1, hue="Category")

sns.lmplot("Calories", "Vitamin A (% Daily Value)", data=ds, hue='Category')

sns.lmplot("Calories", "Sugars", data=ds, hue='Category')
