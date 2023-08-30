#-*- coding:utf-8 -*-
import os
import random
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from tensorflow.keras.layers import Dropout
from tensorflow.keras.regularizers import l2
from sklearn.utils import class_weight
from keras import backend as K
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.layers import Dense
from tensorflow.keras import Sequential
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.applications import (
    ResNet50, ResNet101, ResNet152, ResNet50V2, ResNet101V2, ResNet152V2, 
    DenseNet121, DenseNet169, DenseNet201, 
    InceptionV3, InceptionResNetV2, EfficientNetB0
)

# Set CUDA device
os.environ["CUDA_VISIBLE_DEVICES"] = "0"
val_path = '/content/val_cat/'

seed = 2


def set_seed(seed):
    random.seed(seed)
    np.random.seed(seed)
    tf.random.set_seed(seed)

class Import_data:
    def __init__(self, train_path):
        self.train_path = train_path
        self.test_path = val_path

    def train(self):
      train_datagen = ImageDataGenerator(
          featurewise_std_normalization=True,
          zoom_range=0.2,
          channel_shift_range=0.1,
          rotation_range=20,
          width_shift_range=0.2,
          height_shift_range=0.2,
          horizontal_flip=True
      )

      val_datagen = ImageDataGenerator()  # 검증 데이터용으로 증강을 적용하지 않는 데이터 제너레이터를 생성합니다.

      train_generator = train_datagen.flow_from_directory(self.train_path, target_size=(224, 224), batch_size=16)
      val_generator = val_datagen.flow_from_directory(self.test_path, target_size=(224, 224), batch_size=16)  # 증강이 적용되지 않는 제너레이터를 사용합니다.

      return train_generator, val_generator


class Load_model:
    MODEL_MAP = {
        'resnet_v1_50': ResNet50,
        'resnet_v1_101': ResNet101,
        'resnet_v1_152': ResNet152,
        'resnet_v2_50': ResNet50V2,
        'resnet_v2_101': ResNet101V2,
        'resnet_v2_152': ResNet152V2,
        'densenet_121': DenseNet121,
        'densenet_169': DenseNet169,
        'densenet_201': DenseNet201,
        'inception_v3': InceptionV3,
        'inception_v4': InceptionResNetV2,
        'efficientnet': EfficientNetB0
    }

    def __init__(self, train_path, model_name):
        self.num_class = len(os.listdir(train_path))
        self.model_name = model_name

    def build_network(self):
        network = self.MODEL_MAP[self.model_name](include_top=False, weights='imagenet', input_shape=(224, 224, 3), pooling='avg')
        model = Sequential([network,Dropout(0.5), Dense(2048, activation='relu',  kernel_regularizer=l2(0.01)), Dense(self.num_class, activation='softmax')])
        model.summary()
        return model

class Fine_tunning:
    def __init__(self, train_path, model_name, epoch):
        self.data = Import_data(train_path)
        self.train_data, self.val_data = self.data.train()
        self.load_model = Load_model(train_path, model_name)
        self.epoch = epoch
        self.model_name = model_name
        self.train_path = train_path

    def get_class_weights(self):
      # 클래스별 샘플 수를 얻습니다.
      samples_per_class = np.bincount(self.train_data.classes)
      class_weights = class_weight.compute_class_weight(
          'balanced',
          classes=np.unique(self.train_data.classes),
          y=self.train_data.classes
        )     
      return dict(enumerate(class_weights))

    def training(self):
        data_name = os.path.basename(os.path.dirname(self.train_path))
        optimizer = tf.keras.optimizers.SGD(learning_rate=0.001, momentum=0.999, nesterov=True)
        model = self.load_model.build_network()

        save_folder = os.path.join('./model_saved', data_name, f"{self.model_name}_{self.epoch}")
        os.makedirs(save_folder, exist_ok=True)
        
        check_point = ModelCheckpoint(os.path.join(save_folder, 'model-{epoch:03d}-{acc:03f}-{val_acc:03f}.h5'),
                                    verbose=1, monitor='val_acc', save_best_only=True, mode='auto')
        model.compile(loss='categorical_crossentropy', optimizer=optimizer, metrics=['acc'])
        
        # 클래스 가중치를 가져옵니다.
        class_weights = self.get_class_weights()
        
        history = model.fit(self.train_data,
                            steps_per_epoch=self.train_data.samples / self.train_data.batch_size,
                            epochs=self.epoch,
                            validation_data=self.val_data,
                            validation_steps=self.val_data.samples / self.val_data.batch_size,
                            callbacks=[check_point],
                            class_weight=class_weights, # 클래스 가중치를 추가합니다.
                            verbose=1)
        return history

    def save_accuracy(self, history):
      data_name = self.train_path.split('/')
      data_name = data_name[len(data_name)-3]
      save_folder = './model_saved/' + data_name + '/' + self.model_name + '_' + str(self.epoch) + '/'
      acc = history.history['acc']
      val_acc = history.history['val_acc']
      loss = history.history['loss']
      val_loss = history.history['val_loss']
      epochs = range(len(acc))
      epoch_list = list(epochs)

      df = pd.DataFrame({'epoch': epoch_list, 'train_accuracy': acc, 'validation_accuracy': val_acc},
                        columns=['epoch', 'train_accuracy', 'validation_accuracy'])
      df_save_path = save_folder + 'accuracy.csv'
      df.to_csv(df_save_path, index=False, encoding='euc-kr')

      plt.plot(epochs, acc, 'b', label='Training acc')
      plt.plot(epochs, val_acc, 'r', label='Validation acc')
      plt.title('Training and validation accuracy')
      plt.legend()
      save_path = save_folder + 'accuracy.png'
      plt.savefig(save_path)
      plt.cla()

      plt.plot(epochs, loss, 'b', label='Training loss')
      plt.plot(epochs, val_loss, 'r', label='Validation loss')
      plt.title('Training and validation loss')
      plt.legend()
      save_path = save_folder + 'loss.png'
      plt.savefig(save_path)
      plt.cla()

      name_list = os.listdir(save_folder)
      h5_list = []
      for name in name_list:
          if '.h5' in name:
              h5_list.append(name)
      h5_list.sort()
      h5_list = [save_folder + name for name in h5_list]
      for path in h5_list[:len(h5_list) - 1]:
          os.remove(path)
      K.clear_session()
