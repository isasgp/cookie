from django.http import JsonResponse
from rest_framework.decorators import api_view
from .models import Accounts

@api_view(['POST'])
def create_sign_info(request):
    if request.method == 'POST':
        user_id = request.data.get('user_id')
        password = request.data.get('password')

        Accounts.objects.create(USER_ID=user_id, PASSWORD=password)

        return JsonResponse({'message': 'signup_success'}, status=201)

@api_view(['POST'])
def read_login_info(request):
    if request.method == 'POST':
        user_id = request.data.get('user_id')
        password = request.data.get('password')

        search_user = Accounts.objects.filter(USER_ID=user_id, PASSWORD=password)

        if search_user.exists():
            response_data = {'message': 'login_success'}
        else:
            response_data = {'message': 'login_failed'}
        
        return JsonResponse(response_data)
        