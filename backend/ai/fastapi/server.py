from colabcode import ColabCode
from fastapi import FastAPI, File, UploadFile
import pandas as pd
from sklearn import metrics
import os
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import ImageDataGenerator
import numpy as np
from keras.preprocessing import image
from pydantic import BaseModel
import uuid
from tensorflow.keras.preprocessing import image
import math


os.environ["CUDA_VISIBLE_DEVICES"]="0"

# Constants
MODEL_PATH_CAT = '/content/drive/MyDrive/helpet-training/model/model_saved/train_cat/efficientnet_100/'
MODEL_PATH_DOG = '/content/drive/MyDrive/helpet-training/model/model_saved/dog_train_data/efficientnet_13/'
WEIGHT_CAT = 'model-020-0.939960-0.807720.h5'
WEIGHT_DOG = 'model-008-0.951690-0.984176.h5'
IMG_PATH_DOG = './img/dog-test-set/'
IMG_PATH_CAT = './img/cat-test-set/'
UPLOAD_DIRS_CAT = [
    "/content/drive/MyDrive/helpet/backend/ai/fastapi/img/cat-test-set/asymptomatic",
    "/content/drive/MyDrive/helpet/backend/ai/fastapi/img/cat-test-set/symptomatic"
]

UPLOAD_DIRS_DOG = [
    "/content/drive/MyDrive/helpet/backend/ai/fastapi/img/dog-test-set/asymptomatic",
    "/content/drive/MyDrive/helpet/backend/ai/fastapi/img/dog-test-set/symptomatic"
]


DISEASE_NAMES_CAT = ["안검내반증", "안검염", "안검종양", "유루증", "핵경화"]
DISEASE_NAMES_DOG = ["결막염", "궤양성각막질환", "백내장", "비궤양성각막질환", "색소침착성각막염"] 

# FastAPI app
app = FastAPI()

class DiagnosisResult(BaseModel):
    asymptomaticProbability : float
    symptomProbability : float
    diseaseNames: list[str]


def perform_diagnosis(model_path, weight, test_Path, diseaseNames):
    model = load_model(model_path + weight)

    datagen_test = ImageDataGenerator()
    generator_test = datagen_test.flow_from_directory(
        directory=test_Path,
        target_size=(224, 224),
        batch_size=256,
        shuffle=False
    )
    y_predict = model.predict(generator_test)
    cls_pred = model.predict_generator(generator_test, verbose=1, workers=0)
    cls_pred_argmax = cls_pred.argmax(axis=1)

    print(f'-----{diseaseNames}------')
    print(generator_test.class_indices)
    print(cls_pred)

    return y_predict[0].tolist()

cc = ColabCode(port=8000, code=False)

async def diagnosis_service(postImg: UploadFile, upload_dirs,  model_path, weight, test_Path, diseaseNames):
    content = await postImg.read()
    filename ='{}.jpg'.format('postimg') 

    for upload_dir in upload_dirs:
        with open(os.path.join(upload_dir,filename), "wb") as fp:
            fp.write(content)
    
    result = [perform_diagnosis(MODEL_PATH_CAT, WEIGHT_CAT, test_Path,DISEASE_NAMES_CAT)]
    
    asymptomaticProbability = float(int(result[0][0] * 100))
    symptomProbability = float(int(result[0][1] * 100))
    return DiagnosisResult(
        asymptomaticProbability=asymptomaticProbability,
        symptomProbability = symptomProbability,
        diseaseNames = diseaseNames
    )


@app.post("/api/diagnosis/dog/eye")
async def dogEyeDiagnosisController(postImg: UploadFile):
    return await diagnosis_service(postImg,UPLOAD_DIRS_DOG, MODEL_PATH_DOG, WEIGHT_DOG, IMG_PATH_DOG,DISEASE_NAMES_DOG)
    

@app.post("/api/diagnosis/cat/eye")
async def catEyeDiagnosisController(postImg: UploadFile):
    return await diagnosis_service(postImg,UPLOAD_DIRS_CAT, MODEL_PATH_CAT, WEIGHT_CAT, IMG_PATH_CAT,DISEASE_NAMES_CAT)
    



cc.run_app(app=app)