from django.urls import path, include, re_path
from .models import Accounts
#from .views import CreatePetInfo
from . import login
from . import photo
from . import views
from . import model_views

app_name = 'cookie'

urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category')),
    path('sign_up/', login.create_sign_info, name='create_sign_info'),
    path('log_in/', login.read_login_info, name='read_login_info'),
    path('photo_upload/', photo.upload_photo, name='upload_photo'),
    path('photo_delete/', photo.delete_file, name='delete_file'),
    path('model_view/', model_views.process_uploaded_image, name='predict_view'),
]