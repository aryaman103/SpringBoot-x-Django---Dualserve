from django.test import TestCase
from rest_framework.test import APITestCase
from django.urls import reverse
import json

class ScoringTests(APITestCase):
    def test_score_endpoint(self):
        url = reverse('score')
        data = {'payload': {'feat1': 0.1, 'feat2': 0.9}}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('score', response.data)
        self.assertIn('label', response.data)

    def test_health_check_endpoint(self):
        url = reverse('health_check')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {"status": "ok"}) 