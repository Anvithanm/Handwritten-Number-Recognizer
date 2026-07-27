'''
========================================================================================================================
 * Author = Anvitha Hiriadka
 * Submitted on 04/27/2024
 * Handwritten Number Recognizer Model --> This file contains handwritten number recognizer using TensorFlow,
 leveraging convolutional neural networks (CNNs) to accurately classify digits from the MNIST dataset.
 * Achieved high accuracy in recognizing handwritten digits, demonstrating the effectiveness of deep learning
 techniques for image classification tasks
 The model here is trained and saved, which can be used for predicting number from new images of number
========================================================================================================================
'''
#Import the necessary libraries
import os
import tensorflow as tf
from tensorflow.keras import layers, models
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt

# (training code commented out omitted for brevity)

def recognize_digit(image_path):
    image = Image.open(image_path).convert('L')  # Convert to grayscale
    image = image.resize((28, 28))  # Resize to 28x28 pixels
    # Invert the colors (convert white writing to black and black background to white)
    inverted_image = Image.eval(image, lambda x: 255 - x)
    # Convert the image to a numpy array and normalize pixel values to [0, 1]
    image_array = np.array(inverted_image) / 255.0

    # Reshape the image array to match the input shape expected by the model
    image_array = image_array.reshape((1, 28, 28))
    # Display the image
    plt.imshow(image_array[0], cmap='gray')
    plt.axis('off')
    plt.title('Input Image')
    plt.show()

    # Load the saved model from the repo resources
    script_dir = os.path.dirname(os.path.abspath(__file__))
    model_path = os.path.abspath(os.path.join(script_dir, '..', 'src', 'main', 'resources', 'models', 'handwritten_digit_model2.h5'))
    loaded_model = tf.keras.models.load_model(model_path)
    predictions = loaded_model.predict(image_array)
    predicted_class = np.argmax(predictions)
    return predicted_class

# Load the image
image_path = "processed_image.png"
# Perform prediction
predicted_digit = recognize_digit(image_path)
#Printing the predicted digit
print("Predicted digit:", predicted_digit)
