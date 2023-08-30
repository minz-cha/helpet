from efficientNet_ops_3 import *

train_path = '/content/drive/MyDrive/helpet-training/dog_train_data/'
model_name = 'efficientnet'
epoch = 13

import os

if __name__ == '__main__':
    fine_tunning = Fine_tunning(train_path=train_path,
                                model_name=model_name,
                                epoch=epoch)
    history = fine_tunning.training()
    fine_tunning.save_accuracy(history)

    from tensorflow.python.client import device_lib
    device_lib.list_local_devices()