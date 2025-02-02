from rest_framework import serializers
from .models import Accounts, Pet, Walk, Breed, User

class AccountsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Accounts
        fields = ['USER_ID', 'PASSWORD']

class PetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Pet
        fields = "__all__"

class WalkSerializer(serializers.ModelSerializer):
    class Meta:
        model = Walk
        fields = ['WALK_PLACE', 'WALK_METHOD']

class BreedSerializer(serializers.ModelSerializer):
    class Meta:
        model = Breed
        fields = ['PET_BREED', 'PET_METHOD']
        
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = "__all__"
