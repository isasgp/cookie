import os
import numpy as np
from PIL import Image
import numpy as np
import cv2
from django.http import JsonResponse
from django.core.files.storage import FileSystemStorage

from .dncskin_segmentation import common_conf, run_segmentation
from django.conf import settings

def process_uploaded_image(file_path): # - modify ! -
    img = Image.open(file_path) # - modify ! -
    img = img.resize(common_conf.IMAGE_SIZE)
    img_vis = np.array(img)  # copy image for visualization
    img = np.array(img) / 255.

    img_overlays, pred_clses = run_segmentation.run_segm_model(img, img_vis)
    img_overlays = [img_overlay[:, :, ::-1] for img_overlay in img_overlays]

    alpha = 0.5
    blended_img = cv2.addWeighted(img_overlays[0], 1 - alpha, img_overlays[1], alpha, 0)
    blended_img = blended_img[:, :, ::-1]
    blended_img_cv = cv2.cvtColor(blended_img, cv2.COLOR_RGB2BGR)
    resized_blended_img_cv = cv2.resize(blended_img_cv, dsize=(500, 500))

    # output_fs = FileSystemStorage(location=settings.DOWNLOAD_ROOT)
    # blended_image = output_fs.save('blended_image', resized_blended_img_cv)
    # blended_image_url = output_fs.url(blended_image)

    # text_data = str(pred_clses[1])
    # txt_file_name = 'trueOrFalse.txt'
    # txt_file_path = output_fs.save(txt_file_name, text_data)
    # trueOrFalse_url = output_fs.url(txt_file_path)

    img_save_path = os.path.join(settings.DOWNLOAD_ROOT, 'blended_image.jpg')
    cv2.imwrite(img_save_path, resized_blended_img_cv)

    return str(pred_clses[1])
    # txt_save_path = os.path.join(settings.DOWNLOAD_ROOT, 'text_data.txt')
    # with open(txt_save_path, 'w') as txt_file:
    #     txt_file.write(text_data)

    # result = {
    #     'blended_image_path': blended_image_url,
    #     'txt_file_path': trueOrFalse_url
    # }
    # return result