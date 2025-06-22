from django.urls import path
from .views import score, health_check

urlpatterns = [
    path('', score),
    path('health/', health_check),
] 