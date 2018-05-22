
# coding: utf-8

# In[30]:


import pandas as pd
import numpy as np
from sklearn.metrics import mean_squared_error
import requests
import json
import urllib
import os.path
from keras.applications import ResNet50
from keras.applications.vgg16 import preprocess_input
from keras.preprocessing import image as kimage
from PIL import Image
from IPython.display import Image
from IPython.display import display
from IPython.display import HTML

rating_f = './movielens/ratings.csv'
link_f = './movielens/links.csv'

df = pd.read_csv(rating_f,sep=',')
df_id = pd.read_csv(link_f,sep=',')
df = pd.merge(df,df_id,on=['movieId'])


# In[34]:


link_f    = './movielens/links.csv'
poster_pt = './posters/'
download_posters = 2333

headers = {'Accept':'application/json'}
payload = {'api_key':'b0c718528917515e5f44ef99c52a3590'} 
response = requests.get('http://api.themoviedb.org/3/configuration',params=payload,headers=headers)
response = json.loads(response.text)

base_url = response['images']['base_url']+'w185'

def get_poster(imdb,base_url):
    file_path = ''
    imdb_id = 'tt0{0}'.format(imdb)
    movie_url = 'http://api.themoviedb.org/3/movie/{:}/images'.format(imdb_id)
    response = requests.get(movie_url,params=payload,headers=headers)
    try:
        file_path = json.loads(response.text)['posters'][0]['file_path']
    except:
        print('Failed to get url for imdb: {0}'.format(imdb))

    return base_url+file_path

df_id = pd.read_csv(link_f,sep=',')
idx_to_mv = {}
for row in df_id.itertuples():
    idx_to_mv[row[1]-1] = row[2]

mvs = [0]*len(idx_to_mv.keys())
for i in range(len(mvs)):
    if i in idx_to_mv.keys() and len(str(idx_to_mv[i])) == 6:
        mvs[i] = idx_to_mv[i]
mvs = list(filter(lambda imdb:imdb!=0,mvs))
mvs = mvs[:download_posters]
total_mvs = len(mvs)
total_mvs = 2333


# In[36]:


image = [0]*total_mvs
x     = [0]*total_mvs
prediction_result = 'prediction_result.csv'

print("Brigitta is with you")

def save_predict_res(matrix_res):
    pe = dict([(x,[]) for x in range(matrix_res.shape[0])])
    for i in range(matrix_res.shape[0]):
        pe[i].extend(matrix_res[i])
    df = pd.DataFrame(data=pe)
    df.to_csv(prediction_result)

def load_result_matrix(file):
    if not os.path.exists(file):
        return None
    m_r = pd.read_csv(file,sep=',')
    f_r = np.zeros((m_r.shape[1]-1,m_r.shape[0]))
    for i in range(m_r.values.shape[1]-1):
        f_r[i] = m_r[str(i)].values.tolist()
    return f_r

matrix_res = load_result_matrix(prediction_result)
if matrix_res is None:
    for i in range(total_mvs):    
       # img_file_path = poster_pt+str(i)+'.jpg'
       # image[i] = cv2.imread(img_file_path)
       # image[i] = cv2.resize(image[i], (224, 224))
       # x[i] = cv2.toar  image[i])
        image[i] = kimage.load_img(poster_pt+str(i)+'.jpg',target_size=(224,224))
        x[i] = kimage.img_to_array(image[i])
        x[i] = np.expand_dims(x[i],axis=0)
        x[i] = preprocess_input(x[i])
        
    #model = InceptionV3(weights='imagenet', include_top=False)
    model = ResNet50(weights='imagenet')
    print (total_mvs)
    prediction = [0]*total_mvs
    matrix_res = np.zeros([total_mvs,1000])
    for i in range(total_mvs):
        prediction[i]=model.predict(x[i]).ravel()
        print(i)
        matrix_res[i,:] = prediction[i]

    save_predict_res(matrix_res)


# In[56]:


similarity_deep = matrix_res.dot(matrix_res.T)
norms = np.array([np.sqrt(np.diagonal(similarity_deep))])
similarity_deep = (similarity_deep/(norms*norms.T))
n_display   = 5
base_mv_idx = 888
mv = [x for x in np.argsort(similarity_deep[base_mv_idx])[:-n_display-1:-1]]


# In[57]:


images = ''
for i in range(len(mv)):
    images+="<img style='width: 100px; margin: 0px; float: left; border: 1px solid black;' src={0}.jpg />".format(poster_pt+str(mv[i]))

display(HTML(images))

