from django.contrib import admin
from cookie.models import Accounts, Pet, Walk, Breed, User

admin.site.register(Accounts)
admin.site.register(Pet)
admin.site.register(Walk)
admin.site.register(Breed)
admin.site.register(User)

# Register your models here.
