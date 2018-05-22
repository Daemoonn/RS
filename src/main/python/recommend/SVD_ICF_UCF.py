
# coding: utf-8

# In[1]:


from __future__ import (absolute_import, division, print_function, unicode_literals)
from surprise import KNNBaseline, KNNWithMeans, Dataset, evaluate, print_perf, Reader
from surprise import NormalPredictor, BaselineOnly, KNNBasic, SVD, SVDpp, NMF
from surprise.model_selection import cross_validate
import os
import pandas as pd
import io

file_path = os.path.expanduser('./movielens/ratings.csv')
reader = Reader(line_format='user item rating timestamp', sep=',', skip_lines=1)
data = Dataset.load_from_file(file_path, reader=reader)
# data.split(n_folds=3)
# algo = KNNWithMeans()


############################################

def read_item_names():
    file_name = os.path.expanduser('./movielens/movies.csv')
    rid_to_name = {}
    name_to_rid = {}
    with io.open(file_name, 'r') as f:
        for line in f:
            line = line.split(',')
            rid_to_name[line[0]] = line[1]
            name_to_rid[line[1]] = line[0]
    return rid_to_name, name_to_rid


rid_to_name, name_to_rid = read_item_names()

trainset = data.build_full_trainset()


# In[ ]:


# item-cf

sim_options = {'name': 'pearson_baseline', 'user_based': False}
algo = KNNBaseline(sim_options=sim_options)
algo.fit(trainset)

movieid = raw_input()
# movie = input()
print(rid_to_name[movieid])
print("-----------")
moiveiid = algo.trainset.to_inner_iid(movieid)
neighbors = algo.get_neighbors(moiveiid, k=5)
neighbors_rid = (algo.trainset.to_raw_iid(id) for id in neighbors)
neighbors_name = (rid_to_name[rid] for rid in neighbors_rid)

#for rid in neighbors_rid: print(rid)
#for neighbors_name in neighbors_name: print(name)
for rid in neighbors_rid:
    print(rid, rid_to_name[rid])

cross_validate(algo, data, measures=['RMSE', 'MAE'], cv=5, verbose=True)

# 3114 Toy Story 2 (1999)
# 2355 "Bug's Life
# 78499 Toy Story 3 (2010)
# 6377 Finding Nemo (2003)
# 8961 "Incredibles


#                   Fold 1  Fold 2  Fold 3  Fold 4  Fold 5  Mean    Std     
# RMSE (testset)    0.8686  0.8742  0.8699  0.8899  0.8726  0.8750  0.0077  
# MAE (testset)     0.6622  0.6688  0.6623  0.6788  0.6670  0.6678  0.0060  
# Fit time          11.26   11.28   11.38   11.44   11.53   11.38   0.10    
# Test time         6.50    6.63    6.49    6.43    6.50    6.51    0.06   


# In[ ]:


# user-cf

sim_options = {'name': 'pearson_baseline', 'user_based': True}
algo = KNNBaseline(sim_options=sim_options)
algo.fit(trainset)

movieid = raw_input()
# movie = input()
print(rid_to_name[movieid])
print("-----------")
moiveiid = algo.trainset.to_inner_iid(movieid)
neighbors = algo.get_neighbors(moiveiid, k=5)
neighbors_rid = (algo.trainset.to_raw_iid(id) for id in neighbors)
neighbors_name = (rid_to_name[rid] for rid in neighbors_rid)

#for rid in neighbors_rid: print(rid)
#for neighbors_name in neighbors_name: print(name)
for rid in neighbors_rid:
    print(rid, rid_to_name[rid])

cross_validate(algo, data, measures=['RMSE', 'MAE'], cv=5, verbose=True)


# 3686 Flatliners (1990)
# 586 Home Alone (1990)
# 3017 Creepshow 2 (1987)
# 2187 Stage Fright (1950)
# 2248 Say Anything... (1989)


#        Fold 1  Fold 2  Fold 3  Fold 4  Fold 5  Mean    
# TEST_RMSE0.9076  0.9029  0.8975  0.9101  0.9031  0.9043  
# TEST_MAE0.6929  0.6850  0.6840  0.6945  0.6885  0.6890  
# FIT_TIME0.9886  0.9851  0.9876  0.9550  0.9377  0.9708  
# TEST_TIME1.9343  1.8191  1.8784  1.8139  1.9365  1.8764  


# In[ ]:


#SVD

from collections import defaultdict

def get_top_n(predictions, n=10):
    top_n = defaultdict(list)
    for uid, iid, true_r, est, _ in predictions:
        top_n[uid].append((iid, est))

    for uid, user_ratings in top_n.items():
        user_ratings.sort(key=lambda x: x[1], reverse=True)
        top_n[uid] = user_ratings[:n]

    return top_n

algo = SVD()
algo.fit(trainset)

testset = trainset.build_anti_testset()
predictions = algo.test(testset)

top_n = get_top_n(predictions, n=10)

f = open("cer.txt", "w")  
for uid, user_ratings in top_n.items():
    print(uid, [iid for (iid, _) in user_ratings])
    print(uid, [str(iid) for (iid, _) in user_ratings], file = f) 
f.close()

# top_n.to_csv(os.path.join(sub, 'cer.txt'), header=None, index=False)

# 1 ['1221', '3462', '296', '527', '899', '858', '926', '318', '7153', '1196']
# 2 ['969', '926', '1172', '1060', '3462', '3307', '8874', '318', '745', '1148']
# 3 ['1060', '1221', '1945', '1172', '1224', '2542', '899', '1247', '1256', '926']
# 4 ['50', '527', '593', '2762', '2019', '4973', '923', '48516', '750', '899']
# 5 ['745', '926', '3462', '2542', '308', '318', '2318', '1172', '912', '750']
# 6 ['926', '318', '5418', '1281', '916', '1212', '7502', '1252', '899', '1233']
# 7 ['858', '908', '48516', '1230', '1252', '1221', '750', '3462', '969', '5995']
# 8 ['1221', '923', '994', '913', '1217', '1136', '48516', '2019', '905', '1213']
# 9 ['858', '926', '3462', '50', '922', '1221', '1172', '904', '1276', '969']


# In[ ]:


algo = SVD()
algo.fit(trainset)

uid = str(196) 
iid = str(302) 

pred = algo.predict(uid, iid, r_ui=4, verbose=True)


# In[10]:


from surprise import SVDpp, evaluate
algo = SVDpp()
perf = evaluate(algo, data, measures=['RMSE', 'MAE'])


# In[ ]:





# In[ ]:


import surprise
surprise.dump.dump('./RS.model', algo=algo)
# algo = surprise.dump.load('./RS.model')

