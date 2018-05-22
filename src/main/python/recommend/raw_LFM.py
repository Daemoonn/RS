
# coding: utf-8

# 推荐系统
# 
# 数据分析 (数据集 不需要特征工程 数据分布不完美)
# 
# 基于内容的推荐 (提取特征) 
# 关联规则 (主要用于购物车分析)
# 协同过滤
#  基于邻域的算法 (针对用户行为计算物品或用户相似度)
#  隐语义模型 (通过隐含特征联系用户兴趣和物品)
# 神经网络 (迁移学习 ResNet50 imagenet权重 提取特征 对比向量相似度)
# 
# 维护相似度矩阵 进而可以计算出对新物品的评分
# 
# 矩阵分解技术是实现隐语义模型使用最为广泛的一种方法
# 通过矩阵分解将稀疏且高维的User-Item矩阵分解为两个低维矩阵
# 通过定义损失函数 可以通过梯度下降求解用户特征矩阵和物品特征矩阵
# 两者的内积即为所要预测的评分
# 
# 深度学习 迁移学习的思路用resnet50 imagenet权重 提取特征 对比相似度做排序 

# In[16]:


import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import time
import warnings
warnings.filterwarnings('ignore')
np.random.seed(1)
get_ipython().magic(u'matplotlib inline')
plt.style.use('ggplot')

data = pd.read_csv('./movielens/ratings.csv')
movies = pd.read_csv('./movielens/movies.csv')
movies = movies.set_index('movieId')[['title', 'genres']]


# In[17]:


# 用户数量 电影数量 有效评分数量
print data.userId.nunique(), 'users'
print data.movieId.nunique(), 'movies'
print data.userId.nunique() * data.movieId.nunique(), 'possible ratings'


# In[18]:


# 每个用户评了多少部电影
fig = plt.figure(figsize=(10, 10))
ax = plt.hist(data.groupby('userId').apply(lambda x: len(x)).values, bins=50)
plt.title("Number of ratings per user")


# In[19]:


# 每部电影有多少评分
fig = plt.figure(figsize=(10, 10))
ax = plt.hist(data.groupby('movieId').apply(lambda x: len(x)).values, bins=50)
plt.title('Number of ratings per movie')


# In[20]:


# 数据分布
fig = plt.figure(figsize=(10, 10))
ax = plt.hist(data.rating.values, bins=5)
plt.title("Distribution of ratings")


# In[21]:


# 用户平均打分
fig = plt.figure(figsize=(10, 10))
ax = plt.hist(data.groupby('userId').rating.mean().values, bins=10)
plt.title("Average rating per user")


# In[22]:


# 电影平均打分
fig = plt.figure(figsize=(10, 10))
ax = plt.hist(data.groupby('movieId').rating.mean().values, bins=10)
plt.title('Average rating per movie')


# In[26]:


ratings = data[['userId', 'movieId', 'rating']].values
np.random.shuffle(ratings)
n_users, n_items, _ = ratings.max(axis=0) + 1
n_users = int(n_users)
n_items = int(n_items)
n = len(ratings)
split_ratios = [0, 0.7, 0.85, 1]
train_ratings, valid_ratings, test_ratings = [ratings[int(n*lo):int(n*up)] for (lo, up) in zip(split_ratios[:-1], split_ratios[1:])]


# In[34]:


gradients = ["dL_db", "dL_dbu", "dL_dbv", "dL_dU", "dL_dV"]

class Model(object):
    def __init__(self, latent_factors_size, L2_bias=0, L2_emb=0):
        self.model_parameters = []
        self.gradients = []
        for (name, value) in self.initialize_parameters(latent_factors_size):
            setattr(self, name, value)
            self.gradients.append("dL_d%s" % name)
            self.model_parameters.append(name)
    
    def save_parameters(self):
        return [(name, np.copy(getattr(self, name))) for name in self.model_parameters]
    
    def load_parameters(self, parameters):
        for (name, value) in parameters:
            setattr(self, name, value)
    
    def initialize_parameters(self, latent_factors_size=100, std=0.05):
        U = np.random.normal(0., std, size=(n_users + 1, latent_factors_size))
        V = np.random.normal(0., std, size=(n_items + 1, latent_factors_size))
        u = np.zeros(n_users + 1)
        v = np.zeros(n_items + 1)
        return zip(("b", "u", "v", "U", "V"), (0, u, v, U, V))
            
    def compute_gradient(self, user_ids, item_ids, ratings):
        user_ids = user_ids.astype('int')
        item_ids = item_ids.astype('int')
        predicted_ratings = self.predict(user_ids, item_ids)
        residual = ratings - predicted_ratings

        dL_db = -2 * residual
        dL_dbu = -2 * residual
        dL_dbv = -2 * residual

        eu = self.U[user_ids]
        ev = self.V[item_ids]

        dL_dU = -2 * residual * ev
        dL_dV = -2 * residual * eu

        l2 = 0.1
        dl2eu_dU = l2 * 2*eu
        dl2ev_dV = l2 * 2*ev
        dl2bu_dbu = l2 * 2*self.u[user_ids]
        dl2bv_dbv = l2 * 2*self.v[item_ids]
        
        dL_dbu = dL_dbu + dl2bu_dbu
        dL_dbv = dL_dbv + dl2bv_dbv
        dL_dU = dL_dU + dl2eu_dU
        dL_dV = dL_dV + dl2ev_dV
        
        return dict([(g, eval(g)) for g in gradients])
    
    def predict(self, user_ids, item_ids):
        user_ids = user_ids.astype('int')
        item_ids = item_ids.astype('int')
        return sum([self.b, 
                    self.u[user_ids], 
                    self.v[item_ids], 
                    (self.U[user_ids] * self.V[item_ids]).sum(axis=-1)])
    
    def update_parameters(self, user, item, rating, learning_rate = 0.005):
        user = user.astype('int')
        item = item.astype('int')
        gradients = self.compute_gradient(user, item, rating)
        self.b = self.b - learning_rate * gradients['dL_db']
        self.u[user] = self.u[user] - learning_rate * gradients['dL_dbu']
        self.v[item] = self.v[item] - learning_rate * gradients['dL_dbv']
        self.U[user] = self.U[user] - learning_rate * gradients['dL_dU']
        self.V[item] = self.V[item] - learning_rate * gradients['dL_dV']


# In[35]:


def sample_random_training_index():
    return np.random.randint(0, len(train_ratings))

def compute_rmse(x, y):
    return ((x - y)**2).mean()**0.5

def get_rmse(ratings):
    return compute_rmse(model.predict(*ratings.T[:2]), ratings.T[2])

def get_trainset_rmse():
    return get_rmse(train_ratings)

def get_validset_rmse():
    return get_rmse(valid_ratings)

def get_testset_rmse():
    return get_rmse(test_ratings)

#init
model = Model(latent_factors_size=100)
model.b = train_ratings[:,2].mean()

sgd_iteration_count = 0
best_validation_rmse = 9999
patience = 0
update_frequency = 10000

train_errors = []
valid_errors = []
test_errors = []

best_parameters = None


# In[36]:


start_time = time.time()

while True:
    try:
        if sgd_iteration_count%update_frequency == 0:
            train_set_rmse = get_trainset_rmse()
            valid_set_rmse = get_validset_rmse()
            test_set_rmse = get_testset_rmse()
            
            train_errors.append(train_set_rmse)
            valid_errors.append(valid_set_rmse)
            test_errors.append(test_set_rmse)
            
            print 'Iteration:      ', sgd_iteration_count
            print 'Validation RMSE:', valid_set_rmse

            if valid_set_rmse < best_validation_rmse:
                print 'test RMSE      :', test_set_rmse
                print 'best validation error up to now !'
                patience = 0
                best_validation_rmse = valid_set_rmse
                best_parameters = model.save_parameters()
            else:
                patience += 1
                if patience >= 20:
                    print 'gggggggggggggggggggggggggggggggggggg!'
                    break
            print
        training_idx = sample_random_training_index()
        user, item, rating = train_ratings[training_idx]
        model.update_parameters(user, item, rating)
        sgd_iteration_count += 1
    except KeyboardInterrupt:
        print 'stopped optimization'
        print 'valid set performance=%s' % compute_rmse(model.predict(*valid_ratings.T[:2]), valid_ratings[:,2])
        print 'test set performance=%s' % compute_rmse(model.predict(*test_ratings.T[:2]), test_ratings[:,2])
        break
        
model.load_parameters(best_parameters)
stop_time = time.time()

print 'Optimization time : ', (stop_time - start_time)/60., 'minutes'


# In[37]:


test_predictions = model.predict(*test_ratings.T[:2])
test_df = pd.DataFrame({'userId': test_ratings[:, 0],
                        'movieId': test_ratings[:, 1],
                        'rating': test_ratings[:, 2],
                        'prediction': test_predictions})
test_df.head()
print 'RMSE\t\t', ((test_df.rating - test_df.prediction)**2).mean()**0.5
print 'MAE\t\t', (test_df.rating - test_df.prediction).abs().mean()
plt.plot(*test_df.groupby('rating').prediction.mean().reset_index().values.T)
plt.plot(np.arange(0, 6), np.arange(0, 6), '--')
plt.xlim([0.5, 5])
plt.ylim([0.5, 5])
plt.xlabel('True Rating')
plt.ylabel('Mean Predicted Rating')


# In[38]:


best_predicted_rating_per_user = test_df.groupby('userId').apply(lambda x: x.sort_values('prediction', ascending=False).head(1).rating)
best_rating_per_user = test_df.groupby('userId').apply(lambda x: x.sort_values('rating', ascending=False).head(1).rating)
mean_rating_per_user = test_df.groupby('userId').rating.mean()
print 'Best rating per user\t\t', best_rating_per_user.mean()
print 'Best predicted rating per user\t', best_predicted_rating_per_user.mean()
print 'Best mean rating per user\t', mean_rating_per_user.mean()


# In[39]:


movies_embeddings = dict([(i, model.V[i]) for i in movies.index.values])

def compute_cosine_similarity(movieId):
    movie_embedding = movies_embeddings[movieId]
    movie_embedding_norm = (movie_embedding**2).sum()**0.5
    similarity = dict([(movie, ((movie_embedding*emb).sum()) / (((emb**2).sum()**0.5) * movie_embedding_norm))
                              for (movie, emb) in movies_embeddings.iteritems()])
    return  similarity

def compute_euclidian_similarity(movieId):
    movie_embedding = movies_embeddings[movieId]
    similarity = dict([(movie, -((movie_embedding-emb)**2).sum()**0.5)
                              for (movie, emb) in movies_embeddings.iteritems()])
    return  similarity
# movieId = 5952 
movieId = 79132 
# movieId = 1 

sorted_movies = sorted(compute_cosine_similarity(movieId).items(), key = lambda x:x[1])
# sorted_movies = sorted(compute_euclidian_similarity(movieId).items(), key = lambda x:x[1])

print 'top15'
for i in range(1, 16):
    id, sim = sorted_movies[-i]
    print i, movies.loc[id].title, '\t', movies.loc[id].genres, sim
    print


# In[ ]:





# In[ ]:




